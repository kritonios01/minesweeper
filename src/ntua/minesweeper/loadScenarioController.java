package ntua.minesweeper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import ntua.minesweeper.exceptions.InvalidDescriptionException;
import ntua.minesweeper.exceptions.InvalidValueException;


public class loadScenarioController {
    @FXML private TextField scenarioTextField;
    @FXML private Button loadScenarioBox;
    private int difficulty;
    private int mines;
    private int time;
    private int supermine;

    public void loadScenario(ActionEvent event) throws Exception{
        String filename = new String(scenarioTextField.getText());
        //char[] inputar = new char[10];

        //FileReader inputFile = new FileReader("src/medialab/" + filename + ".txt");
        //inputFile.read(array); //to be removed
        System.out.println("Data in the file:"); //to be removed
        //System.out.println(array); //to be removed
        //inputFile.close();

        try{
            BufferedReader inputFile = new BufferedReader(new FileReader("src/medialab/" + filename + ".txt"));
            //String line1 = inputFile.readLine();
            //String line2 = inputFile.readLine();
            //String line3 = inputFile.readLine();
            //String line4 = inputFile.readLine();

            List<String> lines = List.of(inputFile.readLine(), inputFile.readLine(), inputFile.readLine(), inputFile.readLine());

            if (inputFile.readLine() != null) { //checks if there is a fifth line
                inputFile.close();
                throw new InvalidDescriptionException();
            }

            checkAndAssign(lines);
            /*
            difficulty = Character.getNumericValue(inputar[0]);
            if(inputar[3] != '\n'){ //if there are more than 9 mines
                mines = Integer.parseInt(new String(inputar, 2, 2));
                time = Integer.parseInt(new String(inputar, 5, 3));
                supermine = Character.getNumericValue(inputar[9]);
            }
            else{ //if there are 9 mines
                mines = Character.getNumericValue(inputar[2]);
                time = Integer.parseInt(new String(inputar, 4, 3));
                supermine = Character.getNumericValue(inputar[8]);
            }
            */
            System.out.println(difficulty);
            System.out.println(mines);
            System.out.println(time);
            System.out.println(supermine);

            inputFile.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not Found");//do sth about this
        }
        catch(InvalidDescriptionException e){
            System.out.println("File has more than 4 lines");
        }
        catch(InvalidValueException e){
            System.out.println("Invalid Description");
        }



        //Stage he = (Stage)((Node)event.getSource()).getScene().getWindow();
        //@FXML AnchorPane ap; Stage stage = (Stage) ap.getScene.getWindow();
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();//close window
        //maybe getters/setters for variables????
        //ta throw prepei na einai atomika gia kathe metavliti kai oxi se ena megalo if
    }

    public void checkAndAssign(List<String> lines) throws InvalidValueException{

        String line1 = lines.get(0); //check line 1 and assign difficulty
        /*if (!(line1.equals("1") || !line1.equals("2"))) {
            throw new InvalidValueException();
        }
        else{
            difficulty = Integer.parseInt(line1);
        } */   //AN ENORMOUS MYSTERY WHY IT DOESN'T WORK

        if ( !line1.matches("-?\\d+") //a regular expression to check if line2 is a number
        || (Integer.parseInt(line1) != 1
        && Integer.parseInt(line1) != 2)) {
          throw new InvalidValueException();
      }
        else{
            difficulty = Integer.parseInt(line1);
        }

        switch(difficulty){
            case 1:
                String line2 = lines.get(1); //check line 2 and assign mines
                if ( !line2.matches("-?\\d+") //a regular expression to check if line2 is a number
                  || Integer.parseInt(line2) < 9
                  || Integer.parseInt(line2) > 11) {
                    throw new InvalidValueException();
                }
                else{
                    mines = Integer.parseInt(line2);
                }

                String line3 = lines.get(2); //check line 3 and assign time
                if ( !line3.matches("-?\\d+")
                  || Integer.parseInt(line3) < 120
                  || Integer.parseInt(line3) > 180) {
                    throw new InvalidValueException();
                }
                else{
                    time = Integer.parseInt(line3);
                }

                String line4 = lines.get(3); //check line 4 and assign supermine
                if (!line4.matches("-?\\d+") || Integer.parseInt(line4) != 0) {
                    throw new InvalidValueException();
                }
                else{
                    supermine = Integer.parseInt(line4);
                }

            case 2:

        }





        /*for (int i = 0; i < 10; i++) {
            if ( !Character.isDigit(inputar[i]) || inputar[i] != '\n'){
                throw new InvalidValueException();
            }
        }

        if (inputar[0] != '1' && inputar[0] != '2') { //check and assign difficulty
            throw new InvalidValueException();
        }
        else{
            difficulty = Character.getNumericValue(inputar[0]);
        }

        if(inputar[3] != '\n'){ //if there are more than 9 mines
            mines = Integer.parseInt(new String(inputar, 2, 2));
            time = Integer.parseInt(new String(inputar, 5, 3));
            supermine = Character.getNumericValue(inputar[9]);
        }
        else{ //if there are 9 mines
            mines = Character.getNumericValue(inputar[2]);
            time = Integer.parseInt(new String(inputar, 4, 3));
            supermine = Character.getNumericValue(inputar[8]);
        }*/

    }

    public List<Integer> getScenario() { //returns an immutable list
        return List.of(difficulty, mines, time, supermine);
    }
}
