package implementation.abilityMinions;

import fileio.Coordinates;
import implementation.Minion;

import java.util.ArrayList;

public class Miraj extends Minion {
    public Miraj(int mana, int attackDamage, int health, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setAttackDamage(attackDamage);
        this.setHealth(health);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setTank(false);
        this.setFrozen(false);
        this.setHasAttacked(false);
    }

    public void useMinionAbility(Coordinates cardAttacked) {}
}
