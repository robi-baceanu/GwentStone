package implementation.abilityMinions;

import fileio.Coordinates;
import implementation.Minion;

import java.util.ArrayList;

public class TheRipper extends Minion {
    public TheRipper(int mana, int attackDamage, int health, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setAttackDamage(attackDamage);
        this.setHealth(health);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
        this.setTank(false);
        this.setFrozen(false);
    }

    public void useMinionAbility(Coordinates cardAttacked) {}
}
