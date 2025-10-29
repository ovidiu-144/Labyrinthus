package com.labyrinthus.map;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.labyrinthus.items.*;
import com.labyrinthus.observer.LevelManager;
import com.labyrinthus.window.GamePanel;
import com.labyrinthus.tiles.*;
import javafx.scene.canvas.GraphicsContext;
import com.labyrinthus.camera.*;


public class Map {
    private final int height;
    private final int width;
    public final int[][] mat;


    public Camera camera;

    GamePanel gp;
    TileManager tm;

    private final int level;

    public ArrayList<Enemy> enemies;
    public LevelManager levelManager;

    private static final int[] spikeStates = {19, 20, 35, 36};
    private static final int[] spikeDurations = {40, 10, 10, 40};

    private static int spikeTick = 0;
    private static int spikeIndex = 0;
    private static int spikeDirection = 1; //1 == right -1 == left

    private static int spikeId = 19;

    public Map(GamePanel gp, int height, int width, Hero hero, int level, String filePath){
        this.gp = gp;
        this.height = height;
        this.width = width;
        this.level = level;

        tm = new TileManager(gp);
        mat = new int[width][height];

        camera = new Camera(gp.screenWidth, gp.screenHeight, TileManager.tileSize, hero, tm, mat, level);
        enemies = new ArrayList<Enemy>();

        switch (level){
            case 0:
                break;
            case 1:
                enemyInitLevel1();
                break;
            case 2:
                enemyInitLevel2();
                break;
            case 3:
                enemyInitLevel3();
                break;
        }
        levelManager = new LevelManager(enemies);


        ReadMap(filePath);

    }

    public void LoadWorld(GraphicsContext g) {
        //pozitia initiala a camerei
        double cameraX = camera.getX();
        double cameraY = camera.getY();

        int cols = gp.GetScreenWidth() / TileManager.tileSize + 2;
        int rows = gp.GetScreenHeight() / TileManager.tileSize + 2;

        int startX = (int) (cameraX / TileManager.tileSize) - 1;
        int startY = (int) (cameraY / TileManager.tileSize) - 1;

        if (level != 0)
            spikeUpdate();

        if (level == 0)
            // umplu fundalu cu iarba
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    double drawX = (startX + x) * TileManager.tileSize - cameraX;
                    double drawY = (startY + y) * TileManager.tileSize - cameraY;
                    tm.Draw(g, drawX, drawY, 0, level); // 0 e ID-ul pentru grass
                }
            }

        // harta propriu-zisa
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int id = mat[x][y];
                if (id >= 0) {
                    double drawX = x * TileManager.tileSize - cameraX;
                    double drawY = y * TileManager.tileSize - cameraY;

                    if (drawX + TileManager.tileSize >= 0 && drawX < gp.GetScreenWidth() &&
                            drawY + TileManager.tileSize >= 0 && drawY < gp.GetScreenHeight()) {
                        if (id == 19 && level != 0) {
                            tm.Draw(g, drawX, drawY, 0, level);
                            tm.Draw(g, drawX, drawY, spikeId, level);
                        }
                        else if (id == 36 && level != 0){
                            tm.Draw(g, drawX, drawY, 0, level);
                            tm.Draw(g, drawX, drawY, id, level);
                        }
                        else
                            tm.Draw(g, drawX, drawY, id, level);
                    }
                }
            }
        for (int i = 0; i < enemies.size(); i++) {
            {
                if (enemies.get(i).GetALife() != 0) {
                    enemies.get(i).Update();
                    enemies.get(i).Draw(g, camera.getX(), camera.getY());
                }
            }

        }
    }


    //citeste harta dintr-un fisier text
    private void ReadMap(String filePath)
    {
        try {
            File file = new File(filePath);
            Scanner input = new Scanner(file);

            for (int y = 0; y < height; y++) {  // Liniile sunt pe Y
                for (int x = 0; x < width; x++) {  // Coloanele sunt pe X
                    if (input.hasNextInt()) {
                        mat[x][y] = input.nextInt();
                    } else {
                        System.err.println("Eroare: Fisierul nu contine suficiente valori!");
                        return;
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fisierul nu a fost gasit: " + filePath);
        }
    }
    public int returnId (int x, int y){
        return mat[x][y];
    }

    public void enemyInitLevel1() {

        EnemyFactory ef = new EnemyFactory();
        enemies.add(ef.enemyCreate(EnemyType.HORIZONTAL, 12 * 48, 35 * 48));
        enemies.add(ef.enemyCreate(EnemyType.HORIZONTAL, 6 * 48, 6 * 48));

        enemies.add(ef.enemyCreate(EnemyType.VERTICAL, 21 * 48, 12 * 48));
        enemies.add(ef.enemyCreate(EnemyType.VERTICAL, 21 * 48, 30 * 48));

        enemies.add(ef.enemyCreate(EnemyType.HORIZONTAL, 17 * 48, 20 * 48));


        levelManager = new LevelManager(enemies);
    }

    public void enemyInitLevel2(){
        EnemyFactory ef = new EnemyFactory();

        enemies.add(ef.enemyCreate(EnemyType.HORIZONTAL, 11 * 48, 19 * 48));
        enemies.add(ef.enemyCreate(EnemyType.HORIZONTAL, 4 * 48, 8 * 48));
        enemies.add(ef.enemyCreate(EnemyType.HORIZONTAL, 15 * 48, 8 * 48));

        enemies.add(ef.enemyCreate(EnemyType.VERTICAL, 8 * 48, 33 * 48));
        enemies.add(ef.enemyCreate(EnemyType.VERTICAL, 17 * 48, 33 * 48));

        levelManager = new LevelManager(enemies);
    }

    public void enemyInitLevel3(){
        Minotaur.init((float)(11 * 48), (float)(6 * 48), 64, 120, camera);
        enemies.add(Minotaur.getInstance());
    }

    public void spikeUpdate() {
        //19-20-35-36
        spikeTick++;
        if (spikeTick > spikeDurations[spikeIndex]) {
            spikeTick = 0;
            spikeIndex = (spikeIndex + spikeDirection) % spikeStates.length;
        }
        spikeId = spikeStates[spikeIndex];

        if (spikeId == spikeStates[spikeStates.length - 1])
            spikeDirection = -1;
        else
        if (spikeId == spikeStates[0])
            spikeDirection = 1;

        camera.spikeCollision(spikeId);
    }
}