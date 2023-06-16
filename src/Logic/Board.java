package Logic;

import java.util.Map;

public class Board {

    public enum BoardFieldTypes {
        FOOD,
        FOOD_COOLDOWN,
        SNAKE_HEAD,
        SNAKE_BODY
    }

    private final int[][] board;

    public Board() {
        board = new int[25][16];
    }

}
