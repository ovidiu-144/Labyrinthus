package com.labyrinthus.graphics;

import javax.imageio.ImageIO;
import javafx.scene.image.*;
import java.io.IOException;

public class ImageLoader {
    public static Image LoadImage (String path){
        return new Image(ImageLoader.class.getResourceAsStream(path));
    }
}
