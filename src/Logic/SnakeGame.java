package Logic;

import Listeners.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SnakeGame implements Runnable {

    private int[][] board;
    private int boardWidth = 25;
    private int boardHeight = 16;
    private boolean running;
    private int tick;
    private String playerName;
    private GameManager gameManager;
    private int snakeLength;
    private int headX;
    private int headY;
    private Deque<Integer> bodyX;
    private Deque<Integer> bodyY;

    public enum Directions {
        NORTH,
        WEST,
        EAST,
        SOUTH
    }

    private Directions direction;

    public SnakeGame(GameManager gameManager, int tick, String playerName){
        this.running = true;
        this.direction = null;
        this.playerName = playerName;
        this.tick = tick;
        this.board = new int[boardHeight][boardWidth];
        this.gameManager = gameManager;
        this.snakeLength = 1;
        this.board[5][5] = 1;
        this.headX = 5;
        this.headY = 5;
        this.bodyX = new ArrayDeque<>();
        this.bodyY = new ArrayDeque<>();
        this.board[11][20] = 3;
    }

    @Override
    public void run() {
        System.out.println("NEW GAME");
        while (running) {
            try {
                Thread.sleep(1000/tick);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
            if (direction != null)
                move();
            gameManager.logicListener.gameTick(new GameTickEvent(this, board, direction));
        }
    }

    public void move() {
        int newHeadX = headX;
        int newHeadY = headY;

        switch (direction) {
            case NORTH:
                newHeadY--;
                break;
            case WEST:
                newHeadX--;
                break;
            case SOUTH:
                newHeadY++;
                break;
            case EAST:
                newHeadX++;
                break;
        }

        if (isCrash(newHeadX, newHeadY)) {
            gameManager.logicListener.crash(new CrashEvent(this, snakeLength - 3));
            saveScore();
            gameManager.setGameRunning(false);
            running = false;
            System.out.println("GAME STOPPED");
        } else {
            if (isFood(newHeadX, newHeadY)) {
                snakeLength++;
                gameManager.logicListener.foodEaten(new FoodEatenEvent(this, snakeLength));
                generateFood();
            } else {
                if (snakeLength > 1) {
                    board[bodyY.getLast()][bodyX.getLast()] = 0;
                    bodyX.removeLast();
                    bodyY.removeLast();
                }
            }
            if (snakeLength > 1) {
                bodyX.addFirst(headX);
                bodyY.addFirst(headY);
                board[headY][headX] = 2;
            }

            if(snakeLength == 1)
                board[headY][headX] = 0;

            headX = newHeadX;
            headY = newHeadY;
            board[headY][headX] = 1;
        }
    }

    public boolean isFood(int newHeadX, int newHeadY) {
        if (board[newHeadY][newHeadX] == 3) {
            return true;
        } else
            return false;
    }

    public boolean isCrash(int newHeadX, int newHeadY) {
        if (newHeadX < 0 || newHeadY >= boardHeight || newHeadY < 0 || newHeadX >= boardWidth) {
            return true;
        } else {
            try {
                if (board[newHeadY][newHeadX] == 2)
                    return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException");
                return true;
            }
        }
        return false;
    }

    public void generateFood() {
        boolean viablePosition = false;
        int randomX = (int) (Math.random() * 25);
        int randomY = (int) (Math.random() * 16);
        while (!viablePosition) {
            if (board[randomY][randomX] == 0) {
                viablePosition = true;
                board[randomY][randomX] = 3;
            } else {
                randomX = (int) (Math.random() * 25);
                randomY = (int) (Math.random() * 16);
            }
        }
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    private void saveScore(){
        int score = snakeLength - 1;
        String name = playerName;
        int nameLength = name.length();
        String[] result = {String.valueOf(nameLength), name, String.valueOf(score)};
        ArrayList<String[]> currentScoreboard = scoresAndNamesArrayList();
        currentScoreboard.add(result);
        sortScoreArrayList(currentScoreboard);

        if(currentScoreboard.size() > 10)
            currentScoreboard.remove(currentScoreboard.size());

        try (FileOutputStream output = new FileOutputStream("scores.bin", true)) {
                for (String[] s : currentScoreboard) {
                    output.write(s[0].getBytes());
                    for (char ch : divideStringIntoChars(s[1])) {
                        output.write(ch);
                    }
                    output.write(getByteArray(Integer.parseInt(s[2])));
                }
            } catch (IOException e) {
                System.out.println("Error saving scores (IOException): " + e.getMessage());
            }
    }

    private ArrayList<String[]> sortScoreArrayList(ArrayList<String[]> scoresArrayList){
        Collections.sort(scoresArrayList, (table1, table2) -> {
            int value1 = Integer.parseInt(table1[2]);
            int value2 = Integer.parseInt(table2[2]);
            return value2 - value1;
        });

//        for(String[] s : scoresArrayList){
//            for (String x : s){
//                System.out.println(x);
//            }
//            System.out.println();
//        }

        return scoresArrayList;
    }

    private byte[] getByteArray(int number){
        byte[]byteArray = new byte[4];
        for (int i=0; i<byteArray.length; i++) {
            byteArray[3 - i] = (byte)(number & 0xFF);
            number >>= 8;
        }
        return byteArray;
    }

    private int readScore(FileInputStream in) throws IOException {
        int score = 0;
        byte[] bytes = new byte[4];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) in.read();
        }
        for (byte b : bytes) {
            score = (score << 8) + (b & 0xFF);
        }
        return score;
    }

    private char[] divideStringIntoChars(String name){
        char[] chars = new char[name.length()];
        for(int i = 0; i < name.length(); i++)
            chars[i] = name.charAt(i);
        return chars;
    }


    public ArrayList<String[]> scoresAndNamesArrayList(){
        ArrayList<String[]> scoresList = new ArrayList<>();
        try (FileInputStream input = new FileInputStream("scores.bin")) {
            while(input.available() > 0){
                int nameLength = input.read();
                String name = "";
                char nameLetter;

                for(int i = 0; i < nameLength; i++) {
                    nameLetter = (char) input.read();
                    name += nameLetter;
                }

                int score = readScore(input);
                String[] result = {String.valueOf(nameLength), name, String.valueOf(score)};

                scoresList.add(result);
            }
        } catch (IOException e) {
            System.out.println("Error loading scores (IOException): " + e.getMessage());
        }
        return scoresList;
    }
}

//    private void saveScore(){
//        int score = snakeLength - 1;
//        if(isScoreInHighscore(score)){
//            String name = playerName;
//            int nameLength = name.length();
//            try (FileOutputStream output = new FileOutputStream("scores.bin", true)) {
//                output.write(nameLength);
//                for(char ch : divideStringIntoChars(name)){
//                    output.write(ch);
//                }
//                output.write(getByteArray(score));
//            } catch (IOException e) {
//                System.out.println("Error saving scores (IOException): " + e.getMessage());
//            }
//        }
//        else
//            System.out.println("Your score is too bad to get into the highscores scoreboard LOL");
//    }
//    private boolean isScoreInHighscore(int score){
//        ArrayList<Integer> scoresList = scoresArrayList();
//        if(scoresList.size() == 10){
//            for(Integer i : scoresList){
//                if(score > i) {
//                    return true;
//
//                }
//            }
//            return false;
//        } else
//            return true;
//    }

//    private ArrayList<Integer> scoresArrayList() {
//        ArrayList<Integer> scoresList = new ArrayList<>();
//        try (FileInputStream input = new FileInputStream("scores.bin")) {
//            while(input.available() > 0){
//                int nameLength = input.read();
//
//                for(int i = 0; i < nameLength; i++)
//                    input.read();
//
//                scoresList.add(readScore(input));
//            }
//        } catch (IOException e) {
//            System.out.println("Error loading scores (IOException): " + e.getMessage());
//        }
//        return scoresList;
//    }
