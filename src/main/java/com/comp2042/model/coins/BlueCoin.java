package com.comp2042.model.coins;

import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;

public class BlueCoin extends Coin {
    public BlueCoin(int x, int y, Snake snake, Obstacle obstacle) {
        super(x, y, snake, obstacle, 5, "resources/views/res/bluecoin.png");
    }
}
