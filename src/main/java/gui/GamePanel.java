package gui;

import gameData.GameMapEnvironnment;
import gameData.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private JButton[][] grid;
    private int playerX = 0;
    private int playerY = 0;

    private Player currentPlayer;

    private GameMapEnvironnment gameMapEnvironnment;
    private int mapSize = GameMapEnvironnment.getInstance().getMaxBoundaries().x * 2 + 1;

    public GamePanel(Player player, GameMapEnvironnment gameMapEnvironnment){
        this.currentPlayer = player;
        this.gameMapEnvironnment = gameMapEnvironnment;
        int mapSize = GameMapEnvironnment.getInstance().getMaxBoundaries().x * 2 + 1;
        setLayout(new GridLayout(mapSize, mapSize));
        grid = new JButton[mapSize][mapSize];

        // Initialize grid with buttons
        for (int i = 0; i < mapSize ; i++) {
            for (int j = 0; j < mapSize; j++) {
                grid[i][j] = new JButton();
                add(grid[i][j]);
            }
        }

        // Set initial player position
        updatePlayerPosition();

        // Add key bindings for player movement
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moveUp");
        getActionMap().put("moveUp", new MoveAction(0, -1));

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moveDown");
        getActionMap().put("moveDown", new MoveAction(0, 1));

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moveLeft");
        getActionMap().put("moveLeft", new MoveAction(-1, 0));

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moveRight");
        getActionMap().put("moveRight", new MoveAction(1, 0));
    }

    private class MoveAction extends AbstractAction {
        private int dx;
        private int dy;

        public MoveAction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            movePlayer(dx, dy);
        }
    }

    public void updatePlayerPosition() {
        int mapSize = GameMapEnvironnment.getInstance().getMaxBoundaries().x * 2 + 1;
        // Clear previous position
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                grid[i][j].setText("");
            }
        }
        // Set new position for text "P"
        boolean isEnemyAtPlayerLocation = gameMapEnvironnment.isEnemyAtPlayerLocation(convertGUIIndexToPlayerPos(playerX, playerY));

        if(isEnemyAtPlayerLocation){
            grid[playerX][playerY].setText("E+P");
        } else if(GameMapEnvironnment.getInstance().isMerchantAtPlayerLocation(convertGUIIndexToPlayerPos(playerX, playerY))){
            grid[playerX][playerY].setText("M+P");
        } else {
            grid[playerX][playerY].setText("P");
        }


//        currentPlayer.setLocation(convertGUIIndexToPlayerPos(playerX, playerY));
        currentPlayer.setLocation(convertGUIIndexToPlayerPos(playerX, playerY));
        System.out.println("Player Pos: " + playerX + ", " + playerY);
        System.out.println("Player Location: " + currentPlayer.getLocation());
    }

    public void movePlayer(int dx, int dy) {
        playerX = Math.max(0, Math.min(mapSize-1, playerX + dx));
        playerY = Math.max(0, Math.min(mapSize-1, playerY + dy));
        updatePlayerPosition();

    }

    public Point convertPlayerPositionToGUIIndex(Player player){
        int playerX = player.getX();
        int playerY = player.getY();

        switch(playerX){
            case -5:
                playerX = 0;
                break;
            case -4:
                playerX = 1;
                break;
            case -3:
                playerX = 2;
                break;
            case -2:
                playerX = 3;
                break;
            case -1:
                playerX = 4;
                break;
            case 0:
                playerX = 5;
                break;
            case 1:
                playerX = 6;
                break;
            case 2:
                playerX = 7;
                break;
            case 3:
                playerX = 8;
                break;
            case 4:
                playerX = 9;
                break;
            case 5:
                playerX = 10;
                break;
        }

        switch(playerY){
            case -5:
                playerY = 0;
                break;
            case -4:
                playerY = 1;
                break;
            case -3:
                playerY = 2;
                break;
            case -2:
                playerY = 3;
                break;
            case -1:
                playerY = 4;
                break;
            case 0:
                playerY = 5;
                break;
            case 1:
                playerY = 6;
                break;
            case 2:
                playerY = 7;
                break;
            case 3:
                playerY = 8;
                break;
            case 4:
                playerY = 9;
                break;
            case 5:
                playerY = 10;
                break;
        }
        return new Point(playerX, playerY);
    }

    public Point convertGUIIndexToPlayerPos(int x, int y){
        Point playerPos = new Point(0, 0);
        switch (x){
            case 0:
                playerPos.x = -5;
                break;
            case 1:
                playerPos.x = -4;
                break;
            case 2:
                playerPos.x = -3;
                break;
            case 3:
                playerPos.x = -2;
                break;
            case 4:
                playerPos.x = -1;
                break;
            case 5:
                playerPos.x = 0;
                break;
            case 6:
                playerPos.x = 1;
                break;
            case 7:
                playerPos.x = 2;
                break;
            case 8:
                playerPos.x = 3;
                break;
            case 9:
                playerPos.x = 4;
                break;
            case 10:
                playerPos.x = 5;
                break;
        }
        switch (y){
            case 0:
                playerPos.y = -5;
                break;
            case 1:
                playerPos.y = -4;
                break;
            case 2:
                playerPos.y = -3;
                break;
            case 3:
                playerPos.y = -2;
                break;
            case 4:
                playerPos.y = -1;
                break;
            case 5:
                playerPos.y = 0;
                break;
            case 6:
                playerPos.y = 1;
                break;
            case 7:
                playerPos.y = 2;
                break;
            case 8:
                playerPos.y = 3;
                break;
            case 9:
                playerPos.y = 4;
                break;
            case 10:
                playerPos.y = 5;
                break;
        }
        return playerPos;
    }
}
