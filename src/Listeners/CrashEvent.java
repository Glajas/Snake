package Listeners;

import java.util.EventObject;

public class CrashEvent extends EventObject {
    public CrashEvent(Object source, int score) {
        super(source);
    }
}
