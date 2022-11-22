package implementation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;

public class MainGame {
    private static MainGame match = null;
    private Input inputData;

    private MainGame() {}

    public static MainGame getInstance() {
        if (match == null) {
            match = new MainGame();
        }
        return match;
    }

    public Input getInputData() {
        return inputData;
    }

    public void setInputData(Input inputData) {
        this.inputData = inputData;
    }

    public static void startMatch(ArrayNode output) {
        int gamesNumber = getInstance().inputData.getGames().size();

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

            game.getPlayerOne().setMana(game.getPlayerOne().getMana() + Math.min(game.getRound(), 10));
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana() + Math.min(game.getRound(), 10));

            for (int j = 0; j < currentGame.getActions().size(); j++) {
                ActionsInput currentAction = currentGame.getActions().get(j);

                switch (currentAction.getCommand()) {
                    case "getPlayerDeck" ->
                            CommandsParser.getPlayerDeck(game, currentAction.getPlayerIdx(), output);
                    case "getCardsInHand" ->
                            CommandsParser.getCardsInHand(game, currentAction.getPlayerIdx(), output);
                    case "getPlayerHero" ->
                            CommandsParser.getPlayerHero(game, currentAction.getPlayerIdx(), output);
                    case "getPlayerTurn" ->
                            CommandsParser.getPlayerTurn(game, output);
                    case "endPlayerTurn" ->
                            CommandsParser.endPlayerTurn(game, currentGame.getStartGame().getStartingPlayer());
                    case "getPlayerMana" ->
                            CommandsParser.getPlayerMana(game, currentAction.getPlayerIdx(), output);
                    case "placeCard" ->
                            CommandsParser.placeCard(game, game.getActivePlayer(), currentAction.getHandIdx(), output);
                    case "getCardsOnTable" ->
                            CommandsParser.getCardsOnTable(game, output);
                    case "useEnvironmentCard" ->
                            CommandsParser.useEnvironmentCard(game, game.getActivePlayer(), currentAction.getHandIdx(),
                                    currentAction.getAffectedRow(), output);
                    case "getEnvironmentCardsInHand" ->
                            CommandsParser.getEnvironmentCardsInHand(game, currentAction.getPlayerIdx(), output);
                    case "getCardAtPosition" ->
                            CommandsParser.getCardAtPosition(game, currentAction.getX(), currentAction.getY(), output);
                    case "getFrozenCardsOnTable" ->
                            CommandsParser.getFrozenCardsOnTable(game, output);
                }
            }
        }
    }
}
