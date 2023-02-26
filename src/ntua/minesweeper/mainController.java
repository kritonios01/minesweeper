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
}
