package Graphics;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private int score;

    public ScorePanel() {
        this.score = 0;
        setPreferredSize(new Dimension(100, 60));
    }

    public void addScore() {
        score++;
        repaint();
    }

    public void reset() {
        score = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);

        String scoreText = "Score: " + score;
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(scoreText);
        int textHeight = metrics.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + metrics.getAscent();
        g.drawString(scoreText, x, y);
    }
}