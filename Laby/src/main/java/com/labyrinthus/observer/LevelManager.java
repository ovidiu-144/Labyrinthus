package com.labyrinthus.observer;

import com.labyrinthus.items.Enemy;
import com.labyrinthus.items.Entity;

import java.util.ArrayList;

public class LevelManager implements EnemyObserver {
    private int enemiesRemaining;
    public boolean levelCleared = false;



    public LevelManager(ArrayList<Enemy> enemies) {
        this.enemiesRemaining = enemies.size();
        if (enemiesRemaining == 0)
            levelCleared = true;
        for (Enemy enemy : enemies) {
            enemy.addObserver(this);
        }
    }

    @Override
    public void onEnemyDefeated(Enemy enemy) {
        enemiesRemaining--;
        if (enemiesRemaining == 0) {
            levelCleared = true;
        }
    }
}

