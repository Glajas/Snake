package Listeners;

import Logic.SnakeGame.Directions;

import java.util.EventObject;

public class GameTickEvent extends EventObject {
    int[][] board;
    Directions direction;

    public GameTickEvent(Object source, int[][] board, Directions direction) {
        super(source);
        this.board = board;
        this.direction = direction;
    }

    public int[][] getBoard(){
        return this.board;
    }

    public Directions getDirection(){
        return this.direction;
    }
}
