package com.sherwin.playerAI;

import com.sherwin.BettingHand;
import com.sherwin.Card;
import com.sherwin.CardValue;
import com.sherwin.Player;

import static com.sherwin.BlackJackAI.Action.*;
import static com.sherwin.BlackJackAI.computeHandScore;
import static com.sherwin.BlackJackAI.isSoft;

/**
 * Created by slu on 12/28/16.
 */
public class SimpleStrategyAI extends AbstractPlayerAI {

    @Override
    public void getPlayerAction(Card dealerFaceCard, BettingHand playerHand, Player player, boolean newDeck) throws Exception {
        int handScore = computeHandScore(playerHand.getHand());

        if (handScore > 21) {
            playerHand.setAction(Bust);
        } else if (isSoft(playerHand.getHand())) {
            switch (handScore) {
                case 12:
                    playerHand.setAction(Split);
                    break;
                case 13:
                case 14:
                case 15:
                    playerHand.setAction(Hit);
                    break;
                case 16:
                case 17:
                case 18:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                        playerHand.setAction(DoubleDown);
                    } else {
                        playerHand.setAction(Hit);
                    }
                    break;
                case 19:
                case 20:
                    playerHand.setAction(Stay);
                    break;
                case 21:
                    playerHand.setAction(Blackjack);
                    break;
                default:
                    throw new Exception("Invalid case");
            }
        } else if (canSplit(playerHand.getHand()) && player.getBank() >= playerHand.getBet()) {
            switch (handScore) {
                case 4:
                case 6:
                case 12:
                case 14:
                case 18:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                        playerHand.setAction(Split);
                    }
                case 16:
                    playerHand.setAction(Split);
                    break;
                case 20:
                    playerHand.setAction(Stay);
                    break;
                default:
                    evaluateHardHand(dealerFaceCard, playerHand, handScore);
                    break;
            }
        } else {
            evaluateHardHand(dealerFaceCard, playerHand, handScore);
        }
    }

    @Override
    public int getBettingAmount(Player player) {
        return Player.DEFAULT_BET_AMT;
    }

    private void evaluateHardHand(Card dealerFaceCard, BettingHand playerHand, int handScore) throws Exception {
        switch (handScore) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                playerHand.setAction(Hit);
                break;
            case 9:
                if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                    playerHand.setAction(DoubleDown);
                } else {
                    playerHand.setAction(Hit);
                }
                break;
            case 10:
                if (dealerFaceCard.getCardValue().getCardValue() < CardValue.Ten.getCardValue()) {
                    playerHand.setAction(DoubleDown);
                } else {
                    playerHand.setAction(Hit);
                }
                break;
            case 11:
                if (dealerFaceCard.getCardValue().getCardValue() != CardValue.Ace.getCardValue()) {
                    playerHand.setAction(DoubleDown);
                } else {
                    playerHand.setAction(Hit);
                }
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                    playerHand.setAction(Stay);
                } else {
                    playerHand.setAction(Hit);
                }
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                playerHand.setAction(Stay);
                break;
            default:
                throw new Exception("Invalid case");
        }
    }
}
