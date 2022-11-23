package implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import implementation.abilityMinions.Disciple;
import implementation.abilityMinions.Miraj;
import implementation.abilityMinions.TheCursedOne;
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
                ((Minion) card).setHasAttacked(false);
            }
        }

        if (game.gameTable[backRow] != null) {
            for (Card card : game.gameTable[backRow]) {
                ((Minion) card).setFrozen(false);
                ((Minion) card).setHasAttacked(false);
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

    public static void cardUsesAttack(Game game, Coordinates attackerCoordinates, Coordinates attackedCoordinates, ArrayNode output) {
        Card cardAttacker = game.gameTable[attackerCoordinates.getX()].get(attackerCoordinates.getY());
        Card cardAttacked = game.gameTable[attackedCoordinates.getX()].get(attackedCoordinates.getY());

        ObjectNode nodeAttacker = objectMapper.createObjectNode();
        nodeAttacker.put("x", attackerCoordinates.getX());
        nodeAttacker.put("y", attackerCoordinates.getY());

        ObjectNode nodeAttacked = objectMapper.createObjectNode();
        nodeAttacked.put("x", attackedCoordinates.getX());
        nodeAttacked.put("y", attackedCoordinates.getY());

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "cardUsesAttack");
        toSend.set("cardAttacker", nodeAttacker);
        toSend.set("cardAttacked", nodeAttacked);

        boolean tankExists = false;
        int lookForTankRow = attackedCoordinates.getX();
        if (lookForTankRow == 0) {
            lookForTankRow = 1;
        } else if (lookForTankRow == 3) {
            lookForTankRow = 2;
        }
        for (Card card : game.gameTable[lookForTankRow]) {
            if (((Minion) card).isTank()) {
                tankExists = true;
                break;
            }
        }

        if (((attackerCoordinates.getX() == 1 || attackerCoordinates.getX() == 0) &&
                (attackedCoordinates.getX() == 1 || attackedCoordinates.getX() == 0)) ||
                ((attackerCoordinates.getX() == 2 || attackerCoordinates.getX() == 3) &&
                (attackedCoordinates.getX() == 2 || attackedCoordinates.getX() == 3))) {
            toSend.put("error", "Attacked card does not belong to the enemy.");
            output.add(toSend);
        } else if (((Minion) cardAttacker).hasAttacked()) {
            toSend.put("error", "Attacker card has already attacked this turn.");
            output.add(toSend);
        } else if (((Minion) cardAttacker).isFrozen()) {
            toSend.put("error", "Attacker card is frozen.");
            output.add(toSend);
        } else if (tankExists && !((Minion) cardAttacked).isTank()) {
            toSend.put("error", "Attacked card is not of type 'Tank'.");
            output.add(toSend);
        } else {
            ((Minion) cardAttacked).setHealth(((Minion) cardAttacked).getHealth() - ((Minion) cardAttacker).getAttackDamage());
            if (((Minion) cardAttacked).getHealth() <= 0) {
                game.gameTable[attackedCoordinates.getX()].remove(cardAttacked);
            }

            ((Minion) cardAttacker).setHasAttacked(true);
        }
    }

    public static void cardUsesAbility(Game game, Coordinates attackerCoordinates, Coordinates attackedCoordinates, ArrayNode output) {
        Card cardAttacker = game.gameTable[attackerCoordinates.getX()].get(attackerCoordinates.getY());
        Card cardAttacked = game.gameTable[attackedCoordinates.getX()].get(attackedCoordinates.getY());

        ObjectNode nodeAttacker = objectMapper.createObjectNode();
        nodeAttacker.put("x", attackerCoordinates.getX());
        nodeAttacker.put("y", attackerCoordinates.getY());

        ObjectNode nodeAttacked = objectMapper.createObjectNode();
        nodeAttacked.put("x", attackedCoordinates.getX());
        nodeAttacked.put("y", attackedCoordinates.getY());

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "cardUsesAbility");
        toSend.set("cardAttacker", nodeAttacker);
        toSend.set("cardAttacked", nodeAttacked);

        boolean tankExists = false;
        int lookForTankRow = attackedCoordinates.getX();
        if (lookForTankRow == 0) {
            lookForTankRow = 1;
        } else if (lookForTankRow == 3) {
            lookForTankRow = 2;
        }
        for (Card card : game.gameTable[lookForTankRow]) {
            if (((Minion) card).isTank()) {
                tankExists = true;
                break;
            }
        }

        if (((Minion) cardAttacker).isFrozen()) {
            toSend.put("error", "Attacker card is frozen.");
            output.add(toSend);
        } else if (((Minion) cardAttacker).hasAttacked()) {
            toSend.put("error", "Attacker card has already attacked this turn.");
            output.add(toSend);
        } else if (cardAttacker instanceof Disciple) {
            if (((attackerCoordinates.getX() == 0 || attackerCoordinates.getX() == 1) &&
                    (attackedCoordinates.getX() == 2 || attackedCoordinates.getX() == 3)) ||
                    ((attackerCoordinates.getX() == 2 || attackerCoordinates.getX() == 3) &&
                    (attackedCoordinates.getX() == 0 || attackedCoordinates.getX() == 1))) {
                toSend.put("error", "Attacked card does not belong to the current player.");
                output.add(toSend);
            } else {
                ((AbilityMinion) cardAttacker).useMinionAbility(game, attackedCoordinates);
            }
        } else if (cardAttacker instanceof TheRipper ||
                cardAttacker instanceof Miraj ||
                cardAttacker instanceof TheCursedOne) {
            if (((attackerCoordinates.getX() == 0 || attackerCoordinates.getX() == 1) &&
                    (attackedCoordinates.getX() == 0 || attackedCoordinates.getX() == 1)) ||
                    ((attackerCoordinates.getX() == 2 || attackerCoordinates.getX() == 3) &&
                    (attackedCoordinates.getX() == 2 || attackedCoordinates.getX() == 3))) {
                toSend.put("error", "Attacked card does not belong to the enemy.");
                output.add(toSend);
            } else if (tankExists && !((Minion) cardAttacked).isTank()) {
                toSend.put("error", "Attacked card is not of type 'Tank'.");
                System.out.println(cardAttacked.getName());
                System.out.println(((Minion) cardAttacked).isTank());
                output.add(toSend);
            } else {
                ((AbilityMinion) cardAttacker).useMinionAbility(game, attackedCoordinates);
                ((AbilityMinion) cardAttacker).setHasAttacked(true);
            }
        }
    }

    public static void useAttackHero(Game game, Coordinates attackerCoordinates, ArrayNode output) {
        Hero enemyHero;

        if (attackerCoordinates.getX() == 0 || attackerCoordinates.getX() == 1) {
            enemyHero = game.getPlayerOne().getPlayerHero();
        } else {
            enemyHero = game.getPlayerTwo().getPlayerHero();
        }

        Card cardAttacker = game.gameTable[attackerCoordinates.getX()].get(attackerCoordinates.getY());

        ObjectNode nodeAttacker = objectMapper.createObjectNode();
        nodeAttacker.put("x", attackerCoordinates.getX());
        nodeAttacker.put("y", attackerCoordinates.getY());

        ObjectNode toSend = objectMapper.createObjectNode();
        toSend.put("command", "useAttackHero");
        toSend.set("cardAttacker", nodeAttacker);

        boolean tankExists = false;
        int lookForTankRow;
        if (attackerCoordinates.getX() == 0 || attackerCoordinates.getX() == 1) {
            lookForTankRow = 2;
        } else {
            lookForTankRow = 1;
        }
        for (Card card : game.gameTable[lookForTankRow]) {
            if (((Minion) card).isTank()) {
                tankExists = true;
                break;
            }
        }

        if (((Minion) cardAttacker).isFrozen()) {
            toSend.put("error", "Attacker card is frozen.");
            output.add(toSend);
        } else if (((Minion) cardAttacker).hasAttacked()) {
            toSend.put("error", "Attacker card has already attacked this turn.");
            output.add(toSend);
        } else if (tankExists) {
            toSend.put("error", "Attacked card is not of type 'Tank'.");
            output.add(toSend);
        } else {
            enemyHero.setHealth(enemyHero.getHealth() - ((Minion) cardAttacker).getAttackDamage());
            ((Minion) cardAttacker).setHasAttacked(true);

            if (enemyHero.getHealth() <= 0) {
                game.setGameEnded(true);

                ObjectNode victoryNode = objectMapper.createObjectNode();
                if (attackerCoordinates.getX() == 0 || attackerCoordinates.getX() == 1) {
                    victoryNode.put("gameEnded", "Player two killed the enemy hero.");
                } else {
                    victoryNode.put("gameEnded", "Player one killed the enemy hero.");
                }
                output.add(victoryNode);
            }
        }
    }
}
