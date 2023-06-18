package Listeners;

import Logic.SnakeGame.Directions;

public interface GraphicsListener {
    void newGame(NewGameEvent nge);
    void changeDirection(Directions direction);
    void stopGame();
}
