package gameData;

import java.util.HashMap;

public class PlayerInventory {
    private HashMap<String, Integer> weapons;
    private HashMap<String, Integer> items;

    private int maxWeapons = 2;
    private int maxItems = 10;

    public PlayerInventory(){
        this.weapons = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void addWeapon(Item item){
        if(weapons.size() < maxWeapons){
            weapons.put(item.getName(), item.getValue());
        } else {
            System.out.println("Weapon Inventory full!");
        }
    }

    public void addItem(Item item){
        if(items.size() < maxItems){
            items.put(item.getName(), item.getValue());
        } else {
            System.out.println("Item Inventory full!");
        }
    }

    public HashMap<String, Integer> getWeaponsList(){
        return this.weapons;
    }

    public HashMap<String, Integer> getItemsList(){
        return this.items;
    }
}
