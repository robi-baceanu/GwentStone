package implementation;

/**
 * Abstract class used for describing 'Environment'
 * type cards, inherits Card class, is inherited by
 * classes that describe specific Environment cards.
 *
 * @author wh1ter0se
 */
public abstract class Environment extends Card {
    /**
     * Abstract method that uses an Environment card's ability,
     * implemented according to each type of Environment card.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Environment card.
     */
    public abstract void useEnvironmentAbility(Game game, int affectedRow);
}
