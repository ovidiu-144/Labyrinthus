package com.labyrinthus.window;

public class GameState {
    public int heroHp;
    public int level;
    public double cameraX;
    public double cameraY;
    public String enemiesHP;
    public long lastTime;

    public GameState(int heroHp, int level, double cameraX, double cameraY, String enemiesHP, long lastTime) {
        this.heroHp = heroHp;
        this.level = level;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.enemiesHP = enemiesHP;
        this.lastTime = lastTime;
    }
}