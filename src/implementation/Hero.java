package implementation;

import java.util.ArrayList;

/**
 * Abstract class used for describing each of the
 * available heroes, is inherited by classes that
 * describe specific types of Heroes.
 *
 * @author wh1ter0se
 */
public abstract class Hero {
    private int mana;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean hasAttacked;

    public Hero() {
        this.health = MagicNumbers.HERO_HEALTH;
        this.hasAttacked = false;
    }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final boolean hasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Abstract method that uses the Hero's ability, implemented
     * according to each type of Hero.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by the Hero's ability.
     */
    public abstract void useHeroAbility(Game game, int affectedRow);
}
