package implementation;

import fileio.Coordinates;

/**
 * Abstract class used for describing those specific
 * Minion cards that can use abilities. Inherits Minion
 * class, is inherited by cards that describe
 * specific Minion cards.
 */
public abstract class AbilityMinion extends Minion {
    public AbilityMinion() {
        this.setTank(false);
        this.setFrozen(false);
        this.setHasAttacked(false);
    }

    /**
     * Abstract method that uses a Minion's ability, implemented
     * according to each type of Minion.
     *
     * @param game Current game that is being played.
     * @param cardAttacked Coordinates of card targeted by the Minion.
     */
    public abstract void useMinionAbility(Game game, Coordinates cardAttacked);
}
