package implementation.environmentCards;

import fileio.Coordinates;
import implementation.Card;
import implementation.Environment;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

public class HeartHound extends Environment {
    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    @Override
    public void useEnvironmentAbility(Game game, int affectedRow) {
        int maxHealth = 0;
        Card cardToSteal = null;

        for (Card card : game.gameTable[affectedRow]) {
            if (((Minion) card).getHealth() > maxHealth) {
                maxHealth = ((Minion) card).getHealth();
                cardToSteal = card;
            }
        }

        if (cardToSteal != null) {
            game.gameTable[3 - affectedRow].add(cardToSteal);
            game.gameTable[affectedRow].remove(cardToSteal);
        }
    }
}
