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

            for (int j = 0; j < currentGame.getActions().size(); j++) {
                ActionsInput currentAction = currentGame.getActions().get(j);

                switch (currentAction.getCommand()) {
                    case "getPlayerDeck" -> CommandsParser.getPlayerDeck(game, currentAction.getPlayerIdx(), output);
                    case "getPlayerHero" -> CommandsParser.getPlayerHero(game, currentAction.getPlayerIdx(), output);
                    case "getPlayerTurn" -> CommandsParser.getPlayerTurn(game, output);
                }
            }
        }
    }
}
