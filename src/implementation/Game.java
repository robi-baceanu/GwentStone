package implementation;

public class Game {
    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private int activePlayer;
    private int round = 1;
    public Card[][] gameTable = new Card[4][5];

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

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
