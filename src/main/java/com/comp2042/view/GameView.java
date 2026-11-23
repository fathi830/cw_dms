package com.comp2042.view;

import com.comp2042.model.Game;
import com.comp2042.model.GameObserver;
import com.comp2042.model.coins.Coin;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameView implements GameObserver {

    private Canvas gridCanvas;
    private GraphicsContext gc;
    private Label gameOverLabel;
    private Label gameWonLabel;
    private Label scoreLabel;
    private boolean gameOverVis = false;
    private boolean gameWonVis = false;
    private Game game;
    private double initX;
    private double initY;
    private double fullScale = 1;
    private boolean fullsize = false;
    private SnakeRenderer snakeRenderer;
    private final Map<String, Image> coinImageCache = new HashMap<>();

    public GameView(Game game) {
        this.game = game;
        this.game.addObserver(this);
        this.snakeRenderer = new SnakeRenderer(game.getWidth(), game.getHeight());
    }

    public void makeScene(Scene scene, Snake snake, Coin coin) {
        Scene gameScene = scene;
        gridCanvas = new Canvas(gameScene.getWidth(), gameScene.getHeight());
        initX = scene.getWidth();
        initY = scene.getHeight();
        gc = gridCanvas.getGraphicsContext2D();
        drawGrid(coin, snake, gc);

        gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setId("gameOverLabel");
        String css = "resources/views/styling.css";
        gameOverLabel.getStylesheets().add(css);
        gameOverLabel.setVisible(false);

        gameWonLabel = new Label("YOU WON!");
        gameWonLabel.setId("gameWonLabel");
        gameWonLabel.getStylesheets().add(css);
        gameWonLabel.setVisible(false);

        scoreLabel = new Label("Score: " + game.getScorePoints());
        scoreLabel.setId("scoreLabel");
        scoreLabel.getStylesheets().add(css);
        scoreLabel.setFont(new Font("Arial", 20));
        scoreLabel.setVisible(true);
    }

    public void scaleToFullscreen(boolean fullScreen, Stage primaryStage, Obstacle obstacle, Snake snake, Coin coin) {
        primaryStage.setFullScreen(fullScreen);

        double xScaling = Screen.getPrimary().getVisualBounds().getHeight() / initY;
        double yScaling = Screen.getPrimary().getVisualBounds().getWidth() / initX;

        if (fullScreen && !fullsize) {
            if (xScaling < yScaling) {
                fullScale = xScaling;
            } else {
                fullScale = yScaling;
            }
            gridCanvas.setWidth(gridCanvas.getWidth() * fullScale);
            gridCanvas.setHeight(gridCanvas.getHeight() * fullScale);
            fullsize = true;
            gameOverLabel.setStyle("-fx-font-size: 50;");
            gameWonLabel.setStyle("-fx-font-size: 50;");
            scoreLabel.setStyle("-fx-font-size: 35;");
        } else if (!fullScreen) {
            fullScale = 1;
            gridCanvas.setWidth(initX);
            gridCanvas.setHeight(initY);
            fullsize = false;
            gameOverLabel.setStyle("-fx-font-size: 15;");
            gameWonLabel.setStyle("-fx-font-size: 15;");
            scoreLabel.setStyle("-fx-font-size: 15;");
        }

        gameOverLabel.setFont(new Font("Arial", 25 * fullScale));
        gameWonLabel.setFont(new Font("Arial", 25 * fullScale));
        drawGrid(coin, snake, obstacle, gc, false, 0);
    }

    public void drawGrid(Coin coin, Snake snake, Obstacle obstacle, GraphicsContext gc, boolean a, int b) {
        gc.setFill(game.getColor());
        gc.fillRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());

        gc.drawImage(getCoinImage(coin), (coin.getPos().x * game.getMultiplier()) * fullScale, (coin.getPos().y * game.getMultiplier()) * fullScale, game.getMultiplier() * fullScale - 3 * fullScale, game.getMultiplier() * fullScale - 3 * fullScale);

        snakeRenderer.draw(gc, snake, game.getMultiplier(), fullScale);

        gc.setFill(obstacle.getColor());
        for (Point point : obstacle.getObstacles()) {
            gc.fillRect(point.x * game.getMultiplier() * fullScale, point.y * game.getMultiplier() * fullScale, game.getMultiplier() * fullScale, game.getMultiplier() * fullScale);
        }
    }

    private void drawGrid(Coin coin, Snake snake, GraphicsContext gc) {
        gc.setFill(game.getColor());
        gc.fillRect(0, 0, gridCanvas.getWidth(), gridCanvas.getHeight());

        gc.drawImage(getCoinImage(coin), (coin.getPos().x * game.getMultiplier()) * fullScale, (coin.getPos().y * game.getMultiplier()) * fullScale, game.getMultiplier() * fullScale - 3 * fullScale, game.getMultiplier() * fullScale - 3 * fullScale);

        snakeRenderer.draw(gc, snake, game.getMultiplier(), fullScale);
    }

    public void onScoreChanged(int newScore){
        if (scoreLabel != null){
            scoreLabel.setText("Score: " + newScore);
        }
    }

    public void onGameWon(boolean won){
        if (gameWonLabel != null){
            gameWonVis = won;
            gameWonLabel.setVisible(won);
        }
    }

    public void onGameOver(boolean isOver){
        if  (gameOverLabel != null){
            gameOverVis = isOver;
            gameOverLabel.setVisible(isOver);
        }
    }

    public void turnDir(Snake snake) {
        snakeRenderer.turnDir(snake);
    }

    public void resetTurns() {
        snakeRenderer.resetTurns();
    }

    public Canvas getGridCanvas() {
        return gridCanvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public Label getGameOverLabel() {
        return gameOverLabel;
    }

    public Label getGameWonLabel() {
        return gameWonLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    private javafx.scene.image.Image getCoinImage(Coin coin) {
        return coinImageCache.computeIfAbsent(coin.getImagePath(), Image::new);
    }

}
