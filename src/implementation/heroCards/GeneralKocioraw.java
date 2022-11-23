package implementation.heroCards;

import implementation.Card;
import implementation.Game;
import implementation.Hero;
import implementation.Minion;

import java.util.ArrayList;

/**
 * Hero card.
 *
 * @author wh1ter0se
 */
public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int mana, final String description,
                           final ArrayList<String> colors, final String name) {
        super();
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>Blood Thirst:</strong>
     * Grants all minions on selected row 1 attack damage point.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Hero's ability.
     */
    @Override
    public void useHeroAbility(final Game game, final int affectedRow) {
        for (Card card : game.getGameTable(affectedRow)) {
            ((Minion) card).setAttackDamage(((Minion) card).getAttackDamage() + 1);
        }
    }
}
