package Handlers;

public interface LogicListener {
    void gameOver();
    void gameTickEvent(int[][] board);
    void updateScore();
    void gameWon();
}
