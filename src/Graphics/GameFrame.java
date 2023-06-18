package Graphics;

import Listeners.*;
import Logic.GameManager;
import Logic.SnakeGame;
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
    private String playerName;
    private int tick;
    private boolean gameRunning;
    private Directions currentDirection;

    public GameFrame(){

        this.gameRunning = false;
        addKeyListener(this);
        setFocusable(true);

        setTitle("GameFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 380));
        setMaximumSize(new Dimension(500, 380));
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        scorePanel = new ScorePanel();
        mainPanel.add(scorePanel, BorderLayout.WEST);

        boardTable = new BoardTable(16, 25);
        mainPanel.add(boardTable, BorderLayout.SOUTH);

        startGame = new JButton("Start");
        startGame.setPreferredSize(new Dimension(100, 30));
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
        this.graphicsListener.newGame(evt);
    }

    @Override
    public void crash(CrashEvent ce) {
        System.out.println("= KRASZUUUWAA");
        gameRunning = false;
    }

    @Override
    public void gameTick(GameTickEvent gte) {
        repaintTable(gte.getBoard());
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if(currentDirection != Directions.NORTH && currentDirection != Directions.SOUTH) {
                    graphicsListener.changeDirection(Directions.NORTH);
                    currentDirection = Directions.NORTH;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if(currentDirection != Directions.NORTH && currentDirection != Directions.SOUTH) {
                    graphicsListener.changeDirection(Directions.SOUTH);
                    currentDirection = Directions.SOUTH;
                }
            }
            case KeyEvent.VK_LEFT -> {
                if(currentDirection != Directions.WEST && currentDirection != Directions.EAST) {
                    graphicsListener.changeDirection(Directions.WEST);
                    currentDirection = Directions.WEST;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if(currentDirection != Directions.WEST && currentDirection != Directions.EAST) {
                    graphicsListener.changeDirection(Directions.EAST);
                    currentDirection = Directions.EAST;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
