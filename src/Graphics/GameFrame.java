package Graphics;

import Listeners.LogicListener;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements Runnable, LogicListener, KeyListener {

    private BoardTable boardTable;

    @Override
    public void run() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void gameOver() {

    }

    public void repaintTable(){
        //Zaktualizuj wszystkie pola, aby odpowiadały polom tablicy w SnakeGame
    }

    @Override
    public void gameTickEvent(int[][] board) {
        repaintTable();
    }

    @Override
    public void updateScore() {

    }

    @Override
    public void gameWon() {

    }
}
