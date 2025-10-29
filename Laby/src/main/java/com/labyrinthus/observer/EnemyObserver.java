package com.labyrinthus.observer;

import com.labyrinthus.items.Enemy;

public interface EnemyObserver {
    void onEnemyDefeated(Enemy enemy);
}