package ntua.minesweeper.types;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*public class Resulttxt extends Text{
    public Resulttxt(String s, Color c, Pane p){
        super(s);

        System.out.println(this.getwidth);
        this.setX(p.getWidth()/2);// - this.getLayoutBounds().getWidth()/2);
        this.setY(300);//p.getHeight()/2);
        this.setFont(Font.font("Verdana", 50));
        this.setFill(c);
    }
}*/

public class Resulttxt extends Label{
    public Resulttxt(String s, Color c, Pane p){
        super(s);

        System.out.println(this.getWidth());
        //this.setX(p.getWidth()/2);// - this.getLayoutBounds().getWidth()/2);
        //this.setY(300);//p.getHeight()/2);
        //this.setFont(Font.font("Verdana", 50));
        //this.setFill(c);
    }
}