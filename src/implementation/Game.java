package implementation;

import java.util.ArrayList;

/**
 * Class that describes a game and the game table,
 * where cards are placed.
 *
 * @author wh1ter0se
 */
public final class Game {
    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private int activePlayer;
    private int round = 1;
    private boolean gameEnded;

    private final ArrayList<Card>[] gameTable = new ArrayList[MagicNumbers.NUMBER_OF_ROWS];

    public Game() {
        gameEnded = false;
        for (int i = 0; i < MagicNumbers.NUMBER_OF_ROWS; i++) {
            gameTable[i] = new ArrayList<Card>();
        }
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(final int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getRound() {
        return round;
    }

    public void setRound(final int round) {
        this.round = round;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(final boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     * Getter method for gameTable, but returns only one
     * row of it.
     *
     * @param row Row of table that is to be returned.
     * @return ArrayList that represents one of the table's rows.
     */
    public ArrayList<Card> getGameTable(final int row) {
        return gameTable[row];
    }
}
