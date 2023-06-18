package Logic;

import Listeners.GraphicsListener;
import Listeners.LogicListener;
import Listeners.NewGameEvent;
import Logic.SnakeGame.Directions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GameManager implements GraphicsListener {

    public LogicListener logicListener;
    private SnakeGame game;
    private boolean gameRunning;

    public GameManager() {
        File file = new File("scores.bin");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e){
                System.out.println("Couldnt create file: " + e.getMessage());
            }
        }
    }

    @Override
    public void newGame(NewGameEvent newGameEvent) {
        if(!gameRunning){
            this.game = new SnakeGame(this, newGameEvent.getTick(), newGameEvent.getPlayerName());
            this.gameRunning = true;
            Thread gameThread = new Thread(game, "GameThread");
            gameThread.start();
        } else
            System.out.println("Game is already running");
    }

    @Override
    public void changeDirection(Directions direction) {
        game.setDirection(direction);
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void setLogicListener(LogicListener logicListener){
        this.logicListener = logicListener;
    }
}
