package com.sherwin;

import com.sherwin.playerAI.AbstractPlayerAI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slu on 12/13/16.
 */
public class Player {
    public static final int DEFAULT_BET_AMT = 15;
    private static final int INITIAL_CAPITAL = 100;
    private int bank = INITIAL_CAPITAL;

    private int wins = 0;
    private int losses = 0;
    private int pushes = 0;
    private int maxCash = bank;
    private int lowestCash = bank;
    private int doubleDowns = 0;
    private int numberOfSplits = 0;
    private int sixteenWins = 0;

    private List<BettingHand> hands;
    private Deck deck = null;

    private List<String> bankAccountHistory;
    private List<Integer> bettingHistory;
    private AbstractPlayerAI playerStrategy;

    public Player() {
        hands = new ArrayList<>();
        deck = Deck.getInstance();
        bankAccountHistory = new ArrayList<>();
        bettingHistory = new ArrayList<>();
    }

    public void init() {
        hands.clear();
        BettingHand bettingHand = new BettingHand();
        int bettingAmt = playerStrategy.getBettingAmount(this);
        bettingHand.setBet(bettingAmt);
        bank -= bettingAmt;
        List<Card> playerCards = new ArrayList<>(2);
        playerCards.add(deck.getACard(true));
        playerCards.add(deck.getACard(false));
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

    public int getLowestCash() {
        return lowestCash;
    }

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

    public List<BettingHand> getHands() {
        return hands;
    }

    public void push(BettingHand playerHand) {
        pushes++;
        bank += playerHand.getBet();
        bankAccountHistory.add(Integer.toString(bank));
    }

    public void loss(BettingHand playerHand) {
        losses++;
        if (lowestCash > bank) {
            lowestCash = bank;
        }
        bankAccountHistory.add(Integer.toString(bank));
    }

    public void blackJack(BettingHand playerHand) {
        wins++;
        bank += (playerHand.getBet() * 2.5);
        if (maxCash < bank) {
            maxCash = bank;
        }
        bankAccountHistory.add(Integer.toString(bank));
    }

    public void win(BettingHand playerHand) {
        wins++;
        if (BlackJackAI.computeHandScore(playerHand.getHand()) == 16) {
            sixteenWins++;
        }
        bank += (playerHand.getBet() * 2);
        if (maxCash < bank) {
            maxCash = bank;
        }
        bankAccountHistory.add(Integer.toString(bank));
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

    public List<String> getBankAccountHistory() {
        return bankAccountHistory;
    }

    public List<Integer> getBettingHistory() {
        return bettingHistory;
    }

    public void setStrategy(AbstractPlayerAI playerStrategy) {
        this.playerStrategy = playerStrategy;
    }
}
