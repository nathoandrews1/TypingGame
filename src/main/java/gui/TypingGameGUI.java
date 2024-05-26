package gui;
import gameData.GameMapEnvironnment;
import gameData.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class TypingGameGUI {
    private JFrame frame;
    private StatsPanel statsPanel;
    private GamePanel gamePanel;

    public TypingGameGUI() {
        frame = new JFrame("Typing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        frame.setLocationRelativeTo(null);

        // Create and add the stats panel
        statsPanel = new StatsPanel();
        frame.add(statsPanel, BorderLayout.EAST);

        // Create the game map environment and player
        GameMapEnvironnment gameMapEnvironnment = GameMapEnvironnment.getInstance();
        Player player = new Player(gameMapEnvironnment);

        // Create and add the main game panel
        GamePanel gamePanel = new GamePanel(player, gameMapEnvironnment);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.setVisible(true);

        GameMapEnvironnment.getInstance().outputEnemyLocations();
    }

    public static void main(String[] args) {
        new TypingGameGUI();
    }
}

