package com.sherwin;

import java.util.Collections;
import java.util.Stack;

/**
 * Created by slu on 12/7/16.
 */
public class Deck {
    public static final int NUMOFDECKS = 8;
    public static final int MAXCARDS = NUMOFDECKS * CardValue.values().length * Suit.values().length;
    private Stack<Card> deck = new Stack();

    public Deck() {
        init();
    }

    private void init() {
        deck.clear();
        Stack<Card> singleDeck = new Stack<>();
        for (int i = 0; i < NUMOFDECKS; i++) {
            for (CardValue value : CardValue.values()) {
                for (Suit suitValue : Suit.values()) {
                    Card card = new Card(value, suitValue);
                    singleDeck.push(card);
                }
            }
            Collections.shuffle(singleDeck);
            deck.addAll(singleDeck);
            singleDeck.clear();
        }
        Collections.shuffle(deck);
    }

    public Card getACard(boolean isNewGame) {
        if (deck.size() < (0.25 * MAXCARDS) && isNewGame) {
            init();
        }
        return deck.pop();
    }

    public boolean isNewDeck() {
        return deck.size() >= Deck.MAXCARDS - 4;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public Stack<Card> getStackOfCards() {
        return deck;
    }
}
