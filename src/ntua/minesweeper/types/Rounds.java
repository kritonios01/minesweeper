/**
The Rounds class represents a collection of statistics for each round of gameplay.
*/

package ntua.minesweeper.types;

import java.util.ArrayList;
import java.util.List;

public class Rounds {
    /**
     * A list of all rounds' statistics.
     */
    private static List<List<Integer>> stats = new ArrayList<>();

    /**
     * Adds a new round's statistics to the list of rounds.
     *
     * @param mines    the number of mines in the round
     * @param clicks   the number of clicks made in the round
     * @param playtime the length of available time for the round
     * @param result   the result of the round (0 for loss, 1 for win)
     */
    public static void setStats(int mines, int clicks, int playtime, int result) {
        List<Integer> roundList = new ArrayList<>();
        roundList.add(mines);
        roundList.add(clicks);
        roundList.add(playtime);
        roundList.add(result);

        stats.add(roundList);
    }

    /**
     * Returns the list of all rounds' statistics.
     *
     * @return the list of all rounds' statistics
     */

    public static List<List<Integer>> getStats() {
        return stats;
    }
}
