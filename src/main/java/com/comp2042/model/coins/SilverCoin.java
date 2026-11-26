package com.comp2042.model.coins;

import com.comp2042.controller.GameController;
import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

import java.util.Random;

public class SilverCoin extends Coin {
    public SilverCoin(int x, int y, Snake snake, Obstacle obstacle) {
        super(x, y, snake, obstacle, 5, "resources/views/res/silvercoin.png");
    }

    @Override
    public void applyEffect(Game game, Snake snake, Obstacle obstacle, GameController controller) {
        Random rnd = new Random();
        obstacle.newObstacles(snake, this, rnd.nextInt(game.getWidth() * game.getHeight()/ 20) +3);
        game.decMaxScore(obstacle.getObstacles().size());    }
}
