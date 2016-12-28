package com.sherwin;

import java.util.List;

/**
 * Created by slu on 12/13/16.
 */
public abstract class AbstractPlayerAI {

    public abstract void getPlayerAction(Card dealerFaceCard, BettingHand playerHand, Player player) throws Exception;

    protected boolean canSplit(List<Card> playerHand) {
        boolean canSplit = false;

        if (playerHand.size() != 2) {
            canSplit = false;
        } else if (playerHand.get(0).getCardValue().getCardValue() == playerHand.get(1).getCardValue().getCardValue()) {
            canSplit = true;
        }

        return canSplit;
    }
}
