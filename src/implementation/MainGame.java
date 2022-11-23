package implementation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

/**
 * Class that describes a match between two players, that can
 * play multiple games. Implemented following Singleton pattern.
 *
 * @author wh1ter0se
 */
public final class MainGame {
    private static MainGame match = null;
    private Input inputData;

    private int totalGamesPlayed;
    private int playerOneWins;
    private int playerTwoWins;

    private MainGame() {

    }

    public static MainGame getInstance() {
        if (match == null) {
            match = new MainGame();
        }
        return match;
    }

    public Input getInputData() {
        return inputData;
    }

    public void setInputData(final Input inputData) {
        this.inputData = inputData;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(final int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void setPlayerOneWins(final int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public void setPlayerTwoWins(final int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }

    /**
     * Method that represents the entry point of the project, which simulates
     * a match between two players.
     *
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void startMatch(final ArrayNode output) {
        int gamesNumber = getInstance().inputData.getGames().size();

        MainGame.getInstance().setTotalGamesPlayed(0);
        MainGame.getInstance().setPlayerOneWins(0);
        MainGame.getInstance().setPlayerTwoWins(0);


        for (int i = 0; i < gamesNumber; i++) {
            GameInput currentGame = getInstance().inputData.getGames().get(i);

            Game game = new Game();

            game.getPlayerOne().assignPlayerDeck(1, i);
            game.getPlayerTwo().assignPlayerDeck(2, i);

            game.getPlayerOne().assignPlayerHero(1, i);
            game.getPlayerTwo().assignPlayerHero(2, i);

            game.setActivePlayer(currentGame.getStartGame().getStartingPlayer());

            game.getPlayerOne().drawCardFromDeck();
            game.getPlayerTwo().drawCardFromDeck();

            game.getPlayerOne().setMana(game.getPlayerOne().getMana()
                    + Math.min(game.getRound(), MagicNumbers.MAX_MANA));
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana()
                    + Math.min(game.getRound(), MagicNumbers.MAX_MANA));

            for (int j = 0; j < currentGame.getActions().size(); j++) {
                ActionsInput currentAction = currentGame.getActions().get(j);

                switch (currentAction.getCommand()) {
                    case "getPlayerDeck":
                        CommandsParser.getPlayerDeck(game, currentAction.getPlayerIdx(), output);
                        break;
                    case "getCardsInHand":
                        CommandsParser.getCardsInHand(game, currentAction.getPlayerIdx(), output);
                        break;
                    case "getPlayerHero":
                        CommandsParser.getPlayerHero(game, currentAction.getPlayerIdx(), output);
                        break;
                    case "getPlayerTurn":
                        CommandsParser.getPlayerTurn(game, output);
                        break;
                    case "endPlayerTurn":
                        if (!game.isGameEnded()) {
                            CommandsParser.endPlayerTurn(game,
                                    currentGame.getStartGame().getStartingPlayer());
                        }
                        break;
                    case "getPlayerMana":
                        CommandsParser.getPlayerMana(game, currentAction.getPlayerIdx(), output);
                        break;
                    case "placeCard":
                        if (!game.isGameEnded()) {
                            CommandsParser.placeCard(game, game.getActivePlayer(),
                                    currentAction.getHandIdx(), output);
                        }
                        break;
                    case "getCardsOnTable":
                        CommandsParser.getCardsOnTable(game, output);
                        break;
                    case "useEnvironmentCard":
                        if (!game.isGameEnded()) {
                            CommandsParser.useEnvironmentCard(game, game.getActivePlayer(),
                                    currentAction.getHandIdx(),
                                    currentAction.getAffectedRow(), output);
                        }
                        break;
                    case "getEnvironmentCardsInHand":
                        CommandsParser.getEnvironmentCardsInHand(game,
                                currentAction.getPlayerIdx(), output);
                        break;
                    case "getCardAtPosition":
                        CommandsParser.getCardAtPosition(game, currentAction.getX(),
                                currentAction.getY(), output);
                        break;
                    case "getFrozenCardsOnTable":
                        CommandsParser.getFrozenCardsOnTable(game, output);
                        break;
                    case "cardUsesAttack":
                        if (!game.isGameEnded()) {
                            CommandsParser.cardUsesAttack(game, currentAction.getCardAttacker(),
                                    currentAction.getCardAttacked(), output);
                        }
                        break;
                    case "cardUsesAbility":
                        if (!game.isGameEnded()) {
                            CommandsParser.cardUsesAbility(game,
                                    currentAction.getCardAttacker(),
                                    currentAction.getCardAttacked(), output);
                        }
                        break;
                    case "useAttackHero":
                        if (!game.isGameEnded()) {
                            CommandsParser.useAttackHero(game,
                                    currentAction.getCardAttacker(), output);
                        }
                        break;
                    case "useHeroAbility":
                        if (!game.isGameEnded()) {
                            CommandsParser.useHeroAbility(game,
                                    currentAction.getAffectedRow(), output);
                        }
                        break;
                    case "getPlayerOneWins":
                        CommandsParser.getPlayerOneWins(output);
                        break;
                    case "getPlayerTwoWins":
                        CommandsParser.getPlayerTwoWins(output);
                        break;
                    case "getTotalGamesPlayed":
                        CommandsParser.getTotalGamesPlayed(output);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
