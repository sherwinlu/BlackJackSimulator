package com.sherwin.playerAI;

import com.sherwin.BettingHand;
import com.sherwin.Card;
import com.sherwin.CardValue;
import com.sherwin.Player;

import java.util.Stack;

/**
 * Created by slu on 12/28/16.
 */
public class AceFiveCountStrategy extends AbstractPlayerAI {

    private int aceFiveCount = 0;
    private AbstractPlayerAI basicPlayerStrategy = new BasicPlayerStrategyAI();

    public AceFiveCountStrategy() {
        init();
    }

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
            Stack<Card> cards = getDiscardPile();
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
        player.getBettingHistory().add(playerHand.getBet());
    }

    @Override
    public int getBettingAmount(Player player) {
        int bet = Player.DEFAULT_BET_AMT;
        if (aceFiveCount >= 2) {
            bet = player.getBettingHistory().get(player.getBettingHistory().size() - 1) != null ?
                    player.getBettingHistory().get(player.getBettingHistory().size() - 1) : Player.DEFAULT_BET_AMT;
            if (bet > Player.DEFAULT_BET_AMT * 8) {
                bet = Player.DEFAULT_BET_AMT * 8;
            }
        }

        return bet;
    }
}
