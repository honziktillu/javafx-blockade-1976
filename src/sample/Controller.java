package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Canvas canvas;
    public static GraphicsContext gc;
    public ArrayList<String> input = new ArrayList<>();
    public static Player player;
    public static Player enemy;
    public Timeline round;
    public Timeline hideScore;
    public boolean canShowScore = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        player = new Player(140, 280, 20, 20, "Player", 1);
        enemy = new Player(440, 280, 20, 20, "Enemy", 3);
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleMovement();
                draw();
            }
        };
        gameLoop.start();
        player.update();
        enemy.update();
        round = new Timeline(new KeyFrame(
            Duration.millis(250),
            event -> {
                handleCollision(player);
                handleCollision(enemy);
                player.aiMove();
                enemy.aiMove();
            }
        ));
        round.setCycleCount(Animation.INDEFINITE);
        round.play();
        hideScore = new Timeline(new KeyFrame(
            Duration.millis(3000),
            event -> {
                canShowScore = false;
            }
        ));
        hideScore.setCycleCount(1);
    }

    private void draw() {
        gc.setFill(Paint.valueOf("BLACK"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        showScore();
        Body.draw();
    }

    private void handleMovement() {
        if (input.contains("W")) {
            if (player.getDirection() != 2) {
                player.setDirection(0);
            }
        }
        if (input.contains("A")) {
            if (player.getDirection() != 1) {
                player.setDirection(3);
            }
        }
        if (input.contains("S")) {
            if (player.getDirection() != 0) {
                player.setDirection(2);
            }
        }
        if (input.contains("D")) {
            if (player.getDirection() != 3) {
                player.setDirection(1);
            }
        }
    }

    public void handleCollision(Player p) {
        if (
                player.getX() < enemy.getX() + enemy.getWidth() &&
                player.getX() + player.getWidth() > enemy.getX() &&
                player.getY() < enemy.getY() + enemy.getHeight() &&
                player.getY() + player.getHeight() > enemy.getY()
        ) {
            resetRound(p);
            return;
        }
        for (Body b:
             Body.bodies) {
            if (
                    p.getX() < b.getX() + b.getWidth() &&
                    p.getX() + p.getWidth() > b.getX() &&
                    p.getY() < b.getY() + b.getHeight() &&
                    p.getY() + p.getHeight() > b.getY()
            ) {
                resetRound(p);
                break;
            }
        }
        if (p.getX() < 0 || p.getX() + p.getWidth() > canvas.getWidth() || p.getY() < 0 || p.getY() + p.getHeight() > canvas.getHeight()) {
            resetRound(p);
        }
    }

    private void resetRound(Player p) {
        if (p.getName().equals("Player")) {
            enemy.setScore(enemy.getScore() + 1);
        } else {
            player.setScore(player.getScore() + 1);
        }
        canShowScore = true;
        round.stop();
        Body.reset();
        round.setDelay(Duration.millis(3000));
        round.play();
        hideScore.play();
    }

    private void showScore() {
        if (canShowScore) {
            gc.setFill(Paint.valueOf("WHITE"));
            gc.setFont(new Font(25));
            gc.fillText(String.valueOf(player.getScore()), canvas.getWidth() / 2 - 100, 50, 100);
            gc.fillText(String.valueOf(enemy.getScore()), canvas.getWidth() / 2 + 100, 50, 100);
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (!input.contains(keyEvent.getCode().toString())) {
            input.add(keyEvent.getCode().toString());
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        input.remove(keyEvent.getCode().toString());
    }

}
