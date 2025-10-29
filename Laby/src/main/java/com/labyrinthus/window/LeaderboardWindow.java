package com.labyrinthus.window;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.sql.SQLException;

public class LeaderboardWindow extends StackPane {
    public final Button exitButton;
    public final Button submit;
    public final Label scoreLabel;
    public final Label userLabel;

    public final Label u1;
    public final Label u2;
    public final Label u3;

    public final TextField textField;

    public int score;

    private GameSaver gs;

    private boolean submitted = false;

    public LeaderboardWindow (double width, double height, GameSaver gs){

        this.gs = gs;

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        //imaginea cu you leaderboard
        String path = "file:src/main/resources/textures/leaderboard.png";
        Image leaderboard = new Image(path);
        ImageView gameOverImageView = new ImageView(leaderboard);

        //butonu de iesire
        exitButton = new Button();
        buttonSetup(exitButton, "back_to_menu.png");

        // scoru tau
        scoreLabel = new Label();
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        scoreLabel.setTextFill(Color.YELLOW);

        u1 = new Label();
        u1.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        u1.setTextFill(Color.YELLOW);

        u2 = new Label();
        u2.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        u2.setTextFill(Color.YELLOW);

        u3 = new Label();
        u3.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        u3.setTextFill(Color.YELLOW);

        // user
        String userPath = "file:src/main/resources/textures/user.png";
        Image userImage = new Image(userPath);
        ImageView userImageView = new ImageView(userImage);
        userImageView.setFitHeight(40);
        userImageView.setFitWidth(175);

        userLabel = new Label();
        userLabel.setGraphic(userImageView);

        // aici scrii numele
        textField = new TextField();
        textField.setStyle(
                "-fx-control-inner-background: #1e1e1e;" +  // fundal
                        "-fx-text-fill: white;" +                   // culoarea textului
                        "-fx-background-radius: 10;" +              // colțuri rotunjite
                        "-fx-border-color: gray;" +                 // bordură
                        "-fx-border-radius: 10;" +
                        "-fx-border-width: 2;"
        );
        textField.setMaxWidth(100);
        textField.setPrefHeight(40);

        submit = new Button();
        buttonSetup(submit, "submit.png");

        submit.setOnAction(e -> {
            String userInput = textField.getText();
            try {
                gs.saveScore(score, userInput);
                gs.updateScoreLabels(u1, u2, u3);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            submitted = true;

        });

        HBox hbox = new HBox(20, userLabel, textField, submit);
        hbox.setAlignment(Pos.CENTER);

        VBox box = new VBox(20, gameOverImageView, scoreLabel, hbox, u1, u2, u3, exitButton);
        box.setAlignment(Pos.CENTER);


        this.getChildren().add(box);
        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

    public int getScore(YouWinScreen youWinScreen){
        return youWinScreen.score;
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
