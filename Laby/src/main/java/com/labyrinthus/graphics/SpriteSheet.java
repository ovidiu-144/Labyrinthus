package com.labyrinthus.graphics;

import javafx.scene.image.*;

public class SpriteSheet {
    private Image spriteSheet;
    private static final int tileSize = 16;

    public SpriteSheet(Image buffImg){
        spriteSheet = buffImg;
    }

    public void setImg(Image buffImg){
        spriteSheet = buffImg;
    }

    public Image crop (int x, int y){
        PixelReader reader = spriteSheet.getPixelReader();
        return new WritableImage(reader, x * tileSize, y * tileSize, tileSize, tileSize);
    }

    public Image crop (int x, int y, int tileSize){
        PixelReader reader = spriteSheet.getPixelReader();
        return new WritableImage(reader, x * tileSize, y * tileSize, tileSize, tileSize);
    }
}