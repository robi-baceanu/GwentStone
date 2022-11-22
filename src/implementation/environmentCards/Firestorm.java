package implementation.environmentCards;

import fileio.Coordinates;
import implementation.Card;
import implementation.Environment;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

public class Firestorm extends Environment {
    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    @Override
    public void useEnvironmentAbility(Game game, int affectedRow) {
        if (game.gameTable[affectedRow] != null) {
            for (Card card : game.gameTable[affectedRow]) {
                ((Minion) card).setHealth(((Minion) card).getHealth() - 1);
            }
            game.gameTable[affectedRow].removeIf(card -> ((Minion) card).getHealth() <= 0);
        }
    }
}
