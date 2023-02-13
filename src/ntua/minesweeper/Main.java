package ntua.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application{
	//Button button; //create the button which will appear in the window
	
	public static void main (String[] args) {
		launch(args); //calls the start method, launch comes from Application class and it's static
	}
	
	@Override
	public void start(Stage stage) throws Exception{ //overrride the default start method from javafx
        //Group root = new Group(); //create root node to arrange components in scene
        //Scene scene1 = new Scene(root, Color.BLACK); //add root node to the scene

        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Scene scene2 = new Scene(root);

        //Text t = new Text();
        //t.setText("fds");
        //Image icon = new Image("swell.png"); //import and use image for title of the window, searches for image in src folder by default
        //stage.getIcons().add(icon);

        stage.setTitle("First JavaFX App");

        //stage.setWidth(1000);
        //stage.setHeight(500);
        stage.setResizable(true); //makes window not resizable
        //stage.setX(200); //sets where the window will appear
        //stage.setY(300);
        //stage.setFullScreen(true);
        //stage.setFullScreenExitHint("ignorant sweller press w");
        //stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("w"));

        stage.setScene(scene2); //add scene to the stage
		stage.show(); //always have this at the end so that the window appears
	}
}