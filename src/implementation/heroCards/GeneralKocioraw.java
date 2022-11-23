package implementation.heroCards;

import implementation.Card;
import implementation.Game;
import implementation.Hero;
import implementation.Minion;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(int mana, String description, ArrayList<String> colors, String name) {
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
            ((Minion) card).setAttackDamage(((Minion) card).getAttackDamage() + 1);
        }
    }
}
