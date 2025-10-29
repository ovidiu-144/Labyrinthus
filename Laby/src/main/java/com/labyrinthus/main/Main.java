package com.labyrinthus.main;

import com.labyrinthus.ui.MainMenu;
import com.labyrinthus.window.GamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu();

        mainMenu.start(primaryStage);
    }
}
