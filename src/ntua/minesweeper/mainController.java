package ntua.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.stage.Stage;
import ntua.minesweeper.types.Grid;
import ntua.minesweeper.types.Resulttxt;
import ntua.minesweeper.types.Scenario;
import ntua.minesweeper.types.Time;

public class mainController{
    @FXML AnchorPane myPane;
    @FXML Label timeLeftLabel;

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
        //na dw giati xreiazetai ayto to try-catch block
        try{
            Scenario id = Scenario.getScenario(Scenario.filename);
            Time timer = new Time(id.getScenarioList().get(2), myPane, timeLeftLabel);
            Grid minesGrid = new Grid(myPane, id.getScenarioList());
            minesGrid.setLayoutX(0);
            minesGrid.setLayoutY(110);
            myPane.getChildren().add(minesGrid);
        }
        catch(NullPointerException e){
            System.out.println("You must load a scenario first");
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
}
