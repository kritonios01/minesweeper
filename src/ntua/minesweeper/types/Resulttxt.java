package ntua.minesweeper.types;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Resulttxt extends Label{
    public static Resulttxt currentLabel;

    public Resulttxt(String s, Color c, double winWidth, double winHeight){
        super(s);
        this.setFont(Font.font("Verdana", 70));
        this.setTextFill(c);
        Platform.runLater(() -> { //this allows the label object to be fully rendered before its width is measured so we can get the correct result
            this.setLayoutX(winWidth/2 - this.getWidth()/2);
            this.setLayoutY(winHeight/2);
        });
    }

    public static void handleGameover(Pane p) {
        currentLabel = new Resulttxt("You Lost!", Color.RED, p.getWidth(), p.getHeight());
        p.getChildren().add(currentLabel);
        Grid.deactivateBlocks();
        Time.stoptimer();
    }

    public static void handleWin(Pane p) {
        currentLabel = new Resulttxt("You won!!", Color.GREEN, p.getWidth(), p.getHeight());
        p.getChildren().add(currentLabel);
        Grid.deactivateBlocks();
        Time.stoptimer();
    }

    public static void deleteLabel(){
        if(currentLabel != null) {
            Pane parent = (Pane)currentLabel.getParent();
            if(parent != null){
                parent.getChildren().remove(currentLabel);
            }
        }
    }
}