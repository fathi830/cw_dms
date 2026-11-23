package com.comp2042.controller;

import com.comp2042.model.Game;
import com.comp2042.utill.SoundManager;
import com.comp2042.view.GameView;
import com.comp2042.model.coins.*;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class GameController {
    private Snake snake;
    private Coin coin;
    private GameView gameView;
    private Game game;
    private Stage stage;
    private Obstacle obstacle;
    private SoundManager soundManager;
    private InputHandler inputHandler;


    public GameController(Snake snake, Coin coin, Game game, GameView gameView, Obstacle obstacle, Stage stage) {
        this.snake = snake;
        this.coin = coin;
        this.gameView = gameView;
        this.game = game;
        this.stage = stage;
        this.obstacle = obstacle;
        this.soundManager = new SoundManager();
        this.inputHandler = new InputHandler(this);

        animation.setCycleCount(Animation.INDEFINITE);
        animation.setRate(game.getSpeed());
    }

    private final Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        if (snake.getDead()) {
            return;
        } else {

            if (InputHandler.getKey().equals(KeyCode.LEFT)) {
                snake.move('L');
            } else if (InputHandler.getKey().equals(KeyCode.RIGHT)) {
                snake.move('R');
            } else if (InputHandler.getKey().equals(KeyCode.UP)) {
                snake.move('U');
            } else if (InputHandler.getKey().equals(KeyCode.DOWN)) {
                snake.move('D');
            }

            if (!snake.getDead() && snake.ateCoin(coin)) {
                soundManager.playCoinEatenSound();

                powerCoin();
                game.incScore();
                if (game.gameWon()) {
                    gameView.drawGrid(coin, snake, obstacle, gameView.getGc(), false, 0);
                    return;
                }
                newCoin();
            }

            inputHandler.unlockInput();

            if (!snake.getDead() || snake.hitSelf()) {
                gameView.drawGrid(coin, snake, obstacle, gameView.getGc(), false, 0);
            }

            gameView.gameWon();
            gameView.gameOver(snake);
            gameView.updateScoreLabel();
        }
    }));

    public void reset() {

        animation.jumpTo(Duration.ZERO);
        animation.stop();

        snake = new Snake(game.getWidth(), game.getHeight(), game.isNoBorder(), obstacle);
        coin = new Coin(game.getWidth(), game.getHeight(), snake, obstacle);

        int unusedObstacleCount = new Random().nextInt(game.getWidth() * game.getHeight() / 20) + 3;

        animation.setRate(game.getSpeed());
        inputHandler.resetInput();
        game.resetScore();
        obstacle.reset();

        gameView.gameWon();
        gameView.gameOver(snake);
        gameView.updateScoreLabel();

        gameView.drawGrid(coin, snake, obstacle, gameView.getGc(), false, 0);
        gameView.resetTurns();
    }

    private void powerCoin() {
        animation.setRate(game.getSpeed());
        snake.ignoreBorders(false);
        if (coin.getClass().equals(RainbowCoin.class)) {
            RainbowCoin tempCoin = (RainbowCoin) coin;
            animation.setRate(game.getSpeed() + tempCoin.getSpeedBonus());
        } else if (coin.getClass().equals(SilverCoin.class)){
            Random rnd = new Random();
            obstacle.newObstacles(snake, coin, rnd.nextInt(game.getWidth() * game.getHeight()/ 20) +3);
            game.decMaxScore(obstacle.getObstacles().size());
        } else if (coin.getClass().equals(BlueCoin.class)) {
            snake.ignoreBorders(true);
        }
        game.addPoints(coin.getPoints());
    }

    private void newCoin() {
        coin = CoinFactory.createCoin(game, snake, obstacle);
    }

    public void startGame() {
        animation.play();
    }

    public void pauseGame() {
        animation.stop();
    }

    public void onDirectionChanged(){
        gameView.turnDir(snake);
    }

    public void setFullscreen(boolean full){
        gameView.scaleToFullscreen(full, stage, obstacle, snake, coin);
    }

    public void exitToMenu(){
        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.getViewManager().getPrimaryStage().show();
        mainMenuController.getViewManager().getGameStage().close();
        animation.stop();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

}
