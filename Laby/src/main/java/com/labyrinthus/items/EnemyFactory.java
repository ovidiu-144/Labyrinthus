package com.labyrinthus.items;

public class EnemyFactory {
    public Enemy enemyCreate(EnemyType type, float x, float y) {
        return switch (type) {
            case HORIZONTAL -> new HorizontalRat(x, y, 48, 48);
            case VERTICAL -> new VerticalRat(x, y, 48, 48);
            default -> throw new IllegalArgumentException("Unknown enemy type");
        };
    }
}