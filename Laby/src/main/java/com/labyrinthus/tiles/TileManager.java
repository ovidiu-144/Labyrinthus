package com.labyrinthus.tiles;

import com.labyrinthus.window.GamePanel;

import javafx.scene.canvas.GraphicsContext;
import com.labyrinthus.graphics.*;

public class TileManager extends Assets{
    protected GamePanel gp;
    public Tile[] tiles;
    public Tile[] tilesDung;
    public static int tileSize;

    //tiles-uri

    public TileManager(GamePanel gp){
        this.gp = gp;
        tileSize = gp.tileSize;
        tiles = new Tile[21];
        tilesDung = new Tile[37];
        InitLevel0();
        importTileLevel0();

        initDungeon();
        importTileDungeon();
    }

    public void importTileLevel0(){
        tiles[0] = new Tile (grass, 0, false);
        tiles[1] = new Tile (water,1, true);

        tiles[2] = new Tile (mountainUP1, 2, true);
        tiles[3] = new Tile (mountainUP2, 3, true);
        tiles[4] = new Tile (mountainUP3, 4, true);

        tiles[5] = new Tile (mountainCenter, 5, true);
        tiles[6] = new Tile (mountainDown, 6, true);

        tiles[7] = new Tile (entranceUP, 7, false);
        tiles[8] = new Tile (entranceDown, 8, false);

        tiles[9] = new Tile (pathUP, 9, false);
        tiles[10] = new Tile (pathCenter, 10, false);
        tiles[11] = new Tile (pathDown, 11, false);
        tiles[12] = new Tile (path, 12, false);

        tiles[13] = new Tile (waterEdgeUp, 13, true);
        tiles[14] = new Tile (waterEdgeRightUP, 14, true);
        tiles[15] = new Tile (waterEdgeRight, 15, true);
        tiles[16] = new Tile (waterEdgeRightDown, 16, true);
        tiles[17] = new Tile (waterEdgeDown, 17, true);
        tiles[18] = new Tile (waterEdgeLeftDown, 18, true);
        tiles[19] = new Tile (waterEdgeLeft, 19, true);
        tiles[20] = new Tile (waterEdgeLeftUp, 20, true);
    }

    public void importTileDungeon(){
        //Floor
        tilesDung[0] = new Tile(floorMiddle, 0);
        tilesDung[1] = new Tile(redUpFloor, 1);
        tilesDung[2] = new Tile(redUpLeftFloor, 2);
        tilesDung[3] = new Tile(redLeftFloor, 3);
        tilesDung[4] = new Tile(redDownLeftFloor, 4);
        tilesDung[5] = new Tile(redDownFloor, 5);
        tilesDung[6] = new Tile(redDownRightFloor, 6);
        tilesDung[7] = new Tile(redRightFloor, 7);
        tilesDung[8] = new Tile(redUpRightFloor, 8);

        //Wall+Pillars
        tilesDung[9] = new Tile(wall1, 9, true);
        tilesDung[10] = new Tile(wall2, 10, true);

        tilesDung[11] = new Tile(pillarUp, 11, true);
        tilesDung[12] = new Tile(pillarDown, 12, true);
        //Entrance
        tilesDung[13] = new Tile(dungEntranceUpLeft, 13, true);
        tilesDung[14] = new Tile(dungEntranceUp, 14, true);
        tilesDung[15] = new Tile(dungEntranceUpRight, 15, true);

        tilesDung[16] = new Tile(dungEntranceDownLeft, 16);
        tilesDung[17] = new Tile(dungEntranceDown, 17);
        tilesDung[18] = new Tile(dungEntranceDownRight, 18);

        //spikes
        tilesDung[19] = new Tile(spike1, 19);
        tilesDung[20] = new Tile(spike2, 20);

        //externWall
        tilesDung[21] = new Tile(externWall1, 21, true);
        tilesDung[22] = new Tile(externWall2, 22, true);

        //bricks
        tilesDung[23] = new Tile(brickUP, 23, true);
        tilesDung[24] = new Tile(brickLeftUp, 24, true);
        tilesDung[25] = new Tile(brickLeft, 25, true);
        tilesDung[26] = new Tile(brickLeftDown, 26, true);
        tilesDung[27] = new Tile(brickDown, 27, true);
        tilesDung[28] = new Tile(brickRightDown, 28, true);
        tilesDung[29] = new Tile(brickRight, 29, true);
        tilesDung[30] = new Tile(brickRightUp, 30, true);

        //Corners
        tilesDung[31] = new Tile(cornerUpLeft, 31, true);
        tilesDung[32] = new Tile(cornerUpRight, 32, true);
        tilesDung[33] = new Tile(cornerDownLeft, 33, true);
        tilesDung[34] = new Tile(cornerDownRight, 34, true);

        //tot spikes
        tilesDung[35] = new Tile(spike3, 35);
        tilesDung[36] = new Tile(spike4, 36);
    }

    public boolean hasCollision (int id, int level){
        if (level == 0){
            if (id >= 0 && id < 22)
                return tiles[id].collision;
        }
        else {
            if (id >= 0 && id < 37)
                return tilesDung[id].collision;
        }
        return false;
    }

    public void Draw(GraphicsContext g, double pixelX, double pixelY, int id, int level) {
        if (level == 0)
            g.drawImage(tiles[id].image, pixelX, pixelY, tileSize, tileSize);
        else
            g.drawImage(tilesDung[id].image, pixelX, pixelY, tileSize, tileSize);
    }
}
