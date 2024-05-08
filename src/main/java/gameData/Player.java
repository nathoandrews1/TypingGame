package gameData;

import java.awt.*;
import java.util.Map;
import java.util.Scanner;

public class Player {

    GameMapEnvironnment gameMapEnvironnment;

    PlayerInventory playerInventory = new PlayerInventory();

    public Player(GameMapEnvironnment gameMapEnvironnment){
        this.gameMapEnvironnment = gameMapEnvironnment;
        health = 100;
        gold = 0;
        totalWeaponDamage = 0;
    }

    private int gold = 0;

    private int totalWeaponDamage = 0;
    private int x = 0;
    private int y = 0;
    private int punchingDamage = 10;
    private boolean inCombat = false;

    public boolean inMerchant = false;
    private int health = 100;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean isInBounds(int bounds){
        if(bounds > gameMapEnvironnment.maxBoundaries.x)
        {
            return false;
        }

        int negativeValue = -gameMapEnvironnment.maxBoundaries.x;
        if(bounds < -gameMapEnvironnment.maxBoundaries.x){
            return  false;
        }
        return true;
    }

    public void move(String command) {
        switch (command.toLowerCase()) {
            case "forward":
                y++;
                if(!isInBounds(y)){
                    y--;
                    System.out.println("Can't go that way!");
                    break;
                }

                break;
            case "back":
                y--;
                if(!isInBounds(y)){
                    y++;
                    System.out.println("Can't go that way!");
                    break;
                }

                break;
            case "left":
                x--;
                if(!isInBounds(x)){
                    x++;
                    System.out.println("Can't go that way!");
                    break;
                }

                break;
            case "right":
                x++;
                //Player is out of bounds
                if(!isInBounds(x)){
                    x--;
                    System.out.println("Can't go that way!");
                    break;
                }
                break;
            default:
                System.out.println("Invalid command");
        }
    }

    public void playerInCombat(String command){
        Enemy enemy = gameMapEnvironnment.getEnemyAtPoint(getLocation());


        while(inCombat){
            System.out.println("Would you like to attack? atk : flee");
            Scanner attack = new Scanner(System.in);
            String attackCommand = attack.nextLine();

            switch (attackCommand.toLowerCase()){
                case "atk":
                    enemy.takeDamage(getDamage());
                    playerTakeDamage(enemy.getDamage());

                    if(enemy.getHealth() <= 0){
                        inCombat = false;
                        gameMapEnvironnment.removeEnemyFromPoint(getLocation());
                        return;
                    }
                    break;

                case "flee":
                    playerTakeDamage(enemy.getDamage());
                    System.out.println("You decided to leave the enemy where it is. It will still remain here.");
                    inCombat = false;
                    return;

                default:
                    System.out.println("Error please try again");
                    break;
            }
        }
    }

    public void playerTakeDamage(int damage){
        health -= damage;
        System.out.println("You have taken damage! Health: " + health + "\n");
        if(health <= 0){
            System.out.println("Player defeated!");
            System.exit(0);
        }
    }

    public void waitForPlayerToInteractWithMerchant(String command) {
        Merchant merchant = gameMapEnvironnment.getMerchantAtPoint(getLocation());
        merchant.isActive = true;
        Scanner merchantInput = new Scanner(System.in);

        while(merchant.isActive) {
            System.out.println("Would you like to do? buy : sell : leave");
            switch (merchantInput.nextLine().toLowerCase()) {
                case "buy":
                    merchant.isBuying = true;
                    merchant.outputInventory();
                    merchant.buy(merchantInput, this);
                    break;
                case "sell":
                    merchant.isSelling = true;
                    outputAllInventory();
                    merchant.sell(merchantInput, this);
                    break;
                case "leave":
                    System.out.println("You decided to leave the merchant where it is. It will still remain here.");
                    merchant.isActive = false;
                    break;
                default:
                    System.out.println("Error please try again");
                    break;
            }
        }
    }


    public void waitForPlayerToPickupOrLeave(String command){
        //Alert player they have found an item!
        System.out.println("Would you like to pickup? yes : no");

        Scanner pickup = new Scanner(System.in);
        for(int i = 0; i <= 1; i++){
            String yesOrNo = pickup.nextLine();

            switch (yesOrNo.toLowerCase()){
                case "yes":
                    Point location = getLocation();
                    Item item = gameMapEnvironnment.getItemAtPoint(location);
                    if(addAndSortItemToInventory(item)){
                        gameMapEnvironnment.removeItemFromPoint(location);
                    }
                    return;

                case "no":
                    System.out.println("You decided to leave the item where it is. It will still remain here.");
                    return;

                default:
                    System.out.println("Error please try again");
                    i--;
                    break;
            }
        }
    }

    public boolean isItemFoundAtLocation(){
        Item item = gameMapEnvironnment.getItemAtPoint(getLocation());

        if(item != null){
            System.out.println("Item Found!");
            System.out.println("Item Name: "  + item.getName());
            return true;
        }
        return false;
    }

    public Point getLocation(){
        return new Point(x,y);
    }

    public boolean addItem(Item item) {
        if(gameMapEnvironnment.getGameItems().getItemList().containsKey(item.getName())){
            if(playerInventory.getItemsList().size() < 10) {
                playerInventory.addItem(item);
                System.out.println("Item added to Inventory");
                return true;
            }
        }
        return false;
    }

    public boolean addWeapon(Item item) {
        if(gameMapEnvironnment.getGameItems().getWeaponList().containsKey(item.getName())){
            if(playerInventory.getWeaponsList().size() < 2){
                playerInventory.addWeapon(item);
                System.out.println("Weapon added to Inventory");
                return true;
            }
        }
        return false;
    }

    public boolean addAndSortItemToInventory(Item item){
        if(addItem(item)){
            return true;
        } else if(addWeapon(item)){
            return true;
        }

        System.out.println("That inventory is full!");
        return false;
    }

    public void setGold(int inGold){
        this.gold = inGold;
    }

    public int getGold(){
        return this.gold;
    }

    public void addGold(int inGold){
        this.gold += inGold;
    }

    public void removeGold(int inGold){
        this.gold -= inGold;
    }

    public boolean canPlayerAffordItem(Item item){
        return gold >= item.getValue();
    }

    public boolean canPlayerAffordItem(String item){
        if(GameItems.getInstance().getItemList().containsKey(item)){
            return gold >= GameItems.getInstance().getItemList().get(item);
        } else if(GameItems.getInstance().getWeaponPriceList().containsKey(item)){
            return gold >= GameItems.getInstance().getWeaponPriceList().get(item);
        }
        return false;
    }

    public int getDamage(){
        //If inventory is not empty add the weapon damage to the players totalDamage
        if(!playerInventory.getWeaponsList().isEmpty()) {
            totalWeaponDamage = 0;
            for (Map.Entry<String, Integer> in : playerInventory.getWeaponsList().entrySet()) {
                totalWeaponDamage += in.getValue();
            }
        } else {
            //Else add the base damage, hand combat damage
            totalWeaponDamage = punchingDamage;
        }
        return totalWeaponDamage;
    }

    public void outputWeaponInventory(){
        System.out.println("Weapon Inventory:");

        if(playerInventory.getWeaponsList().isEmpty()){
            System.out.println("Empty\n");
            return;
        }

        for(Map.Entry<String,Integer> in : playerInventory.getWeaponsList().entrySet()){
            System.out.println("Weapon: " + in.getKey());
        }
        System.out.println(); //Creating empty line for clarity of reading after weapon results shown
    }

    public void outputItemInventory(){
        System.out.println("Item Inventory:");

        if(playerInventory.getItemsList().isEmpty()){
            System.out.println("Empty\n");
            return;
        }

        for(Map.Entry<String,Integer> in : playerInventory.getItemsList().entrySet()){
            System.out.println("Item: " + in.getKey());
        }
        System.out.println(); //Creating empty line for clarity of reading after item results shown
    }

    public void outputAllInventory(){
        outputWeaponInventory();
        outputItemInventory();
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    public PlayerInventory getInventory() {
        return playerInventory;
    }
}
