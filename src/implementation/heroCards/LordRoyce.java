package implementation.heroCards;

import implementation.Hero;

import java.util.ArrayList;

public class LordRoyce extends Hero {
    public LordRoyce(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setHealth(30);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    @Override
    public void useHeroAbility(int affectedRow) {

    }
}
