package Listeners;

import java.util.EventObject;

public class GameTickEvent extends EventObject {
    int[][] board;
    public GameTickEvent(Object source, int[][] board) {
        super(source);
        this.board = board;
    }

    public int[][] getBoard(){
        return this.board;
    }
}
