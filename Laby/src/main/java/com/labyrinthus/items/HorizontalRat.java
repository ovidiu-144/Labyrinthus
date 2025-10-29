package com.labyrinthus.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.labyrinthus.graphics.Assets.*;
import static com.labyrinthus.graphics.Assets.downRat4;

public class HorizontalRat extends Enemy {
    private int moveTimer = 0;
    private int moveDirection = 1; // 1 = dreapta, -1 = stânga

    public HorizontalRat(float x, float y, int width, int height){
        super(x, y, width, height);
//        this.gp = gp;

        normalBounds.setX(0);
        normalBounds.setY(16);
        normalBounds.setWidth(96);
        normalBounds.setHeight(64);

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

        xMove = moveDirection * speed / 2; // mai lent decât jucătorul

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

//        g.drawImage(leftRat, x - cameraX, y - cameraY, 96, 96);

        if(xMove < 0)
        {
            if(moveTimer <= 15)
            {
                g.drawImage(leftRat, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <=30)
            {
                g.drawImage(leftRat2, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <= 45)
            {
                g.drawImage(leftRat3, x - cameraX, y - cameraY, 96, 96);
            }
            else
            {
                g.drawImage(leftRat4, x - cameraX, y - cameraY, 96, 96);
            }
        }
        else
        {
            if(moveTimer <= 15)
            {
                g.drawImage(rightRat, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <=30)
            {
                g.drawImage(rightRat2, x - cameraX, y - cameraY, 96, 96);
            }
            else if(moveTimer <= 45)
            {
                g.drawImage(rightRat3, x - cameraX, y - cameraY, 96, 96);
            }
            else
            {
                g.drawImage(rightRat4, x - cameraX, y - cameraY, 96, 96);
            }
        }



        // DEBUG: desenează hitbox-ul real
//        g.setStroke(Color.RED);
//        g.strokeRect(
//                x - cameraX + normalBounds.getX(),
//                y - cameraY + normalBounds.getY(),
//                normalBounds.getWidth(),
//                normalBounds.getHeight()
//        );
    }
}
