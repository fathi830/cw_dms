package com.comp2042.model.coins;

import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

public class GoldCoin extends Coin {
    public GoldCoin(int x, int y, Snake snake, Obstacle obstacle) {
        super(x, y, snake, obstacle, 15, "resources/views/res/goldcoin.png");
    }
}
