package Logic;

import java.util.Map;

public class Board {

    public enum fieldBinaryCode {
        FOOD,
        SNAKE_HEAD,
        SNAKE_BODY,
        SNAKE_TAIL,
        // SNAKE_TAIL ma się tworzyć na końcu węża, a poprzedni SNAKE_TAIL ma zamienić się w SNAKE_BODY
    }

    private final int[][] board;

    public Board() {
        board = new int[25][16];
    }

}
