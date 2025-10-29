package com.labyrinthus.items;

import com.labyrinthus.camera.*;
import com.labyrinthus.window.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.labyrinthus.graphics.Assets;
import com.labyrinthus.input.KeyHandler;
import javafx.scene.shape.Rectangle;


import static com.labyrinthus.graphics.Assets.InitHero;
import static com.labyrinthus.graphics.Assets.sword;

public class Hero extends Entity{
    public final KeyHandler keyH;
    private final GamePanel gp;
    private String lastPressed;

    public double xByCamera;
    public double yByCamera;

    public boolean attacking = false;
    public int attackTimer = 0;
    public final int attackDuration = 20;

    public boolean isInvulnerable = false;
    public int invulnerabilityTimer = 0;
    public final int invulnerabilityDuration = 20;

    public int maxLife = 100;

    public Hero(float x, float y, int width, int height, KeyHandler keyH, GamePanel gp) {
        super(x, y, width, height);
        this.gp = gp;
        this.keyH = keyH;

        xByCamera = x;
        yByCamera = y;

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.setX(144); // (384 - 96) / 2, adică centrat la 96px
        normalBounds.setY(144);
        normalBounds.setWidth(64);
        normalBounds.setHeight(96);

        ///directia default a caracterului
        lastPressed = "up";
        direction = "up";

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.setX(0);
        attackBounds.setY(-19);
        attackBounds.setWidth(113);
        attackBounds.setHeight(44);

        super.speed = 15f;
        super.life = maxLife;

        InitHero();
    }

    private int spriteCounter = 0, spriteNum = 1;

    /// modificam pozitia caracterului in functie de taste
    @Override
    public void Update() {
        SetNormalMode();
        if (keyH.upPressed){
            direction = "up";
            lastPressed = "up";
        }
        if (keyH.downPressed){
            direction = "down";
            lastPressed = "down";
        }
        if (keyH.rightPressed){
            direction = "right";
            lastPressed = "right";
        }
        if (keyH.leftPressed){
            direction = "left";
            lastPressed = "left";
        }
        /// Daca nu apasam pe nimic sa ramana in starea "idle"
        if (!keyH.anyPressed()){
            direction = "idle";
        }
        spriteCounter++;
        if (spriteCounter > 10){
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;
            spriteCounter = 0;
        }

        if (attacking) {
            attackTimer++;
            if (attackTimer >= attackDuration) {
                attacking = false;
            }
        }

        if(isInvulnerable) {
            invulnerabilityTimer++;
            if(invulnerabilityTimer >= invulnerabilityDuration) {
                isInvulnerable = false;
            }
        }
    }

    /// Proiectam animatia pentru miscare
    @Override
    public void Draw(GraphicsContext g) {
        Image img = null;
        switch (direction){
            case "up":
                if (spriteNum == 1)
                    img = Assets.downHero2;
                else
                    img = Assets.downHero3;
                break;
            case "left":
                if (spriteNum == 1)
                    img = Assets.leftHero2;
                else
                    img = Assets.leftHero3;
                break;
            case "down":
                if (spriteNum == 1)
                    img = Assets.upHero2;
                else
                    img = Assets.upHero3;
                break;
            case "right":
                if (spriteNum == 1)
                    img = Assets.rightHero2;
                else
                    img = Assets.rightHero3;
                break;
            case "idle":
                img = switch (lastPressed) {
                    case "up" -> Assets.downHero;
                    case "left" -> Assets.leftHero;
                    case "right" -> Assets.rightHero;
                    case "down" -> Assets.upHero;
                    default -> img;
                };
        }

        g.drawImage(img, x, y, gp.tileSize * 8, gp.tileSize * 8);

//        g.setStroke(javafx.scene.paint.Color.BLUE);
//        g.strokeRect(
//                x + 3 * 48 + 16,
//                y + 3 * 48,
//                normalBounds.getWidth(),
//                normalBounds.getHeight()
//        );
    }

    public void drawSword(GraphicsContext g) {
        if (!attacking) return;

        double rectX;
        double rectY;
        double arcStart;

        switch(lastPressed){
            case "up":
                rectX = 14 * 48;
                rectY = 10.25 * 48;
                arcStart = -180;
                break;
            case "right":
                rectX = 14.75 * 48;
                rectY = 11.75 * 48;
                arcStart = -90;
                break;
            case "left":
                rectX = 13 * 48;
                rectY = 11.75 * 48;
                arcStart = 90;
                break;
            case "down":
                rectX = 14 * 48;
                rectY = 12.75 * 48;
                arcStart = 0;
                break;
            default:
                rectX = 14 * 48;
                rectY = 10.25 * 48;
                arcStart = -180;
        }

        double rectWidth = 100;
        double rectHeight = 20;

        double originX = rectX + rectWidth;
        double originY = rectY + rectHeight;

        double progress = (double) attackTimer / attackDuration;
        double currentAngle = arcStart + 180 * progress;

        double swordWidth = 128;
        double swordHeight = 128;

        g.save();
        g.translate(originX, originY);
        g.rotate(currentAngle);
        g.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        g.drawImage(sword,0, -swordHeight / 2, swordWidth, swordHeight);
//        g.strokeRect(
//                attackBounds.getX(),
//                attackBounds.getY(),
//                attackBounds.getWidth(),
//                attackBounds.getHeight()
//        );
        g.restore();
    }

    public void Draw(GraphicsContext g, double cameraX, double cameraY){}

    public Rectangle getAbsoluteBounds(Camera camera) {
        Rectangle r = new Rectangle();

        r.setX(x + camera.getX() + 3 * 48 + 16);
        r.setY(y + camera.getY() + 3 * 48);

        r.setWidth(bounds.getWidth());
        r.setHeight(bounds.getHeight());
        return r;
    }

    public Rectangle getSwordBounds(Camera camera) {
        if (!attacking) return null;

        Rectangle r = new Rectangle();

        switch(lastPressed){
            case "up":
                r.setX(14 * 48 - 10 + camera.getX());
                r.setY(10.25 * 48 - 70 + camera.getY());
                r.setWidth(attackBounds.getWidth() * 2);
                r.setHeight(attackBounds.getHeight() * 2.5);
                break;
            case "right":
                r.setX(14.75 * 48 + 90 + camera.getX());
                r.setY(11.75 * 48 - 90 + camera.getY());
                r.setWidth(attackBounds.getWidth() * 2);
                r.setHeight(attackBounds.getHeight() * 2.5);
                break;
            case "left":
                r.setX(13 * 48 + camera.getX());
                r.setY(11.75 * 48 - 90 + camera.getY());
                r.setWidth(attackBounds.getWidth());
                r.setHeight(attackBounds.getHeight() * 5);
                break;
            case "down":
                r.setX(14 * 48 - 10 + camera.getX());
                r.setY(10.25 * 48 + 12 + camera.getY());
                r.setWidth(attackBounds.getWidth());
                r.setHeight(attackBounds.getHeight() * 5);
                break;
            default:
                r.setX(14 * 48 - 10 + camera.getX());
                r.setY(10.25 * 48 - 70 + camera.getY());
                r.setWidth(attackBounds.getWidth() * 2);
                r.setHeight(attackBounds.getHeight() * 2.5);
        }

        return r;
    }

    public void startAttack() {
        if (!attacking) {
            this.attacking = true;
            this.attackTimer = 0;
        }
    }

    public void triggerInvulnerability(){
        if(!isInvulnerable) {
            this.isInvulnerable = true;
            this.invulnerabilityTimer = 0;
        }
    }

}