package implementation;

import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;
import implementation.abilityMinions.*;
import implementation.environmentCards.*;
import implementation.heroCards.*;
import implementation.standardMinions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
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

    public void setPlayerHero(Hero playerHero) {
        this.playerHero = playerHero;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void assignPlayerDeck(int player, int gameNumber) {
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
                        playerDeck.add(new Sentinel(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "Berserker" ->
                        playerDeck.add(new Berserker(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "Goliath" ->
                        playerDeck.add(new Goliath(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "Warden" ->
                        playerDeck.add(new Warden(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "Miraj" ->
                        playerDeck.add(new Miraj(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "The Ripper" ->
                        playerDeck.add(new TheRipper(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "Disciple" ->
                        playerDeck.add(new Disciple(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "The Cursed One" ->
                        playerDeck.add(new TheCursedOne(cardMana, cardAttackDamage, cardHealth, cardDescription, cardColors, cardName));
                case "Firestorm" ->
                        playerDeck.add(new Firestorm(cardMana, cardDescription, cardColors, cardName));
                case "Winterfell" ->
                        playerDeck.add(new Winterfell(cardMana, cardDescription, cardColors, cardName));
                case "Heart Hound" ->
                        playerDeck.add(new HeartHound(cardMana, cardDescription, cardColors, cardName));
            }
        }

        Collections.shuffle(playerDeck, new Random(currentGame.getShuffleSeed()));
    }

    public void assignPlayerHero(int player, int gameNumber) {
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
                    playerHero = new LordRoyce(heroMana, heroDescription, heroColors, heroName);
            case "Empress Thorina" ->
                    playerHero = new EmpressThorina(heroMana, heroDescription, heroColors, heroName);
            case "King Mudface" ->
                    playerHero = new KingMudface(heroMana, heroDescription, heroColors, heroName);
            case "General Kocioraw" ->
                    playerHero = new GeneralKocioraw(heroMana, heroDescription, heroColors, heroName);
        }
    }

    public void drawCardFromDeck() {
        if (!playerDeck.isEmpty()) {
            playerHand.add(playerDeck.get(0));
            playerDeck.remove(0);
        }
    }
}
