package ntua.minesweeper;

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

import ntua.minesweeper.exceptions.ScenarioInstantiationException;
import ntua.minesweeper.types.Scenario;

//A controller for the Load Scenario Controller
//We check for each kind of problem there can be with the file and appropriate error message is thrown to the user
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
    }
}
