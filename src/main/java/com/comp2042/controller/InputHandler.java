package com.comp2042.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private static KeyCode key = KeyCode.LEFT;
    private static KeyCode lastKey = KeyCode.LEFT;
    private boolean keyPressed = false;
    private final GameController gameController;

    public static KeyCode getLastKey() {
        return lastKey;
    }

    public static KeyCode getKey() {
        return key;
    }

    public InputHandler(GameController gameController) {
        this.gameController = gameController;
    }

    public void handle(KeyEvent event) {
        if (event.getCode().isArrowKey()) gameController.startGame();

        if (valKey(event.getCode()) && !keyPressed) {
            lastKey = key;
            key = event.getCode();
            keyPressed = true;
            gameController.onDirectionChanged();
        }

        if (event.getCode().equals(KeyCode.P)) {
            gameController.pauseGame();
        }

        if (event.getCode().equals(KeyCode.TAB)) {
            gameController.reset();
        }

        if (event.getCode().equals(KeyCode.PLUS) || event.getCode().equals(KeyCode.EQUALS)) {
            gameController.setFullscreen(true);
        }

        if (event.getCode().equals(KeyCode.MINUS)) {
            gameController.setFullscreen(false);
        }

        if (event.getCode().equals(KeyCode.ESCAPE)) {
            gameController.exitToMenu();
        }
    }

    private boolean valKey(KeyCode key) {
        if (!key.isArrowKey()) return false;
        else {
            if (key.equals(KeyCode.LEFT)) if (!this.key.equals(KeyCode.RIGHT)) return true;
            if (key.equals(KeyCode.RIGHT)) if (!this.key.equals(KeyCode.LEFT)) return true;
            if (key.equals(KeyCode.DOWN)) if (!this.key.equals(KeyCode.UP)) return true;
            if (key.equals(KeyCode.UP)) return !this.key.equals(KeyCode.DOWN);
            return false;
        }
    }

    public void unlockInput() {
        keyPressed = false;
    }

    public void resetInput() {
        key = KeyCode.LEFT;
        keyPressed = false;
    }
}
