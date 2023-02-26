package ntua.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	public static void main (String[] args) {
		launch(args); //calls the start method, launch comes from Application class and it's static
	}

	@Override
	public void start(Stage mainStage) throws Exception{ //overrride the default start method from javafx application class

        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Scene mainScene = new Scene(root);

        mainStage.setTitle("MediaLab Minesweeper");
        mainStage.setResizable(false);
        mainStage.setScene(mainScene); //add scene to the stage
		mainStage.show(); //always have this at the end so that the window appears
	}
}