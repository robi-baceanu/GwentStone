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
import implementation.heroCards.EmpressThorina;
import implementation.heroCards.GeneralKocioraw;
import implementation.heroCards.KingMudface;
import implementation.heroCards.LordRoyce;
import implementation.standardMinions.Goliath;
import implementation.standardMinions.Warden;

import java.util.ArrayList;

/**
 * Utility class that implements debugging and action commands
 * given at input.
 *
 * @author wh1ter0se
 */
public final class CommandsParser {
    private CommandsParser() {

    }
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Method that passes to output the selected player's deck.
     *
     * @param game Current game that is being played.
     * @param playerIdx Index of selected player.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getPlayerDeck(final Game game,
                                     final int playerIdx, final ArrayNode output) {
        ArrayNode nestedOutput = OBJECT_MAPPER.createArrayNode();
        ArrayNode deckNode = OBJECT_MAPPER.createArrayNode();
        ArrayList<Card> deck;

        if (playerIdx == 1) {
            deck = game.getPlayerOne().getPlayerDeck();
        } else {
            deck = game.getPlayerTwo().getPlayerDeck();
        }

        for (Card card : deck) {
            ObjectNode cardNode = OBJECT_MAPPER.createObjectNode();

            if (card instanceof Minion) {
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            }
            ArrayNode colors = OBJECT_MAPPER.createArrayNode();
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

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getPlayerDeck");
        toSend.set("output", nestedOutput);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    /**
     * Method that passes to output the selected player's hand.
     *
     * @param game Current game that is being played.
     * @param playerIdx Index of selected player.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getCardsInHand(final Game game,
                                      final int playerIdx, final ArrayNode output) {
        ArrayNode nestedOutput = OBJECT_MAPPER.createArrayNode();
        ArrayNode handNode = OBJECT_MAPPER.createArrayNode();
        ArrayList<Card> hand;

        if (playerIdx == 1) {
            hand = game.getPlayerOne().getPlayerHand();
        } else {
            hand = game.getPlayerTwo().getPlayerHand();
        }

        for (Card card : hand) {
            ObjectNode cardNode = OBJECT_MAPPER.createObjectNode();

            if (card instanceof Minion) {
                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            }
            ArrayNode colors = OBJECT_MAPPER.createArrayNode();
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

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getCardsInHand");
        toSend.set("output", nestedOutput);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    /**
     * Method that passes to output the selected player's Hero.
     *
     * @param game Current game that is being played.
     * @param playerIdx Index of selected player.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getPlayerHero(final Game game,
                                     final int playerIdx, final ArrayNode output) {
        ObjectNode heroNode = OBJECT_MAPPER.createObjectNode();
        Hero hero;

        if (playerIdx == 1) {
            hero = game.getPlayerOne().getPlayerHero();
        } else {
            hero = game.getPlayerTwo().getPlayerHero();
        }

        heroNode.put("mana", hero.getMana());
        heroNode.put("description", hero.getDescription());
        ArrayNode colors = OBJECT_MAPPER.createArrayNode();
        for (int i = 0; i < hero.getColors().size(); i++) {
            colors.add(hero.getColors().get(i));
        }
        heroNode.set("colors", colors);
        heroNode.put("name", hero.getName());
        heroNode.put("health", hero.getHealth());

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getPlayerHero");
        toSend.set("output", heroNode);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    /**
     * Method that passes to output which player's turn it is currently.
     *
     * @param game Current game that is being played.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getPlayerTurn(final Game game,
                                     final ArrayNode output) {
        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getPlayerTurn");
        toSend.put("output", game.getActivePlayer());

        output.add(toSend);
    }

    /**
     * Method that ends the current player's turn and unfreezes its frozen cards.
     * If a new round is to begin, both players also draw a card from their decks.
     *
     * @param game Current game that is being played.
     * @param startingPlayer Index of player that started the game.
     */
    public static void endPlayerTurn(final Game game,
                                     final int startingPlayer) {
        int activePlayer = game.getActivePlayer();
        Hero activePlayerHero;
        int frontRow;
        int backRow;

        if (activePlayer == 1) {
            activePlayerHero = game.getPlayerOne().getPlayerHero();
            frontRow = MagicNumbers.ROW_2;
            backRow = MagicNumbers.ROW_3;
        } else {
            activePlayerHero = game.getPlayerTwo().getPlayerHero();
            frontRow = MagicNumbers.ROW_1;
            backRow = MagicNumbers.ROW_0;
        }

        for (Card card : game.getGameTable(frontRow)) {
            ((Minion) card).setFrozen(false);
            ((Minion) card).setHasAttacked(false);
        }

        for (Card card : game.getGameTable(backRow)) {
            ((Minion) card).setFrozen(false);
            ((Minion) card).setHasAttacked(false);
        }

        activePlayerHero.setHasAttacked(false);

        game.setActivePlayer(MagicNumbers.SWITCH_PLAYER - game.getActivePlayer());

        if (game.getActivePlayer() == startingPlayer) {
            game.setRound(game.getRound() + 1);

            game.getPlayerOne().drawCardFromDeck();
            game.getPlayerTwo().drawCardFromDeck();

            game.getPlayerOne().setMana(game.getPlayerOne().getMana()
                    + Math.min(game.getRound(), MagicNumbers.MAX_MANA));
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana()
                    + Math.min(game.getRound(), MagicNumbers.MAX_MANA));
        }
    }

    /**
     * Method that passes to output the selected player's mana.
     *
     * @param game Current game that is being played.
     * @param playerIdx Index of selected player.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getPlayerMana(final Game game, final int playerIdx,
                                     final ArrayNode output) {
        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getPlayerMana");
        toSend.put("playerIdx", playerIdx);
        if (playerIdx == 1) {
            toSend.put("output", game.getPlayerOne().getMana());
        } else {
            toSend.put("output", game.getPlayerTwo().getMana());
        }

        output.add(toSend);
    }

    /**
     * Method that places a card from a player's hand on the table and passes
     * errors to output if needed.
     *
     * @param game Current game that is being played.
     * @param activePlayer Player that is currently making a move.
     * @param handIdx Index (in hand) of card that is to be placed.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void placeCard(final Game game, final int activePlayer,
                                 final int handIdx, final ArrayNode output) {
        Player player;
        int frontRow;
        int backRow;

        if (activePlayer == 1) {
            player = game.getPlayerOne();
            frontRow = MagicNumbers.ROW_2;
            backRow = MagicNumbers.ROW_3;
        } else {
            player = game.getPlayerTwo();
            frontRow = MagicNumbers.ROW_1;
            backRow = MagicNumbers.ROW_0;
        }

        Card cardToPlace = player.getPlayerHand().get(handIdx);

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
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

            if (cardToPlace instanceof Goliath || cardToPlace instanceof Warden
                    || cardToPlace instanceof TheRipper || cardToPlace instanceof Miraj) {
                rowToPLace = "front";
            } else {
                rowToPLace = "back";
            }

            if (rowToPLace.equals("front")) {
                if (game.getGameTable(frontRow).size() == MagicNumbers.ROW_CAPACITY) {
                    toSend.put("error", "Cannot place card on table since row is full.");
                    output.add(toSend);
                } else {
                    game.getGameTable(frontRow).add(cardToPlace);
                    player.getPlayerHand().remove(cardToPlace);
                    player.setMana(player.getMana() - cardToPlace.getMana());
                }
            } else {
                if (game.getGameTable(backRow).size() == MagicNumbers.ROW_CAPACITY) {
                    toSend.put("error", "Cannot place card on table since row is full.");
                    output.add(toSend);
                } else {
                    game.getGameTable(backRow).add(cardToPlace);
                    player.getPlayerHand().remove(cardToPlace);
                    player.setMana(player.getMana() - cardToPlace.getMana());
                }
            }
        }
    }

    /**
     * Method that passes to output all cards that are currently placed on the table.
     *
     * @param game Current game that is being played.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getCardsOnTable(final Game game, final ArrayNode output) {
        ArrayNode nestedOutput = OBJECT_MAPPER.createArrayNode();

        for (int i = 0; i < MagicNumbers.NUMBER_OF_ROWS; i++) {
            ArrayNode rowNode = OBJECT_MAPPER.createArrayNode();
            for (Card card : game.getGameTable(i)) {
                ObjectNode cardNode = OBJECT_MAPPER.createObjectNode();

                cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                ArrayNode colors = OBJECT_MAPPER.createArrayNode();
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

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getCardsOnTable");
        toSend.set("output", nestedOutput);

        output.add(toSend);
    }

    /**
     * Method that uses an Environment card's ability, and then removes it from the
     * player's hand.
     *
     * @param game Current game that is being played.
     * @param activePlayer Player that is currently making a move.
     * @param handIdx Index (in hand) of card that is to be used.
     * @param affectedRow Row targeted by the Environment card.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void useEnvironmentCard(final Game game, final int activePlayer,
                                          final int handIdx, final int affectedRow,
                                          final ArrayNode output) {
        Player player;
        int frontRowToAttack;
        int backRowToAttack;

        if (activePlayer == 1) {
            player = game.getPlayerOne();
            frontRowToAttack = MagicNumbers.ROW_1;
            backRowToAttack = MagicNumbers.ROW_0;
        } else {
            player = game.getPlayerTwo();
            frontRowToAttack = MagicNumbers.ROW_2;
            backRowToAttack = MagicNumbers.ROW_3;
        }

        Card cardToUse = player.getPlayerHand().get(handIdx);

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
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
            if (cardToUse instanceof HeartHound
                    && game.getGameTable(MagicNumbers.SWITCH_PLAYER - affectedRow).size()
                    == MagicNumbers.ROW_CAPACITY) {
                toSend.put("error", "Cannot steal enemy card since the player's row is full.");
                output.add(toSend);
            } else {
                ((Environment) cardToUse).useEnvironmentAbility(game, affectedRow);
                player.setMana(player.getMana() - cardToUse.getMana());
                player.getPlayerHand().remove(handIdx);
            }
        }
    }

    /**
     * Method that passes to output the selected player's Environment cards in hand.
     *
     * @param game Current game that is being played.
     * @param playerIdx Index of selected player.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getEnvironmentCardsInHand(final Game game, final int playerIdx,
                                                 final ArrayNode output) {
        ArrayNode nestedOutput = OBJECT_MAPPER.createArrayNode();
        ArrayNode handNode = OBJECT_MAPPER.createArrayNode();
        ArrayList<Card> hand;

        if (playerIdx == 1) {
            hand = game.getPlayerOne().getPlayerHand();
        } else {
            hand = game.getPlayerTwo().getPlayerHand();
        }

        for (Card card : hand) {
            if (card instanceof Environment) {
                ObjectNode cardNode = OBJECT_MAPPER.createObjectNode();

                cardNode.put("mana", card.getMana());
                cardNode.put("description", card.getDescription());
                ArrayNode colors = OBJECT_MAPPER.createArrayNode();
                for (int j = 0; j < card.getColors().size(); j++) {
                    colors.add(card.getColors().get(j));
                }
                cardNode.set("colors", colors);
                cardNode.put("name", card.getName());

                handNode.add(cardNode);
            }
        }

        nestedOutput.addAll(handNode);

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getEnvironmentCardsInHand");
        toSend.set("output", nestedOutput);
        toSend.put("playerIdx", playerIdx);

        output.add(toSend);
    }

    /**
     * Method that passes to output the card placed on the x-th row and y-th column
     * on the table, or an error if there is no card at that position.
     *
     * @param game Current game that is being played.
     * @param x Row of table.
     * @param y Position in row.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getCardAtPosition(final Game game, final int x, final int y,
                                         final ArrayNode output) {
        ObjectNode cardNode = OBJECT_MAPPER.createObjectNode();

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getCardAtPosition");
        toSend.put("x", x);
        toSend.put("y", y);

        if (game.getGameTable(x).size() <= y) {
            toSend.put("output", "No card available at that position.");
        } else {
            Card card = game.getGameTable(x).get(y);

            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
            cardNode.put("health", ((Minion) card).getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colors = OBJECT_MAPPER.createArrayNode();
            for (int i = 0; i < card.getColors().size(); i++) {
                colors.add(card.getColors().get(i));
            }
            cardNode.set("colors", colors);
            cardNode.put("name", card.getName());

            toSend.set("output", cardNode);
        }

        output.add(toSend);
    }

    /**
     * Method that passes to output all currently frozen cards on the table.
     *
     * @param game Current game that is being played.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getFrozenCardsOnTable(final Game game, final ArrayNode output) {
        ArrayNode nestedOutput = OBJECT_MAPPER.createArrayNode();

        for (int i = 0; i < MagicNumbers.NUMBER_OF_ROWS; i++) {
            for (Card card : game.getGameTable(i)) {
                if (((Minion) card).isFrozen()) {
                    ObjectNode cardNode = OBJECT_MAPPER.createObjectNode();

                    cardNode.put("attackDamage", ((Minion) card).getAttackDamage());
                    ArrayNode colors = OBJECT_MAPPER.createArrayNode();
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

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getFrozenCardsOnTable");
        toSend.set("output", nestedOutput);

        output.add(toSend);
    }

    /**
     * Method that lets a card placed on the table attack an enemy one and passes
     * errors to output if needed.
     *
     * @param game Current game that is being played.
     * @param attackerCoordinates Coordinates of attacker.
     * @param attackedCoordinates Coordinates of victim.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void cardUsesAttack(final Game game, final Coordinates attackerCoordinates,
                                      final Coordinates attackedCoordinates,
                                      final ArrayNode output) {
        Card cardAttacker = game.getGameTable(attackerCoordinates.getX())
                .get(attackerCoordinates.getY());
        Card cardAttacked = game.getGameTable(attackedCoordinates.getX())
                .get(attackedCoordinates.getY());

        ObjectNode nodeAttacker = OBJECT_MAPPER.createObjectNode();
        nodeAttacker.put("x", attackerCoordinates.getX());
        nodeAttacker.put("y", attackerCoordinates.getY());

        ObjectNode nodeAttacked = OBJECT_MAPPER.createObjectNode();
        nodeAttacked.put("x", attackedCoordinates.getX());
        nodeAttacked.put("y", attackedCoordinates.getY());

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "cardUsesAttack");
        toSend.set("cardAttacker", nodeAttacker);
        toSend.set("cardAttacked", nodeAttacked);

        boolean tankExists = false;
        int lookForTankRow = attackedCoordinates.getX();
        if (lookForTankRow == MagicNumbers.ROW_0) {
            lookForTankRow = MagicNumbers.ROW_1;
        } else if (lookForTankRow == MagicNumbers.ROW_3) {
            lookForTankRow = MagicNumbers.ROW_2;
        }
        for (Card card : game.getGameTable(lookForTankRow)) {
            if (((Minion) card).isTank()) {
                tankExists = true;
                break;
            }
        }

        if (((attackerCoordinates.getX() == MagicNumbers.ROW_1
                || attackerCoordinates.getX() == MagicNumbers.ROW_0)
                && (attackedCoordinates.getX() == MagicNumbers.ROW_1
                || attackedCoordinates.getX() == MagicNumbers.ROW_0))
                || ((attackerCoordinates.getX() == MagicNumbers.ROW_2
                || attackerCoordinates.getX() == MagicNumbers.ROW_3)
                && (attackedCoordinates.getX() == MagicNumbers.ROW_2
                || attackedCoordinates.getX() == MagicNumbers.ROW_3))) {
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
            ((Minion) cardAttacked).setHealth(((Minion) cardAttacked).getHealth()
                    - ((Minion) cardAttacker).getAttackDamage());
            if (((Minion) cardAttacked).getHealth() <= 0) {
                game.getGameTable(attackedCoordinates.getX()).remove(cardAttacked);
            }

            ((Minion) cardAttacker).setHasAttacked(true);
        }
    }

    /**
     * Method that lets a card placed on the table use its ability on an enemy one and passes
     * errors to output if needed.
     *
     * @param game Current game that is being played.
     * @param attackerCoordinates Coordinates of attacker.
     * @param attackedCoordinates Coordinates of victim.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void cardUsesAbility(final Game game, final Coordinates attackerCoordinates,
                                       final Coordinates attackedCoordinates,
                                       final ArrayNode output) {
        Card cardAttacker = game.getGameTable(attackerCoordinates.getX())
                .get(attackerCoordinates.getY());
        Card cardAttacked = game.getGameTable(attackedCoordinates.getX())
                .get(attackedCoordinates.getY());

        ObjectNode nodeAttacker = OBJECT_MAPPER.createObjectNode();
        nodeAttacker.put("x", attackerCoordinates.getX());
        nodeAttacker.put("y", attackerCoordinates.getY());

        ObjectNode nodeAttacked = OBJECT_MAPPER.createObjectNode();
        nodeAttacked.put("x", attackedCoordinates.getX());
        nodeAttacked.put("y", attackedCoordinates.getY());

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "cardUsesAbility");
        toSend.set("cardAttacker", nodeAttacker);
        toSend.set("cardAttacked", nodeAttacked);

        boolean tankExists = false;
        int lookForTankRow = attackedCoordinates.getX();
        if (lookForTankRow == MagicNumbers.ROW_0) {
            lookForTankRow = MagicNumbers.ROW_1;
        } else if (lookForTankRow == MagicNumbers.ROW_3) {
            lookForTankRow = MagicNumbers.ROW_2;
        }
        for (Card card : game.getGameTable(lookForTankRow)) {
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
            if (((attackerCoordinates.getX() == MagicNumbers.ROW_0
                    || attackerCoordinates.getX() == MagicNumbers.ROW_1)
                    && (attackedCoordinates.getX() == MagicNumbers.ROW_2
                    || attackedCoordinates.getX() == MagicNumbers.ROW_3))
                    || ((attackerCoordinates.getX() == MagicNumbers.ROW_2
                    || attackerCoordinates.getX() == MagicNumbers.ROW_3)
                    && (attackedCoordinates.getX() == MagicNumbers.ROW_0
                    || attackedCoordinates.getX() == MagicNumbers.ROW_1))) {
                toSend.put("error", "Attacked card does not belong to the current player.");
                output.add(toSend);
            } else {
                ((AbilityMinion) cardAttacker).useMinionAbility(game, attackedCoordinates);
            }
        } else if (cardAttacker instanceof TheRipper || cardAttacker instanceof Miraj
                || cardAttacker instanceof TheCursedOne) {
            if (((attackerCoordinates.getX() == MagicNumbers.ROW_0
                    || attackerCoordinates.getX() == MagicNumbers.ROW_1)
                    && (attackedCoordinates.getX() == MagicNumbers.ROW_0
                    || attackedCoordinates.getX() == MagicNumbers.ROW_1))
                    || ((attackerCoordinates.getX() == MagicNumbers.ROW_2
                    || attackerCoordinates.getX() == MagicNumbers.ROW_3)
                    && (attackedCoordinates.getX() == MagicNumbers.ROW_2
                    || attackedCoordinates.getX() == MagicNumbers.ROW_3))) {
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

    /**
     * Method that lets a card placed on the table attack the enemy Hero.
     *
     * @param game Current game that is being played.
     * @param attackerCoordinates Coordinates of attacker.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void useAttackHero(final Game game, final Coordinates attackerCoordinates,
                                     final ArrayNode output) {
        Hero enemyHero;

        if (attackerCoordinates.getX() == MagicNumbers.ROW_0
                || attackerCoordinates.getX() == MagicNumbers.ROW_1) {
            enemyHero = game.getPlayerOne().getPlayerHero();
        } else {
            enemyHero = game.getPlayerTwo().getPlayerHero();
        }

        Card cardAttacker = game.getGameTable(attackerCoordinates.getX())
                .get(attackerCoordinates.getY());

        ObjectNode nodeAttacker = OBJECT_MAPPER.createObjectNode();
        nodeAttacker.put("x", attackerCoordinates.getX());
        nodeAttacker.put("y", attackerCoordinates.getY());

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "useAttackHero");
        toSend.set("cardAttacker", nodeAttacker);

        boolean tankExists = false;
        int lookForTankRow;
        if (attackerCoordinates.getX() == MagicNumbers.ROW_0
                || attackerCoordinates.getX() == MagicNumbers.ROW_1) {
            lookForTankRow = MagicNumbers.ROW_2;
        } else {
            lookForTankRow = MagicNumbers.ROW_1;
        }
        for (Card card : game.getGameTable(lookForTankRow)) {
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
                MainGame.getInstance().setTotalGamesPlayed(
                        MainGame.getInstance().getTotalGamesPlayed() + 1);

                ObjectNode victoryNode = OBJECT_MAPPER.createObjectNode();
                if (attackerCoordinates.getX() == MagicNumbers.ROW_0
                        || attackerCoordinates.getX() == MagicNumbers.ROW_1) {
                    victoryNode.put("gameEnded", "Player two killed the enemy hero.");
                    MainGame.getInstance().setPlayerTwoWins(
                            MainGame.getInstance().getPlayerTwoWins() + 1);
                } else {
                    victoryNode.put("gameEnded", "Player one killed the enemy hero.");
                    MainGame.getInstance().setPlayerOneWins(
                            MainGame.getInstance().getPlayerOneWins() + 1);
                }
                output.add(victoryNode);
            }
        }
    }

    /**
     * Method that lets a Hero use its ability.
     *
     * @param game Current game that is being played.
     * @param affectedRow Row targeted by Hero.
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void useHeroAbility(final Game game, final int affectedRow,
                                      final ArrayNode output) {
        int activePlayer = game.getActivePlayer();
        Player currentPLayer;
        Hero currentHero;

        if (activePlayer == 1) {
            currentPLayer = game.getPlayerOne();
        } else {
            currentPLayer = game.getPlayerTwo();
        }
        currentHero = currentPLayer.getPlayerHero();

        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "useHeroAbility");
        toSend.put("affectedRow", affectedRow);

        if (currentPLayer.getMana() < currentHero.getMana()) {
            toSend.put("error", "Not enough mana to use hero's ability.");
            output.add(toSend);
        } else if (currentHero.hasAttacked()) {
            toSend.put("error", "Hero has already attacked this turn.");
            output.add(toSend);
        } else if (currentHero instanceof LordRoyce || currentHero instanceof EmpressThorina) {
            if ((activePlayer == 1
                    && (affectedRow == MagicNumbers.ROW_2 || affectedRow == MagicNumbers.ROW_3))
                    || (activePlayer == 2
                    && (affectedRow == MagicNumbers.ROW_0 || affectedRow == MagicNumbers.ROW_1))) {
                toSend.put("error", "Selected row does not belong to the enemy.");
                output.add(toSend);
            } else {
                currentHero.useHeroAbility(game, affectedRow);
                currentHero.setHasAttacked(true);
                currentPLayer.setMana(currentPLayer.getMana() - currentHero.getMana());
            }
        } else if (currentHero instanceof GeneralKocioraw || currentHero instanceof KingMudface) {
            if ((activePlayer == 1
                    && (affectedRow == MagicNumbers.ROW_0 || affectedRow == MagicNumbers.ROW_1))
                    || (activePlayer == 2
                    && (affectedRow == MagicNumbers.ROW_2 || affectedRow == MagicNumbers.ROW_3))) {
                toSend.put("error", "Selected row does not belong to the current player.");
                output.add(toSend);
            } else {
                currentHero.useHeroAbility(game, affectedRow);
                currentHero.setHasAttacked(true);
                currentPLayer.setMana(currentPLayer.getMana() - currentHero.getMana());
            }
        }
    }

    /**
     * Method that passes to output number of games played so far
     * during the match.
     *
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getTotalGamesPlayed(final ArrayNode output) {
        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getTotalGamesPlayed");
        toSend.put("output", MainGame.getInstance().getTotalGamesPlayed());
        output.add(toSend);
    }

    /**
     * Method that passes to output number of games won by player one so far
     * during the match.
     *
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getPlayerOneWins(final ArrayNode output) {
        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getPlayerOneWins");
        toSend.put("output", MainGame.getInstance().getPlayerOneWins());
        output.add(toSend);
    }

    /**
     * Method that passes to output number of games won by player two so far
     * during the match.
     *
     * @param output ArrayNode in which output data is stored to be passed as JSON file.
     */
    public static void getPlayerTwoWins(final ArrayNode output) {
        ObjectNode toSend = OBJECT_MAPPER.createObjectNode();
        toSend.put("command", "getPlayerTwoWins");
        toSend.put("output", MainGame.getInstance().getPlayerTwoWins());
        output.add(toSend);
    }
}
