package com.labyrinthus.items;

import com.labyrinthus.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.labyrinthus.graphics.Assets.*;

public class Minotaur extends Enemy{

    private int moveTimer = 0;
    private Camera target;

    float epsilon = 4;

    enum Axis { X, Y }
    private Axis currentAxis = Axis.X;

    private static Minotaur instance = null;

    private Minotaur(float x, float y, int width, int height, Camera camera){
        super(x, y, width, height);
//        this.gp = gp;
        super.speed = 15;

        damage = 25;

        this.target = camera;

        normalBounds.setX(0);
        normalBounds.setY(16);
        normalBounds.setWidth(160);
        normalBounds.setHeight(220);

        life = 1000;
        InitMino();
    }

    public static void init(float x, float y, int width, int height, Camera camera){
        if (instance == null){
            instance = new Minotaur(x, y, width, height, camera);
        }
    }
    public static Minotaur getInstance(){
        if (instance == null){
            //System.out.println("Inca nu a fost creata o instanta");
            throw new IllegalStateException("Minotaur not initialized! Call init() first.");
        }
        return instance;
    }
//    public static Minotaur getInstance(float x, float y, int width, int height, Camera camera){
//        if (instance == null){
//            //System.out.println("Inca nu a fost creata o instanta");
//            instance = new Minotaur(x, y, width, height, camera);
//            //throw new IllegalStateException("Minotaur not initialized! Call init() first.");
//        }
//        return instance;
//    }

    public static void Reset (){
        instance = null;
    }

    @Override
    public void Update() {
        moveTimer++;
        if(moveTimer >= 60){
            moveTimer = 0;
        }

        float dx = (float)target.getX() + 14 * 48 - this.x;
        float dy = (float)target.getY() + 8 * 48 - this.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float detectionRadius = 24 * 48;

        xMove = 0;
        yMove = 0;

        if(distance <= detectionRadius)
        {
            if (currentAxis == Axis.X) {
                if (Math.abs(dx) > epsilon) {
                    xMove = (dx > 0) ? speed / 2 : -speed / 2;
                    yMove = 0;
                } else {
                    xMove = 0;
                    currentAxis = Axis.Y;
                }
            } else {
                if (Math.abs(dy) > epsilon) {
                    yMove = (dy > 0) ? speed / 2 : -speed / 2;
                    xMove = 0;
                } else {
                    yMove = 0;
                    currentAxis = Axis.X;
                }
            }
        }

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

        if(xMove > 0)
        {
            if(moveTimer < 15)
            {
                g.drawImage(rightMino, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 30)
            {
                g.drawImage(rightMino2, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 45)
            {
                g.drawImage(rightMino3, x - cameraX, y - cameraY, 160, 240);
            }
            else
            {
                g.drawImage(rightMino4, x - cameraX, y - cameraY, 160, 240);
            }
        }
        else if(xMove < 0)
        {
            if(moveTimer < 15)
            {
                g.drawImage(leftMino, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 30)
            {
                g.drawImage(leftMino2, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 45)
            {
                g.drawImage(leftMino3, x - cameraX, y - cameraY, 160, 240);
            }
            else
            {
                g.drawImage(leftMino4, x - cameraX, y - cameraY, 160, 240);
            }
        }
        else if(yMove > 0)
        {
            if(moveTimer < 15)
            {
                g.drawImage(downMino, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 30)
            {
                g.drawImage(downMino2, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 45)
            {
                g.drawImage(downMino3, x - cameraX, y - cameraY, 160, 240);
            }
            else
            {
                g.drawImage(downMino4, x - cameraX, y - cameraY, 160, 240);
            }
        }
        else if(yMove < 0)
        {
            if(moveTimer < 15)
            {
                g.drawImage(upMino, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 30)
            {
                g.drawImage(upMino2, x - cameraX, y - cameraY, 160, 240);
            }
            else if(moveTimer < 45)
            {
                g.drawImage(upMino3, x - cameraX, y - cameraY, 160, 240);
            }
            else
            {
                g.drawImage(upMino4, x - cameraX, y - cameraY, 160, 240);
            }
        }
        else
        {
            g.drawImage(downMino, x -cameraX, y - cameraY, 160, 240);
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