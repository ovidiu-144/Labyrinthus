package com.labyrinthus.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.labyrinthus.graphics.Assets.*;

public class VerticalRat extends Enemy{
    private int moveTimer = 0;
    private int moveDirection = 1; // 1 = dreapta, -1 = stânga

    public VerticalRat(float x, float y, int width, int height){
        super(x, y, width, height);
//        this.gp = gp;

        normalBounds.setX(12);
        normalBounds.setY(0);
        normalBounds.setWidth(64);
        normalBounds.setHeight(96);

        InitRat();
        SetNormalMode();
    }

    @Override
    public void Update() {
        moveTimer++;
        if (moveTimer >= 60) { // schimbă direcția la fiecare 60 de cadre (~1 sec.)
            moveDirection *= -1;
            moveTimer = 0;
        }

        yMove = moveDirection * speed / 2; // mai lent decât jucătorul

        Move();
    }

    @Override
    public void Draw(GraphicsContext g) {
    }

    public void Draw(GraphicsContext g, double cameraX, double cameraY) {
        // Desenează inamicul
        Image img = null;
//        g.setFill(Color.GREEN);
//        g.fillRect(x - cameraX, y - cameraY, width, height);

        if(yMove < 0)
        {
            if(moveTimer <= 15)
            {
                g.drawImage(upRat, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <=30)
            {
                g.drawImage(upRat2, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <= 45)
            {
                g.drawImage(upRat3, x - cameraX, y - cameraY, 96, 96);
            }
            else
            {
                g.drawImage(upRat4, x - cameraX, y - cameraY, 96, 96);
            }
        }
        else
        {
            if(moveTimer <= 15)
            {
                g.drawImage(downRat, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <=30)
            {
                g.drawImage(downRat2, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <= 45)
            {
                g.drawImage(downRat3, x - cameraX, y - cameraY, 96, 96);
            }
            else
            {
                g.drawImage(downRat4, x - cameraX, y - cameraY, 96, 96);
            }
        }

//        // DEBUG: desenează hitbox-ul real
//        g.setStroke(Color.RED);
//        g.strokeRect(
//                x - cameraX + normalBounds.getX(),
//                y - cameraY + normalBounds.getY(),
//                normalBounds.getWidth(),
//                normalBounds.getHeight()
//        );
    }
}
