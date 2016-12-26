package com.sherwin;

import java.util.ArrayList;
import java.util.List;

import static com.sherwin.Player.DEFAULT_BET_AMT;

/**
 * Created by slu on 12/8/16.
 */
public class BlackJackSimulator {
    private static final int MAX_ITERATIONS = 1000000;

    private Player player = null;
    private Deck deck = null;
    private List<Card> dealerHand = null;
    private int numOfPlayerHands = 0;

    public BlackJackSimulator() {
        dealerHand = new ArrayList<>();
        deck = new Deck();
        player = new Player();
    }

    public static void main(String[] args) throws Exception {
        BlackJackSimulator simulator = new BlackJackSimulator();
        simulator.runSimulation();
    }

    public void init() {
        player.init();
        dealerHand.clear();
        Card dealerVisibleCard = deck.getACard(true);
        Card dealerHiddenCard = deck.getACard(false);
        numOfPlayerHands = player.getHands().size();

        dealerHand.add(dealerVisibleCard);
        dealerHand.add(dealerHiddenCard);
    }

    public void generatePlayerHand(BettingHand playerHand) throws Exception {
        PlayerAI.getPlayerAction(dealerHand.get(0), playerHand, player);
        switch (playerHand.getAction()) {
            case Blackjack:
            case Bust:
            case Stay:
                break;
            case Hit:
                playerHand.getHand().add(deck.getACard(false));
                generatePlayerHand(playerHand);
                break;
            case DoubleDown:
                if (player.getBank() >= playerHand.getBet()) {
                    player.decrementBank(playerHand.getBet());
                    playerHand.setBet(playerHand.getBet() * 2);
                    playerHand.getHand().add(deck.getACard(false));
                    PlayerAI.getPlayerAction(dealerHand.get(0), playerHand, player);
                    player.incremenatDoubleDownCount();
                } else { // player doesn't have enough cash to cover a double down, so treat it as a hit
                    playerHand.getHand().add(deck.getACard(false));
                    generatePlayerHand(playerHand);
                }
                break;
            case Split:
                if (player.getBank() >= playerHand.getBet()) {
                    boolean isDoubleAces = isDoubleAces(playerHand);
                    player.incrementSplitCount();

                    BettingHand splitHand = new BettingHand();
                    splitHand.getHand().add(playerHand.getHand().get(1));

                    playerHand.getHand().set(1, deck.getACard(false));
                    if (!isDoubleAces) {
                        generatePlayerHand(playerHand);
                    }
                    splitHand.getHand().add(deck.getACard(false));
                    splitHand.setBet(playerHand.getBet());
                    player.decrementBank(splitHand.getBet());
                    player.getHands().add(splitHand);
                    numOfPlayerHands++;
                    if (!isDoubleAces) {
                        generatePlayerHand(splitHand);
                    }
                }
                break;
            default:
                throw new Exception("Invalid case");
        }
    }

    private BlackJackAI.Action generateDealerHand() throws Exception {
        BlackJackAI.Action dealerAction = BlackJackAI.getDealerAction(dealerHand);
        while (dealerAction == BlackJackAI.Action.Hit) {
            dealerHand.add(deck.getACard(false));
            dealerAction = BlackJackAI.getDealerAction(dealerHand);
        }

        return dealerAction;
    }

    private void updateWinStats() {
//        wins++;
//        if (playerBank > maxCash) {
//            maxCash = playerBank;
//        }
    }

    private void updateLossStats() {
//        losses++;
//        if (playerBank < lowestCash) {
//            lowestCash = playerBank;
//        }
    }

    public boolean isDoubleAces(BettingHand bettingHand) {
        List<Card> hand = bettingHand.getHand();
        if (hand.size() == 2) {
            if (hand.get(0).getCardValue() == CardValue.Ace && hand.get(1).getCardValue() == CardValue.Ace) {
                return true;
            }
        }
        return false;
    }

    private void printStats() {
        System.out.println("# of hands played:" + player.getIterations());
        System.out.println("current bank account: " + player.getBank());
        System.out.println("max bank account: " + player.getMaxCash());
        System.out.println("lowest bank account: " + player.getLowestCash());
        System.out.println("number of wins: " + player.getWins());
        System.out.println("number of losses: " + player.getLosses());
        System.out.println("number of pushes: " + player.getPushes());
        System.out.println("number of double downs: " + player.getDoubleDowns());
        System.out.println("number of splits: " + player.getNumberOfSplits());
        System.out.println();

        System.out.println("% wins: " + ((float) player.getWins() / (float) player.getIterations()) * 100 + "%");
        System.out.println("% losses: " + ((float) player.getLosses() / (float) player.getIterations()) * 100 + "%");

        String bankAccountHistory = String.join(",", player.getBankAccountHistory());

        System.out.println("Bank acct: " + bankAccountHistory);
    }

    private void runSimulation() throws Exception {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            init();
            // make sure the player still has enough cash to play
            if (player.getBank() >= 0) {
                // check and see if dealer has BJ
                if (BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Blackjack) {
                    // iterate through all the player hands and see what we need to do
                    for (BettingHand playerHand : player.getHands()) {
                        // check and see if player has BJ
                        if (playerHand.getAction() == BlackJackAI.Action.Blackjack) {
                            player.push(playerHand);
                        } else {
                            player.loss(playerHand);
                        }
                    }
                } else { // dealer doesn't have BJ
                    for (int handNumber = 0; handNumber < numOfPlayerHands; handNumber++) {
                        BettingHand playerHand = player.getHands().get(handNumber);
                        generatePlayerHand(playerHand);
                        if (playerHand.getAction() == BlackJackAI.Action.Blackjack) {
                            player.blackJack(playerHand);
                        } else if (playerHand.getAction() != BlackJackAI.Action.Bust) {
                            if (generateDealerHand() != BlackJackAI.Action.Bust) {
                                int playerTotal = BlackJackAI.computeHandScore(playerHand.getHand());
                                int dealerTotal = BlackJackAI.computeHandScore(dealerHand);

                                if (dealerTotal > playerTotal) {
                                    player.loss(playerHand);
                                } else if (dealerTotal < playerTotal) {
                                    player.win(playerHand);
                                } else {
                                    player.push(playerHand);
                                }
                            } else { // dealer busted, but player did not, thus player won
                                player.win(playerHand);
                            }
                        } else { // player busted
                            player.loss(playerHand);
                        }

                    }
                }
            }
            // player ran out of money
            else {
                System.out.println("BANKRUPT!!!!");
                player.decrementBank(-DEFAULT_BET_AMT);
                break;
            }
        }
        printStats();
    }
}
