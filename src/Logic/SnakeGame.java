package Logic;

import Handlers.LogicListener;
import Handlers.GraphicsListener;

public class SnakeGame implements Runnable, GraphicsListener {

    private LogicListener listener;

    public enum Directions {
        NORTH,
        WEST,
        EAST,
        SOUTH
    }

    private Directions direction;

    public SnakeGame(){

    }

    @Override
    public void run() {

    }

    @Override
    public void changeDirection(Directions direction) {

    }

    @Override
    public void stopGame() {

    }
}
