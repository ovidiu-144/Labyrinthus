package com.labyrinthus.window;

import com.labyrinthus.input.KeyHandler;

import com.labyrinthus.items.Enemy;
import com.labyrinthus.items.Entity;
import com.labyrinthus.items.Hero;
import com.labyrinthus.items.Minotaur;
import com.labyrinthus.map.Map;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.labyrinthus.ui.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;


public class GamePanel extends Pane{
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 40;
    final int maxScreenRow = 22;

    public final int screenWidth = maxScreenCol * tileSize; //1920
    public final int screenHeight = maxScreenRow * tileSize; //1056
    Hero hero;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Canvas canvas;
    GraphicsContext g;

    //pozitia default a playerului in raport cu harta
    int playerX = 12 * tileSize;
    int playerY = 8 * tileSize;
    int FPS = 60;

    private long startTime;
    private long lastTime = -1;

    public Map map;
    Map map1;
    Map map2;
    Map map3;

    int level = 0;
    GameSaver gs;

    private PauseMenu pauseMenu;
    private boolean paused = false;

    private GameOverScreen gameOverScreen;
    private YouWinScreen youWinScreen;
    private LeaderboardWindow leaderboardWindow;

    AnimationTimer timer;

    MediaPlayer mediaPlayer;

    public GamePanel(Scene scene, Stage primaryStage, MediaPlayer mediaPlayer){
        try {
            gs = new GameSaver("Labyrinthus.db");
//            gs.SetTable(100, 10, 5, 5);
//            gs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.setPrefSize(screenWidth, screenHeight);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setFocused(true);
        this.hero = new Hero((float)(playerX), (float)(playerY), tileSize, tileSize, keyH, this); // HP in hero
        hero.SetNormalMode();

        canvas = new Canvas(screenWidth, screenHeight);
        g = canvas.getGraphicsContext2D();

        this.mediaPlayer = mediaPlayer;

        pauseMenu = new PauseMenu(screenWidth, screenHeight);
        //Setare Butoane
        ///Resume
        pauseMenu.resumeButton.setOnAction(e->{
            pauseMenu.hideMenu();
            System.out.println("RESUME");;
            paused = false;
        });

        /// Exit ->
        pauseMenu.exitButton.setOnAction(e-> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);

            Minotaur.Reset();
            mediaPlayer.stop();
            stopGameLoop();

        });

        /// Save
        pauseMenu.save1Button.setOnAction(e-> saveStage(1));
        pauseMenu.save2Button.setOnAction(e-> saveStage(2));
        pauseMenu.save3Button.setOnAction(e-> saveStage(3));

        /// Game over
        gameOverScreen = new GameOverScreen(screenWidth, screenHeight);
        gameOverScreen.exitButton.setOnAction(e-> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);

            Minotaur.Reset();
            mediaPlayer.stop();
            stopGameLoop();

        });

        /// You win
        youWinScreen = new YouWinScreen(screenWidth, screenHeight);
        youWinScreen.exitButton.setOnAction(e-> {
            leaderboardWindow.score = youWinScreen.score;
            leaderboardWindow.scoreLabel.setText("Score: " + youWinScreen.score);

            youWinScreen.hide();

            leaderboardWindow.show();

            Minotaur.Reset();
            mediaPlayer.stop();
            stopGameLoop();

        });

        /// Leaderboard
        leaderboardWindow = new LeaderboardWindow(screenWidth, screenHeight, gs);
        leaderboardWindow.exitButton.setOnAction(e-> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);

        });


        map = new Map(this, 27, 25, hero, 0, "src/main/resources/Levels/level0.txt");

        map.camera.centerOn(16 * tileSize, 20 * tileSize);

        //level1
        map1 = new Map(this, 42, 27, hero, 1, "src/main/resources/Levels/level2.txt");
        map1.camera.centerOn(8.5 * tileSize, 38 * tileSize);

        //level2
        map2 = new Map(this, 42, 27, hero, 2, "src/main/resources/Levels/level1.txt");
        map2.camera.centerOn(17 * tileSize, 38 * tileSize);

        //level3
        map3 = new Map(this, 50, 27, hero, 3, "src/main/resources/Levels/level3.txt");
        map3.camera.centerOn(17 * tileSize, 46.8 * tileSize);




        this.getChildren().add(canvas);
        this.getChildren().add(pauseMenu);
        this.getChildren().add(gameOverScreen);
        this.getChildren().add(youWinScreen);
        this.getChildren().add(leaderboardWindow);


        scene.setOnKeyPressed(keyH::keyPressed);
        scene.setOnKeyReleased(keyH::keyReleased);

        scene.setOnMousePressed(e -> {
            hero.startAttack();
        });

        startGameLoop();
    }
    private void startGameLoop(){
        startTime = System.currentTimeMillis();
        final long[] lastUpdate = {System.nanoTime()};
        final double drawInterval = 1_000_000_000.0 / FPS; // 60 FPS
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate[0] >= drawInterval) {
                    if (!paused) {
                        update();
                        draw();
                        lastUpdate[0] = now;
                    }
                }
            }
        };
        timer.start();
    }

    private void stopGameLoop(){
        if (timer != null){
            timer.stop();
        }
    }

    private void draw() {

        g.setFill(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Desenează harta
        switch (level){
            case 0:
                map.LoadWorld(g);
                break;
            case 1:
                map1.LoadWorld(g);
                break;
            case 2:
                map2.LoadWorld(g);
                break;
            case 3:
                map3.LoadWorld(g);
                break;
        }

        hero.Draw(g);
        g.setImageSmoothing(false);
        drawHealthBar(g);
        hero.drawSword(g);
    }


    public void startGameThread(){
        gameThread = new Thread(String.valueOf(this));
        gameThread.start();
    }

    //updateaza pozitia jucatorului in functie de tastele apasate
    public void update(){
        hero.Update();
        switch (level){
            case 0:
                map.camera.updateCamera();
                break;
            case 1:
                checkCollision(map1);
                break;
            case 2:
                checkCollision(map2);
                break;
            case 3:
                checkCollision(map3);
                break;
        }
        updateLevel();
        if (keyH.pausePressed){
            paused = true;
            pauseMenu.showMenu();
        }

        if(hero.GetALife() < 0){
            paused = true;
            gameOverScreen.showGameOver();
        }
        if(map3.levelManager.levelCleared){
            paused = true;

            long endTime = System.currentTimeMillis();
            long totalTimeMillis = endTime - startTime;


            if (lastTime != -1)
                totalTimeMillis += lastTime;

            int totalDamageTaken = hero.maxLife - hero.GetALife();

            youWinScreen.score = (int)((float)(1000000 / totalTimeMillis) * 100) - totalDamageTaken * 10;
            youWinScreen.score = Math.max(youWinScreen.score, 0);

            youWinScreen.scoreLabel.setText("Score: " + youWinScreen.score);

            youWinScreen.show();
        }

    }
    private void saveStage(int save){
            try {
                StringBuilder enemiesHp = new StringBuilder();

                long newLastTime = 0;
                if (lastTime == -1){
                    newLastTime = System.currentTimeMillis() - startTime;
                }
                else
                    newLastTime = System.currentTimeMillis() - startTime + lastTime;

                switch (level) {
                    case 0:
                        for(Enemy enemy: map.enemies){
                            enemiesHp.append(enemy.GetALife()).append(' ');
                        }
                        gs.SetTable(save, hero.GetALife(), level, map.camera.getX(), map.camera.getY(), enemiesHp.toString(), newLastTime);
                        break;
                    case 1:
                        for(Enemy enemy: map1.enemies){
                            enemiesHp.append(enemy.GetALife()).append(' ');
                        }
                        gs.SetTable(save, hero.GetALife(), level, map1.camera.getX(), map1.camera.getY(), enemiesHp.toString(), newLastTime);
                        break;
                    case 2:
                        for(Enemy enemy: map2.enemies){
                            enemiesHp.append(enemy.GetALife()).append(' ');
                        }
                        gs.SetTable(save, hero.GetALife(), level, map2.camera.getX(), map2.camera.getY(), enemiesHp.toString(), newLastTime);
                        break;
                    case 3:
                        for(Enemy enemy: map3.enemies){
                            enemiesHp.append(enemy.GetALife()).append(' ');
                        }
                        gs.SetTable(save, hero.GetALife(), level, map3.camera.getX(), map3.camera.getY(), enemiesHp.toString(), newLastTime);
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public void loadStage (int save){

        /// PENTRU INAMICI NU II STERGEM DIN ENEMIES, DAR LE SETAM VIATA 0, si facem checkColission doar daca nu viata > 0
            try {
                GameState state = gs.LoadTable(save);
                if (state != null){
                    hero.SetLife(state.heroHp);
                    level = state.level;
                    String[] enemiesHp = state.enemiesHP.split(" ");
                    lastTime = state.lastTime;
                    int poz = 0;
                    switch (level) {
                        case 0:
                            for (Enemy enemy:map.enemies)
                                enemy.takeDamage(Integer.parseInt(enemiesHp[poz++]));
                            map.camera.setX(state.cameraX);
                            map.camera.setY(state.cameraY);
                            break;
                        case 1:
                            for (Enemy enemy:map1.enemies)
                                enemy.takeDamage(enemy.GetALife() - Integer.parseInt(enemiesHp[poz++]));
                            map1.camera.setX(state.cameraX);
                            map1.camera.setY(state.cameraY);
                            break;
                        case 2:
                            for (Enemy enemy:map2.enemies)
                                enemy.takeDamage(enemy.GetALife() -Integer.parseInt(enemiesHp[poz++]));
                            map2.camera.setX(state.cameraX);
                            map2.camera.setY(state.cameraY);
                            break;
                        case 3:
                            for (Enemy enemy:map3.enemies)
                                enemy.takeDamage(enemy.GetALife() -Integer.parseInt(enemiesHp[poz++]));
                            map3.camera.setX(state.cameraX);
                            map3.camera.setY(state.cameraY);
                            break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public int GetScreenWidth(){
        return screenWidth;
    }
    public int GetScreenHeight(){
        return screenHeight;
    }

    private void updateLevel(){
        int id;
        switch (level){
            case 0:
                id = map.returnId((int)hero.xByCamera, (int)hero.yByCamera);
                if (id == 8 && map.levelManager.levelCleared)
                    level = 1;
                break;
            case 1:
                id = map1.returnId((int)hero.xByCamera, (int)hero.yByCamera);
                if ((id == 16 || id == 17 || id == 18) && map1.levelManager.levelCleared)
                    level = 2;
                break;
            case 2:
                id = map2.returnId((int)hero.xByCamera, (int)hero.yByCamera);
                if ((id == 16 || id == 17 || id == 18) && map2.levelManager.levelCleared)
                    level = 3;
                break;
        }
    }
    private void drawHealthBar(GraphicsContext g) {
        int maxLife = Entity.DEFAULT_LIFE;
        int currentLife = hero.GetALife();

        double barWidth = 200;
        double barHeight = 20;
        double x = 20;
        double y = 20;

        g.setFill(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        double healthPercent = (double) currentLife / maxLife;
        g.setFill(Color.RED);
        g.fillRect(x, y, barWidth * healthPercent, barHeight);

        g.setStroke(Color.BLACK);
        g.strokeRect(x, y, barWidth, barHeight);
    }

    private void checkCollision(Map map) {
        Rectangle heroBounds = hero.getAbsoluteBounds(map.camera);
        Rectangle swordBounds = hero.getSwordBounds(map.camera);
        map.camera.updateCamera();
        for (int i = 0; i < map.enemies.size(); i++) {
            if (map.enemies.get(i).GetALife() != 0) {
                map.enemies.get(i).getAbsoluteBounds();
                Rectangle enemyBounds = map.enemies.get(i).getAbsoluteBounds();
                if (heroBounds.intersects(enemyBounds.getBoundsInLocal())) {
                    if (!hero.isInvulnerable)
                        hero.SetLife(hero.GetALife() - map.enemies.get(i).damage);
                    hero.triggerInvulnerability();
                }
                if (swordBounds != null && swordBounds.intersects(enemyBounds.getBoundsInLocal())) {
                    map.enemies.get(i).takeDamage(10);
                }
            }
        }
    }
}
