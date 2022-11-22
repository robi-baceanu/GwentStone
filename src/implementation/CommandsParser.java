package implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import implementation.abilityMinions.Miraj;
import implementation.abilityMinions.TheRipper;
import implementation.environmentCards.HeartHound;
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
        int activePlayer = game.getActivePlayer();
        int frontRow;
        int backRow;

        if (activePlayer == 1) {
            frontRow = 2;
            backRow = 3;
        } else {
            frontRow = 1;
            backRow = 0;
        }

        if (game.gameTable[frontRow] != null) {
            for (Card card : game.gameTable[frontRow]) {
                ((Minion) card).setFrozen(false);
            }
        }

        if (game.gameTable[backRow] != null) {
            for (Card card : game.gameTable[backRow]) {
                ((Minion) card).setFrozen(false);
            }
        }

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

        Card cardToPlace = player.getPlayerHand().get(handIdx);

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "placeCard");
        toSend.put("handIdx", handIdx);

        if (cardToPlace instanceof Environment) {
            toSend.put("error", "Cannot place environment card on table.");
            output.add(toSend);
        } else if (player.getMana() < cardToPlace.getMana()) {
            toSend.put("error", "Not enough mana to place card on table.");
            output.add(toSend);
        } else {
            String rowToPLace;

            if (cardToPlace instanceof Goliath || cardToPlace instanceof Warden ||
                    cardToPlace instanceof TheRipper || cardToPlace instanceof Miraj) {
                rowToPLace = "front";
            } else {
                rowToPLace = "back";
            }

            if (rowToPLace.equals("front")) {
                if (game.gameTable[frontRow].size() == 5) {
                    toSend.put("error", "Cannot place card on table since row is full.");
                    output.add(toSend);
                } else {
                    game.gameTable[frontRow].add(cardToPlace);
                    player.getPlayerHand().remove(cardToPlace);
                    player.setMana(player.getMana() - cardToPlace.getMana());
                }
            } else {
                if (game.gameTable[backRow].size() == 5) {
                    toSend.put("error", "Cannot place card on table since row is full.");
                    output.add(toSend);
                } else {
                    game.gameTable[backRow].add(cardToPlace);
                    player.getPlayerHand().remove(cardToPlace);
                    player.setMana(player.getMana() - cardToPlace.getMana());
                }
            }
        }
    }

    public static void getCardsOnTable(Game game, ArrayNode output) {
        ArrayNode nestedOutput = objectMapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            ArrayNode rowNode = objectMapper.createArrayNode();
            for (Card card : game.gameTable[i]) {
                ObjectNode cardNode = objectMapper.createObjectNode();

                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                ArrayNode colors = objectMapper.createArrayNode();
                for (int k = 0; k < card.getColors().size(); k++) {
                    colors.add(card.getColors().get(k));
                }
                cardNode.set("colors", colors);
                cardNode.put("description", card.getDescription());
                cardNode.put("health", ((Minion) card).getHealth());
                cardNode.put("mana", card.getMana());
                cardNode.put("name", card.getName());

                rowNode.add(cardNode);
            }

            nestedOutput.add(rowNode);
        }

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getCardsOnTable");
        toSend.set("output", nestedOutput);

        output.add(toSend);
    }

    public static void useEnvironmentCard(Game game, int activePlayer, int handIdx, int affectedRow, ArrayNode output) {
        Player player;
        int frontRowToAttack;
        int backRowToAttack;

        if (activePlayer == 1) {
            player = game.getPlayerOne();
            frontRowToAttack = 1;
            backRowToAttack = 0;
        } else {
            player = game.getPlayerTwo();
            frontRowToAttack = 2;
            backRowToAttack = 3;
        }

        Card cardToUse = player.getPlayerHand().get(handIdx);

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "useEnvironmentCard");
        toSend.put("handIdx", handIdx);
        toSend.put("affectedRow", affectedRow);

        if (!(cardToUse instanceof Environment)) {
            toSend.put("error", "Chosen card is not of type environment.");
            output.add(toSend);
        } else if (player.getMana() < cardToUse.getMana()) {
            toSend.put("error", "Not enough mana to use environment card.");
            output.add(toSend);
        } else if (affectedRow != frontRowToAttack && affectedRow != backRowToAttack) {
            toSend.put("error", "Chosen row does not belong to the enemy.");
            output.add(toSend);
        } else {
            if (cardToUse instanceof HeartHound && game.gameTable[3 - affectedRow].size() == 5) {
                toSend.put("error", "Cannot steal enemy card since the player's row is full.");
                output.add(toSend);
            } else {
                ((Environment) cardToUse).useEnvironmentAbility(game, affectedRow);
                player.setMana(player.getMana() - cardToUse.getMana());
                player.getPlayerHand().remove(handIdx);
            }
        }
    }

    public static void getEnvironmentCardsInHand(Game game, int playerIdx, ArrayNode output) {
        ArrayNode nestedOutput = objectMapper.createArrayNode();
        ArrayNode handNode = objectMapper.createArrayNode();
        ArrayList<Card> hand;

        if (playerIdx == 1) {
            hand = game.getPlayerOne().getPlayerHand();
        } else {
            hand = game.getPlayerTwo().getPlayerHand();
        }

        for (Card card : hand) {
            if (card instanceof Environment) {
                ObjectNode cardNode = objectMapper.createObjectNode();

                cardNode.put("mana", card.getMana());
                cardNode.put("description", card.getDescription());
                ArrayNode colors = objectMapper.createArrayNode();
                for (int j = 0; j < card.getColors().size(); j++) {
                    colors.add(card.getColors().get(j));
                }
                cardNode.set("colors", colors);
                cardNode.put("name", card.getName());

                handNode.add(cardNode);
            }
        }

        nestedOutput.addAll(handNode);

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getEnvironmentCardsInHand");
        toSend.set("output", nestedOutput);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    public static void getCardAtPosition(Game game, int x, int y, ArrayNode output) {
        ObjectNode cardNode = objectMapper.createObjectNode();

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getCardAtPosition");
        toSend.put("x", x);
        toSend.put("y", y);

        if (game.gameTable[x] == null) {
            toSend.put("output", "No card available at that position.");
        } else if (game.gameTable[x].size() <= y) {
            toSend.put("output", "No card available at that position.");
        } else {
            Card card = game.gameTable[x].get(y);

            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", ((Minion) card).getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = objectMapper.createArrayNode();
            for (int i = 0; i < card.getColors().size(); i++) {
                colors.add(card.getColors().get(i));
            }
            cardNode.set("colors", colors);
            cardNode.put("name", card.getName());

            toSend.set("output", cardNode);
        }

        output.add(toSend);
    }

    public static void getFrozenCardsOnTable(Game game, ArrayNode output) {
        ArrayNode nestedOutput = objectMapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            for (Card card : game.gameTable[i]) {
                if (((Minion) card).isFrozen()) {
                    ObjectNode cardNode = objectMapper.createObjectNode();

                    cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                    ArrayNode colors = objectMapper.createArrayNode();
                    for (int k = 0; k < card.getColors().size(); k++) {
                        colors.add(card.getColors().get(k));
                    }
                    cardNode.set("colors", colors);
                    cardNode.put("description", card.getDescription());
                    cardNode.put("health", ((Minion) card).getHealth());
                    cardNode.put("mana", card.getMana());
                    cardNode.put("name", card.getName());

                    nestedOutput.add(cardNode);
                }
            }
        }

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "getFrozenCardsOnTable");
        toSend.set("output", nestedOutput);

        output.add(toSend);
    }

}
