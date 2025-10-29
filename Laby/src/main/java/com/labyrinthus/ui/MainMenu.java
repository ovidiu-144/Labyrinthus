package com.labyrinthus.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Slider;

import com.labyrinthus.window.LoadStage;

import java.util.Objects;

public class MainMenu {
    public void start(Stage primaryStage) {
        //dimensiuni ecran
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        //muzica de fundal
        String menuThemePath = Objects.requireNonNull(getClass().getResource("/audio/menu_theme.mp3")).toExternalForm();
        Media menuTheme = new Media(menuThemePath);

        MediaPlayer menuThemePlayer = new MediaPlayer(menuTheme);
        menuThemePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        menuThemePlayer.setVolume(0.5);
        menuThemePlayer.play();

        //fundal gif
        ImageView background = new ImageView(new Image("file:src/main/resources/textures/menu_background.gif"));
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);

        //creare logo
        Image gameLogo = new Image("file:src/main/resources/textures/game_logo.png");
        ImageView gameLogoImageView = new ImageView(gameLogo);
        gameLogoImageView.setFitHeight(200);
        gameLogoImageView.setFitHeight(200);

        //creare butoane
        Button newGameButton = new Button();
        buttonSetup(newGameButton, "new_game.png");

        Button loadGameButton = new Button();
        buttonSetup(loadGameButton, "load_game.png");

        Button settingsButton = new Button();
        buttonSetup(settingsButton, "settings.png");

        Button quitGameButton = new Button();
        buttonSetup(quitGameButton, "quit_game.png");

        //load stage
        LoadStage loadStage = new LoadStage();

        //setare actiune butoane
        newGameButton.setOnAction(e -> loadStage.start(primaryStage, menuThemePlayer));
        loadGameButton.setOnAction(e -> openLoadWindow(primaryStage, menuThemePlayer, loadStage, screenWidth, screenHeight));
        settingsButton.setOnAction(e -> openSettingsWindow(menuThemePlayer, screenWidth, screenHeight));
        quitGameButton.setOnAction(e -> System.exit(0));

        //creare vbox
        VBox layout = new VBox(30, gameLogoImageView, newGameButton,
                loadGameButton, settingsButton, quitGameButton);

        //aliniere la stanga
        layout.setAlignment(Pos.BASELINE_LEFT);

        //aranjare butoane si background
        StackPane root = new StackPane();
        root.getChildren().addAll(background, layout);

        //generare scena
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Labyrinthus");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private void openSettingsWindow(MediaPlayer mediaPlayer, double width, double height) {
        Stage settingsStage = new Stage();

        //label volum
        String path = "file:src/main/resources/textures/volume.png";

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(50);
        imageView.setFitWidth(200);

        Label volumeLabel = new Label();
        volumeLabel.setGraphic(imageView);

        //slider volum
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> mediaPlayer.setVolume(newVal.doubleValue()));
        volumeSlider.setPrefWidth(200);
        volumeSlider.setMaxWidth(200);

        //aliniere orizontala
        HBox volumeLayout = new HBox(20);
        volumeLayout.setAlignment(Pos.CENTER);
        volumeLayout.getChildren().addAll(volumeLabel, volumeSlider);

        //iesire din setari
        Button quitSettingsButton = new Button();
        buttonSetup(quitSettingsButton, "exit_settings.png");
        quitSettingsButton.setOnAction(e -> settingsStage.close());

        //background
        ImageView settingsBackground = new ImageView(new Image("file:src/main/resources/textures/menu_background.gif"));
        settingsBackground.setFitWidth(width);
        settingsBackground.setFitHeight(height);

        //aliniere verticala
        VBox settingsVerticalLayout = new VBox(30, volumeLayout, quitSettingsButton);
        settingsVerticalLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(settingsBackground, settingsVerticalLayout);

        Scene settingsScene = new Scene(root, width, height);
        settingsStage.setScene(settingsScene);
        settingsStage.setFullScreen(true);
        settingsStage.show();
    }

    private void openLoadWindow(Stage primaryStage, MediaPlayer menuThemePlayer, LoadStage loadStage, double width, double height) {
        Stage settingsStage = new Stage();

        //label volum
        String path = "file:src/main/resources/textures/volume.png";

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(50);
        imageView.setFitWidth(200);

        Label volumeLabel = new Label();
        volumeLabel.setGraphic(imageView);

        //iesire din setari
        Button quitSettingsButton = new Button();
        buttonSetup(quitSettingsButton, "exit_settings.png");
        quitSettingsButton.setOnAction(e -> settingsStage.close());

        Button save1 = new Button();
        buttonSetup(save1, "v1/load_game.png");
        save1.setOnAction(e -> {
            loadStage.load(primaryStage, menuThemePlayer, 0);
            settingsStage.close();
        });


        Button save2 = new Button();
        buttonSetup(save2, "v1/load_game.png");
        save2.setOnAction(e -> {
            loadStage.load(primaryStage, menuThemePlayer, 1);
            settingsStage.close();
        });

        Button save3 = new Button();
        buttonSetup(save3, "v1/load_game.png");
        save3.setOnAction(e -> {
            loadStage.load(primaryStage, menuThemePlayer, 2);
            settingsStage.close();
        });


        //background
        ImageView settingsBackground = new ImageView(new Image("file:src/main/resources/textures/menu_background.gif"));
        settingsBackground.setFitWidth(width);
        settingsBackground.setFitHeight(height);

        //aliniere verticala
        VBox settingsVerticalLayout = new VBox(30, save1, save2, save3, quitSettingsButton);
        settingsVerticalLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(settingsBackground, settingsVerticalLayout);

        Scene settingsScene = new Scene(root, width, height);
        settingsStage.setScene(settingsScene);
        settingsStage.setFullScreen(true);
        settingsStage.show();
    }

}
