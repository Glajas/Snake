package Logic;

import Graphics.GameFrame;
import Listeners.*;

public class SnakeGame implements Runnable {

    private int[][] board;
    private int boardWidth = 25;
    private int boardHeight = 16;
    private boolean running;
    private int tick;
    private String playerName;
    private GameManager gameManager;
    private int snakeLength;
    private int headX;
    private int headY;
    private int tailX;
    private int tailY;

    public enum Directions {
        NORTH,
        WEST,
        EAST,
        SOUTH
    }

    private Directions direction;

    public SnakeGame(GameManager gameManager, int tick, String playerName){
        this.running = true;
        this.direction = null;
        this.playerName = playerName;
        this.tick = tick;
        this.board = new int[boardHeight][boardWidth];
        this.gameManager = gameManager;
        this.snakeLength = 3;
        this.board[5][5] = 1;
        this.headX = 5;
        this.headY = 5;
        this.board[11][20] = 3;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000/tick);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
            if(direction != null)
                move();
            gameManager.logicListener.gameTick(new GameTickEvent(this, board));
        }
    }

    public void move(){
        int newHeadX = headX;
        int newHeadY = headY;

        switch (direction){
            case NORTH -> {
                newHeadY--;
            }
            case WEST -> {
                newHeadX--;
            }
            case SOUTH -> {
                newHeadY++;
            }
            case EAST -> {
                newHeadX++;
            }
        }

        if(isCrash(newHeadX, newHeadY)) {
            System.out.println("Izka <3");
            gameManager.logicListener.crash(new CrashEvent(this));
            running = false;
        } else {
            if(isFood(newHeadX, newHeadY)){
                snakeLength++;
                gameManager.logicListener.foodEaten(new FoodEatenEvent(this, snakeLength));
                generateFood();
            }

            board[headY][headX] = 2;
            headX = newHeadX;
            headY = newHeadY;
            board[headY][headX] = 1;
        }
    }

    public boolean isFood(int newHeadX, int newHeadY){
        if(board[newHeadY][newHeadX] == 3){
            return true;
        } else
            return false;
    }

    public boolean isCrash(int newHeadX, int newHeadY){
        if(newHeadX < 0 || newHeadY >= boardHeight || newHeadY < 0 || newHeadX >= boardWidth){
            return true;
        } else {
            try {
                if(board[newHeadY][newHeadX] == 2)
                    return true;
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("ArrayIndexOutOfBoundsException");
                return true;
            }
        }
            return false;
    }

    public void generateFood(){
        boolean viablePosition = false;
        int randomX = (int)(Math.random()*25);
        int randomY = (int)(Math.random()*16);
        while(!viablePosition){
            if(board[randomY][randomX] == 0){
                viablePosition = true;
                board[randomY][randomX] = 3;
            } else {
                randomX = (int)(Math.random()*25);
                randomY = (int)(Math.random()*16);
            }
        }
    }

    public void setDirection(Directions direction){
        this.direction = direction;
    }
}
