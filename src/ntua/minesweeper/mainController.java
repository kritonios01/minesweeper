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

public class mainController{
    @FXML AnchorPane myPane;
    @FXML Label timeLeftLabel;
    @FXML Label totalMinesLabel;
    @FXML Label flagsLabel;

    public void createScenario() throws Exception{
        Stage createScenarioStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("createScenarioScene.fxml"));
        Scene createScenarioScene = new Scene(root);

        createScenarioStage.setTitle("Create Scenario...");
        createScenarioStage.setResizable(false);
        createScenarioStage.setScene(createScenarioScene);
		createScenarioStage.show();
    }

    public void loadScenario() throws Exception {
        Stage loadScenarioStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loadScenarioScene.fxml"));
        Scene loadScenarioScene = new Scene(root);

        loadScenarioStage.setTitle("Load Scenario...");
        loadScenarioStage.setResizable(false);
        loadScenarioStage.setScene(loadScenarioScene);
		loadScenarioStage.show();
    }

    public void start(ActionEvent event) throws Exception {
        Grid.deleteBlocks();
        Resulttxt.deleteLabel();
        try{
            Time.stoptimer();
        }
        catch(NullPointerException e){

        }
        try{
            Scenario id = Scenario.getScenario(Scenario.filename);
            Time timer = new Time(id.getScenarioList().get(2), myPane, timeLeftLabel);
            Grid minesGrid = new Grid(myPane, id.getScenarioList(), flagsLabel);
            minesGrid.setLayoutX(0);
            minesGrid.setLayoutY(110);
            myPane.getChildren().add(minesGrid);
            totalMinesLabel.setText(Integer.toString(minesGrid.getTotalMines()));
        }
        catch(ScenarioInstantiationException e){
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

    public void exit() throws Exception{
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

    public void rounds() throws Exception {
        Stage roundsStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("roundsScene.fxml"));
        Scene roundsScene = new Scene(root);

        roundsStage.setTitle("Rounds");
        roundsStage.setResizable(false);
        roundsStage.setScene(roundsScene);
		roundsStage.show();
    }

    public void solution() throws Exception {
        Grid.solution(myPane);
    }
}
