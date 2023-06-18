import Graphics.*;
import Logic.*;

public class SnakeApp {
    public static void main(String[] args) {
        GameManager logic = new GameManager();
        GameFrame graphics = new GameFrame();
        graphics.setGraphicsListener(logic);
        logic.setLogicListener(graphics);
    }
}
