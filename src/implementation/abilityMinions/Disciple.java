package implementation.abilityMinions;

import fileio.Coordinates;
import implementation.AbilityMinion;
import implementation.Card;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

/**
 * Minion card that can use an ability.
 *
 * @author wh1ter0se
 */
public final class Disciple extends AbilityMinion {
    public Disciple(final int mana, final int attackDamage, final int health,
                    final String description, final ArrayList<String> colors,
                    final String name) {
        super();
        this.setMana(mana);
        this.setAttackDamage(attackDamage);
        this.setHealth(health);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    /**
     * <strong>God's Plan:</strong>
     * Drops selected card's health by 2 points.
     *
     * @param game Current game that is being played.
     * @param cardAttacked Coordinates of card targeted by the Minion.
     */
    public void useMinionAbility(final Game game,
                                 final Coordinates cardAttacked) {
        Card cardToAlter =
                game.getGameTable(cardAttacked.getX()).get(cardAttacked.getY());
        ((Minion) cardToAlter).setHealth(((Minion) cardToAlter).getHealth() + 2);
    }
}
