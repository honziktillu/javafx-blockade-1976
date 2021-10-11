package sample;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private double startX;
    private double startY;
    private int startDirection;
    private int direction;
    private double x;
    private double y;
    private double width;
    private double height;
    private int score = 0;
    private String name;
    private ArrayList<String> possibleAiMoves = new ArrayList<>();
    private String oldMove = "";

    public Player(double x, double y, double width, double height, String name, int startDirection) {
        this.startDirection = startDirection;
        this.direction = startDirection;
        this.startX = x;
        this.startY = y;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        Body.bodies.add(new Body(x, y, width, height));
    }

    public void update() {
        switch (direction) {
            case 0:
                y -= height;
                Body.bodies.add(new Body(x, y + height, width, height));
                break;
            case 1:
                x += width;
                Body.bodies.add(new Body(x - width, y, width, height));
                break;
            case 2:
                y += height;
                Body.bodies.add(new Body(x, y - height, width, height));
                break;
            case 3:
                x -= width;
                Body.bodies.add(new Body(x + width, y, width, height));
                break;
        }
    }

    public void reset() {
        x = startX;
        y = startY;
        direction = startDirection;
    }

    public void aiMove() {
        if (oldMove.equals("right")) {
            if (isRightMovePossible()) {
                aiMoveRight();
                oldMove = "";
                return;
            } else {
                oldMove = "";
            }
        }

        if (direction != 1 && isLeftMovePossible()) {
            possibleAiMoves.add("left");
        }
        if (direction != 3 && isRightMovePossible()) {
            possibleAiMoves.add("right");
        }
        if (direction != 0 && isBottomMovePossible()) {
            possibleAiMoves.add("bottom");
        }
        if (direction != 2 && isTopMovePossible()) {
            possibleAiMoves.add("top");
        }

        if (!possibleAiMoves.isEmpty()) {
            Random random = new Random();
            int randomNumber = random.nextInt(possibleAiMoves.size());
            switch (possibleAiMoves.get(randomNumber)) {
                case "left":
                    aiMoveLeft();
                    break;
                case "right":
                    oldMove = "right";
                    aiMoveRight();
                    break;
                case "top":
                    aiMoveTop();
                    break;
                case "bottom":
                    aiMoveBottom();
                    break;
            }
            possibleAiMoves.clear();
        } else {
            aiMoveTop();
        }

    }

    private boolean isLeftMovePossible() {
        boolean state = true;
        for (Body b : Body.bodies) {
            if (b.getX() == x - width && b.getY() == y || x - width < 0) {
                state = false;
                break;
            }
        }
        return state;
    }

    private boolean isRightMovePossible() {
        boolean state = true;
        for (Body b : Body.bodies) {
            if (b.getX() == x + width && b.getY() == y || x + (width * 2) > 600) {
                state = false;
                break;
            }
        }
        return state;
    }

    private boolean isTopMovePossible() {
        boolean state = true;
        for (Body b : Body.bodies) {
            if (b.getX() == x && b.getY() == y - height || y - height < 0) {
                state = false;
                break;
            }
        }
        return state;
    }

    private boolean isBottomMovePossible() {
        boolean state = true;
        for (Body b : Body.bodies) {
            if (b.getX() == x && b.getY() == y + height || y + (height * 2) > 600) {
                state = false;
                break;
            }
        }
        return state;
    }

    private void aiMoveTop() {
        setDirection(0);
        update();
    }

    private void aiMoveRight() {
        setDirection(1);
        update();
    }

    private void aiMoveBottom() {
        setDirection(2);
        update();
    }

    private void aiMoveLeft() {
        setDirection(3);
        update();
    }




    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public int getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(int startDirection) {
        this.startDirection = startDirection;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPossibleAiMoves() {
        return possibleAiMoves;
    }

    public void setPossibleAiMoves(ArrayList<String> possibleAiMoves) {
        this.possibleAiMoves = possibleAiMoves;
    }

    public String getOldMove() {
        return oldMove;
    }

    public void setOldMove(String oldMove) {
        this.oldMove = oldMove;
    }
}
