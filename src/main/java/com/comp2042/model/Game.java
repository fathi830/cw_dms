package com.comp2042.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final double EASY_SPEED = 8.5;
    private static final double HARD_SPEED = 13.0;

    private Color color = Color.web("feffe4");
    private int width;
    private int height;
    private int score;
    private int maxScore;
    private double multiplier;
    private int scorePoints;
    private double speed;
    private boolean powerUp;
    private boolean noBorder;
    private final List<GameObserver> observers = new ArrayList<>();

    public Game(int x, int y, boolean powerUp, char difficulty) {
        if (difficulty == 'E') {
            speed = EASY_SPEED;
            noBorder = true;
        } else if (difficulty == 'N') {
            speed = EASY_SPEED;
            noBorder = false;
        } else if (difficulty == 'H') {
            speed = HARD_SPEED;
            noBorder = false;
        }
        this.powerUp = powerUp;
        width = x;
        height = y;
        maxScore = x * y - 2;
        if (height > 50) {
            multiplier = 10;
        } else {
            multiplier = 20;
        }
    }

    public boolean gameWon() {
        return score == maxScore;
    }

    public void decMaxScore(int num) {
        this.maxScore -= (num - 1);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public boolean isPowerUp() {
        return powerUp;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isNoBorder() {
        return noBorder;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void resetScore() {
        score = 0;
        scorePoints = 0;
        notifyScoreChanged();
        notifyGameWon();
        notifyGameOver(false);
    }

    public void incScore() {
        score++;
        notifyGameWon();
    }

    public void addPoints(int n) {
        scorePoints += n;
        notifyScoreChanged();
    }

    private void notifyScoreChanged() {
        for (GameObserver o : observers) {
            o.onScoreChanged(getScorePoints());
        }
    }

    private void notifyGameWon() {
        for (GameObserver o : observers) {
            o.onGameWon(gameWon());
        }
    }

    public void notifyGameOver(boolean isOver) {
        for (GameObserver o : observers) {
            o.onGameOver(isOver);
        }
    }
}
