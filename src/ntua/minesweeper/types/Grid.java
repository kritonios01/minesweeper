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

//A grid is instantiated with all parameters from the scenario last loaded
public class Grid extends GridPane {
    private static Block[][] blocks; //blocks array stores all the blocks instantiated
    private final boolean[][] MINEPOS; //minepos array stores all the positions of mines in the grid
    private List<Integer> SUPERMINEPOS; //superminepos stores the row an column of the supermine
    private final int MINES; //total ammount of mines
    private final int TIME; //total time
    private final int gridLength; //length (and height as well) in terms of blocks for the grid

    private final int supermine; //0 for no supermine, 1 for supermine
    private int supermineCount; //counts successful left clicks
    private int flagCount; //counts total flags in the grid
    private Label flagLabel; //reference to the flags label in the main pane
    private int openBlocks; //amount of blocks open

    private final AnchorPane parentWindow; //the main pane
    private static Grid currentGrid; //everytime a new grid is instantiated, currentgrid references it

    public Grid(AnchorPane x, List<Integer> scenario, Label flags) {
        super();
        if(scenario.get(0) == 1){ //instantiates the grid according the difficulty
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
        this.openBlocks = 0;
        this.parentWindow = x;
        currentGrid = this;
        this.generateGrid();
    }

    private void generateGrid() { //with this function we add mines to the appropriate positions and add mouseclick events to each button
        generateMinePositions();
        for (int i = 0; i < gridLength; i++) {
            for (int j = 0; j < gridLength; j++) {
                Block block = new Block(gridLength);
                final int row = i;
                final int column = j;
                block.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        handleLeftClick(block, row, column);
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        handleRightClick(block, row, column);
                    }
                });
                blocks[i][j] = block;
                this.add(block, i, j);
            }
        }
    }

    private void generateMinePositions() {//this function generates mines and a supermine if this option is on
        Random random = new Random();
        try {
            FileWriter minesdata = new FileWriter("src/medialab/mines.txt");

            for (int i = 0; i < MINES; i++) {
                int row = random.nextInt(gridLength);
                int column = random.nextInt(gridLength);
                if (MINEPOS[row][column]) {
                    i--;
                } else {
                    MINEPOS[row][column] = true;
                    minesdata.write(Integer.toString(row));
                    minesdata.write(",");
                    minesdata.write(Integer.toString(column));
                    minesdata.write(",");

                    if (i == 0 && supermine == 1) {
                        SUPERMINEPOS.add(row);
                        SUPERMINEPOS.add(column);
                        minesdata.write("1");
                        minesdata.write("\n");
                    } else {
                        minesdata.write("0");
                        minesdata.write("\n");
                    }
                }
            }
            minesdata.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private void handleLeftClick(Block block, int row, int column) { //the left-click scenario
        if (!block.getflag()) {
            if (MINEPOS[row][column] == true) { // if they click on a mine they lose
                if (gridLength == 9) {
                    Image mineImage = new Image("media/mine.png", 40, 0, true, true);
                    block.setGraphic(new ImageView(mineImage));
                } else {
                    Image mineImage = new Image("media/mine.png", 23, 0, true, true);
                    block.setGraphic(new ImageView(mineImage));
                }
                Rounds.setStats(MINES, supermineCount, TIME, 0);
                Resulttxt.handleGameover(parentWindow);
            } else { //if they don't click on a mine adjacent mines are computed
                supermineCount++;
                openBlocks++;
                int adjacentMines = countAdjacentMines(row, column);
                if (adjacentMines == 0) { //if there are no mines around we do adjacent reveal
                    block.setDisable(true);
                    adjacentReveal(row, column);
                } else { // if there are mines around we show their number
                    block.setFont(Font.font("Verdana", 20));
                    block.setText(Integer.toString(adjacentMines));
                    block.setDisable(true);
                }
                if (gridLength * gridLength - MINES == openBlocks) {
                    Rounds.setStats(MINES, supermineCount, TIME, 1);
                    Resulttxt.handleWin(parentWindow);
                }
            }
        }
    }

    private void handleRightClick(Block block, int row, int column) { // the right click scenario
        if (!block.getflag()) { //if there is no flag
            if (flagCount < MINES) { //and if there are less flags than mines
                if (gridLength == 9) { //we show a flag according to block size
                    Image flagImage = new Image("media/flag.png", 40, 0, true, true);
                    block.setGraphic(new ImageView(flagImage));
                } else {
                    Image flagImage = new Image("media/flag.png", 22, 0, true, true);
                    block.setGraphic(new ImageView(flagImage));
                }
                block.setflag();
                flagCount++;
                if (supermine == 1 && supermineCount <= 4 && SUPERMINEPOS.get(0) == row
                        && SUPERMINEPOS.get(1) == column) { //we handle the case of having a supermine below
                    handleSupermine(row, column);
                }
            }
        } else { //if the block is flagged we unflag it
            block.setGraphic(null);
            block.unsetflag();
            flagCount--;
        }
        flagLabel.setText(Integer.toString(flagCount));
    }

    private int countAdjacentMines(int row, int column) {//simple algorithm which counts adjacent mines
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

    private void adjacentReveal(int row, int column) {//simple algorithm to handle adjacent reveal
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = row + i;
                int y = column + j;
                if (x >= 0 && x < gridLength && y >= 0 && y < gridLength && !blocks[x][y].isDisabled()
                 && !blocks[x][y].getflag() && !MINEPOS[x][y]) {
                    blocks[x][y].setDisable(true);
                    openBlocks++;
                    int adjacentMines = countAdjacentMines(x, y);
                    if (adjacentMines == 0) {
                        adjacentReveal(x, y);
                    } else {
                        blocks[x][y].setFont(Font.font("Verdana", 20));
                        blocks[x][y].setText(Integer.toString(adjacentMines));
                    }
                }
            }
        }
    }

    public static void deleteBlocks() { //a method for deleting preexisting blocks when a new game starts
        if (blocks != null) {
            GridPane parent = (GridPane) blocks[0][0].getParent();
            if (parent != null) {
                for (int i = 0; i < blocks.length; i++) {
                    for (int j = 0; j < blocks.length; j++) {
                        parent.getChildren().remove(blocks[i][j]);
                    }
                }
            }
        }
    }

    public static void deactivateBlocks() { //a method for deactivating left and right click functionalities on a block when the user wins or loses
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                blocks[i][j].setOnMouseClicked(null);
            }
        }
    }

    private void handleSupermine(int row, int column) { //a method for handling supermines
        for (int i = 0; i < 16; i++) {
            Block block = blocks[i][column]; //first we check each block in the same column
            if(block.getflag()){
                block.unsetflag();
                block.setGraphic(null);
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
            block = blocks[row][i]; //then we check each block in the same row
            if(block.getflag()){
                block.unsetflag();
                block.setGraphic(null);
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
        }
    }

    public int getTotalMines() {
        return MINES;
    }

    public static void solution(AnchorPane parent) { //a method to handle solution button of the main pane
        if (blocks != null) {
            if (parent != null) { //given that there is a grid and blocks we import mine position from the file and for each position we show the mines
                try {
                    BufferedReader file = new BufferedReader(new FileReader("src/medialab/mines.txt"));
                    String line;
                    while ((line = file.readLine()) != null) {
                        String[] mine = line.split(",");
                        int x = Integer.parseInt(mine[0]);
                        int y = Integer.parseInt(mine[1]);

                        if (currentGrid.gridLength == 9) {
                            Image mineImage = new Image("media/mine.png", 40, 0, true, true);
                            blocks[x][y].setGraphic(new ImageView(mineImage));
                        } else {
                            Image mineImage = new Image("media/mine.png", 23, 0, true, true);
                            blocks[x][y].setGraphic(new ImageView(mineImage));
                        }
                    }
                    Rounds.setStats(currentGrid.MINES, currentGrid.supermineCount, currentGrid.TIME, 0);
                    Resulttxt.handleGameover(parent);
                    file.close();
                } catch (IOException e) {
                    System.out.println("You need to start a game first");
                }
            }
        }
    }

}
