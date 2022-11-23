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
public final class EmpressThorina extends Hero {
    public EmpressThorina(final int mana, final String description,
                          final ArrayList<String> colors, final String name) {
        super();
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>Low Blow:</strong>
     * Destroys opponent card with most health on selected row.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Hero's ability.
     */
    @Override
    public void useHeroAbility(final Game game, final int affectedRow) {
        int maxHealth = 0;
        Card cardToRemove = null;

        for (Card card : game.getGameTable(affectedRow)) {
            if (((Minion) card).getHealth() > maxHealth) {
                maxHealth = ((Minion) card).getHealth();
                cardToRemove = card;
            }
        }

        if (cardToRemove != null) {
            game.getGameTable(affectedRow).remove(cardToRemove);
        }
    }
}
