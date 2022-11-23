package implementation.abilityMinions;

import fileio.Coordinates;
import implementation.AbilityMinion;
import implementation.Card;
import implementation.Game;
import implementation.Minion;

import java.util.ArrayList;

public class TheCursedOne extends AbilityMinion {
    public TheCursedOne(int mana, int attackDamage, int health, String description, ArrayList<String> colors, String name) {
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
        Minion tempCard = new Minion();

        int healthOfCard = ((Minion) cardToAlter).getHealth();
        int attackOfCard = ((Minion) cardToAlter).getAttackDamage();

        tempCard.setHealth(attackOfCard);
        tempCard.setAttackDamage(healthOfCard);

        ((Minion) cardToAlter).setAttackDamage(tempCard.getAttackDamage());
        ((Minion) cardToAlter).setHealth(tempCard.getHealth());

        if (((Minion) cardToAlter).getHealth() == 0) {
            game.gameTable[cardAttacked.getX()].remove(cardToAlter);
        }
    }
}
