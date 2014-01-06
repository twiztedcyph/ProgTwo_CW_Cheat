/*
 * This program simulates a deck of cards.
 */

package QuestionOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Deck class.
 * 
 * CMPC2M1Y Programming 2 Coursework.
 * 
 * @author Ian Weeks 6204848
 */
public class Deck implements Iterable<Card>, Serializable
{
    private ArrayList<Card> deck;
    private static final long serialVersionUID = 101;
    
    /**
     * Deck constructor.
     * Initialises a new, full and un-shuffled deck
     */
    public Deck()
    {
        deck = new ArrayList<>();
        //Create a card object of each unique combination of rank and suit.
        for (Card.Rank rank : Card.Rank.values())
        {
            for (Card.Suit suit : Card.Suit.values())
            {
                deck.add(new Card(rank, suit));
            }
        }
    }
    
    /**
     * Shuffle the deck.
     */
    public void shuffle()
    {
        /**
         * Could have just used Collections.shuffle. That felt like cheating.
         * 
         * A random card is chosen and then added to the end of
         * the pack.  This is done 10000 times resulting in a 
         * well shuffled deck.
         */
        Random rand = new Random();
        Card tempCard;
        for (int i = 0; i < 10000; i++)
        {
            tempCard = deck.remove(rand.nextInt(deck.size()));
            deck.add(tempCard);
        }
    }
    
    /**
     * Deal a card from the deck.
     * 
     * @return A card off the top of the deck.
     * @throws QuestionOne.Deck.EmptyDeckException If the deck is empty.
     */
    public Card deal() throws EmptyDeckException
    {
        if(this.getSize() > 0)
        {
            //If there are cards in the deck then return the last one.
            return deck.remove(deck.size() - 1);
        }
        //If the deck is empty then throw an EmptyDeckException.
        throw new EmptyDeckException("The deck is empty");
    }
    
    /**
     * Get the current size of the deck.
     * 
     * @return The size of the deck.
     */
    public int getSize()
    {
        return deck.size();
    }
    
    /**
     * Reinitialise the deck (full and un-shuffled).
     */
    public final void newDeck()
    {
        //Reinitialise the card list.
        deck = new ArrayList<>();
        //Create a card object of each unique combination of rank and suit.
        for (Card.Rank rank : Card.Rank.values())
        {
            for (Card.Suit suit : Card.Suit.values())
            {
                deck.add(new Card(rank, suit));
            }
        }
    }
    
    /**
     * Accessor method for the deal order Iterator.
     * 
     * @return A deal order Iterator.
     */
    @Override
    public Iterator<Card> iterator()
    {
        return new dealOrderIterator();
    }
    
    /**
     * Accessor method for the odd/even iterator.
     * 
     * @return An odd/even iterator
     */
    public Iterator<Card> getOddEvenIterator()
    {
        return new OddEvenIterator();
    }
    
    /**
     * Definition of the deal order iterator.
     */
    private class dealOrderIterator implements Iterator<Card>
    {
        /*
         * Current position starts at the deck size (last card)
         * and moves towards the first card.
         */
        private int currentPosition = deck.size();
        
        /**
         * Checks if there is another card to iterate through.
         * 
         * @return True if there are more cards. False otherwise.
         */
        @Override
        public boolean hasNext()
        {
            return currentPosition > 0;
        }
        
        /**
         * The next card.
         * 
         * @return The next card in this order iteration.
         */
        @Override
        public Card next()
        {
            currentPosition--;
            return deck.get(currentPosition);
        }
        
        /**
         * Unimplemented remove method as iterators should
         * not be used for modification.
         */
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("NO!!!");
        }
        
    }
    
    /**
     * Odd even iterator.
     */
    private class OddEvenIterator implements Iterator<Card>
    {
        Card currentCard;
        int loopPass = 0;
        int currentPosition = 1;
        
        /**
         * Checks if there is another card to iterate through.
         * 
         * @return True if there are more cards. False otherwise.
         */
        @Override
        public boolean hasNext()
        {
            /*
             * All the work is done here. The next method simply 
             * returns a card if there is one to return.
             */
            if((currentPosition + 2) < deck.size())
            {
                /*
                 * If the current position is at least 2 less 
                 * than the deck size then set the current card
                 * to the index of currentPosition.  Add 2 to
                 * the value of currentPosition and return true.
                 */
                currentCard = deck.get(currentPosition);
                return true;
            }
            if(currentPosition < deck.size())
            {
                /*
                 * This part ensures the last card of each pass is displayed.
                 */
                currentCard = deck.get(currentPosition);
                return true;
            }
            if(loopPass == 0)
            {
                /*
                 * Set the currentPosition to an even starting position (0) 
                 * once the first (odd) pass is complete.
                 */
                loopPass = 1;
                currentPosition = 0;
                currentCard = deck.get(currentPosition);
                return true;
            }
            return false;
        }
        
        /**
         * The next card.
         * 
         * @return The next card in this order iteration.
         */
        @Override
        public Card next()
        {
            currentPosition += 2;
            return currentCard;
        }
        
        /**
         * Remove current iteration object.
         */
        @Override
        public void remove()
        {
            deck.remove(currentPosition);
        }
    }
    
    /**
     * Exception for empty deck.
     */
    public class EmptyDeckException extends Exception
    {
       /*
        * Custom exception for when there are no cards left in the deck.
        * This isn't really an issue when dealing to four decks but better
        * to be safe.
        */
        public EmptyDeckException(){}
        
        public EmptyDeckException(String message)
        {
            super(message);
        }
    }
}
