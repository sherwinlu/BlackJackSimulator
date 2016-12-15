package com.sherwin;

import java.util.List;

import static com.sherwin.BlackJackAI.Action.*;
import static com.sherwin.BlackJackAI.computeHandScore;
import static com.sherwin.BlackJackAI.isSoft;

/**
 * Created by slu on 12/13/16.
 */
public class PlayerAI {

    public static void getPlayerAction(Card dealerFaceCard, BettingHand playerHand) throws Exception {
        int handScore = computeHandScore(playerHand.getHand());

        if (handScore > 21) {
            playerHand.setAction(Bust);
        } else if (isSoft(playerHand.getHand())) {
            switch (handScore) {
                case 2:
                case 12:
                    playerHand.setAction(Split);
                    break;
                case 13:
                case 14:
                    if ((dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Four.getCardValue()) ||
                            dealerFaceCard.getCardValue().getCardValue() >= CardValue.Seven.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(DoubleDown);
                    }
                    break;
                case 15:
                case 16:
                    if ((dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Three.getCardValue()) ||
                            dealerFaceCard.getCardValue().getCardValue() >= CardValue.Seven.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(DoubleDown);
                    }
                    break;
                case 17:
                    if ((dealerFaceCard.getCardValue().getCardValue() == CardValue.Two.getCardValue()) ||
                            dealerFaceCard.getCardValue().getCardValue() >= CardValue.Seven.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(DoubleDown);
                    }
                    break;
                case 18:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                        playerHand.setAction(DoubleDown);
                    } else if (dealerFaceCard.getCardValue().getCardValue() == CardValue.Seven.getCardValue() || dealerFaceCard.getCardValue().getCardValue() == CardValue.Eight.getCardValue()) {
                        playerHand.setAction(Stay);
                    } else {
                        playerHand.setAction(Hit);
                    }
                    break;
                case 19:
                case 20:
                    if (dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue() &&
                            dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue()) {
                        playerHand.setAction(DoubleDown);
                    } else {
                        playerHand.setAction(Stay);
                    }
                    break;
                case 21:
                    playerHand.setAction(Blackjack);
                    break;
                default:
                    throw new Exception("Invalid case");
            }
        } else if (canSplit(playerHand.getHand())) {
            switch (handScore) {
                case 4:
                case 6:
                    if (dealerFaceCard.getCardValue().getCardValue() <= CardValue.Seven.getCardValue() &&
                            dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue()) {
                        playerHand.setAction(Split);
                    } else {
                        playerHand.setAction(Hit);
                    }
                    break;
                case 8:
                    if (dealerFaceCard.getCardValue().getCardValue() <= CardValue.Four.getCardValue() || dealerFaceCard.getCardValue().getCardValue() >= CardValue.Seven.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(Split);
                    }
                    break;
                case 10:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Ten.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(DoubleDown);
                    }
                    break;
                case 12:
                    playerHand.setAction(Hit);
                    break;
                case 14:
                    if (dealerFaceCard.getCardValue().getCardValue() <= CardValue.Seven.getCardValue() &&
                        dealerFaceCard.getCardValue().getCardValue() >= CardValue.Two.getCardValue()) {
                        playerHand.setAction(Split);
                    } else {
                        playerHand.setAction(Hit);
                    }
                    break;
                case 16:
                    playerHand.setAction(Split);
                    break;
                case 18:
                    if (dealerFaceCard.getCardValue().getCardValue() == CardValue.Seven.getCardValue() || dealerFaceCard.getCardValue().getCardValue() == CardValue.Ten.getCardValue() || dealerFaceCard.getCardValue().getCardValue() == CardValue.Ace.getCardValue()) {
                        playerHand.setAction(Stay);
                    } else {
                        playerHand.setAction(Split);
                    }
                    break;
                case 20:
                    playerHand.setAction(Stay);
                    break;
                default:
                    throw new Exception("Invalid case");
            }
        } else {
            switch (handScore) {
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    playerHand.setAction(Hit);
                    break;
                case 9:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Three.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                        playerHand.setAction(DoubleDown);
                    } else {
                        playerHand.setAction(Hit);
                    }
                    break;
                case 10:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Ten.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(DoubleDown);
                    }
                    break;
                case 11:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Ace.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(DoubleDown);
                    }
                    break;
                case 12:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Four.getCardValue() && dealerFaceCard.getCardValue().getCardValue() <= CardValue.Six.getCardValue()) {
                        playerHand.setAction(Stay);
                    } else {
                        playerHand.setAction(Hit);
                    }
                    break;
                case 13:
                case 14:
                case 15:
                    if (dealerFaceCard.getCardValue().getCardValue() >= CardValue.Seven.getCardValue()) {
                        playerHand.setAction(Hit);
                    } else {
                        playerHand.setAction(Stay);
                    }
                    break;
                case 16:
                    playerHand.setAction(Stay);
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

    private static boolean canSplit(List<Card> playerHand) {
        boolean canSplit = false;

        if (playerHand.size() != 2) {
            canSplit = false;
        } else if (playerHand.get(0).getCardValue().getCardValue() == playerHand.get(1).getCardValue().getCardValue()) {
            canSplit = true;
        }

        return canSplit;
    }
}
