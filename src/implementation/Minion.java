package implementation;

/**
 * Class used for describing 'Minion' type cards,
 * inherits Card class, is inherited by classes
 * that describe specific type of Minions.
 *
 * @author wh1ter0se
 */
public class Minion extends Card {
    private int health;
    private int attackDamage;
    private boolean isTank;
    private boolean isFrozen;
    private boolean hasAttacked;

    public Minion() {
        this.isFrozen = false;
        this.hasAttacked = false;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final boolean isTank() {
        return isTank;
    }

    public final void setTank(final boolean tank) {
        isTank = tank;
    }

    public final boolean isFrozen() {
        return isFrozen;
    }

    public final void setFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    public final boolean hasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
