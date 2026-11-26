package com.comp2042.model;

public interface GameObserver {
    void onScoreChanged(int newScore);
    void onGameWon(boolean won);
    void onGameOver(boolean isOver);
}