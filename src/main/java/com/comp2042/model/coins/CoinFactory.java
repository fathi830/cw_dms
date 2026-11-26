package com.comp2042.model.coins;

import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

import java.util.Random;

public class CoinFactory {
    private static final Random rnd = new Random();

    public static Coin createCoin(Game game, Snake snake, Obstacle obstacle){
        if (game.isPowerUp()) {
            Random rnd = new Random();
            int ran = rnd.nextInt(12);
            if (ran == 1) {
                return new GoldCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else if (ran == 2) {
                return new RainbowCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else if (ran == 3) {
                return new BlueCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else if (ran == 4 && obstacle.getObstacles().isEmpty()) {
                return new SilverCoin(game.getWidth(), game.getHeight(), snake, obstacle);
            } else {
                return new Coin(game.getWidth(), game.getHeight(), snake, obstacle);
            }
        }
        return new Coin(game.getWidth(), game.getHeight(), snake, obstacle);
    }
}
