package ntua.minesweeper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.FileNotFoundException;
import java.io.IOException;
import ntua.minesweeper.exceptions.InvalidDescriptionException;
import ntua.minesweeper.exceptions.InvalidValueException;
import ntua.minesweeper.exceptions.ScenarioInstantiationException;
import ntua.minesweeper.types.Scenario;


import ntua.minesweeper.types.Scenario;

public class loadScenarioController {
    @FXML private TextField scenarioTextField;
    @FXML private Button loadScenarioBox;
    @FXML private AnchorPane parent;
    private static Label currentError;

    public void loadScenario(ActionEvent event) throws Exception{
        String filename = new String(scenarioTextField.getText());
        Scenario.filename = filename;
        try{
            Scenario id = Scenario.getScenario(filename);
            //System.out.println("All good!");
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }
        catch(ScenarioInstantiationException e){
            if(currentError != null){
                currentError.setVisible(false);
            }
            Label error = new Label(e.getError());
            error.setFont(Font.font("Verdana", 20));
            error.setTextFill(Color.RED);
            error.setLayoutX(35);
            error.setLayoutY(105);
            currentError = error;
            parent.getChildren().add(error);
        }
        catch(Exception e){
            System.out.println("Error");
        }
        //((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
