package Handlers;

import Logic.SnakeGame.Directions;

public interface GraphicsListener {
    void changeDirection(Directions direction);
    void stopGame();
}
