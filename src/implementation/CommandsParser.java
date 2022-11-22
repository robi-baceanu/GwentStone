package implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.abilityMinions.Miraj;
import implementation.abilityMinions.TheRipper;
import implementation.standardMinions.Goliath;
import implementation.standardMinions.Warden;

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

    public static void getCardsInHand(Game game, int playerIdx, ArrayNode output) {
        ArrayNode nestedOutput = objectMapper.createArrayNode();
        ArrayNode handNode = objectMapper.createArrayNode();
        ArrayList<Card> hand;

        if (playerIdx == 1) {
            hand = game.getPlayerOne().getPlayerHand();
        } else {
            hand = game.getPlayerTwo().getPlayerHand();
        }

        for (Card card : hand) {
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

            handNode.add(cardNode);
        }

        nestedOutput.addAll(handNode);

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getCardsInHand");
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

    public static void endPlayerTurn(Game game, int startingPlayer) {
        game.setActivePlayer(3 - game.getActivePlayer());
        if (game.getActivePlayer() == startingPlayer) {
            // Active player is starting player again, so a new round starts
            game.setRound(game.getRound() + 1);

            game.getPlayerOne().drawCardFromDeck();
            game.getPlayerTwo().drawCardFromDeck();

            game.getPlayerOne().setMana(game.getPlayerOne().getMana() + Math.min(game.getRound(), 10));
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana() + Math.min(game.getRound(), 10));
        }
    }

    public static void getPlayerMana(Game game, int playerIdx, ArrayNode output) {
        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getPlayerMana");
        toSend.put("playerIdx", playerIdx);
        if (playerIdx == 1) {
            toSend.put("output", game.getPlayerOne().getMana());
        } else {
            toSend.put("output", game.getPlayerTwo().getMana());
        }

        output.add(toSend);
    }

    public static void placeCard(Game game, int activePlayer, int handIdx, ArrayNode output) {
        Player player;
        int frontRow;
        int backRow;

        if (activePlayer == 1) {
            player = game.getPlayerOne();
            frontRow = 2;
            backRow = 3;
        } else {
            player = game.getPlayerTwo();
            frontRow = 1;
            backRow = 0;
        }

        if (handIdx < player.getPlayerHand().size()) {
            Card card = player.getPlayerHand().get(handIdx);
            if (card instanceof Environment) {
                ObjectNode toSend = objectMapper.createObjectNode();
                toSend.put("command", "placeCard");
                toSend.put("handIdx", handIdx);
                toSend.put("error", "Cannot place environment card on table.");
                output.add(toSend);
            } else if (card.getMana() > player.getMana()) {
                ObjectNode toSend = objectMapper.createObjectNode();
                toSend.put("command", "placeCard");
                toSend.put("handIdx", handIdx);
                toSend.put("error", "Not enough mana to place card on table.");
                output.add(toSend);
            } else {
                String row;

                if (card instanceof Goliath || card instanceof Warden || card instanceof TheRipper || card instanceof Miraj) {
                    row = "front";
                } else {
                    row = "back";
                }

                if (row.equals("front")) {
                    if (game.gameTable[frontRow][4] != null) {
                        ObjectNode toSend = objectMapper.createObjectNode();
                        toSend.put("command", "placeCard");
                        toSend.put("handIdx", handIdx);
                        toSend.put("error", "Cannot place card on table since row is full.");
                        output.add(toSend);
                    } else {
                        int position = 0;
                        while (game.gameTable[frontRow][position] != null) {
                            position++;
                        }
                        game.gameTable[frontRow][position] = player.getPlayerHand().get(handIdx);
                        player.getPlayerHand().remove(handIdx);
                        player.setMana(Math.max(player.getMana() - card.getMana(), 0));
                    }
                } else {
                    if (game.gameTable[backRow][4] != null) {
                        ObjectNode toSend = objectMapper.createObjectNode();
                        toSend.put("command", "placeCard");
                        toSend.put("handIdx", handIdx);
                        toSend.put("error", "Cannot place card on table since row is full.");
                        output.add(toSend);
                    } else {
                        int position = 0;
                        while (game.gameTable[backRow][position] != null) {
                            position++;
                        }
                        game.gameTable[backRow][position] = player.getPlayerHand().get(handIdx);
                        player.getPlayerHand().remove(handIdx);
                        player.setMana(Math.max(player.getMana() - card.getMana(), 0));
                    }
                }
            }
        }
    }

    public static void getCardsOnTable(Game game, ArrayNode output) {
        ArrayNode nestedOutput = objectMapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            ArrayNode rowNode = objectMapper.createArrayNode();

            for (int j = 0; j < 5; j++) {
                if (game.gameTable[i][j] != null) {
                    ObjectNode cardNode = objectMapper.createObjectNode();
                    Card card = game.gameTable[i][j];

                    if (card instanceof Minion) {
                        cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                    }
                    ArrayNode colors = objectMapper.createArrayNode();
                    for (int k = 0; k < card.getColors().size(); k++) {
                        colors.add(card.getColors().get(k));
                    }
                    cardNode.set("colors", colors);
                    cardNode.put("description", card.getDescription());
                    if (card instanceof Minion) {
                        cardNode.put("health", ((Minion) card).getHealth());
                    }
                    cardNode.put("mana", card.getMana());
                    cardNode.put("name", card.getName());

                    rowNode.add(cardNode);
                }
            }

            nestedOutput.add(rowNode);
        }

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getCardsOnTable");
        toSend.set("output", nestedOutput);

        output.add(toSend);
    }
}
