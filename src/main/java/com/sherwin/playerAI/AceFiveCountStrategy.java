package com.sherwin.playerAI;

import com.sherwin.*;

import java.util.Stack;

/**
 * Created by slu on 12/28/16.
 */
public class AceFiveCountStrategy extends AbstractPlayerAI {

    private int aceFiveCount = 0;
    private AbstractPlayerAI basicPlayerStrategy = new BasicPlayerStrategyAI();

    @Override
    public void init() {
        super.init();
        aceFiveCount = 0;
    }

    @Override
    public void getPlayerAction(Card dealerFaceCard, BettingHand playerHand, Player player, boolean newDeck) throws Exception {
        if (newDeck) {
            init();
        } else {
            Deck discardDeck = getDiscardPile();
            Stack<Card> cards = discardDeck.getStackOfCards();
            while (!cards.isEmpty()) {
                Card card = cards.pop();
                if (card.getCardValue() == CardValue.Five) {
                    aceFiveCount++;
                }
                if (card.getCardValue() == CardValue.Ace) {
                    aceFiveCount--;
                }
            }
        }
        basicPlayerStrategy.getPlayerAction(dealerFaceCard, playerHand, player, newDeck);
        if (aceFiveCount >= 2) {
            Integer bet = player.getBettingHistory().get(player.getBettingHistory().size() - 1);
            playerHand.setBet(bet != null ? bet * 2 : playerHand.getBet() * 2);
        }
        player.getBettingHistory().add(playerHand.getBet());
    }
}
