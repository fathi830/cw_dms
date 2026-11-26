package com.comp2042.model.coins;

import com.comp2042.controller.GameController;
import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

import java.awt.*;
import java.util.Random;

public class Coin {
    private Point pos;
    protected String imagePath;;
    private int points;

    public Coin(int x, int y, Snake snake, Obstacle obstacle, int points, String imagePath) {
        this.points = points;
        this.imagePath = imagePath;

        do {
            Random rnd = new Random();

            int tx = rnd.nextInt(x);
            int ty = rnd.nextInt(y);
            pos = new Point(tx, ty);
        } while (snake.getHead().equals(pos) || snake.getBody().contains(pos) || obstacle.getObstacles().contains(pos));
    }

    public Coin(int x, int y, Snake snake, Obstacle obstacle) {
        this(x, y, snake, obstacle, 5, "resources/views/res/redcoin.png");
    }

    public void applyEffect(Game game, Snake snake, Obstacle obstacle, GameController controller) {
        // Default coin has no special effect
    }

    public int getPoints() {
        return points;
    }
    public String getImagePath() { return imagePath; }
    public Point getPos() {
        return new Point(pos);
    }
}
