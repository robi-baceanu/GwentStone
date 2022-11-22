package implementation.environmentCards;

import implementation.Environment;

import java.util.ArrayList;

public class Firestorm extends Environment {
    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        this.setMana(mana);
        this.setDescription(description);
        this.setColors(colors);
        this.setName(name);
    }

    @Override
    public void useEnvironmentAbility(int affectedRow) {

    }
}
