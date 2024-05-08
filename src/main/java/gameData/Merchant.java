package gameData;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merchant {
    private HashMap<String, Integer> items;
    private HashMap<String, Integer> weapons;
    private int maxItems = 10;
    private int maxWeapons = 2;
    private GameItems gameItems;

    public Point location;

    public boolean isSelling = false;

    public boolean isBuying = false;

    public boolean isActive = false;

    public Merchant(Point point){
        location = point;
        this.items = new HashMap<>();
        this.weapons = new HashMap<>();
        gameItems = GameItems.getInstance();
        addItemsToMerchant();
    }

    public void addItemsToMerchant(){
        int numberOfItems = (int) (Math.random() * maxItems);
        int numberOfWeapons = (int) (Math.random() * maxWeapons);

        for(int i = 0; i < numberOfItems; i++){
            Item item = gameItems.getRandomItem();
            addItemToList(item, items);
        }

        for(int i = 0; i < numberOfWeapons; i++){
            Item weapon = gameItems.getRandomWeapon();
            addWeaponToList(weapon, weapons);
        }
    }

    public void addItemToList(Item item, HashMap<String, Integer> inList){
        inList.put(item.getName(), item.getValue());
    }

    //Weapons need to be separate from items, value is weapon damage * 6
    public void addWeaponToList(Item weapon, HashMap<String, Integer> inList){
        inList.put(weapon.getName(), gameItems.getWeaponPriceList().get(weapon.getName()));
    }

    public void outputInventory(){
        System.out.println("Merchant Inventory: ");
        System.out.println("Items: ");

        if(items.isEmpty()) {
            System.out.println("No items available");
        } else {
            for (String item : items.keySet()) {
                System.out.println(item + " - " + items.get(item));
            }
        }

        System.out.println("Weapons: ");
        if(weapons.isEmpty()){
            System.out.println("No weapons available");
        } else {
            for (String weapon : weapons.keySet()) {
                System.out.println(weapon + " - " + weapons.get(weapon));
            }
        }
    }

    public void buy(Scanner input, Player player){
        while(isBuying){
            System.out.println("What would you like to buy? buy + item name");
            System.out.println("You have " + player.getGold() + " gold");

            String userInput = input.nextLine().toLowerCase();

            if(userInput.equalsIgnoreCase("leave")){
                isBuying = false;
                return;
            }

            String item = userInput.replace("buy ", "");

            item = capitalizeInputToMatchMap(item);

            //Check if the item is in the merchant inventory and remove player gold
            if(items.containsKey(item)){
                //Check if player can fit the item in their inventory
                if(player.canPlayerAffordItem(item)) {
                    if(player.getInventory().getItemsList().size() < 10) {
                        System.out.println("You bought " + item + " for " + items.get(item));
                        player.getInventory().addItem(gameItems.getItemByName(item));
                        player.removeGold(items.get(item));
                        items.remove(item);
                    } else {
                        System.out.println("You can't carry anymore items");
                    }
                } else {
                    System.out.println("You can't afford this item");
                }
            } else if(weapons.containsKey(item)){
                //Player can afford the item
                if(player.canPlayerAffordItem(item)) {
                    //Can the player carry more weapons
                    if(player.getInventory().getWeaponsList().size() < 2) {
                        System.out.println("You bought " + item + " for " + gameItems.getWeaponPriceList().get(item));
                        player.getInventory().addWeapon(gameItems.getWeaponByName(item));
                        weapons.remove(item);
                        player.removeGold(gameItems.getWeaponPriceList().get(item));
                    } else {
                        System.out.println("You can't carry anymore weapons");
                    }
                } else {
                    System.out.println("You can't afford this item");
                }
            } else {
                System.out.println("Item not found");
            }
        }
    }

    public void sell(Scanner input, Player player){
        while (isSelling) {
            System.out.println("What would you like to sell? sell + item name");

            String userInput = input.nextLine().toLowerCase();

            if (userInput.equalsIgnoreCase("leave")) {
                isSelling = false;
                return;
            }

            String item = userInput.replace("sell ", "");

            item = capitalizeInputToMatchMap(item);

            if (player.getInventory().getItemsList().containsKey(item)) {
                System.out.println("You sold " + item + " for " + player.getInventory().getItemsList().get(item));
                player.getInventory().getItemsList().remove(item);

                //Adding item to merchant inventory
                addItemToList(gameItems.getItemByName(item), items);
            } else if (player.getInventory().getWeaponsList().containsKey(item)) {
                System.out.println("You sold " + item + " for " + gameItems.getWeaponPriceList().get(item));
                player.getInventory().getWeaponsList().remove(item);

                //Add weapon to merchant inventory
                addWeaponToList(gameItems.getWeaponByName(item), weapons);

                //Adding the value to the player's gold
                player.setGold(player.getGold() + gameItems.getWeaponPriceList().get(item));
            } else {
                System.out.println("Item not found");
            }
        }
    }

    private String capitalizeInputToMatchMap(String input){
        // Create a pattern to find the first letter of each word
        Pattern pattern = Pattern.compile("\\b[a-z]");
        Matcher matcher = pattern.matcher(input);

        // Buffer to build the result
        StringBuffer result = new StringBuffer();

        // Process each matching letter
        while (matcher.find()) {
            // Replace each matched letter with its uppercase version
            matcher.appendReplacement(result, matcher.group().toUpperCase());
        }
        // Append the last segment of the input to the result
        matcher.appendTail(result);

        return result.toString();
    }
}
