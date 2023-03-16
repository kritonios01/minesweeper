package ntua.minesweeper.types;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
    private final int TIME;
    private final int gridLength;

    private final int supermine;
    private int supermineCount;
    private int flagCount;
    private Label flagLabel;
    private Label timeLeftLabel;
    private int openBlocks;

    private final AnchorPane parentWindow;
    private static Grid currentGrid;

    public Grid(AnchorPane x, List<Integer> scenario, Label flags, Label timeLeft) {
        super();
        if(scenario.get(0) == 1){
            this.gridLength = 9;
            this.setPadding(new Insets(23));
            this.setHgap(3);
            this.setVgap(3);
        }
        else{
            this.gridLength = 16;
            this.setPadding(new Insets(14));
            this.setHgap(2);
            this.setVgap(2);
        }
        this.MINES = scenario.get(1);
        this.TIME = scenario.get(2);
        this.supermine = scenario.get(3);

        blocks = new Block[gridLength][gridLength];
        this.MINEPOS = new boolean[gridLength][gridLength];

        this.SUPERMINEPOS = new ArrayList<Integer>();
        this.supermineCount = 0;
        this.flagCount = 0;
        this.flagLabel = flags;
        this.timeLeftLabel = timeLeft;
        this.openBlocks = 0;
        this.parentWindow = x;
        currentGrid = this;
        this.generateGrid();
    }

    private void generateGrid() {
        //Resulttxt.handleGameover(parentWindow);
        //Resulttxt.handleWin(parentWindow);

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
        try{
            FileWriter minesdata = new FileWriter("src/medialab/mines.txt");

            for (int i = 0; i < MINES; i++) {
                int row = random.nextInt(gridLength);
                int column = random.nextInt(gridLength);
                if (MINEPOS[row][column]) {
                    i--;
                }
                else{
                    MINEPOS[row][column] = true;
                    minesdata.write(Integer.toString(row));
                    minesdata.write(",");
                    minesdata.write(Integer.toString(column));
                    minesdata.write(",");

                    if(i == 0 && supermine == 1){
                        SUPERMINEPOS.add(row);
                        SUPERMINEPOS.add(column);
                        minesdata.write("1");
                        minesdata.write("\n");
                    }
                    else{
                        minesdata.write("0");
                        minesdata.write("\n");
                    }
                }
            }
            minesdata.close();
        }
        catch(IOException e){
            System.out.println("IOException");
        }


    }

    private void handleLeftClick(Block block, int row, int column) {
        if(!block.getflag()) {
            if (MINEPOS[row][column] == true) {
                if(gridLength == 9){
                    Image mineImage = new Image("media/mine.png", 40, 0, true, true);
                    block.setGraphic(new ImageView(mineImage));
                }
                else{
                    Image mineImage = new Image("media/mine.png", 23, 0, true, true);
                    block.setGraphic(new ImageView(mineImage));
                }
                Rounds.setStats(MINES, supermineCount, TIME, 0);
                Resulttxt.handleGameover(parentWindow);
                //Rounds.printstats();
            }
            else{
                supermineCount++;
                openBlocks++;
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
                //System.out.println(gridLength*gridLength-MINES +" "+openBlocks);
                if(gridLength*gridLength - MINES == openBlocks){
                    Rounds.setStats(MINES, supermineCount, TIME, 1);
                    Resulttxt.handleWin(parentWindow);
                }
            }
        }
    }

    private void handleRightClick(Block block, int row, int column) {
            if(!block.getflag()) {
                if(flagCount < MINES){
                    if(gridLength == 9){
                        Image flagImage = new Image("media/flag.png", 40, 0, true, true);
                        block.setGraphic(new ImageView(flagImage));
                    }
                    else{
                        Image flagImage = new Image("media/flag.png", 22, 0, true, true);
                        block.setGraphic(new ImageView(flagImage));
                    }
                    block.setflag();
                    flagCount++;
                    if(supermine == 1 && supermineCount <= 4 && SUPERMINEPOS.get(0) == row && SUPERMINEPOS.get(1) == column) {
                        handleSupermine(row, column);
                    }
                }
            }
            else {
                block.setGraphic(null);
                block.unsetflag();
                flagCount--;
            }
            flagLabel.setText(Integer.toString(flagCount));
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
                if (x >= 0 && x < gridLength && y >= 0 && y < gridLength && !blocks[x][y].isDisabled() && !blocks[x][y].getflag() && !MINEPOS[x][y]) {
                    blocks[x][y].setDisable(true);
                    openBlocks++;
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
        for (int i = 0; i < 16; i++) {
            //if(i != row && i != column){
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
                        openBlocks++;
                    }
                    else{
                        openBlocks++;
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
                        openBlocks++;
                    }
                    else{
                        openBlocks++;
                        block.setFont(Font.font("Verdana", 20));
                        block.setText(Integer.toString(adjacentMines));
                        block.setDisable(true);
                    }
                }
            //}
        }
    }

    public int getTotalMines(){
        return MINES;
    }

    public static void solution(AnchorPane parent) {
        if(blocks != null){
            //GridPane grid = (GridPane)blocks[0][0].getParent();
            if(parent != null){
                try{
                    BufferedReader file = new BufferedReader(new FileReader("src/medialab/mines.txt"));
                    String line;
                    while ((line = file.readLine()) != null) {
                        String[] mine = line.split(",");
                        int x = Integer.parseInt(mine[0]);
                        int y = Integer.parseInt(mine[1]);

                        if(currentGrid.gridLength == 9){
                            Image mineImage = new Image("media/mine.png", 40, 0, true, true);
                            blocks[x][y].setGraphic(new ImageView(mineImage));
                        }
                        else{
                            Image mineImage = new Image("media/mine.png", 23, 0, true, true);
                            blocks[x][y].setGraphic(new ImageView(mineImage));
                        }
                    }
                    Rounds.setStats(currentGrid.MINES, currentGrid.supermineCount, currentGrid.TIME, 0);
                    Resulttxt.handleGameover(parent);
                    file.close();
                }
                catch(IOException e){
                    System.out.println("You need to start a game first");
                }
            }
        }
    }


}
