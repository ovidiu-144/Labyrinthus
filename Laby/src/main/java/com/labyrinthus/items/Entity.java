package com.labyrinthus.items;

import javafx.scene.shape.Rectangle;

public abstract class Entity extends Item
{
    public static final int DEFAULT_LIFE            = 100;   /*!< Valoarea implicita a vietii unui caracter.*/
    public static final float DEFAULT_SPEED         = 10.0f; /*!< Viteza implicita a unu caracter.*/
    public static final int DEFAULT_CREATURE_WIDTH  = 48;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 48;   /*!< Inaltimea implicita a imaginii caracterului.*/
    public String direction = "";


    protected int life;     /*!< Retine viata caracterului.*/
    protected float speed;  /*!< Retine viteza de deplasare caracterului.*/
    protected float xMove;  /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected float yMove;  /*!< Retine noua pozitie a caracterului pe axa Y.*/



    public Entity(float x, float y, int width, int height) {
        super(x,y, width, height);
        life    = DEFAULT_LIFE;
        speed   = DEFAULT_SPEED;
        xMove   = 0;
        yMove   = 0;
    }

    public Rectangle getAbsoluteBounds() {
        Rectangle r = new Rectangle();
        r.setX(x + bounds.getX());
        r.setY(y + bounds.getY());
        r.setWidth(bounds.getWidth());
        r.setHeight(bounds.getHeight());
        return r;
    }

    public void Move() {
        MoveX();
        MoveY();
    }

    public void MoveX() {
        x += xMove;
    }

    public void MoveY() {
        y += yMove;
    }

    public int GetALife() {
        return life;
    }

    public float GetSpeed() {
        return speed;
    }

    public void SetLife(int life) {
        this.life = life;
    }

    public void SetSpeed(float speed) {
        this.speed = speed;
    }

    public float GetXMove() {
        return xMove;
    }

    public float GetYMove() {
        return yMove;
    }

    public void SetXMove(float xMove) {
        this.xMove = xMove;
    }

    public void SetYMove(float yMove) {
        this.yMove = yMove;
    }
}