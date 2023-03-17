package ntua.minesweeper.types;

import javafx.util.Duration;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


//A class for a simple timer which is implemented with tha javafx timeline library and a static method to stop the timer when the game ends
public class Time {
    private int counter;
    private static Timeline currentTimeline;

    public Time(int time, Pane parent, Label label) {
        counter = 0;
        label.setText(Integer.toString(time));
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                label.setText(Integer.toString(time - counter));
                counter++;
                if (counter >= time) {
                    Resulttxt.handleGameover(parent);
                }
            })
        );
        timeline.setCycleCount(time);
        timeline.play();
        currentTimeline = timeline;
    }

    public static void stoptimer() {
        currentTimeline.stop();
    }
}
