package ru.spbau.karlina.find.pairs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void main() throws Exception {
        Timeline timeline = new Timeline(new KeyFrame(
                javafx.util.Duration.millis(4000), ae -> {
            Main.destroy();
        }));
        timeline.play();

        Main.main(new String[]{"4"});
    }

}