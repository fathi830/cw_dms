package com.comp2042.model.coins;

import com.comp2042.controller.GameController;
import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

public class RainbowCoin extends Coin {
    public RainbowCoin(int x, int y, Snake snake, Obstacle obstacle) {
        super(x, y, snake, obstacle, 5, "resources/views/res/rainbowcoin.png");
    }

    public double getSpeedBonus() {
        return 5.0;
    }

    @Override
    public void applyEffect(Game game, Snake snake, Obstacle obstacle, GameController controller) {
        controller.setGameSpeed(game.getSpeed() + getSpeedBonus());
    }
}
