package com.sherwin;

/**
 * Created by slu on 12/12/16.
 */
public class Config {
    private int numDecks;
    private int numIterations;
    private int startingCash;
    private int bettingAmount;
    private boolean dealerHitsOnSoft17 = true;

    public int getNumDecks() {
        return numDecks;
    }

    public void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    public int getNumIterations() {
        return numIterations;
    }

    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public int getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(int startingCash) {
        this.startingCash = startingCash;
    }

    public int getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(int bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    public boolean isDealerHitsOnSoft17() {
        return dealerHitsOnSoft17;
    }

    public void setDealerHitsOnSoft17(boolean dealerHitsOnSoft17) {
        this.dealerHitsOnSoft17 = dealerHitsOnSoft17;
    }
}
