package ntua.minesweeper;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class exitController {
    private Stage parentWindow;

    public exitController(Stage x) {
        this.parentWindow = x;
    }

    public void handleYes(ActionEvent e) throws Exception {
        ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
        parentWindow.close();
    }

    public void handleNo(ActionEvent e){
        ((Stage)((Node)e.getSource()).getScene().getWindow()).close();
    }
}
