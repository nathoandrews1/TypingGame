package gameData;

import java.util.ArrayList;

public final class GameQuotes {
    static ArrayList<String> injuryQuotes = new ArrayList<>();
    static ArrayList<String> deathQuotes = new ArrayList<>();
    public GameQuotes(){
        createDeathQuotes();
        createInjuryQuotes();
    }

        public static void createDeathQuotes(){
            deathQuotes.add("Enemy lost 6 teeth and died horribly");
            deathQuotes.add("Another one bites the dust");
            deathQuotes.add("Enemy down, victory achieved");
            deathQuotes.add("That's one less enemy to worry about");
            deathQuotes.add("Enemy vanquished");
            deathQuotes.add("Rest in pieces, foe");
            deathQuotes.add("The battlefield claims another victim");
        }

        public static void createInjuryQuotes(){
            injuryQuotes.add("Enemy sustains a minor injury");
            injuryQuotes.add("That was a close call, enemy still standing");
            injuryQuotes.add("Enemy takes a hit but fights on");
            injuryQuotes.add("A mere scratch on the enemy's armor");
            injuryQuotes.add("Enemy wounded, but not defeated");
            injuryQuotes.add("Tough adversary, enemy still in the fight");
        }
}
