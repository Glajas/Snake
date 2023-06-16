package Handlers;

import java.util.EventObject;

public class FoodEatenEvent extends EventObject {
    public FoodEatenEvent(Object source) {
        super(source);
    }
}
