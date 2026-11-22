package com.comp2042.view;

import com.comp2042.controller.GameController;
import com.comp2042.model.Snake;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;

public class SnakeRenderer {
    private int width;
    private int height;
    private int[][] turnArray;

    private Image imgInputHead = new Image("resources/views/res/snakehead.png");
    private ImageView imageHead = new ImageView(imgInputHead);
    private Image imgInputBody = new Image("resources/views/res/snakebody.png");
    private ImageView imageBody = new ImageView(imgInputBody);
    private Image imgInputTurn = new Image("resources/views/res/snaketurnleft.png");
    private ImageView imageTurn = new ImageView(imgInputTurn);
    private Image imgInputTail = new Image("resources/views/res/snaketail.png");
    private ImageView imageTail = new ImageView(imgInputTail);

    public SnakeRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        turnArray = new int[width][height];
    }

    public void turnDir(Snake snake) {
        if ((GameController.getLastKey() == KeyCode.LEFT && GameController.getKey() == KeyCode.UP) || (GameController.getLastKey() == KeyCode.DOWN && GameController.getKey() == KeyCode.RIGHT)) {
            turnArray[(int) snake.getHead().getX()][(int) snake.getHead().getY()] = 2;
        } else if ((GameController.getLastKey() == KeyCode.LEFT && GameController.getKey() == KeyCode.DOWN) || (GameController.getLastKey() == KeyCode.UP && GameController.getKey() == KeyCode.RIGHT)) {
            turnArray[(int) snake.getHead().getX()][(int) snake.getHead().getY()] = 3;
        } else if ((GameController.getLastKey() == KeyCode.RIGHT && GameController.getKey() == KeyCode.UP) || (GameController.getLastKey() == KeyCode.DOWN && GameController.getKey() == KeyCode.LEFT)) {
            turnArray[(int) snake.getHead().getX()][(int) snake.getHead().getY()] = 1;
        } else if ((GameController.getLastKey() == KeyCode.UP && GameController.getKey() == KeyCode.LEFT) || (GameController.getLastKey() == KeyCode.RIGHT && GameController.getKey() == KeyCode.DOWN)) {
            turnArray[(int) snake.getHead().getX()][(int) snake.getHead().getY()] = 4;
        } else turnArray[(int) snake.getHead().getX()][(int) snake.getHead().getY()] = 0;
    }

    public void resetTurns(){
        turnArray = new int[width][height];
    }

    private void drawBody(Snake snake, Point point, int index, GraphicsContext gc, double cellSize, double fullScale) {
        Image rotatedImage;
        if (snake.getBody().size() <= 1) {
            imageTail.setRotate(snake.getSnakeDir());
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(javafx.scene.paint.Color.TRANSPARENT);
            rotatedImage = imageTail.snapshot(params, null);
        } else if (index == 0) {
            int rotateTail;
            if (snake.getBody().get(1).getX() == snake.getBody().get(0).getX() && snake.getBody().get(1).getY() == snake.getBody().get(0).getY() - 1) {
                rotateTail = 0;
            } else if (snake.getBody().get(1).getX() == snake.getBody().get(0).getX() && snake.getBody().get(1).getY() == snake.getBody().get(0).getY() + 1) {
                rotateTail = 180;
            } else if (snake.getBody().get(1).getX() == snake.getBody().get(0).getX() - 1 && snake.getBody().get(1).getY() == snake.getBody().get(0).getY()) {
                rotateTail = 270;
            } else {
                rotateTail = 90;
            }
            imageTail.setRotate(rotateTail);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(javafx.scene.paint.Color.TRANSPARENT);
            rotatedImage = imageTail.snapshot(params, null);
        } else if (index > 0 && (turnArray[(int) snake.getBody().get(index).getX()][(int) snake.getBody().get(index).getY()] != 0)) {
            imageTurn.setRotate(90 * turnArray[(int) snake.getBody().get(index).getX()][(int) snake.getBody().get(index).getY()]);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(javafx.scene.paint.Color.TRANSPARENT);
            rotatedImage = imageTurn.snapshot(params, null);
        } else {
            imageBody.setRotate(snake.getBodyDir(index));
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            rotatedImage = imageBody.snapshot(params, null);
        }

        if (turnArray[(int) snake.getBody().get(0).getX()][(int) snake.getBody().get(0).getY()] != 0) {
            turnArray[(int) snake.getBody().get(0).getX()][(int) snake.getBody().get(0).getY()] = 0;
        }

        gc.drawImage(rotatedImage, (point.x * cellSize) * fullScale, (point.y * cellSize) * fullScale, cellSize * fullScale, cellSize * fullScale);
    }

    private void drawHead(Snake snake, GraphicsContext gc, double cellSize, double fullScale) {
        imageHead.setRotate(snake.getSnakeDir());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageHead.snapshot(params, null);
        gc.drawImage(rotatedImage, (snake.getHead().x * cellSize) * fullScale, (snake.getHead().y * cellSize) * fullScale, cellSize * fullScale, cellSize * fullScale);
    }

    public void draw(GraphicsContext gc, Snake snake, double cellSize, double fullScale){
        int bodyIndex = 0;
        for (Point point : snake.getBody()) {
            drawBody(snake, point, bodyIndex, gc, cellSize, fullScale);
            bodyIndex++;
        }
        drawHead(snake, gc, cellSize, fullScale);
    }
}
