package gameData;

import java.awt.*;
import java.util.ArrayList;

public class Enemy {
    private String name;
    private int health;

    private Point enemyLocation;

    private int damage = 5;

    public Enemy(int health, Point inLocation) {
        this.health = health;
        this.name = "Enemy";
        enemyLocation = inLocation;
    }

    public void takeDamage(int damage) {
        int random = (int) (Math.random() * 100);
        if (random < 10) {
            // 10% chance of injury
            damage += 5;
            System.out.println(generateRandomInjuryQuote());
        }

        health -= damage;
        if (health <= 0) {
            System.out.println(generateRandomDeathQuote());
        }
        System.out.println(name + " health: " + health);
    }

    public String generateRandomInjuryQuote(){
        ArrayList<String> injuryQuotes = GameQuotes.injuryQuotes;

        int randomIndex = (int) (Math.random() * injuryQuotes.size()-1);

        return injuryQuotes.get(randomIndex);
    }

    public String generateRandomDeathQuote(){
        ArrayList<String> deathQuotes = GameQuotes.deathQuotes;

        int randomIndex = (int) (Math.random() * deathQuotes.size());

        return deathQuotes.get(randomIndex);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public Point getLocation() {
        return enemyLocation;
    }

    public int getDamage() {
        return damage;
    }
}
