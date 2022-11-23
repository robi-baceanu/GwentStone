package implementation.abilityMinions;

import fileio.Coordinates;
import implementation.AbilityMinion;
import implementation.Card;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

public class Miraj extends AbilityMinion {
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

    public void useMinionAbility(Game game, Coordinates cardAttacked) {
        Card cardToAlter = game.gameTable[cardAttacked.getX()].get(cardAttacked.getY());

        int attackerHealth = this.getHealth();
        int attackedHealth = ((Minion) cardToAlter).getHealth();

        this.setHealth(attackedHealth);
        ((Minion) cardToAlter).setHealth(attackerHealth);
    }
}
