/*
 * This program simulates a deck of cards.
 */

package QuestionOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Hand class.
 * 
 * CMPC2M1Y Programming 2 Coursework.
 * 
 * @author Ian Weeks 6204848
 */
public class Hand implements Iterable<Card>, Serializable
{
    private final int[] ranksHeld = new int[13];
    private final int[] suitsHeld = new int[4];
    private ArrayList<Card> heldCards;
    private static final long serialVersionUID = 102;

    /**
     * Default constructor.
     */
    public Hand()
    {
        //Initialise an empty ArrayList of cards.
        heldCards = new ArrayList<>();
    }
    
    /**
     * Hand constructor with hand input.
     * 
     * @param newHand A hand of cards to be included in this hand.
     */
    public Hand(Hand newHand)
    {
        //Call the default constructor to initialise the hand.
        this();
        for (Card card : newHand)
        {
            if (heldCards.add(card))
            {
                /*
                 * If the card is successfully added, 
                 * update the rank and suit records.
                 */
                ranksHeld[card.getRank().ordinal()]++;
                suitsHeld[card.getSuit().ordinal()]++;
            }
        }
    }
    
    /**
     * Hand constructor with card collection input.
     * 
     * @param newCards A collection of cards to be included in this hand.
     */
    public Hand(Collection<Card> newCards)
    {
        //Call the default constructor to initialise the hand.
        this();
        for (Card card : newCards)
        {
            if (heldCards.add(card))
            {
                /*
                 * If the card is successfully added, 
                 * update the rank and suit records.
                 */
                ranksHeld[card.getRank().ordinal()]++;
                suitsHeld[card.getSuit().ordinal()]++;
            }
        }
    }
    
    /**
     * Add a card to the hand.
     * 
     * @param newCard A card to be added to the hand.
     */
    public void add(Card newCard)
    {
        if(heldCards.add(newCard))
        {
            /*
             * If the card is successfully added, 
             * update the rank and suit records.
             */
            ranksHeld[newCard.getRank().ordinal()]++;
            suitsHeld[newCard.getSuit().ordinal()]++;
        }
    }
    
    /**
     * Add a collection of cards to this hand.
     * 
     * @param newCards A collection of cards to be added to the hand.
     */
    public void add(Collection<Card> newCards)
    {
        for (Card card : newCards)
        {
            if (heldCards.add(card))
            {
               /*
                * If the card is successfully added, 
                * update the rank and suit records.
                */
                ranksHeld[card.getRank().ordinal()]++;
                suitsHeld[card.getSuit().ordinal()]++;
            }
        }
    }
    
    /**
     * Add another hand to this hand.
     * 
     * @param newHand A hand to be added to this hand.
     */
    public void add(Hand newHand)
    {
        for (Card card : newHand)
        {
            if (heldCards.add(card))
            {
               /*
                * If the card is successfully added, 
                * update the rank and suit records.
                */
                ranksHeld[card.getRank().ordinal()]++;
                suitsHeld[card.getSuit().ordinal()]++;
            }
        }
    }
    
    /**
     * Remove a card from the hand.
     * 
     * @param card A card to be removed from this hand.
     * @return True if the card was found and removed. False otherwise.
     */
    public boolean remove(Card card)
    {
        for (int i = 0; i < heldCards.size(); i++)
        {
            Card tempCard = heldCards.get(i);
            //Search for and remove the card.
            if (card.compareTo(tempCard) == 0 && heldCards.remove(tempCard))
            {
                //If found update records and return true.
                ranksHeld[card.getRank().ordinal()]--;
                suitsHeld[card.getSuit().ordinal()]--;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Remove all cards from a passed in hand (if present).
     * 
     * @param hand A hand of cards to be removed.
     * @return True if all cards were found and removed. False otherwise.
     */
    public boolean remove(Hand hand)
    {
        /*
         * Wasn't sure if the operation should be canceled if
         * all cards were not found.... Decided to leave it as
         * is.
         */
        int removeCount = 0;
        for (Card card : hand)
        {
            for (int i = 0; i < heldCards.size(); i++)
            {
                Card tempCard = heldCards.get(i);
                //Search for and remove cards
                if (card.compareTo(tempCard) == 0 && heldCards.remove(tempCard))
                {
                    //Update records
                    ranksHeld[tempCard.getRank().ordinal()]--;
                    suitsHeld[tempCard.getSuit().ordinal()]--;
                    removeCount++;
                }
            }
        }
        //If the number or removes is eqaul to the passed in hand return true.
        return removeCount == hand.size();
    }
    
    
    /**
     * Remove a card at a specific index in the hand.
     * 
     * @param position The index of the card to be removed.
     * @return The card that was removed.
     */
    public Card remove(int position)
    {
        //Check if the index is valid.
        if(position < this.heldCards.size())
        {
            //Update records.
            ranksHeld[heldCards.get(position).getRank().ordinal()]--;
            suitsHeld[heldCards.get(position).getSuit().ordinal()]--;
            //Return the removed card.
            return this.heldCards.remove(position);
        }
        //Else throw an exception.
        String msg = "Index " + position + ". Size " + heldCards.size() + ".";
        throw new IndexOutOfBoundsException(msg);
    }
    
    /**
     * Sort this hand into ascending order.
     */
    public void sortAscending()
    {
        Collections.sort(heldCards);
    }
    
    /**
     * Sort this hand into descending order.
     */
    public void sortDescending()
    {
        Collections.sort(heldCards, Card.getCompareDescending());
    }
    
    /**
     * Get the number of cards in the hand.
     * 
     * @return the size of the hand.
     */
    public int size()
    {
        return this.heldCards.size();
    }
    
    /**
     * Get an iterator for the list of cards in the hand.
     * 
     * @return An iterator for this hand of cards.
     */
    @Override
    public Iterator<Card> iterator()
    {
        return heldCards.iterator();
    }
    
    /**
     * Get the number of cards of a specific suit.
     * 
     * @param suit The suit to be counted.
     * @return The number of cards of the specified suit.
     */
    public int countSuit(Card.Suit suit)
    {
        /*
         * Just like the record of ranks I decided to keep a record of suits.
         * Not sure if addition methods were allowed but will stick to this.
         * 
         * Uses suit ordinal as the index for the suit record array.
         */
        return suitsHeld[suit.ordinal()];
    }
    
    /**
     * Get the number of cards of a specific rank.
     * 
     * @param rank The rank to be counted.
     * @return The number of card of the specified rank.
     */
    public int countRank(Card.Rank rank)
    {
        //Uses the rank ordinal as the index for the rank record array.
        return ranksHeld[rank.ordinal()];
    }
    
    /**
     * Get the value of all the cards in the hand.
     * 
     * @return The value of all the cards in the hand.
     */
    public int handValue()
    {
        int sum = 0;
        //Add up all the card values.
        for (Card card : heldCards)
        {
            sum += card.getRank().getCardRankValue();
        }
        return sum;
    }
    
    /**
     * Show if the hand is flush.
     * 
     * @return True if the hand is flush. False otherwise.
     */
    public boolean isFlush()
    {
        /*
         * Check if the count of the suit of the first card
         * in the hand is equal to the hand size. If not then
         * this hand is not flush.
         */
        Card.Suit suit = heldCards.get(0).getSuit();
        return suitsHeld[suit.ordinal()] == heldCards.size();
    }
    
    /**
     * Show if the hand is a straight.
     * 
     * @return True if the hand is a straight. False otherwise.
     */
    public boolean isStraight()
    {
        /*
         * Create a copy of the current hand and sort it.
         * Check that each card is the next of the previous card.
         */
        ArrayList<Card> tempList = new ArrayList<>(heldCards);
        Collections.sort(tempList);
        
        Card.Rank tempRankOne, tempRankTwo;
        for (int i = 0; i < tempList.size() - 1; i++)
        {
            tempRankOne = tempList.get(i).getRank();
            tempRankTwo = tempList.get(i + 1).getRank();
            
            if(tempRankTwo != tempRankOne.getNext())
            {
                //If even one card is not corrent then return false.
                return false;
            }
        }
        //If all tests pass then return true.
        return true;
    }
    
    /**
     * Hand toString method.
     * 
     * @return A string representation of the cards in this hand object.
     */
    @Override
    public String toString()
    {
        /*
         * If there are cards in the hand then iterate through
         * them and append each one to the output string.
         */
        if(this.size() > 0)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (Card card : this)
            {
                stringBuilder.append(card);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
        //Else state that the hand is empty.
        return "This hand is empty\n";
    }
    
    /**
     * Accessor method for the card list. This was required to sort each hand.
     * Might make a method to do this later....
     * 
     * @return the current list of cards in the hand.
     */
    public List<Card> getHeldCards()
    {
        return this.heldCards;
    }
}
