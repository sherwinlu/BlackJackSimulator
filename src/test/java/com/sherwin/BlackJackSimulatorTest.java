package com.sherwin;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slu on 12/20/16.
 */
public class BlackJackSimulatorTest {
    @Test
    public void generatePlayerHand() throws Exception {
        BettingHand bettingHand = new BettingHand();
        bettingHand.init();
        List<Card> cards = new ArrayList<>(2);
        Card ace = new Card(CardValue.Ace, Suit.Diamonds);
        cards.add(ace);
        cards.add(ace);
        bettingHand.setBet(Player.DEFAULT_BET_AMT);
        bettingHand.setHand(cards);
        BlackJackSimulator simulator = new BlackJackSimulator();
        simulator.init();
        simulator.generatePlayerHand(bettingHand);
    }

}