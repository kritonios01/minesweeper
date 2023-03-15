package ntua.minesweeper.types;

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.swing.text.PlainDocument;

public class Rounds {
    //private int mines;
    //private int tries;
    //private int playtime;
    //private int result;

    private static List<List<Integer>> stats = new ArrayList<>();


    /*public Rounds(int mines, int tries, int playtime, int result) {
        this.mines = mines;
        this.tries = tries;
        this.playtime = playtime;
        this.result = result;
    }*/

    public static void setStats(int mines, int clicks, int playtime, int result){
        //Rounds round = new Rounds(0, 0, 0, 0);
        List<Integer> roundList = new ArrayList<>();
        roundList.add(mines);
        roundList.add(clicks);
        roundList.add(playtime);
        roundList.add(result);

        stats.add(roundList);
    }

    public static List<List<Integer>> getStats(){
        return stats;
    }

    public static void printstats(){
        System.out.println(stats.size());
    }
}
