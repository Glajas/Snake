package Graphics;

import Listeners.*;

import Logic.SnakeGame.Directions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

public class GameFrame extends JFrame implements LogicListener, KeyListener {

    private GraphicsListener graphicsListener;
    private BoardTable boardTable;
    private ScorePanel scorePanel;
    private JButton startGame;
    private ScoreBoardPanel scoreBoard;
    public JPanel mainPanel;
    private String playerName;
    private int tick;
    private boolean gameRunning;
    private Directions currentDirection;
    private Directions lastDirection;

    public GameFrame(){

        this.gameRunning = false;
        addKeyListener(this);
        setFocusable(true);

        setTitle("GameFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 380));
        setMaximumSize(new Dimension(500, 380));
        setResizable(false);

        mainPanel = new JPanel(new BorderLayout());

        scorePanel = new ScorePanel();
        mainPanel.add(scorePanel, BorderLayout.WEST);

        boardTable = new BoardTable(16, 25);
        mainPanel.add(boardTable, BorderLayout.SOUTH);

        scoreBoard = new ScoreBoardPanel();

        startGame = new JButton("Start");
        startGame.setMinimumSize(new Dimension(100, 30));
        startGame.setMaximumSize(new Dimension(100, 30));
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean gameConfirmed = false;
                String playerNameInput = "Player";
                int tickInput = 5;

                while (!gameConfirmed) {

                    JPanel optionPanel = new JPanel();
                    optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

                    JTextField inputField = new JTextField();
                    optionPanel.add(inputField);

                    JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JSlider tickInputSlider = new JSlider(1,11,6);
                    tickInputSlider.setMajorTickSpacing(5);
                    tickInputSlider.setMinorTickSpacing(1);
                    tickInputSlider.setPaintTicks(true);

                    Hashtable<Integer, JLabel> labels = new Hashtable<>();
                    labels.put(1, new JLabel("1"));
                    labels.put(6, new JLabel("6"));
                    labels.put(11, new JLabel("11"));
                    tickInputSlider.setLabelTable(labels);

                    tickInputSlider.setPaintLabels(true);

                    sliderPanel.add(tickInputSlider);
                    optionPanel.add(sliderPanel);

                    int option = JOptionPane.showOptionDialog(
                            null,
                            optionPanel,
                            "Enter your name and game tickrate",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            null
                    );

                    if (option == JOptionPane.OK_OPTION) {
                        if(!inputField.getText().trim().equals("")){
                            playerNameInput = inputField.getText();
                        } else
                            playerNameInput = "Unknown Player";
                        System.out.println("Player Name: " + playerNameInput);
                        tickInput = tickInputSlider.getValue();
                        gameConfirmed = true;
                        gameRunning = true;
                        GameFrame.this.startGame.setVisible(false);
                    } else {
                        System.out.println("Please enter your name...");
                    }

                    scorePanel.reset();

                    playerName = playerNameInput;
                    tick = tickInput;
                }

                EventQueue.invokeLater(
                        ()-> {
                            fireNewGame(
                                    new NewGameEvent(this, tick, playerName)
                            );
                        }
                );

                if(scoreBoard.isVisible()) {
                    scoreBoard.setVisible(false);
                    mainPanel.remove(scoreBoard);
                    mainPanel.add(boardTable, BorderLayout.SOUTH);
                }
            }
        });

        mainPanel.add(startGame, BorderLayout.EAST);

        setContentPane(mainPanel);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void repaintTable(int[][] board){
        for(int row = 0; row < board.length; row++){
            for(int column = 0; column < board[row].length; column++){
                switch (board[row][column]){
                    // EMPTY CELL
                    case 0 -> {
                        boardTable.setValueAt(Color.GRAY, row, column);
                    }
                    // SNAKE HEAD
                    case 1 -> {
                        boardTable.setValueAt(Color.GREEN, row, column);
                    }
                    // SNAKE BODY
                    case 2 -> {
                        boardTable.setValueAt(Color.ORANGE, row, column);
                    }
                    // FOOD
                    case 3 -> {
                        boardTable.setValueAt(Color.RED, row, column);
                    }
                }
            }
        }
    }

    public void fireNewGame(NewGameEvent evt){
        if(this.graphicsListener != null){
            this.graphicsListener.newGame(evt);
        } else
            System.out.println("Couldn't start the game, couldn't find the graphics listener");
    }

    @Override
    public void crash(CrashEvent ce) {
        reset();
        mainPanel.remove(boardTable);
        mainPanel.add(scoreBoard, BorderLayout.SOUTH);
        scoreBoard.setVisible(true);
    }

    @Override
    public void gameTick(GameTickEvent gte) {
        repaintTable(gte.getBoard());
        lastDirection = gte.getDirection();
    }

    @Override
    public void foodEaten(FoodEatenEvent fee) {
        scorePanel.addScore();
        scorePanel.revalidate();
        scorePanel.repaint();
    }

    public void setGraphicsListener(GraphicsListener graphicsListener){
        this.graphicsListener = graphicsListener;
    }

    public void reset() {
        gameRunning = false;
        currentDirection = null;
        startGame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if(currentDirection != Directions.SOUTH && lastDirection != Directions.SOUTH) {
                    currentDirection = Directions.NORTH;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if(currentDirection != Directions.NORTH && lastDirection != Directions.NORTH) {
                    currentDirection = Directions.SOUTH;
                }
            }
            case KeyEvent.VK_LEFT -> {
                if(currentDirection != Directions.EAST && lastDirection != Directions.EAST) {
                    currentDirection = Directions.WEST;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if(currentDirection != Directions.WEST && lastDirection != Directions.WEST) {
                    currentDirection = Directions.EAST;
                }
            }
        }
        graphicsListener.changeDirection(currentDirection);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
