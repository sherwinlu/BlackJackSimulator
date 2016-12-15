package com.sherwin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by slu on 12/13/16.
 */
public class Player {
    public static final int DEFAULT_BET_AMT = 5;
    private static final int INITIAL_CAPITAL = 10000;
    private int bank = INITIAL_CAPITAL;

    private int wins = 0;
    private int losses = 0;
    private int pushes = 0;
    private int maxCash = bank;
    private int lowestCash = bank;
    private int doubleDowns = 0;
    private int numberOfSplits = 0;
    private int sixteenWins = 0;
    private int lossMultipliers = 1;
    private int maxLossStreak = 0;
    private int currentLossStreak = 0;

    private List<BettingHand> hands;
    private Deck deck = null;

    public Player() {
        hands = new LinkedList<>();
        deck = new Deck();
    }

    public void init() {
        hands.clear();
        BettingHand bettingHand = new BettingHand();
        bettingHand.setBet(DEFAULT_BET_AMT * lossMultipliers);
        bank -= DEFAULT_BET_AMT;
        List<Card> playerCards = new ArrayList<>(2);
        playerCards.add(deck.getACard());
        playerCards.add(deck.getACard());
        bettingHand.setHand(playerCards);
        hands.add(bettingHand);
    }

    public int getBank() {
        return bank;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getPushes() {
        return pushes;
    }

    public int getMaxCash() {
        return maxCash;
    }

    public int getLowestCash() { return lowestCash; }

    public int getIterations() {
        return wins + losses + pushes;
    }

    public int getDoubleDowns() {
        return doubleDowns;
    }

    public int getNumberOfSplits() {
        return numberOfSplits;
    }

    public int getSixteenWins() {
        return sixteenWins;
    }

    public int getLossMultipliers() { return lossMultipliers; }

    public void setLossMultipliers(int lossMultipliers) { this.lossMultipliers = lossMultipliers; }

    public int getMaxLossStreak() { return maxLossStreak; }

    public void setMaxLossStreak(int maxLossStreak) { this.maxLossStreak = maxLossStreak; }

    public List<BettingHand> getHands() {
        return hands;
    }

    public void push(BettingHand playerHand) {
        pushes++;
        bank += playerHand.getBet();
    }

    public void loss(BettingHand playerHand) {
        losses++;
        currentLossStreak++;
        if (currentLossStreak > maxLossStreak) {
            maxLossStreak = currentLossStreak;
        }
        bank -= playerHand.getBet();
        if (lowestCash > bank) {
            lowestCash = bank;
        }
    }

    public void blackJack(BettingHand playerHand) {
        wins++;
        currentLossStreak = 0;
        bank += (playerHand.getBet() * 2.5);
        if (maxCash < bank) {
            maxCash = bank;
        }
    }

    public void win(BettingHand playerHand) {
        wins++;
        currentLossStreak = 0;
        if (BlackJackAI.computeHandScore(playerHand.getHand()) == 16) {
            sixteenWins++;
        }
        bank += (playerHand.getBet() * 2);
        if (maxCash < bank) {
            maxCash = bank;
        }
    }

    public void decrementBank(int bet) {
        bank -= bet;
    }

    public void incrementSplitCount() {
        numberOfSplits++;
    }

    public void incremenatDoubleDownCount() {
        doubleDowns++;
    }
}
