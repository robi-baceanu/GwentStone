package implementation.environmentCards;

import implementation.Card;
import implementation.Environment;
import implementation.Game;
import implementation.Minion;
import implementation.MagicNumbers;

import java.util.ArrayList;

/**
 * Environment card.
 *
 * @author wh1ter0se
 */
public final class HeartHound extends Environment {
    public HeartHound(final int mana, final String description,
                      final ArrayList<String> colors, final String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>Heart Hound:</strong>
     * Steals card with most health from the opponent's selected
     * row and places it on the corresponding allied
     * row, if there is place to do so.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Environment card.
     */
    @Override
    public void useEnvironmentAbility(final Game game, final int affectedRow) {
        int maxHealth = 0;
        Card cardToSteal = null;

        for (Card card : game.getGameTable(affectedRow)) {
            if (((Minion) card).getHealth() > maxHealth) {
                maxHealth = ((Minion) card).getHealth();
                cardToSteal = card;
            }
        }

        if (cardToSteal != null) {
            game.getGameTable(MagicNumbers.SWITCH_PLAYER - affectedRow).add(cardToSteal);
            game.getGameTable(affectedRow).remove(cardToSteal);
        }
    }
}
