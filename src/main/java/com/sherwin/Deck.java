package com.sherwin;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by slu on 12/7/16.
 */
public class Deck {
    private List<Card> deck = new ArrayList<>(52);
    private static final SecureRandom random = new SecureRandom();

    public Deck() {
//        Stream.of(CardValue.values()).forEach(
//                Card card = new Card(value, Suit.values()[j]);
//                Stream.of(S))
        for (CardValue value : CardValue.values()) {
            for (Suit suitValue : Suit.values()) {
                Card card = new Card(value, suitValue);
                this.deck.add(card);
            }
        }

        Collections.shuffle(deck);
    }

    public Card getFirstCard() {
        return deck.get(0);
    }

    public Card getLastCard() {
        return deck.get(deck.size() - 1);
    }

    public Card getACard() {
        return deck.get(random.nextInt(deck.size() - 1));
    }
}
