package implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class CommandsParser {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void getPlayerDeck(Game game, int playerIdx, ArrayNode output) {
        ArrayNode nestedOutput = objectMapper.createArrayNode();
        ArrayNode deckNode = objectMapper.createArrayNode();
        ArrayList<Card> deck;

        if (playerIdx == 1) {
            deck = game.getPlayerOne().getPlayerDeck();
        } else {
            deck = game.getPlayerTwo().getPlayerDeck();
        }

        for (Card card : deck) {
            ObjectNode cardNode = objectMapper.createObjectNode();

            if (card instanceof Minion) {
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            }
            ArrayNode colors = objectMapper.createArrayNode();
            for (int j = 0; j < card.getColors().size(); j++) {
                colors.add(card.getColors().get(j));
            }
            cardNode.set("colors", colors);
            cardNode.put("description", card.getDescription());
            if (card instanceof Minion) {
                cardNode.put("health", ((Minion) card).getHealth());
            }
            cardNode.put("mana", card.getMana());
            cardNode.put("name", card.getName());

            deckNode.add(cardNode);
        }

        nestedOutput.addAll(deckNode);

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getPlayerDeck");
        toSend.set("output", nestedOutput);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    public static void getPlayerHero(Game game, int playerIdx, ArrayNode output) {
        ObjectNode heroNode = objectMapper.createObjectNode();
        Hero hero;

        if (playerIdx == 1) {
            hero = game.getPlayerOne().getPlayerHero();
        } else {
            hero = game.getPlayerTwo().getPlayerHero();
        }

        heroNode.put("mana", hero.getMana());
        heroNode.put("description", hero.getDescription());
        ArrayNode colors = objectMapper.createArrayNode();
        for (int i = 0; i < hero.getColors().size(); i++) {
            colors.add(hero.getColors().get(i));
        }
        heroNode.set("colors", colors);
        heroNode.put("name", hero.getName());
        heroNode.put("health", hero.getHealth());

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getPlayerHero");
        toSend.set("output", heroNode);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    public static void getPlayerTurn(Game game, ArrayNode output) {
        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getPlayerTurn");
        toSend.put("output", game.getActivePlayer());

        output.add(toSend);
    }
}
