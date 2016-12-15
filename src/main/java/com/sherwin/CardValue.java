package com.sherwin;

/**
 * Created by slu on 12/7/16.
 */
public enum CardValue {
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Jack(11),
    Queen(12),
    King(13),
    Ace(14);

    private int cardValue;

    private CardValue(int value) {
        this.cardValue = value;
    }

    public int getCardValue() {
        return cardValue;
    }
}
