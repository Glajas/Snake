package Listeners;

public interface LogicListener {
    void crash(CrashEvent ce);
    void gameTick(GameTickEvent gte);
    void foodEaten(FoodEatenEvent fee);
}
