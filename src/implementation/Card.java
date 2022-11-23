package implementation;

import java.util.ArrayList;

/**
 * General class used for describing cards (except Heroes),
 * which is inherited by classes that describe main types
 * of cards.
 *
 * @author wh1ter0se
 */
public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
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
}
