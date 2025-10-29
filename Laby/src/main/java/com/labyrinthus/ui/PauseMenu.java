package com.labyrinthus.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class PauseMenu extends StackPane {
    public final Button resumeButton;

    public final Button save1Button;
    public final Button save2Button;
    public final Button save3Button;

    public final Button exitButton;

    public PauseMenu (double width, double height){

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        resumeButton = new Button();
        buttonSetup(resumeButton,"resume.png");

        save1Button = new Button();
        buttonSetup(save1Button, "save_slot_1.png");

        save2Button = new Button();
        buttonSetup(save2Button, "save_slot_2.png");

        save3Button = new Button();
        buttonSetup(save3Button, "save_slot_3.png");

        exitButton = new Button();
        buttonSetup(exitButton, "back_to_menu.png");

        VBox menuBox = new VBox(20, resumeButton, save1Button, save2Button, save3Button, exitButton);
        menuBox.setAlignment(Pos.CENTER);

        this.getChildren().add(menuBox);
        this.setVisible(false);
    }

    public void showMenu(){
        this.setVisible(true);
    }

    public void hideMenu(){
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