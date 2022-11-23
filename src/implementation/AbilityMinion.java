package implementation;

import fileio.Coordinates;

public abstract class AbilityMinion extends Minion {
    public abstract void useMinionAbility(Game game, Coordinates cardAttacked);
}
