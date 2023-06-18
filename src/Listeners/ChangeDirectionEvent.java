package Listeners;

import Logic.SnakeGame.Directions;

import java.util.EventObject;

public class ChangeDirectionEvent extends EventObject {
    private Directions direction;
    public ChangeDirectionEvent(Object source, Directions direction) {
        super(source);
        this.direction = direction;
    }
    public Directions getDirection(){
        return this.direction;
    }
}
