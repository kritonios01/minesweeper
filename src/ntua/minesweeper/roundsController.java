package ntua.minesweeper;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.spi.InitialContextFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ntua.minesweeper.types.Rounds;

public class roundsController implements Initializable{

    @FXML private TreeView gamesTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<List<Integer>> gameStats = Rounds.getStats();
        int games = gameStats.size();
        TreeItem<String> root = new TreeItem<String>("Games");

        for(int i = 0; i < games; i++){
            TreeItem<String> branch = new TreeItem<String>("Game" + Integer.toString(i+1));
            TreeItem<String> leaf1 = new TreeItem<String>("Total Mines: " + Integer.toString(gameStats.get(i).get(0)));
            TreeItem<String> leaf2 = new TreeItem<String>("Total Clicks: " + Integer.toString(gameStats.get(i).get(1)));
            TreeItem<String> leaf3 = new TreeItem<String>("Total Time: " + Integer.toString(gameStats.get(i).get(2)));
            /*if(gameStats.get(i).get(3) == 1){
                TreeItem<String> leaf4 = new TreeItem<>("You won");
            }
            else{
                TreeItem<String> leaf4 = new TreeItem<>("You lost");
            }*/
            TreeItem<String> leaf4 = gameStats.get(i).get(3) == 1 ? new TreeItem<String>("You won") : new TreeItem<String>("You lost");
            branch.getChildren().addAll(leaf1, leaf2, leaf3, leaf4);
            root.getChildren().add(branch);
        }
        gamesTreeView.setRoot(root);
    }



}
