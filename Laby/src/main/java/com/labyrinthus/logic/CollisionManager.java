package com.labyrinthus.logic;

import com.labyrinthus.items.Entity;
import javafx.scene.shape.Rectangle;

public class CollisionManager {

    public static boolean checkCollision(Entity a, Entity b) {
        a.SetNormalMode();
        b.SetNormalMode();

        Rectangle rectA = a.getAbsoluteBounds();
        Rectangle rectB = b.getAbsoluteBounds();

        return rectA.intersects(rectB.getBoundsInLocal());
    }
}
