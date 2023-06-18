package Listeners;

import java.util.EventObject;

public class NewGameEvent extends EventObject {

    private int tick;
    private String playerName;

    public NewGameEvent(Object source, int tick, String playerName) {
        super(source);
        this.tick = tick;
        this.playerName = playerName;
    }
    public int getTick(){
        return this.tick;
    }

    public String getPlayerName(){
        return this.playerName;
    }
}
