package ntua.minesweeper.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;



//prepei na ginei handle winning
public class Grid2 extends GridPane{
    private final List<List<Integer>> MINEPOS = new ArrayList<>();
    private final List<Integer> SUPERMINEPOS = new ArrayList<>();



    private static Block[][] blocks;
    private final List<List<Integer>> he;
    //private final boolean[][] MINEPOS;
    //private final boolean SUPERMINEPOS;
    private final int MINES;
    private final int gridLength;

    //private int timeLeft;
    private final int supermine;

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
        this.generateGrid();

        this.parentWindow = x;
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
                        handleRightClick(block);
                    }
                });
				blocks[i][j] = block;
				this.add(block, i, j);
			}
		}
    }

//HANDLE WINNING

    //auto prepei na ginei me mia parametro sti klasi block kai oxi me array
    private void generateMinePositions() {
        Random seed = new Random();
        for (int i = 0; i < MINES; i++) {
            int row = random.nextInt(gridLength);
            int column = random.nextInt(gridLength);
            if (MINEPOS.get(row).get(column) != null) {
                i--;
            }
            else{
                //if(i == 0 && supermine == 1){

                //}
                MINEPOS.get(row).get(column) = true;
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

    private void handleRightClick(Block block) {
        if(!block.getflag()) {
            Image flagImage = new Image("media/flag.png", 30, 0, true, true);
            block.setGraphic(new ImageView(flagImage));
            block.setflag();
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

    public static void deleteBlocks(){
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

    public static void deactivateBlocks(){
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
}
