package com.labyrinthus.window;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;


public class GameOverScreen extends StackPane {
    public final Button exitButton;

    public GameOverScreen (double width, double height){

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        String path = "file:src/main/resources/textures/game_over.png";
        Image gameOver = new Image(path);
        ImageView gameOverImageView = new ImageView(gameOver);

        exitButton = new Button();
        buttonSetup(exitButton, "back_to_menu.png");

        VBox box = new VBox(20, gameOverImageView, exitButton);
        box.setAlignment(Pos.CENTER);

        this.getChildren().add(box);
        this.setVisible(false);
    }

    public void showGameOver() {
        this.setVisible(true);
    }

    public void hideGameOver() {
        this.setVisible(false);
    }

    public void buttonHoverDetection(Button button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-border-color: #FFF; -fx-border-width: 5px; -fx-background-color: transparent;");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent;");
        });
    }

    public void buttonGetImage(Button button, String name) {
        String path = "file:src/main/resources/textures/";

        Image image = new Image(path + name);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(50);
        imageView.setFitWidth(200);
        button.setGraphic(imageView);
    }

    public void buttonSetup(Button button, String name) {
        buttonGetImage(button, name);
        button.setStyle("-fx-background-color: transparent;");
        buttonHoverDetection(button);
    }
}