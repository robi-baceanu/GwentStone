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
public final class Firestorm extends Environment {
    public Firestorm(final int mana, final String description,
                     final ArrayList<String> colors, final String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>Firestorm:</strong>
     * Drops each card's health placed on the selected row by
     * 1 point. If any card's health drops to 0, it is removed
     * from the table.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Environment card.
     */
    @Override
    public void useEnvironmentAbility(final Game game, final int affectedRow) {
        for (Card card : game.getGameTable(affectedRow)) {
            ((Minion) card).setHealth(((Minion) card).getHealth() - 1);
        }
        game.getGameTable(affectedRow).removeIf(card -> ((Minion) card).getHealth() <= 0);
    }
}
