package implementation.heroCards;

import implementation.Card;
import implementation.Game;
import implementation.Hero;
import implementation.Minion;

import java.util.ArrayList;

public class EmpressThorina extends Hero {
    public EmpressThorina(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setHealth(30);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setHasAttacked(false);
    }

    @Override
    public void useHeroAbility(Game game, int affectedRow) {
        int maxHealth = 0;
        Card cardToRemove = null;

        for (Card card : game.gameTable[affectedRow]) {
            if (((Minion) card).getHealth() > maxHealth) {
                maxHealth = ((Minion) card).getHealth();
                cardToRemove = card;
            }
        }

        if (cardToRemove != null) {
            game.gameTable[affectedRow].remove(cardToRemove);
        }
    }
}
