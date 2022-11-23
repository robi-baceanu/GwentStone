package implementation.heroCards;

import implementation.Card;
import implementation.Game;
import implementation.Hero;
import implementation.Minion;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setHealth(30);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setHasAttacked(false);
    }

    @Override
    public void useHeroAbility(Game game, int affectedRow) {
        int maxAttackDamage = 0;
        Card cardToFreeze = null;

        for (Card card : game.gameTable[affectedRow]) {
            if (((Minion) card).getHealth() > maxAttackDamage) {
                maxAttackDamage = ((Minion) card).getHealth();
                cardToFreeze = card;
            }
        }

        if (cardToFreeze != null) {
            ((Minion) cardToFreeze).setFrozen(true);
        }
    }
}
