import gameData.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        boolean isRunning = true;
        GameQuotes gameQuotes = new GameQuotes();
        PlayerCommands playerController = new PlayerCommands();
        GameMapEnvironnment gameMapEnvironnment =  new GameMapEnvironnment();
        Player player = new Player(gameMapEnvironnment);

        player.setGold(500);
        player.addWeapon(gameMapEnvironnment.getGameItems().getRandomWeapon());
        player.addItem(gameMapEnvironnment.getGameItems().getRandomItem());

        playerController.setGameMap(gameMapEnvironnment);
        playerController.setPlayer(player);

        outputAllItemsOnMap(gameMapEnvironnment);
        outputMerchantLocations(gameMapEnvironnment);

        welcomeMsg();

        while(isRunning) {
            // Game loop
            Scanner gameInput = new Scanner(System.in);

            String playerCommand = gameInput.nextLine();

            playerController.processCommand(playerCommand, player);
        }
    }

    private static void outputAllItemsOnMap(GameMapEnvironnment gameMapEnvironnment){
        HashMap<Point, Item> items = gameMapEnvironnment.getItemsOnMapLocation();

        System.out.println("Listing items that are currently spawned on the map\n");
        for(Map.Entry<Point, Item> in : items.entrySet()){
            System.out.println("Item Name: " + in.getValue().getName());
            System.out.println("Item Location on map: " + in.getKey().toString() + "\n");
        }
    }

    private static void outputMerchantLocations(GameMapEnvironnment gameMapEnvironnment){
        HashMap<Point, Merchant> merchants = gameMapEnvironnment.getMerchantsOnMapLocation();

        System.out.println("Listing merchants that are currently spawned on the map\n");
        for(Map.Entry<Point, Merchant> in : merchants.entrySet()){
            System.out.println("Merchant Location on map: " + in.getKey().toString() + "\n");
        }
    }

    private static void welcomeMsg(){
        System.out.println("Welcome to the game!");
        System.out.println("Here are the commands you can use: ");
        System.out.println("Forward, Back, Left, Right, Attack, Inventory, Quit");
    }
}
