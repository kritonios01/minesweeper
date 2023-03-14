package ntua.minesweeper.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;



//prepei na ginei handle winning
public class Grid extends GridPane{
    private static Block[][] blocks;
    private final boolean[][] MINEPOS;
    private List<Integer> SUPERMINEPOS;
    private final int MINES;
    private final int gridLength;

    private final int supermine;
    private int supermineCount;

    private final AnchorPane parentWindow;

    public Grid(AnchorPane x, List<Integer> scenario) {
        super();
        if(scenario.get(0) == 1){
            this.gridLength = 9;
            this.setPadding(new Insets(18));
            this.setHgap(3);
            this.setVgap(3);
        }
        else{
            this.gridLength = 11;
            this.setPadding(new Insets(15));
            this.setHgap(2);
            this.setVgap(2);
        }
        this.MINES = scenario.get(1);
        //this.timeLeft = scenario.get(2);
        this.supermine = scenario.get(3);

        blocks = new Block[gridLength][gridLength];
        this.MINEPOS = new boolean[gridLength][gridLength];

        this.SUPERMINEPOS = new ArrayList<Integer>();
        this.supermineCount = 0;
        this.parentWindow = x;
        this.generateGrid();
    }

    private void generateGrid() {
        generateMinePositions();
        for (int i = 0; i < gridLength; i++) {
			for (int j = 0; j < gridLength; j++) {
				Block block = new Block(gridLength);
				final int row = i;
				final int column = j;
				block.setOnMouseClicked(event -> {
                    if(event.getButton() == MouseButton.PRIMARY){
                        handleLeftClick(block, row, column);
                    }
                    else if(event.getButton() == MouseButton.SECONDARY){
                        handleRightClick(block, row, column);
                    }
                });
				blocks[i][j] = block;
				this.add(block, i, j);
			}
		}
        if(supermine == 1){
            blocks[SUPERMINEPOS.get(0)][SUPERMINEPOS.get(1)].setSupermine();
            System.out.println(SUPERMINEPOS.get(0).intValue() + " " + SUPERMINEPOS.get(1).intValue());
        }

    }

//HANDLE WINNING

    //auto prepei na ginei me mia parametro sti klasi block kai oxi me array
    private void generateMinePositions() {
        Random random = new Random();
        for (int i = 0; i < MINES; i++) {
            int row = random.nextInt(gridLength);
            int column = random.nextInt(gridLength);
            if (MINEPOS[row][column]) {
                i--;
            }
            else{
                if(i == 0 && supermine == 1){
                    //SUPERMINEPOS.set(0, row);
                    SUPERMINEPOS.add(row);
                    //SUPERMINEPOS.set(1, column);
                    SUPERMINEPOS.add(column);
                }
                MINEPOS[row][column] = true;
            }
        }
    }

    private void handleLeftClick(Block block, int row, int column) {
        if(!block.getflag()) {
            if (MINEPOS[row][column] == true) {
                Image mineImage = new Image("media/mine.png", 30, 0, true, true);
                block.setGraphic(new ImageView(mineImage));

                Resulttxt.handleGameover(parentWindow);
            }
            else{
                supermineCount++;
                int adjacentMines = countAdjacentMines(row, column);
                if (adjacentMines == 0) {
                    block.setDisable(true);
                    adjacentReveal(row, column);
                }
                else{
                    block.setFont(Font.font("Verdana", 20));
                    block.setText(Integer.toString(adjacentMines));
                    block.setDisable(true);
                }
            }
        }
    }

    private void handleRightClick(Block block, int row, int column) {
        if(!block.getflag()) {
            Image flagImage = new Image("media/flag.png", 30, 0, true, true);
            block.setGraphic(new ImageView(flagImage));
            block.setflag();
            if(supermine == 1 && supermineCount <= 4 && SUPERMINEPOS.get(0) == row && SUPERMINEPOS.get(1) == column) {
                handleSupermine(row, column);
            }
        }
        else {
            block.setGraphic(null);
            block.unsetflag();
        }
    }

    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = row + i;
                int y = column + j;
                if (x >= 0 && x < gridLength && y >= 0 && y < gridLength && MINEPOS[x][y] == true) {
                    count++;
                }
            }
        }
        return count;
    }

    private void adjacentReveal(int row, int column) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = row + i;
                int y = column + j;
                if (x >= 0 && x < gridLength && y >= 0 && y < gridLength && !blocks[x][y].isDisabled() && !MINEPOS[x][y]) {
                    blocks[x][y].setDisable(true);
                    int adjacentMines = countAdjacentMines(x, y);
                    if (adjacentMines == 0) {
                        adjacentReveal(x, y);
                    }
                    else{
                        blocks[x][y].setFont(Font.font("Verdana", 20));
                        blocks[x][y].setText(Integer.toString(adjacentMines));
                    }
                }
            }
        }
    }

    public static void deleteBlocks() {
        if(blocks != null){
            GridPane parent = (GridPane)blocks[0][0].getParent();
            if(parent != null){
                for(int i = 0; i < blocks.length; i++) {
                    for(int j = 0; j < blocks.length; j++) {
                        //if(blocks[i][j] != null){
                            parent.getChildren().remove(blocks[i][j]);
                        //}
                    }
                }
            }

        }

    }

    public static void deactivateBlocks() {
        //if(blocks != null){
            //GridPane parent = (GridPane)blocks[0][0].getParent();
            //if(parent != null){
                for(int i = 0; i < blocks.length; i++) {
                    for(int j = 0; j < blocks.length; j++) {
                        //if(blocks[i][j] != null){
                            blocks[i][j].setOnMouseClicked(null);
                        //}
                    }
                }
            //}

        //}

    }

    private void handleSupermine(int row, int column) {
        for (int i = 0; i < 11; i++) {
            if(i != row && i != column){
                Block block = blocks[i][column];
                if(block.getflag()){
                    block.unsetflag();
                }
                if(MINEPOS[i][column]){
                    Image supermineImage = new Image("media/supermine.png", 0, 30, true, true);
                    block.setGraphic(new ImageView(supermineImage));
                    block.setOnMouseClicked(null);
                }
                else{
                    int adjacentMines = countAdjacentMines(i, column);
                    if (adjacentMines == 0) {
                        block.setDisable(true);
                    }
                    else{
                        block.setFont(Font.font("Verdana", 20));
                        block.setText(Integer.toString(adjacentMines));
                        block.setDisable(true);
                    }
                }
                block = blocks[row][i];
                if(block.getflag()){
                    block.unsetflag();
                }
                if(MINEPOS[row][i]){
                    Image supermineImage = new Image("media/supermine.png", 0, 30, true, true);
                    block.setGraphic(new ImageView(supermineImage));
                    block.setOnMouseClicked(null);
                }
                else{
                    int adjacentMines = countAdjacentMines(row, i);
                    if (adjacentMines == 0) {
                        block.setDisable(true);
                    }
                    else{
                        block.setFont(Font.font("Verdana", 20));
                        block.setText(Integer.toString(adjacentMines));
                        block.setDisable(true);
                    }
                }
            }
        }
    }
}
