package ntua.minesweeper;

import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import ntua.minesweeper.types.Grid;


public class Main extends Application{
	public static void main (String[] args) {
		launch(args); //calls the start method, launch comes from Application class and it's static
	}

	@Override //overrride the default start method from javafx application class
	public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("MediaLab Minesweeper"); //give title to window
        mainStage.setResizable(false); //make it not resizable

        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml")); //Load top level nodes (anchor pane with menubar)
        Scene mainScene = new Scene(root); //pass root node to the main scene

        //Create a new GridPane with blocks(buttons)
        //[this step was done manually (without SceneBuilder) because it is much easier and more configurable that way]

        //o,ti kanw edw einai gia to 11x11 grid, prepei na ginei kai to 9x9
        /*GridPane minesGrid = new GridPane();
        minesGrid.setPadding(new Insets(15));
        minesGrid.setHgap(2);
        minesGrid.setVgap(2);
        minesGrid.setLayoutX(0);
        minesGrid.setLayoutY(110);*/
        //anti autwn panw prepei na mpei kati tou styl Grid minesGrid = new Grid
        Grid minesGrid = new Grid();
        minesGrid.setLayoutX(0);
        minesGrid.setLayoutY(110);



        //generateGrid(minesGrid);
        /*for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				Button button = new Button();
				button.setPrefSize(50, 50);
				final int row = i;
				final int column = j;
				button.setOnAction(event -> handleButtonClick(button, i, j));
				//buttons[i][j] = button;
				minesGrid.add(button, i, j);
			}
		}*/




        AnchorPane rootPane = (AnchorPane)mainScene.lookup("#myPane"); //get the mainpane (anchorpane) from fxml
        rootPane.getChildren().add(minesGrid); //add the minesgrid to it

        mainStage.setScene(mainScene); //add scene to the stage
		mainStage.show(); //always have this at the end so that the window appears
	}

    /*private Button[][] blocks = new Button[11][11];
    private boolean[][] minePositions = new boolean[11][11];

    private void generateGrid(GridPane grid) {
        generateMinePositions();
        for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				Button button = new Button();
				button.setPrefSize(50, 50);
				final int row = i;
				final int column = j;
				button.setOnAction(event -> handleButtonClick(button, row, column));
				blocks[i][j] = button;
				grid.add(button, i, j);
			}
		}
    }

    private void handleButtonClick(Button block, int row, int column) {
        if (minePositions[row][column] == true) {
            Image mineImage = new Image("media/mine.png", 30, 0, true, true);
            block.setGraphic(new ImageView(mineImage));
            //handle gameover
            //Label lose = new Label("You lose");
            //AnchorPane rootPane = (AnchorPane)mainScene.lookup("#myPane");

            //rootPane.getChildren().add(minesGrid);
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

    private void adjacentReveal(int row, int column) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = row + i;
                int y = column + j;
                if (x >= 0 && x < 11 && y >= 0 && y < 11 && !blocks[x][y].isDisabled() && !minePositions[x][y]) {
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


    private static final int MINES = 10;
    private void generateMinePositions() {
        Random random = new Random();

        for (int i = 0; i < MINES; i++) {
            int row = random.nextInt(11);
            int column = random.nextInt(11);
            if (minePositions[row][column]) {
                i--;
            }
            else{
                minePositions[row][column] = true;
            }
        }
    }

    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = row + i;
                int y = column + j;
                if (x >= 0 && x < 11 && y >= 0 && y < 11 && minePositions[x][y] == true) {
                    count++;
                }
            }
        }
        return count;
    }*/
}
