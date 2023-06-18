package Graphics;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private JTextField textField;
    private int score;

    public ScorePanel(){
        this.textField = new JTextField("Score: 0");
        setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(100, 60));
        this.add(textField);
    }

    public void addScore(){
        score++;
        textField.setText("Score: " + score);
    }

    public void reset() {
        textField.setText("Score: 0");
    }
}
