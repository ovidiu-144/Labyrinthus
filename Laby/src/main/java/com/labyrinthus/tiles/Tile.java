package com.labyrinthus.tiles;

import javafx.scene.image.Image;


public class Tile {
    protected Image image;
    protected int id;
    public boolean collision;

    public Tile(Image image, int id, boolean collision) {
        this.image = image;
        this.id = id;
        this.collision = collision;
    }

    public Tile(Image image, int id) {
        this.image = image;
        this.id = id;
        this.collision = false;
    }

}