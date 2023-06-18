package Graphics;

import javax.swing.*;
import java.awt.*;

public class ScoreBoardPanel extends JPanel {

    JTable highscoresTable;
    JPanel highscoresPanel;

    public ScoreBoardPanel() {
        setLayout(new BorderLayout());

        highscoresTable = new JTable(10, 2);

        highscoresPanel = new JPanel();
        highscoresPanel.add(highscoresTable);
        highscoresPanel.setMinimumSize(new Dimension(520, 300));
        highscoresPanel.setMaximumSize(new Dimension(520, 300));

        add(highscoresPanel, BorderLayout.CENTER);
    }
}
