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
public final class LordRoyce extends Hero {
    public LordRoyce(final int mana, final String description,
                     final ArrayList<String> colors, final String name) {
        super();
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>Sub-Zero:</strong>
     * Freezes card with most attack damage points on selected row.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Hero's ability.
     */
    @Override
    public void useHeroAbility(final Game game, final int affectedRow) {
        int maxAttackDamage = 0;
        Card cardToFreeze = null;

        for (Card card : game.getGameTable(affectedRow)) {
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
