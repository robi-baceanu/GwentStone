package implementation;

import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;
import implementation.standardMinions.Warden;
import implementation.standardMinions.Goliath;
import implementation.standardMinions.Berserker;
import implementation.standardMinions.Sentinel;
import implementation.abilityMinions.Disciple;
import implementation.abilityMinions.TheRipper;
import implementation.abilityMinions.TheCursedOne;
import implementation.abilityMinions.Miraj;
import implementation.environmentCards.HeartHound;
import implementation.environmentCards.Winterfell;
import implementation.environmentCards.Firestorm;
import implementation.heroCards.KingMudface;
import implementation.heroCards.GeneralKocioraw;
import implementation.heroCards.EmpressThorina;
import implementation.heroCards.LordRoyce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class that describes a player, his assigned deck and his assigned Hero.
 *
 * @author wh1ter0se
 */
public final class Player {
    private final Input inputData = MainGame.getInstance().getInputData();
    private final ArrayList<Card> playerDeck = new ArrayList<Card>();
    private final ArrayList<Card> playerHand = new ArrayList<Card>();
    private Hero playerHero;
    private int mana;

    public ArrayList<Card> getPlayerDeck() {
        return playerDeck;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public Hero getPlayerHero() {
        return playerHero;
    }

    public void setPlayerHero(final Hero playerHero) {
        this.playerHero = playerHero;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Method that assigns a player one deck from a selection of many given at input,
     * and also shuffles the deck.
     *
     * @param player Whether deck is being assigned to player one or player two.
     * @param gameNumber Number of current game from the match.
     */
    public void assignPlayerDeck(final int player, final int gameNumber) {
        StartGameInput currentGame = inputData.getGames().get(gameNumber).getStartGame();

        int playerDeckIdx;
        int deckSize;
        ArrayList<CardInput> currentDeck;

        if (player == 1) {
            playerDeckIdx = currentGame.getPlayerOneDeckIdx();
            deckSize = inputData.getPlayerOneDecks().getNrCardsInDeck();
            currentDeck = inputData.getPlayerOneDecks().getDecks().get(playerDeckIdx);
        } else {
            playerDeckIdx = currentGame.getPlayerTwoDeckIdx();
            deckSize = inputData.getPlayerTwoDecks().getNrCardsInDeck();
            currentDeck = inputData.getPlayerTwoDecks().getDecks().get(playerDeckIdx);
        }

        for (int i = 0; i < deckSize; i++) {
            CardInput currentCard = currentDeck.get(i);

            int cardMana = currentCard.getMana();
            int cardAttackDamage = currentCard.getAttackDamage();
            int cardHealth = currentCard.getHealth();
            String cardDescription = currentCard.getDescription();
            ArrayList<String> cardColors = currentCard.getColors();
            String cardName = currentCard.getName();

            switch (cardName) {
                case "Sentinel" ->
                        playerDeck.add(new Sentinel(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "Berserker" ->
                        playerDeck.add(new Berserker(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "Goliath" ->
                        playerDeck.add(new Goliath(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "Warden" ->
                        playerDeck.add(new Warden(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "Miraj" ->
                        playerDeck.add(new Miraj(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "The Ripper" ->
                        playerDeck.add(new TheRipper(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "Disciple" ->
                        playerDeck.add(new Disciple(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "The Cursed One" ->
                        playerDeck.add(new TheCursedOne(cardMana, cardAttackDamage, cardHealth,
                                cardDescription, cardColors, cardName));
                case "Firestorm" ->
                        playerDeck.add(new Firestorm(cardMana, cardDescription, cardColors,
                                cardName));
                case "Winterfell" ->
                        playerDeck.add(new Winterfell(cardMana, cardDescription, cardColors,
                                cardName));
                case "Heart Hound" ->
                        playerDeck.add(new HeartHound(cardMana, cardDescription, cardColors,
                                cardName));
                default -> System.out.println("Unknown card.");
            }
        }

        Collections.shuffle(playerDeck, new Random(currentGame.getShuffleSeed()));
    }

    /**
     * Method that assigns a player his Hero, given at input.
     *
     * @param player Whether Hero is being assigned to player one or player two.
     * @param gameNumber Number of current game from the match.
     */
    public void assignPlayerHero(final int player, final int gameNumber) {
        StartGameInput currentGame = inputData.getGames().get(gameNumber).getStartGame();

        CardInput currentHero;

        if (player == 1) {
            currentHero = currentGame.getPlayerOneHero();
        } else {
            currentHero = currentGame.getPlayerTwoHero();
        }

        int heroMana = currentHero.getMana();
        String heroDescription = currentHero.getDescription();
        ArrayList<String> heroColors = currentHero.getColors();
        String heroName = currentHero.getName();

        switch (heroName) {
            case "Lord Royce" ->
                    this.setPlayerHero(new LordRoyce(heroMana, heroDescription,
                            heroColors, heroName));
            case "Empress Thorina" ->
                    this.setPlayerHero(new EmpressThorina(heroMana, heroDescription,
                            heroColors, heroName));
            case "King Mudface" ->
                    this.setPlayerHero(new KingMudface(heroMana, heroDescription,
                            heroColors, heroName));
            case "General Kocioraw" ->
                    this.setPlayerHero(new GeneralKocioraw(heroMana, heroDescription,
                            heroColors, heroName));
            default -> System.out.println("Unknown hero.");
        }
    }

    /**
     * Method that draws a card from the player's deck and puts it in
     * his hand.
     */
    public void drawCardFromDeck() {
        if (!playerDeck.isEmpty()) {
            playerHand.add(playerDeck.get(0));
            playerDeck.remove(0);
        }
    }
}
