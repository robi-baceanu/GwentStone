package implementation.environmentCards;

import implementation.Card;
import implementation.Environment;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

/**
 * Environment card.
 *
 * @author wh1ter0se
 */
public final class Winterfell extends Environment {
    public Winterfell(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>Winterfell:</strong>
     * Freezes all cards on the opponent's selected row.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Environment card.
     */
    @Override
    public void useEnvironmentAbility(final Game game, final int affectedRow) {
        for (Card card : game.getGameTable(affectedRow)) {
            ((Minion) card).setFrozen(true);
        }
    }
}
