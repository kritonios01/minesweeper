package ntua.minesweeper;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

//A controller for the exit window
public class exitController {
    private Stage parentWindow;

    public exitController(Stage x) {
        this.parentWindow = x;
    }

    public void handleYes(ActionEvent e) throws Exception { //if yes is pressed the app closes
        ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
        parentWindow.close();
    }

    public void handleNo(ActionEvent e) { // if no is pressed only the exit window closes
        ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
    }
}
