package ntua.minesweeper.types;

import java.util.List;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Grid extends GridPane{
    //ola edw ginontai gia 11x11, prepei na ginei kai gia to easy
    private final Block[][] blocks;
    private final boolean[][] MINEPOS;
    private final int MINES;
    private final AnchorPane parentWindow;

    public Grid(AnchorPane x, List<Integer> scenario) {
        super();
        this.setPadding(new Insets(15));
        this.setHgap(2);
        this.setVgap(2);
        if(scenario.get(0) == 1) {
            this.blocks = new Block[9][9];
            this.MINEPOS = new boolean[9][9];
        }
        else
        this.blocks = new Block[11][11];
        this.MINEPOS = new boolean[11][11];
        this.MINES = 10;
        this.generateGrid();
        this.parentWindow = x;
    }

    private void generateGrid() {
        generateMinePositions();
        for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				Block block = new Block();
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
        Random random = new Random();
        for (int i = 0; i < MINES; i++) {
            int row = random.nextInt(11);
            int column = random.nextInt(11);
            if (MINEPOS[row][column]) {
                i--;
            }
            else{
                MINEPOS[row][column] = true;
            }
        }
    }

    private void handleLeftClick(Block block, int row, int column) {
        if(!block.getflag()) {
            if (MINEPOS[row][column] == true) {
                Image mineImage = new Image("media/mine.png", 30, 0, true, true);
                block.setGraphic(new ImageView(mineImage));
                //handle gameover
                //handleGameover();
                ///Resulttxt losetxt = new Resulttxt("You Lose!", Color.RED, parentWindow);
                ///parentWindow.getChildren().add(losetxt);

            }
            else{
                int adjacentMines = countAdjacentMines(row, column);
                if (adjacentMines == 0) {
                    block.setDisable(true);
                    adjacentReveal(row, column);
                }
                else{
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
                if (x >= 0 && x < 11 && y >= 0 && y < 11 && MINEPOS[x][y] == true) {
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
                if (x >= 0 && x < 11 && y >= 0 && y < 11 && !blocks[x][y].isDisabled() && !MINEPOS[x][y]) {
                    blocks[x][y].setDisable(true);
                    int adjacentMines = countAdjacentMines(x, y);
                    if (adjacentMines == 0) {
                        adjacentReveal(x, y);
                    }
                    else{
                        blocks[x][y].setText(Integer.toString(adjacentMines));
                    }
                }
            }
        }
    }

}
