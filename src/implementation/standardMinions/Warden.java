package implementation.standardMinions;

import implementation.Minion;

import java.util.ArrayList;

/**
 * Minion card that does not have an ability.
 * Is of 'Tank' type, which means it has to be attacked first
 * if placed on the table.
 *
 * @author wh1ter0se
 */
public final class Warden extends Minion {
    public Warden(final int mana, final int attackDamage, final int health,
                  final String description, final ArrayList<String> colors,
                  final String name) {
        super();
        this.setMana(mana);
        this.setAttackDamage(attackDamage);
        this.setHealth(health);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setTank(true);
    }
}
