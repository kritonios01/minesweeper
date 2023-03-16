package ntua.minesweeper.types;

import java.util.ArrayList;
import java.util.List;

public class Rounds {

    private static List<List<Integer>> stats = new ArrayList<>();

    public static void setStats(int mines, int clicks, int playtime, int result){
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
}
