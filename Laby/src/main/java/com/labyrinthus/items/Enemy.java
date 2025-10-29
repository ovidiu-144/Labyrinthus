package com.labyrinthus.items;

import com.labyrinthus.observer.EnemyObserver;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class Enemy extends Entity{

    public int damage = 10;

    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    private final ArrayList<EnemyObserver> observers = new ArrayList<>();

    public void takeDamage(int amount) {
        life -= amount;
        if (life <= 0) {
            notifyDefeated();
        }
    }

    public void addObserver(EnemyObserver observer) {
        observers.add(observer);
    }

    private void notifyDefeated() {
        for (EnemyObserver obs : observers) {
            obs.onEnemyDefeated(this);
        }
    }

}
