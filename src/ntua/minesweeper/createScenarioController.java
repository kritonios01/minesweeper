package ntua.minesweeper;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.FileWriter;

//A controller for the Create Scenario Window. input values are used to create the appropriate txt file
public class createScenarioController implements Initializable{
    @FXML private TextField scenarioTextField;
    @FXML private ChoiceBox<String> difficultyChoiceBox;
    @FXML private TextField minesTextField;
    @FXML private ChoiceBox<String> supermineChoiceBox;
    @FXML private TextField timeTextField;
    @FXML private Button createScenarioButton;

    private String[] difficulty_list = {"1", "2"};
    private String[] supermine_list = {"0", "1"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficultyChoiceBox.getItems().addAll(difficulty_list);
        supermineChoiceBox.getItems().addAll(supermine_list);
    }

    public void createScenario(ActionEvent event) throws Exception{
        try{
            if (scenarioTextField.getText().isEmpty()
            || minesTextField.getText().isEmpty()
            || timeTextField.getText().isEmpty()
            || difficultyChoiceBox.getValue() == null
            || supermineChoiceBox.getValue() == null) {
                throw new NullPointerException();
            }

            String filename = new String(scenarioTextField.getText());
            String mines = new String(minesTextField.getText());
            String time = new String(timeTextField.getText());
            String difficulty = new String(difficultyChoiceBox.getValue());
            String supermine = new String(supermineChoiceBox.getValue());

            FileWriter output = new FileWriter("src/medialab/" + filename + ".txt");
            output.write(difficulty + "\n" + mines + "\n" + time + "\n" + supermine);
            output.close();

            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();//close window

        }
        catch(NullPointerException e){

        }
    }

}
