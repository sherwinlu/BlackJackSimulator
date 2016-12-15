package com.sherwin;

import java.util.List;

import static com.sherwin.BlackJackAI.Action.*;

/**
 * Created by slu on 12/7/16.
 */
public class BlackJackAI {
    public enum Action {
        Hit, Stay, DoubleDown, Split, Bust, Blackjack;
    }

    public static Action getDealerAction(List<Card> dealerHand) throws Exception {
        int handScore = computeHandScore(dealerHand);
        Action dealerAction = null;

        if (handScore > 21) {
            dealerAction = Bust;
        } else if (handScore < 17) {
            dealerAction = Hit;
        } else if (isSoft(dealerHand) && handScore == 17) {
            dealerAction = Hit;
        } else if (handScore >= 17 && handScore <= 21) {
            dealerAction = Stay;
        } else {
            dealerAction = Bust;
        }

        if (handScore == 21 && isSoft(dealerHand)) {
            dealerAction = Blackjack;
        }

        return dealerAction;
    }


    public static int computeHandScore(List<Card> hand) {
        int score = 0;
        boolean hasAce = false;
        for (Card card : hand) {
            if (card.getCardValue() == CardValue.Ace) {
                hasAce = true;
                score++;
            } else if (card.getCardValue().getCardValue() >= CardValue.Ten.getCardValue() &&
                    card.getCardValue().getCardValue() <= CardValue.King.getCardValue()) {
                score += 10;
            } else {
                score += card.getCardValue().getCardValue();
            }
        }

        if (score < 12 && hasAce) {
            score += 10;
        }

        return score;
    }

    public static boolean isSoft(List<Card> playerHand) {
        int score = 0;
        boolean isSoft = false;

        if (playerHand.get(0).getCardValue() == CardValue.Ace || playerHand.get(1).getCardValue() == CardValue.Ace) {
            for (Card card : playerHand) {
                if (card.getCardValue() == CardValue.Ace) {
                    score++;
                } else if (card.getCardValue().getCardValue() >= CardValue.Ten.getCardValue() &&
                        card.getCardValue().getCardValue() <= CardValue.King.getCardValue()) {
                    score += 10;
                } else {
                    score += card.getCardValue().getCardValue();
                }
            }

            if (score < 12) {
                isSoft = true;
            }
        }

        return isSoft;
    }
}
