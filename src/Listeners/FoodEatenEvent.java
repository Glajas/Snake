package Listeners;

import java.util.EventObject;

public class FoodEatenEvent extends EventObject {
    public FoodEatenEvent(Object source, int snakeLength) {
        super(source);
    }
}
