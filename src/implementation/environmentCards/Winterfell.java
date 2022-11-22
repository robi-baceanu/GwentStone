package implementation.environmentCards;

import implementation.Card;
import implementation.Environment;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

public class Winterfell extends Environment {
    public Winterfell(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    @Override
    public void useEnvironmentAbility(Game game, int affectedRow) {
        for (Card card : game.gameTable[affectedRow]) {
            ((Minion) card).setFrozen(true);
        }
    }
}
