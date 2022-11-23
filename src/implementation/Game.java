package implementation;

import java.util.ArrayList;

public class Game {
    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private int activePlayer;
    private int round = 1;
    private boolean gameEnded;

    public ArrayList<Card>[] gameTable = new ArrayList[4];

    public Game() {
        gameEnded = false;
        for (int i = 0; i < 4; i++) {
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

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
