package implementation.standardMinions;

import implementation.Minion;

import java.util.ArrayList;

public class Goliath extends Minion {
    public Goliath(int mana, int attackDamage, int health, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setAttackDamage(attackDamage);
        this.setHealth(health);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setTank(true);
        this.setFrozen(false);
        this.setHasAttacked(false);
    }
}
