package ntua.minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.*;

public class Controller {
    @FXML

    private TextArea txt;
    
    public void heliad(ActionEvent event){
        txt.setFont(Font.font("Menlo"));
        txt.setText("heliad");
        
    }

    public void pamprid(ActionEvent event){
        txt.setFont(Font.font("Menlo"));
        txt.setText("PAMPRID");
    }
}
