package implementation;

public class Game {
    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private int activePlayer;

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }
}
