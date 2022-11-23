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
public final class TheCursedOne extends AbilityMinion {
    public TheCursedOne(final int mana, final int attackDamage, final int health,
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
     * <strong>Shapeshift:</strong>
     * Swaps an opponent minion's health with its attack damage.
     * If such, health becomes 0, card is removed from the table.
     *
     * @param game Current game that is being played.
     * @param cardAttacked Coordinates of card targeted by the Minion.
     */
    public void useMinionAbility(final Game game, final Coordinates cardAttacked) {
        Card cardToAlter = game.getGameTable(cardAttacked.getX()).get(cardAttacked.getY());
        Minion tempCard = new Minion();

        int healthOfCard = ((Minion) cardToAlter).getHealth();
        int attackOfCard = ((Minion) cardToAlter).getAttackDamage();

        tempCard.setHealth(attackOfCard);
        tempCard.setAttackDamage(healthOfCard);

        ((Minion) cardToAlter).setAttackDamage(tempCard.getAttackDamage());
        ((Minion) cardToAlter).setHealth(tempCard.getHealth());

        if (((Minion) cardToAlter).getHealth() == 0) {
            game.getGameTable(cardAttacked.getX()).remove(cardToAlter);
        }
    }
}
