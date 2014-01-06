/*
 * This program simulates a deck of cards.
 */
package QuestionOne;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Card class.
 *
 * CMPC2M1Y Programming 2 Coursework.
 *
 * @author Ian Weeks 6204848
 */
public class Card implements Comparable<Card>, Serializable
{

    private Rank rank = null;
    private Suit suit = null;
    private static final long serialVersionUID = 100;

    /**
     * Rank enum. Contains all possible values for card rank and associated
     * methods.
     */
    public enum Rank
    {
        /*
         * Ordinal is used in card rank calculations. cardRankValue is used in
         * card value calculations.
         */

        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        private final int cardRankValue;

        /**
         * Rank constructor.
         *
         * @param cardValue The card value. e.g. TWO = 2, Queen = 10, ACE = 11.
         */
        Rank(int cardValue)
        {
            //Assign a value to a rank.
            this.cardRankValue = cardValue;
        }

        /**
         * Returns the rank after this one;
         *
         * @return The following rank.
         */
        public Rank getNext()
        {
            int nextOrd = ordinal() + 1;
            /*
             * Modular math of 13 allows a "circularization" of the ranks.
             */
            return Rank.values()[nextOrd % 13];
        }

        /**
         * Get the value of the card.
         *
         * @return The value of the card.
         */
        public int getCardRankValue()
        {
            return this.cardRankValue;
        }
    }

    /**
     * Suit enum. Contains all possible values for suit and associated methods.
     */
    public enum Suit
    {

        CLUBS, DIAMONDS, HEARTS, SPADES;
    }
    
    /**
     * Card constructor.
     *
     * @param rank Rank of the card object to be created.
     * @param suit Suit of the card object to be created.
     */
    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public Card()
    {

    }

    /**
     * Get the rank of the current card.
     *
     * @return The rank of the card.
     */
    public Rank getRank()
    {
        return this.rank;
    }

    /**
     * Get the suit of the current card.
     *
     * @return The suit of the card.
     */
    public Suit getSuit()
    {
        return this.suit;
    }

    /**
     * Compares this card to another. Cards are compared by rank first and then
     * by suit.
     *
     * Both compares are ordinal based.
     *
     * @param card The card to be compared with.
     * @return A negative number if this card is lower. A positive number if
     * this card is higher. Zero if the cards are the same.
     */
    @Override
    public int compareTo(Card card)
    {
        if (card.rank == this.rank)
        {
            //If the ranks are the same compare by suit.
            return this.suit.compareTo(card.suit);
        } else
        {
            //Else compare by rank.
            return this.rank.compareTo(card.rank);
        }
    }

    /**
     * Display the current card in string form.
     *
     * @return A string representation of the card.
     */
    @Override
    public String toString()
    {
        return String.format("%5s of %-8s",
                this.rank.toString(), this.suit.toString());
    }

    /**
     * Contains two methods for getting the difference between cards.
     */
    public static class Difference
    {
        /*
         * Nested static class. This class an its methods are not associated
         * with its parent class and can be used independently.
         */

        /**
         * Get the difference in rank between two cards.
         *
         * @param cardOne First card for comparison.
         * @param cardTwo Second card for comparison.
         * @return The rank difference between the two cards.
         */
        public static int difference(Card cardOne, Card cardTwo)
        {
            //Uses enum ordinal.
            return Math.abs(cardOne.rank.ordinal() - cardTwo.rank.ordinal());
        }

        /**
         * Get the difference in card rank value between two cards.
         *
         * @param cardOne First card for comparison.
         * @param cardTwo Second card for comparison.
         * @return The card value difference between the two cards.
         */
        public static int differenceValue(Card cardOne, Card cardTwo)
        {
            //Uses value stored in each enum.
            return Math.abs(cardOne.rank.getCardRankValue() - cardTwo.rank.getCardRankValue());
        }
    }

    /**
     * Accessor for the CompareDescending comparator.
     *
     * @return A descending order Card comparator.
     */
    public static Comparator<Card> getCompareDescending()
    {
        return new CompareDescending();
    }

    /**
     * Accessor for the CompareSuit comparator.
     *
     * @return A suit order Card comparator.
     */
    public static Comparator<Card> getCompareSuit()
    {
        return new CompareSuit();
    }

    /**
     * Comparator to be used with the Card class. Cards will be sorted in
     * descending order.
     */
    private static class CompareDescending implements Comparator<Card>
    {

        /**
         * Overridden compare method. Compares by rank first and then by suit.
         *
         * @param cardOne A card object to be compared with.
         * @param cardTwo A card object to be compared to.
         * @return A negative number if cardOne is lower than cardTwo, zero if
         * the cards are equal and a positive number if cardOne is greater than
         * cardTwo.
         */
        @Override
        public int compare(Card cardOne, Card cardTwo)
        {
            if (cardOne.rank.equals(cardTwo.rank))
            {
                //If ranks are equal then compare by suit.
                return cardOne.suit.compareTo(cardTwo.suit);
            } else
            {
                //If ranks are not equal then compare by rank.
                return cardTwo.rank.compareTo(cardOne.rank);
            }
        }
    }

    /**
     * Comparator to be used with the Card class. Cards will be sorted in suit
     * order.
     */
    private static class CompareSuit implements Comparator<Card>
    {

        /**
         * Overridden compare method. Compares by suit first and then by rank.
         *
         * @param cardOne A card object to be compared with.
         * @param cardTwo A card object to be compared to.
         * @return A negative number if cardOne is lower than cardTwo, zero if
         * the cards are equal and a positive number if cardOne is greater than
         * cardTwo.
         */
        @Override
        public int compare(Card cardOne, Card cardTwo)
        {
            if (cardOne.suit.equals(cardTwo.suit))
            {
                //If suits are eqaul then compare by rank.
                return cardOne.rank.compareTo(cardTwo.rank);
            } else
            {
                //If suits are not equall then compare by suit;
                return cardOne.suit.compareTo(cardTwo.suit);
            }
        }
    }
}
