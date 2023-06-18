package Logic;

import Listeners.ChangeDirectionEvent;
import Listeners.GraphicsListener;
import Listeners.LogicListener;
import Listeners.NewGameEvent;
import Logic.SnakeGame.Directions;

import java.awt.*;

public class GameManager implements GraphicsListener {

    public LogicListener logicListener;
    private SnakeGame game;
    private boolean gameRunning;

    public GameManager(){

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

    @Override
    public void stopGame() {

    }

    public void setLogicListener(LogicListener logicListener){
        this.logicListener = logicListener;
    }
}
