package ntua.minesweeper;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainController{


    public void createScenario() throws Exception{
        Stage createScenarioStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("createScenarioScene.fxml"));
        Scene createScenarioScene = new Scene(root);

        createScenarioStage.setTitle("Create Scenario...");
        createScenarioStage.setResizable(false);
        createScenarioStage.setScene(createScenarioScene);
		createScenarioStage.show();
    }

    public void loadScenario() throws Exception{
        Stage loadScenarioStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loadScenarioScene.fxml"));
        Scene loadScenarioScene = new Scene(root);

        loadScenarioStage.setTitle("Load Scenario...");
        loadScenarioStage.setResizable(false);
        loadScenarioStage.setScene(loadScenarioScene);
		loadScenarioStage.show();
    }

    //left and right click events are handled in the grid class

}
