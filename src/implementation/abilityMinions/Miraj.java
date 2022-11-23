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
public final class Miraj extends AbilityMinion {
    public Miraj(final int mana, final int attackDamage, final int health,
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
     * <strong>Skyjack:</strong>
     * Swaps minion's health with an opponent minion's health.
     *
     * @param game Current game that is being played.
     * @param cardAttacked Coordinates of card targeted by the Minion.
     */
    public void useMinionAbility(final Game game, final Coordinates cardAttacked) {
        Card cardToAlter = game.getGameTable(cardAttacked.getX()).get(cardAttacked.getY());

        int attackerHealth = this.getHealth();
        int attackedHealth = ((Minion) cardToAlter).getHealth();

        this.setHealth(attackedHealth);
        ((Minion) cardToAlter).setHealth(attackerHealth);
    }
}
