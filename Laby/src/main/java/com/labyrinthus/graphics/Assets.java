package com.labyrinthus.graphics;

import javafx.scene.image.*;

public class Assets {
    //and others
    public static Image grass;
    public static Image water;
    public static Image mountainDown;
    public static Image mountainCenter;

    public static Image mountainUP1;
    public static Image mountainUP2;
    public static Image mountainUP3;

    public static Image entranceUP;
    public static Image entranceDown;

    public static Image pathUP;
    public static Image pathCenter;
    public static Image pathDown;
    public static Image path;

    public static Image waterEdgeUp;
    public static Image waterEdgeRightUP;
    public static Image waterEdgeRight;
    public static Image waterEdgeRightDown;
    public static Image waterEdgeDown;
    public static Image waterEdgeLeftDown;
    public static Image waterEdgeLeft;
    public static Image waterEdgeLeftUp;

    //Pun toate Asset-urile aici
    //Cu metode diferite pentru fiecare // Maps, Hero, Minotaur, Rat

    //HERO
    public static Image upHero;
    public static Image downHero;
    public static Image leftHero;
    public static Image rightHero;

    public static Image upHero2;
    public static Image downHero2;
    public static Image leftHero2;
    public static Image rightHero2;

    public static Image upHero3;
    public static Image downHero3;
    public static Image leftHero3;
    public static Image rightHero3;

    //Dungeon
    /// FLOOR
    public static Image floorMiddle;

    public static Image redUpFloor;
    public static Image redUpLeftFloor;
    public static Image redDownLeftFloor;
    public static Image redLeftFloor;
    public static Image redDownFloor;
    public static Image redDownRightFloor;
    public static Image redRightFloor;
    public static Image redUpRightFloor;

    /// WALLS
    public static Image wall1;
    public static Image wall2;

    public static Image externWall1;
    public static Image externWall2;

    public static Image pillarUp;
    public static Image pillarDown;

    public static Image dungEntranceUpLeft;
    public static Image dungEntranceUp;
    public static Image dungEntranceUpRight;
    public static Image dungEntranceDownLeft;
    public static Image dungEntranceDown;
    public static Image dungEntranceDownRight;

    /// SPIKES
    public static Image spike1;
    public static Image spike2;
    public static Image spike3;
    public static Image spike4;


    /// BRICKS
    public static Image brickUP;
    public static Image brickDown;
    public static Image brickLeftUp;
    public static Image brickLeft;
    public static Image brickLeftDown;
    public static Image brickRightDown;
    public static Image brickRight;
    public static Image brickRightUp;

    /// BRICKS corners
    public static Image cornerUpLeft;
    public static Image cornerUpRight;
    public static Image cornerDownLeft;
    public static Image cornerDownRight;

    ///RAT
    public static Image leftRat;
    public static Image leftRat2;
    public static Image leftRat3;
    public static Image leftRat4;

    public static Image rightRat;
    public static Image rightRat2;
    public static Image rightRat3;
    public static Image rightRat4;

    public static Image upRat;
    public static Image upRat2;
    public static Image upRat3;
    public static Image upRat4;

    public static Image downRat;
    public static Image downRat2;
    public static Image downRat3;
    public static Image downRat4;

    /// Minotaur
    public static Image downMino;
    public static Image downMino2;
    public static Image downMino3;
    public static Image downMino4;

    public static Image upMino;
    public static Image upMino2;
    public static Image upMino3;
    public static Image upMino4;

    public static Image leftMino;
    public static Image leftMino2;
    public static Image leftMino3;
    public static Image leftMino4;

    public static Image rightMino;
    public static Image rightMino2;
    public static Image rightMino3;
    public static Image rightMino4;

    //sword
    public static Image sword;

    public static void InitLevel0(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/Level0.png"));

        grass = sheet.crop(3, 12);
        water = sheet.crop(16, 15);

        mountainUP1 = sheet.crop(7, 16);
        mountainUP2 = sheet.crop(8, 16);
        mountainUP3 = sheet.crop(8, 16);
        mountainCenter = sheet.crop(7, 17);
        mountainDown = sheet.crop(7, 18);

        entranceUP = sheet.crop(15, 17);
        entranceDown = sheet.crop(15,18);

        pathUP = sheet.crop(9, 11);
        pathCenter = sheet.crop(7, 11);
        pathDown = sheet.crop(6, 13);
        path = sheet.crop(13, 13);

        waterEdgeUp = sheet.crop(16, 14);
        waterEdgeRightUP = sheet.crop(17, 14);
        waterEdgeRight = sheet.crop(17, 15);
        waterEdgeRightDown = sheet.crop(17, 16);
        waterEdgeDown =  sheet.crop(16, 16);
        waterEdgeLeftDown =  sheet.crop(15, 16);
        waterEdgeLeft =  sheet.crop(15, 15);
        waterEdgeLeftUp =  sheet.crop(15, 14);
    }

    public static void InitHero(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/character_Idle.png"));
        upHero = sheet.crop(0, 0, 128);
        leftHero = sheet.crop(0, 1, 128);
        rightHero = sheet.crop(0, 2, 128);
        downHero = sheet.crop(0, 3, 128);


        sheet.setImg(ImageLoader.LoadImage("/textures/character_Move.png"));
        upHero2 = sheet.crop(1, 0, 128);
        upHero3 = sheet.crop(3, 0, 128);

        leftHero2 = sheet.crop(1, 1, 128);
        leftHero3 = sheet.crop(3, 1, 128);

        rightHero2 = sheet.crop(1,2,128);
        rightHero3 = sheet.crop(3, 2, 128);

        downHero2 = sheet.crop(1, 3, 128);
        downHero3 = sheet.crop(3, 3, 128);

        sword = ImageLoader.LoadImage("/textures/sword.png");
    }

    public static void initDungeon(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/redFloor.png"));

        //FLOOR
        floorMiddle = sheet.crop(1, 2, 32);
        redUpFloor = sheet.crop(1, 1, 32);
        redUpLeftFloor = sheet.crop(0, 1, 32);
        redLeftFloor = sheet.crop(0, 2, 32);
        redDownLeftFloor = sheet.crop(0, 3, 32);
        redDownFloor = sheet.crop(1, 3, 32);
        redDownRightFloor = sheet.crop(2, 3, 32);
        redRightFloor = sheet.crop(2, 2, 32);
        redUpRightFloor = sheet.crop(2, 1, 32);

        //WALLS
        sheet.setImg(ImageLoader.LoadImage("/textures/evildungeon.png"));
        wall1 = sheet.crop(0, 3, 32);
        wall2 = sheet.crop(0, 4, 32);

        externWall1 = sheet.crop(6, 3, 32);
        externWall2 = sheet.crop(6, 4, 32);

        pillarUp = sheet.crop(1, 3, 32);
        pillarDown = sheet.crop(1, 4, 32);

        dungEntranceUpLeft = sheet.crop(4, 6, 32);
        dungEntranceUp = sheet.crop(5, 6, 32);
        dungEntranceUpRight = sheet.crop(6, 6, 32);

        dungEntranceDownLeft = sheet.crop(4, 7, 32);
        dungEntranceDown = sheet.crop(5, 7, 32);
        dungEntranceDownRight = sheet.crop(6, 7, 32);

        //spikes
        //spikes
        sheet.setImg(ImageLoader.LoadImage("/textures/spikes.png"));
        spike1 = sheet.crop(0, 0, 32);
        spike2 = sheet.crop(1, 0, 32);
        spike3 = sheet.crop(2, 0, 32);
        spike4 = sheet.crop(3, 0, 32);

        //Bricks
        sheet.setImg(ImageLoader.LoadImage("/textures/bricks.png"));
        brickUP = sheet.crop(1, 1, 32);
        brickLeftUp = sheet.crop(0, 1, 32);
        brickLeft = sheet.crop(0, 2, 32);
        brickLeftDown = sheet.crop(0, 3, 32);
        brickRightDown = sheet.crop(2, 3, 32);
        brickRight = sheet.crop(2, 2, 32);
        brickRightUp = sheet.crop(2,1, 32);
        brickDown = sheet.crop(1, 3, 32);

        //BricksCorners

//        cornerUpLeft = sheet.crop(0, 0, 16);
//        cornerUpRight = sheet.crop(0, 0, 16);
//        cornerDownRight = sheet.crop(0, 0, 16);
//        cornerDownLeft = sheet.crop(0, 0, 16);

        cornerUpLeft = ImageLoader.LoadImage("/textures/cornerLeftUp.png");
        cornerUpRight = ImageLoader.LoadImage("/textures/cornerRightUp.png");
        cornerDownRight = ImageLoader.LoadImage("/textures/cornerRightDown.png");
        cornerDownLeft = ImageLoader.LoadImage("/textures/cornerLeftDown.png");

//        cornerUpLeft = sheet.crop(2, 0, 32);
//        cornerUpRight = sheet.crop(2, 0, 32);
//        cornerDownRight = sheet.crop(2, 0, 32);
//        cornerDownLeft = sheet.crop(2, 0, 32);



    }

    public static void InitRat(){
        leftRat = ImageLoader.LoadImage("/textures/rat_left.png");
        leftRat2 = ImageLoader.LoadImage("/textures/rat_left2.png");
        leftRat3 = ImageLoader.LoadImage("/textures/rat_left3.png");
        leftRat4 = ImageLoader.LoadImage("/textures/rat_left4.png");

        rightRat = ImageLoader.LoadImage("/textures/rat_right.png");
        rightRat2 = ImageLoader.LoadImage("/textures/rat_right2.png");
        rightRat3 = ImageLoader.LoadImage("/textures/rat_right3.png");
        rightRat4 = ImageLoader.LoadImage("/textures/rat_right4.png");

        upRat = ImageLoader.LoadImage("/textures/rat_up.png");
        upRat2 = ImageLoader.LoadImage("/textures/rat_up2.png");
        upRat3 = ImageLoader.LoadImage("/textures/rat_up3.png");
        upRat4 = ImageLoader.LoadImage("/textures/rat_up4.png");

        downRat = ImageLoader.LoadImage("/textures/rat_down.png");
        downRat2 = ImageLoader.LoadImage("/textures/rat_down2.png");
        downRat3 = ImageLoader.LoadImage("/textures/rat_down3.png");
        downRat4 = ImageLoader.LoadImage("/textures/rat_down4.png");
    }

    public static void InitMino(){
        downMino = ImageLoader.LoadImage("/textures/image1x1.png");
        downMino2 = ImageLoader.LoadImage("/textures/image2x1.png");
        downMino3 = ImageLoader.LoadImage("/textures/image3x1.png");
        downMino4 = ImageLoader.LoadImage("/textures/image4x1.png");

        leftMino = ImageLoader.LoadImage("/textures/image1x2.png");
        leftMino2 = ImageLoader.LoadImage("/textures/image2x2.png");
        leftMino3 = ImageLoader.LoadImage("/textures/image3x2.png");
        leftMino4 = ImageLoader.LoadImage("/textures/image4x2.png");

        rightMino = ImageLoader.LoadImage("/textures/image1x3.png");
        rightMino2 = ImageLoader.LoadImage("/textures/image2x3.png");
        rightMino3 = ImageLoader.LoadImage("/textures/image3x3.png");
        rightMino4 = ImageLoader.LoadImage("/textures/image4x3.png");

        upMino = ImageLoader.LoadImage("/textures/image1x4.png");
        upMino2 = ImageLoader.LoadImage("/textures/image2x4.png");
        upMino3 = ImageLoader.LoadImage("/textures/image3x4.png");
        upMino4 = ImageLoader.LoadImage("/textures/image4x4.png");


    }
}