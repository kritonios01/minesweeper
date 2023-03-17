package ntua.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ntua.minesweeper.exceptions.ScenarioInstantiationException;
import ntua.minesweeper.types.Grid;
import ntua.minesweeper.types.Resulttxt;
import ntua.minesweeper.types.Scenario;
import ntua.minesweeper.types.Time;

//The main controller class which handles all the buttons of the menu bar
public class mainController{
    @FXML AnchorPane myPane;
    @FXML Label timeLeftLabel;
    @FXML Label totalMinesLabel;
    @FXML Label flagsLabel;

    public void createScenario() throws Exception{ //this method is used to open the CreateScenario window
        Stage createScenarioStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("createScenarioScene.fxml"));
        Scene createScenarioScene = new Scene(root);

        createScenarioStage.setTitle("Create Scenario...");
        createScenarioStage.setResizable(false);
        createScenarioStage.setScene(createScenarioScene);
		createScenarioStage.show();
    }

    public void loadScenario() throws Exception { //this method is used to open the LoadScenario window
        Stage loadScenarioStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loadScenarioScene.fxml"));
        Scene loadScenarioScene = new Scene(root);

        loadScenarioStage.setTitle("Load Scenario...");
        loadScenarioStage.setResizable(false);
        loadScenarioStage.setScene(loadScenarioScene);
		loadScenarioStage.show();
    }

    public void start(ActionEvent event) throws Exception { //this method is used to start the game
        Grid.deleteBlocks(); //we delete exisitng blocks if there are any
        Resulttxt.deleteLabel(); //we delete the win or lose label if there is one
        try{
            Time.stoptimer(); //we stop the timer when a new game begins on top of a running game
        }
        catch(NullPointerException e){

        }
        try{
            Scenario id = Scenario.getScenario(Scenario.filename); //the last-selected scenario is loaded
            Time timer = new Time(id.getScenarioList().get(2), myPane, timeLeftLabel); //a new timer is set
            Grid minesGrid = new Grid(myPane, id.getScenarioList(), flagsLabel); //a new grid is instantiated
            minesGrid.setLayoutX(0);
            minesGrid.setLayoutY(110);
            myPane.getChildren().add(minesGrid);
            totalMinesLabel.setText(Integer.toString(minesGrid.getTotalMines()));
        }
        catch(ScenarioInstantiationException e){ //this exception occurs when the user starts the game without having loaded a scenario first
            Stage errorStage = new Stage();
            Label error = new Label("You must load a scenario first!!!");
            error.setFont(Font.font("Verdana", 20));
            error.setTextFill(Color.RED);
            error.setAlignment(Pos.CENTER);
            Scene errorScene = new Scene(error);
            errorStage.setTitle("Error...");
            errorStage.setResizable(false);
            errorStage.setScene(errorScene);
            errorStage.setWidth(400);
            errorStage.setHeight(200);
		    errorStage.show();
        }
    }

    public void exit() throws Exception{ //this method is used to open the exit-confirmation window
        Stage exitStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exitScene.fxml"));
        //with the setcontrollerfactory we can manipulate the constructor of the exitcontroller class
        loader.setControllerFactory(c -> {
                return new exitController((Stage)myPane.getScene().getWindow());
            });
        Parent root = loader.load();
        Scene exitScene = new Scene(root);

        exitStage.setTitle("Load Scenario...");
        exitStage.setResizable(false);
        exitStage.setScene(exitScene);
		exitStage.show();
    }

    public void rounds() throws Exception { //this method is used to open the rounds window
        Stage roundsStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("roundsScene.fxml"));
        Scene roundsScene = new Scene(root);

        roundsStage.setTitle("Rounds");
        roundsStage.setResizable(false);
        roundsStage.setScene(roundsScene);
		roundsStage.show();
    }

    public void solution() throws Exception { //this method calls the static solution method solution which ends the game
        Grid.solution(myPane);
    }
}
