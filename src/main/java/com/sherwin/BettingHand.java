package com.sherwin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slu on 12/13/16.
 */
public class BettingHand {
    private List<Card> hand = null;
    private int bet = 0;
    private BlackJackAI.Action action = null;

    public BettingHand() {
        hand = new ArrayList<>();
        init();
    }

    public void init() {
        bet = 0;
        hand.clear();
        action = null;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public BlackJackAI.Action getAction() {
        return action;
    }

    public void setAction(BlackJackAI.Action action) {
        this.action = action;
    }

    public boolean isDoubleAces() {
        if (hand.size() == 2) {
            if (hand.get(0).getCardValue() == CardValue.Ace && hand.get(1).getCardValue() == CardValue.Ace) {
                return true;
            }
        }
        return false;
    }
}
