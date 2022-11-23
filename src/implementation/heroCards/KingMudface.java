package implementation.heroCards;

import implementation.Card;
import implementation.Game;
import implementation.Hero;
import implementation.Minion;

import java.util.ArrayList;

public class KingMudface extends Hero {
    public KingMudface(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setHealth(30);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setHasAttacked(false);
    }

    @Override
    public void useHeroAbility(Game game, int affectedRow) {
        for (Card card : game.gameTable[affectedRow]) {
            ((Minion) card).setHealth(((Minion) card).getHealth() + 1);
        }
    }
}
