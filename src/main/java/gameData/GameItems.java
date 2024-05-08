package gameData;


import gameData.interfaces.Weapon;

import java.util.HashMap;
import java.util.Map;

public class GameItems extends Item {

    private GameItems(){}

    private static GameItems instance;
    private Map<String, Integer> weaponList = new HashMap<>();

    private Map<String, Integer> weaponPriceList = new HashMap<>();

    private Map<String, Integer> itemList = new HashMap<>();

    public static GameItems getInstance() {
        if (instance == null) {
            instance = new GameItems();
            initWeaponlist(instance.weaponList);
            initItemList(instance.itemList);
            instance.initWeaponPriceList();
        }
        return instance;
    }


    private static void initItemList(Map<String,Integer> itemList){
        itemList.put("Elixir Of Vigor", 120);   // High-value health potion
        itemList.put("Brew Of Hastening", 100); // High-value stamina potion
        itemList.put("Cartographer Scroll", 80); // Useful map
        itemList.put("Skeleton Key", 150);      // Key that can open many locks
        itemList.put("Amulet Of Yendor", 160);  // Rare trinket
        itemList.put("Starlight Sapphire", 200); // Extremely valuable gem
        itemList.put("Hunter Feast", 60);     // Nutritious and restorative food
        itemList.put("Dwarven Mead", 30);       // Common but hearty drink
        itemList.put("Sunset Ruby", 180);       // Another highly valuable gem
        itemList.put("Ring Of Shadows", 140);   // Stealth-enhancing trinket
        itemList.put("Wayfarer Boots", 90);   // Increases travel speed
        itemList.put("Ironbread", 20);          // Basic, filling food
        itemList.put("Springwater Flask", 10);  // Simple, refreshing drink
    }

    private static void initWeaponlist(Map<String, Integer> weaponList){

        // Swords
        weaponList.put("Longsword", 25);
        weaponList.put("Broadsword", 30);
        weaponList.put("Falchion", 20);
        weaponList.put("Arming Sword", 18);
        weaponList.put("Rapier", 15);
        weaponList.put("Claymore", 35);
        weaponList.put("Estoc", 22);
        weaponList.put("Bastard Sword", 28);

        // Axes
        weaponList.put("Battle Axe", 32);
        weaponList.put("Dane Axe", 34);
        weaponList.put("Bearded Axe", 30);
        weaponList.put("Francisca", 18); // Noting lower damage as it could also be used for throwing

        // Maces
        weaponList.put("Flanged Mace", 27);
        weaponList.put("Morning Star", 26);
        weaponList.put("War Hammer", 33);
    }

    public Item getItemByName(String itemName){
        if(itemList.containsKey(itemName)){
            return new Item(itemName, itemList.get(itemName));
        } else {
            return null;
        }
    }

    public Item getWeaponByName(String weaponName){
        if(weaponList.containsKey(weaponName)){
            return new Item(weaponName, weaponList.get(weaponName));
        } else {
            return null;
        }
    }

    private void initWeaponPriceList(){
        for(String weapon : weaponList.keySet()){
            weaponPriceList.put(weapon, weaponList.get(weapon) * 6);
        }
    }

    public Item getRandomItem(){
        int random = (int) (Math.random() * itemList.size());
        String itemName = (String) itemList.keySet().toArray()[random];

        return new Item(itemName, itemList.get(itemName));
    }

    public Item getRandomWeapon(){
        int random = (int) (Math.random() * weaponList.size());
        String itemName = (String) weaponList.keySet().toArray()[random];

        return new Item(itemName, weaponList.get(itemName));
    }

    public HashMap<String, Integer> getWeaponPriceList() {
        return (HashMap<String, Integer>) weaponPriceList;
    }

    public Map<String, Integer> getWeaponList() {
        return weaponList;
    }

    public Map<String, Integer> getItemList() {
        return itemList;
    }
}
