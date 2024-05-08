package gameData;

public class PlayerCommands {

    private static GameMapEnvironnment gameMap;
    private static Player player;

public static void processCommand(String command, Player player) {
        switch (command.toLowerCase()) {
            case "forward":
            case "back":
            case "left":
            case "right":
                player.move(command);

                if(gameMap.isEnemyAtPlayerLocation(player)){
                    System.out.println("You are in combat!");
                    player.setInCombat(true);
                    player.playerInCombat(command);
                }

                //Player is in bounds
                if(player.isItemFoundAtLocation()){
                    player.waitForPlayerToPickupOrLeave(command);
                }

                if(gameMap.isMerchantAtPlayerLocation(player)){
                    player.inMerchant = true;
                    player.waitForPlayerToInteractWithMerchant(command);
                }

                System.out.println("Player Pos: " + player.getX() + ", " + player.getY());
                break;
            case "inventory":
                player.outputAllInventory();
                break;
            case "enemyspawns":
                gameMap.outputEnemyLocations();
                break;
            case "showdamage":
                System.out.println("Player damage: " + player.getDamage());
                break;
            case "quit":
                System.out.println("Quitting game...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid command");
        }
    }

    public void setGameMap(GameMapEnvironnment gameMap) {
        this.gameMap = gameMap;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
