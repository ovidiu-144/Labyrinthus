package com.labyrinthus.camera;

import com.labyrinthus.items.Hero;
import com.labyrinthus.tiles.TileManager;

import java.awt.datatransfer.FlavorEvent;

public class Camera {
    private double x, y;
    private final int viewWidth, viewHeight;
    private final int tileSize;

    private final int[][] mat;
    private final TileManager tm;
    private final Hero hero;
    private final int level;
    private int id;

    public Camera(int viewWidth, int viewHeight, int tileSize, Hero hero, TileManager tm, int[][] mat, int level) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.tileSize = tileSize;
        this.hero = hero;
        this.tm = tm;
        this.mat = mat;
        this.level = level;

    }

    //tine playeru in mijlocu ecranului
    public void centerOn(double targetX, double targetY) {
        x = targetX + tileSize / 2.0 - viewWidth / 2.0;
        y = targetY + tileSize / 2.0 - viewHeight / 2.0;
//        x = viewWidth + targetX;
//        y = viewHeight + targetY;
    }

    public void updateCamera(){
        hero.yByCamera = (y + 8 * tileSize)/tileSize + 4.9;
        hero.xByCamera = (x + 12 * tileSize)/tileSize + 4;
        float speed = hero.GetSpeed();

        if (hero.keyH.upPressed){
            hero.yByCamera = ((hero.yByCamera * tileSize - speed)/tileSize);
            id = mat[(int)hero.xByCamera][(int)hero.yByCamera];
            if (!tm.hasCollision(id, level))
                y -= hero.GetSpeed();

//            System.out.println("id:..." + id + "collision" + tm.hasCollision(id, level));
//            System.out.println("Camera Y: " + y + "   Player X: " + x + "\n\n" +
//                    "Player Y: " + hero.yByCamera + "  Player X: " + hero.xByCamera +
//                    "\n------------------------------");
        }
        if (hero.keyH.downPressed){
            hero.yByCamera = ((hero.yByCamera * tileSize + speed)/tileSize);
            id = mat[(int)hero.xByCamera][(int)hero.yByCamera];
            if (!tm.hasCollision(id, level))
                y += hero.GetSpeed();


//            System.out.println("id:..." + id + "collision" + tm.hasCollision(id, level));
//            System.out.println("Camera Y: " + y + "   Player X: " + x + "\n\n" +
//                    "Player Y: " + hero.yByCamera + "  Player X: " + hero.xByCamera +
//                    "\n------------------------------");

        }
        if (hero.keyH.rightPressed){
            hero.xByCamera = ((hero.xByCamera * tileSize + speed)/tileSize);
            id = mat[(int)hero.xByCamera][(int)hero.yByCamera];
            if (!tm.hasCollision(id, level))
                x += hero.GetSpeed();


//            System.out.println("id:..." + id + "collision" + tm.hasCollision(id, level));
//            System.out.println("Camera Y: " + y + "   Player X: " + x + "\n\n" +
//                    "Player Y: " + hero.yByCamera + "  Player X: " + hero.xByCamera +
//                    "\n------------------------------");

        }
        if (hero.keyH.leftPressed){
            hero.xByCamera = ((hero.xByCamera * tileSize - speed)/tileSize);
            id = mat[(int)hero.xByCamera][(int)hero.yByCamera];
            if (!tm.hasCollision(id, level))
                x -= hero.GetSpeed();

//
//            System.out.println("id:..." + id + "collision" + tm.hasCollision(id, level));
//            System.out.println("Camera Y: " + y + "   Player X: " + x + "\n\n" +
//                    "Player Y: " + hero.yByCamera + "  Player X: " + hero.xByCamera +
//                    "\n------------------------------");
        }
    }

    public void spikeCollision(int spikeId){
        if (!hero.isInvulnerable) {
            if (id == 19) {
                switch (spikeId) {
                    case 20:
                        hero.SetLife(hero.GetALife() - 10);
                        hero.triggerInvulnerability();
                        break;
                    case 35:
                        hero.SetLife(hero.GetALife() - 20);
                        hero.triggerInvulnerability();
                        break;
                    case 36:
                        hero.SetLife(hero.GetALife() - 50);
                        hero.triggerInvulnerability();
                        break;
                }
            } else if (id == 36)
                hero.SetLife(hero.GetALife() - 10);
                hero.triggerInvulnerability();
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getXByCamera() {
        return hero.xByCamera;
    }

    public double getYByCamera() {
        return hero.yByCamera;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
}
