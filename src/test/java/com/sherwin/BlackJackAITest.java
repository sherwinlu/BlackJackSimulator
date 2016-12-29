package com.sherwin;

import com.sherwin.playerAI.AbstractPlayerAI;
import com.sherwin.playerAI.BasicPlayerStrategyAI;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created by slu on 12/7/16.
 */
public class BlackJackAITest {
    private AbstractPlayerAI basicPlayerStrategyAI = new BasicPlayerStrategyAI();

    @Test
    public void computeHandScore() throws Exception {
        List<Card> blackJackHand = new ArrayList<>();
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        Card kingCard = new Card(CardValue.King, Suit.Clubs);
        blackJackHand.add(aceCard);
        blackJackHand.add(kingCard);
        Assert.assertEquals(21, BlackJackAI.computeHandScore(blackJackHand));

        List<Card> bustHand = new ArrayList<>();
        Card sixCard = new Card(CardValue.Six, Suit.Clubs);
        bustHand.add(sixCard);
        bustHand.add(kingCard);
        bustHand.add(kingCard);
        Assert.assertEquals(26, BlackJackAI.computeHandScore(bustHand));

        List<Card> fifteenHand = new ArrayList<>();
        Card threeCard = new Card(CardValue.Three, Suit.Diamonds);
        Card eightCard = new Card(CardValue.Eight, Suit.Spades);
        Card fourCard = new Card(CardValue.Four, Suit.Hearts);
        fifteenHand.add(threeCard);
        fifteenHand.add(eightCard);
        fifteenHand.add(fourCard);
        Assert.assertEquals(15, BlackJackAI.computeHandScore(fifteenHand));

        fifteenHand.clear();
        fifteenHand.add(aceCard);
        fifteenHand.add(fourCard);
        Assert.assertEquals(15, BlackJackAI.computeHandScore(fifteenHand));

        List<Card> sixteenHand = new ArrayList<>();
        sixteenHand.addAll(fifteenHand);
        sixteenHand.add(aceCard);
        Assert.assertEquals(16, BlackJackAI.computeHandScore(sixteenHand));
    }

    @Test
    public void checkIfHandisSoft() throws Exception {
        List<Card> hand = new ArrayList<>();
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        hand.add(aceCard);
        Card twoCard = new Card(CardValue.Two, Suit.Diamonds);
        hand.add(twoCard);

        Assert.assertTrue(BlackJackAI.isSoft(hand));

        Card threeCard = new Card(CardValue.Three, Suit.Diamonds);

        hand.add(threeCard);

        Assert.assertTrue(BlackJackAI.isSoft(hand));
    }

    @Test
    public void checkPlayerAction() throws Exception {
        List<Card> bustHand = new ArrayList<>();
        Card sixCard = new Card(CardValue.Six, Suit.Clubs);
        Card kingCard = new Card(CardValue.King, Suit.Clubs);
        bustHand.add(sixCard);
        bustHand.add(kingCard);
        bustHand.add(kingCard);
        BettingHand bettingHand = new BettingHand();
        bettingHand.setHand(bustHand);
        basicPlayerStrategyAI.getPlayerAction(sixCard, bettingHand, null, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Bust);

        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        List<Card> blackjackHand = new ArrayList<>();
        blackjackHand.add(aceCard);
        blackjackHand.add(kingCard);
        bettingHand = new BettingHand();
        bettingHand.setHand(blackjackHand);
        basicPlayerStrategyAI.getPlayerAction(sixCard, bettingHand, null, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Blackjack);

        bustHand.clear();
        Deck deckOfCards = new Deck();
        for (int i=0; i < 22; i++) {
            bustHand.add(deckOfCards.getACard(false));
        }
        bettingHand = new BettingHand();
        bettingHand.setHand(bustHand);
        basicPlayerStrategyAI.getPlayerAction(aceCard, bettingHand, null, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Bust);
    }

    @Test
    public void checkPlayerAction2() throws Exception {
        List<Card> twentyOneHand = new ArrayList<>();
        Card fourCard = new Card(CardValue.Four, Suit.Clubs);
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        twentyOneHand.add(fourCard);
        twentyOneHand.add(aceCard);

        BettingHand bettingHand = new BettingHand();
        bettingHand.setHand(twentyOneHand);
        basicPlayerStrategyAI.getPlayerAction(aceCard, bettingHand, null, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Hit);

        twentyOneHand.add(aceCard);
        basicPlayerStrategyAI.getPlayerAction(aceCard, bettingHand, null, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Hit);

        Card fiveCard = new Card(CardValue.Five, Suit.Spades);
        twentyOneHand.add(fiveCard);
        bettingHand.setHand(twentyOneHand);
        basicPlayerStrategyAI.getPlayerAction(aceCard, bettingHand, null, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Blackjack);
    }

    @Test
    public void checkPlayerSplitAction() throws Exception {
        List<Card> splitHand = new ArrayList<>();
        Player player = new Player();
        player.init();
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        splitHand.add(aceCard);
        splitHand.add(aceCard);
        BettingHand bettingHand = new BettingHand();
        bettingHand.setHand(splitHand);
        basicPlayerStrategyAI.getPlayerAction(aceCard, bettingHand, player, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Split);

        splitHand.add(aceCard);
        bettingHand.setHand(splitHand);
        basicPlayerStrategyAI.getPlayerAction(aceCard, bettingHand, player, false);
        Assert.assertTrue(bettingHand.getAction() == BlackJackAI.Action.Hit);
    }

    @Test
    public void checkDealerAction() throws Exception {
        List<Card> badHand = new ArrayList<>();
        Card tenCard = new Card(CardValue.Ten, Suit.Clubs);
        Card sixCard = new Card(CardValue.Six, Suit.Diamonds);
        badHand.add(tenCard);
        badHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(badHand) == BlackJackAI.Action.Hit);

        badHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(badHand) == BlackJackAI.Action.Bust);
    }

    @Test
    public void checkDealerAction2() throws Exception {
        List<Card> badHand = new ArrayList<>();
        Card sixCard1 = new Card(CardValue.Six, Suit.Clubs);
        Card sixCard2 = new Card(CardValue.Six, Suit.Diamonds);
        badHand.add(sixCard1);
        badHand.add(sixCard2);
        Assert.assertTrue(BlackJackAI.getDealerAction(badHand) == BlackJackAI.Action.Hit);

        badHand.add(sixCard2);
        Assert.assertTrue(BlackJackAI.getDealerAction(badHand) == BlackJackAI.Action.Stay);
    }

    @Test
    public void checkDealerActionHardSeventeen() throws Exception {
        List<Card> dealerHand = new ArrayList<>();
        Card tenCard = new Card(CardValue.Ten, Suit.Clubs);
        Card sevenCard = new Card(CardValue.Seven, Suit.Diamonds);
        dealerHand.add(tenCard);
        dealerHand.add(sevenCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Stay);
    }

    @Test
    public void checkDealerActionSoftSeventeen() throws Exception {
        List<Card> dealerHand = new ArrayList<>();
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        Card sixCard = new Card(CardValue.Six, Suit.Diamonds);
        dealerHand.add(aceCard);
        dealerHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Hit);

        dealerHand.add(aceCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Stay);
    }

    @Test
    public void checkDealerActionSoftSeventeen2() throws Exception {
        List<Card> dealerHand = new ArrayList<>();
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        Card sixCard = new Card(CardValue.Six, Suit.Diamonds);
        dealerHand.add(aceCard);
        dealerHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Hit);

        dealerHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Hit);

        dealerHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Stay);
    }

    @Test
    public void checkDealerActionSoftSeventeen3() throws Exception {
        List<Card> dealerHand = new ArrayList<>();
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        Card sixCard = new Card(CardValue.Five, Suit.Diamonds);
        dealerHand.add(aceCard);
        dealerHand.add(sixCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Hit);

        dealerHand.add(aceCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Hit);

        dealerHand.add(aceCard);
        Assert.assertTrue(BlackJackAI.getDealerAction(dealerHand) == BlackJackAI.Action.Stay);
    }

    @Test
    public void splitAceTest() throws Exception {
        Deck deck = createMock(Deck.class);
        Card aceCard = new Card(CardValue.Ace, Suit.Clubs);
        Card nineCard = new Card(CardValue.Nine, Suit.Hearts);
        expect(deck.getACard(false)).andReturn(aceCard);
        expect(deck.getACard(true)).andReturn(aceCard).times(4).andReturn(nineCard);
        replay(deck);
        BlackJackSimulator simulator = new BlackJackSimulator();
        simulator.runSimulation();
    }
}