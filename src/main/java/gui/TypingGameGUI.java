package gui;
import gameData.GameMapEnvironnment;
import gameData.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class TypingGameGUI {
    private JFrame frame;

    public TypingGameGUI() {
        frame = new JFrame("Typing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        frame.setLocationRelativeTo(null);
        // Create and add the main game panel
        GameMapEnvironnment gameMapEnvironnment = GameMapEnvironnment.getInstance();
        Player player = new Player(gameMapEnvironnment);
        GamePanel gamePanel = new GamePanel(player, gameMapEnvironnment);
        frame.add(gamePanel, BorderLayout.CENTER);
        gamePanel.movePlayer(5, 5);

        frame.setVisible(true);


        GameMapEnvironnment.getInstance().outputEnemyLocations();
    }

    public static void main(String[] args) {
        new TypingGameGUI();
    }
}

