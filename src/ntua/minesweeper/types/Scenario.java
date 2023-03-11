package ntua.minesweeper.types;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ntua.minesweeper.exceptions.InvalidDescriptionException;
import ntua.minesweeper.exceptions.InvalidValueException;


//!!!! na xrisimopoihtei ayth h klash kai gia th dhmiourg;ia scenariou
public class Scenario {
    public static String filename;

    private int difficulty;
    private int mines;
    private int time;
    private int supermine;

    public Scenario(int difficulty, int mines, int time, int supermine) {
        this.difficulty = difficulty;
        this.mines = mines;
        this.time = time;
        this.supermine = supermine;
    }


    public static Scenario getScenario(String input) throws IOException{
        try{
            BufferedReader inputFile = new BufferedReader(new FileReader("src/medialab/" + input + ".txt"));

            List<Integer> values = checkAndAssign(checklines(inputFile)); //we check values if there are 4 lines exactly

            inputFile.close();

            return new Scenario(values.get(0).intValue(), values.get(1).intValue(), values.get(2).intValue(), values.get(3).intValue());
        }
        catch(FileNotFoundException e){
            System.out.println("File not Found");//actually handle the exceptions
        }
        catch(InvalidDescriptionException e){
            System.out.println("File doesn't have 4 lines");
        }
        catch(InvalidValueException e){
            System.out.println("Invalid Value Description");
        }

        return null;
    }


    //checks if there are four lines exactly and returns a list of strings, each of which is a line
    public static List<String> checklines (BufferedReader x) throws InvalidDescriptionException, IOException{
        try{
            //tries to read each line, if a line doesn't exist NullPointerException is thrown
            List<String> lines = List.of(x.readLine(),
                                         x.readLine(),
                                         x.readLine(),
                                         x.readLine());
            if (x.readLine() != null) { //checks if there is a fifth line
                throw new InvalidDescriptionException();
            }
            return lines;
        }
        catch(NullPointerException e){ //if there's a line missing, we want to throw InvDescException
            throw new InvalidDescriptionException(); //it's caught at the loadscenario func
        }
    }

    //checks if values are within bounds and assignes each value to the corresponding variable
    public static List<Integer> checkAndAssign(List<String> lines) throws InvalidValueException{
        int difficulty = -1;
        int mines = -1;
        int time = -1;
        int supermine = -1;

        String line1 = lines.get(0);
        String line2 = lines.get(1);
        String line3 = lines.get(2);
        String line4 = lines.get(3);

        if (!(line1.equals("1") || line1.equals("2"))) { //check line 1 and assign difficulty
            throw new InvalidValueException();
        }
        else{
            difficulty = Integer.parseInt(line1);
        }

        switch(difficulty){ // parameters bounds depend on difficulty level
            case 1: // "\\d+" is a regular expression to check if a line is a number
                if ( !line2.matches("\\d+") //check line 2 and assign mines
                  || Integer.parseInt(line2) < 9
                  || Integer.parseInt(line2) > 11) {
                    throw new InvalidValueException();
                }
                else{
                    mines = Integer.parseInt(line2);
                }
                if ( !line3.matches("\\d+") //check line 3 and assign time
                  || Integer.parseInt(line3) < 120
                  || Integer.parseInt(line3) > 180) {
                    throw new InvalidValueException();
                }
                else{
                    time = Integer.parseInt(line3);
                }
                if (!line4.matches("\\d+") || Integer.parseInt(line4) != 0) { //check line 4 and assign supermine
                    throw new InvalidValueException();
                }
                else{
                    supermine = Integer.parseInt(line4);
                }
                break;

            case 2:
                if ( !line2.matches("\\d+") //check line 2 and assign mines
                || Integer.parseInt(line2) < 35
                || Integer.parseInt(line2) > 45) {
                    throw new InvalidValueException();
                }
                else{
                    mines = Integer.parseInt(line2);
                }

                if ( !line3.matches("\\d+") //check line 3 and assign time
                || Integer.parseInt(line3) < 240
                || Integer.parseInt(line3) > 360) {
                    throw new InvalidValueException();
                }
                else{
                    time = Integer.parseInt(line3);
                }

                if (!line4.matches("\\d+") //check line 4 and assign supermine
                || (Integer.parseInt(line4) != 0 && Integer.parseInt(line4) != 1)) {
                    throw new InvalidValueException();
                }
                else{
                    supermine = Integer.parseInt(line4);
                }
                break;
        }

        return List.of(difficulty, mines, time, supermine);
    }

    public List<Integer> getScenarioList(){
        return List.of(difficulty, mines, time, supermine);
    }
}