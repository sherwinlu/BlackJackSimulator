package com.sherwin.playerAI;

import com.sherwin.BettingHand;
import com.sherwin.Card;
import com.sherwin.Player;

import java.util.List;
import java.util.Stack;

/**
 * Created by slu on 12/13/16.
 */
public abstract class AbstractPlayerAI {

    private Stack<Card> discardPile = new Stack();

    public void init() {
        discardPile.clear();
    }

    public abstract void getPlayerAction(Card dealerFaceCard, BettingHand playerHand, Player player, boolean newDeck) throws Exception;

    protected boolean canSplit(List<Card> playerHand) {
        boolean canSplit = false;

        if (playerHand.size() != 2) {
            canSplit = false;
        } else if (playerHand.get(0).getCardValue().getCardValue() == playerHand.get(1).getCardValue().getCardValue()) {
            canSplit = true;
        }

        return canSplit;
    }

    public void addToDiscardPile(List<Card> hand) {
        discardPile.addAll(hand);
    }

    public Stack<Card> getDiscardPile() {
        return discardPile;
    }

    public abstract int getBettingAmount(Player player);
}
