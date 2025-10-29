package com.labyrinthus.window;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class LoadStage {
    public void start(Stage primaryStage, MediaPlayer mediaPlayer) {
        Pane temporary = new Pane();
        Scene scene = new Scene(temporary);

        GamePanel gamePanel = new GamePanel(scene, primaryStage, mediaPlayer);

        scene.setRoot(gamePanel);

        primaryStage.setTitle("Labyrinthus");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void load(Stage primaryStage, MediaPlayer mediaPlayer, int save) {
        Pane temporary = new Pane();
        Scene scene = new Scene(temporary);

        GamePanel gamePanel = new GamePanel(scene, primaryStage, mediaPlayer);
        gamePanel.loadStage(save);

        scene.setRoot(gamePanel);

        primaryStage.setTitle("Labyrinthus");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
