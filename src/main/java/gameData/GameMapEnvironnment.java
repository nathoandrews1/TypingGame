package gameData;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;


public class GameMapEnvironnment {
    private HashMap<Point, String> gameMap = new HashMap<Point, String>();
    private static HashMap<Point, Item> itemsOnMapLocation = new HashMap<Point, Item>();

    private static HashMap<Point, Enemy> enemiesOnMapLocation = new HashMap<Point, Enemy>();

    private static HashMap<Point, Merchant> merchantsOnMapLocation = new HashMap<Point, Merchant>();

    private static GameItems gameItems;

    private static int width;
    private static int height;
    private static int minHeight;
    private static int minWidth;

    static Point maxBoundaries = new Point(5, 5);

    private static GameMapEnvironnment instance;

    public static GameMapEnvironnment getInstance(){
        if(instance == null){
            instance = new GameMapEnvironnment();
            width = maxBoundaries.x;
            height = maxBoundaries.y;
            minHeight = maxBoundaries.y;
            minWidth = maxBoundaries.x;

            minWidth *= -1; // Make the min width negative
            minHeight *= -1; // Make the min height negative

            gameItems = GameItems.getInstance();
            spawnAllItems();
            spawnEnemiesOnMap();
            spawnMerchantsOnMap();
            instance = new GameMapEnvironnment();
        }
        return instance;
    }

    private GameMapEnvironnment(){}

    private static void spawnGameItemsOnMap(){
        // Spawn items on the map based on size of the map
        int numberOfItems = (width * height) / 3; // 1/3 of the map will have items

        if(numberOfItems == 0){
            numberOfItems = 1;
        }

        // Randomly spawn items on the map
        for(int i = 0; i < numberOfItems; i++){
            Point itemLocation = new Point(new Random().nextInt(width + width) - width,
                    new Random().nextInt(height + height) - height);

            // Check if the location is already occupied
            if(itemsOnMapLocation.containsKey(itemLocation) || itemLocation.x == 0 && itemLocation.y == 0){
                i--;
            } else {
                itemsOnMapLocation.put(itemLocation, gameItems.getRandomItem());
            }
        }
    }

    private static void spawnWeaponsOnMap(){
        // Spawn items on the map based on size of the map
        int numberOfItems = (width * height) / 6; // 1/6 of the map will have items

        if(numberOfItems == 0){
            numberOfItems = 1;
        }

        // Randomly spawn items on the map
        for(int i = 0; i < numberOfItems; i++){
            Point itemLocation = new Point(new Random().nextInt(width + width) - width,
                    new Random().nextInt(height + height) - height);

            // Check if the location is already occupied
            if(itemsOnMapLocation.containsKey(itemLocation) || itemLocation.x == 0 && itemLocation.y == 0){
                i--;
            } else {
                itemsOnMapLocation.put(itemLocation, gameItems.getRandomWeapon());
            }
        }
    }

    private static void spawnEnemiesOnMap(){
        // Spawn items on the map based on size of the map
        int numberOfEnemies = (width * height) / 6; // 1/6 of the map will have enemies

        if(numberOfEnemies == 0){
            numberOfEnemies = 1;
        }

        // Randomly spawn enemies on the map
        for(int i = 0; i < numberOfEnemies; i++){
            Point enemyLocation = new Point(new Random().nextInt(width + width) - width,
                    new Random().nextInt(height + height) - height);


            //Spawning Enemy
            Enemy enemy = new Enemy(100, enemyLocation);

            // Check if the location is already occupied
            if(enemiesOnMapLocation.containsKey(enemyLocation) || enemyLocation.x == 0 && enemyLocation.y == 0){
                i--;
            } else {
                enemiesOnMapLocation.put(enemyLocation, enemy);
            }
        }
    }

    public static void spawnMerchantsOnMap(){
        int numberOfmerchantsOnMap = (width * height) / 10; // 1/10 of the map will have merchants

        if(numberOfmerchantsOnMap == 0){
            numberOfmerchantsOnMap = 1;
        }

        for(int i = 0; i < numberOfmerchantsOnMap; i++){
            Point merchantLocation = new Point(new Random().nextInt(width + width) - width,
                    new Random().nextInt(height + height) - height);

            Merchant merchant = new Merchant(merchantLocation);

            // Check if the location is already occupied
            if(merchantsOnMapLocation.containsKey(merchantLocation) ||
                    merchantLocation.x == 0 && merchantLocation.y == 0){
                i--;
            } else {
                merchantsOnMapLocation.put(merchantLocation, merchant);
            }
        }
    }

    public boolean isEnemyAtPlayerLocation(Player player){
        Enemy enemy = getEnemyAtPoint(player.getLocation());

        if(enemy != null){
            System.out.println("Enemy Found!");
            System.out.println("Enemy Name: "  + enemy.getName());
            return true;
        }
        return false;
    }

    public boolean isEnemyAtPlayerLocation(Point playerLocation){
        Enemy enemy = getEnemyAtPoint(playerLocation);

        if(enemy != null){
            System.out.println("Enemy Found!");
            System.out.println("Enemy Name: "  + enemy.getName());
            return true;
        }
        return false;
    }

    public boolean isMerchantAtPlayerLocation(Player player){
        Merchant merchant = merchantsOnMapLocation.get(player.getLocation());

        if(merchant != null){
            System.out.println("Merchant Found!");
            return true;
        }
        return false;
    }

    public boolean isMerchantAtPlayerLocation(Point player){
        Merchant merchant = merchantsOnMapLocation.get(player);

        if(merchant != null){
            System.out.println("Merchant Found!");
            return true;
        }
        return false;
    }

    private static void spawnAllItems(){
        spawnGameItemsOnMap();
        spawnWeaponsOnMap();
    }

    public Item getItemAtPoint(Point playerLocation){
        if(itemsOnMapLocation.containsKey(playerLocation)){
            return itemsOnMapLocation.get(playerLocation);
        } else {
            return null;
        }
    }

    public Enemy getEnemyAtPoint(Point point){
        if(enemiesOnMapLocation.containsKey(point)){
            return enemiesOnMapLocation.get(point);
        } else {
            return null;
        }
    }

    public Merchant getMerchantAtPoint(Point point){
        if(merchantsOnMapLocation.containsKey(point)){
            return merchantsOnMapLocation.get(point);
        } else {
            return null;
        }
    }

    public void removeEnemyFromPoint(Point inPoint){
        enemiesOnMapLocation.remove(inPoint);
    }

    public void removeItemFromPoint(Point inPoint){
        itemsOnMapLocation.remove(inPoint);
    }

    private static final String[] environments = {
            "Forest", "Desert", "Mountain", "River"
    };

    public String getEnvironment() {
        return environments[new Random().nextInt(environments.length)];
    }

    public HashMap<Point, Item> getItemsOnMapLocation() {
        return itemsOnMapLocation;
    }

    public HashMap<Point, Merchant> getMerchantsOnMapLocation() {
        return merchantsOnMapLocation;
    }

    public GameItems getGameItems() {
        return gameItems;
    }

    public void outputEnemyLocations(){
        for(Point p : enemiesOnMapLocation.keySet()){
            System.out.println("Enemy at: " + p.x + ", " + p.y);
        }
    }

    public Point getMaxBoundaries() {
        return maxBoundaries;
    }

    public HashMap<Point, Enemy> getEnemiesOnMapLocation() {
        return enemiesOnMapLocation;
    }
}
