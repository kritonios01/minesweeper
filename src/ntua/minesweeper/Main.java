package ntua.minesweeper;

//import java.lang.System;
	
import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class Main extends Application{
	//Button button; //create the button which will appear in the window
	
	public static void main (String[] args) {
		launch(args); //calls the start method, launch comes from Application class and it's static
	}
	
	@Override
	public void start(Stage stage) throws Exception{ //overrride the default start method from javafx
		stage.setTitle("First JavaFX App");
		/*button = new Button("Press me mr Sweller");
		button.setOnAction(this); //look in this class for the handle method for what to do when i click the button
		
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		
		Scene scene = new Scene(layout, 300, 250);
		stage.setScene(scene);*/
        Group root = new Group(); //create root node to arrange components in scene
        Scene scene1 = new Scene(root,Color.BLACK); //add root node to the scene

        stage.setScene(scene1); //add scene to the stage
		stage.show(); //always have this at the end so that the window appears
	}
}