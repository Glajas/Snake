package Handlers;

import java.util.EventObject;

public class CrashEvent extends EventObject {
    public CrashEvent(Object source) {
        super(source);
    }
}
