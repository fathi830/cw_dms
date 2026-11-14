package com.comp2042;

public class RainbowCoin extends Coin {
    public RainbowCoin(int x, int y, Snake snake, Obstacle obstacle) {
        super(x, y, snake, obstacle, 5, "resources/views/res/rainbowcoin.png");
    }

    public double getSpeedBonus() {
        return 5.0;
    }
}
