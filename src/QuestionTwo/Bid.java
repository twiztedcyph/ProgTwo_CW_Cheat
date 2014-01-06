/*
 * This program simulates the card game: Cheat.
 */
package QuestionTwo;

/**
 * Bid class.
 * 
 * CMPC2M1Y Programming 2 Coursework.
 * 
 * @author Not sure who wrote the initial class.
 * @author Ian Weeks 6204848
 */
public class Bid
{
    Hand h;
    Card.Rank r;
    
    /**
     * Default constructor.
     */
    public Bid()
    {
        //Initialise a Hand and set the starting rank.
        h = new Hand();
        r = Card.Rank.TWO;
    }
    
    /**
     * Constructor
     * 
     * @param hand The bid hand.
     * @param bidRank The bid rank.
     */
    public Bid(Hand hand, Card.Rank bidRank)
    {
        h = hand;
        r = bidRank;
    }
    
    /**
     * Set the bid hand.
     * 
     * @param hand The bid hand.
     */
    public void setHand(Hand hand)
    {
        h = hand;
    }
    
    /**
     * Set the bid rank.
     * 
     * @param rank The bid rank.
     */
    public void setRank(Card.Rank rank)
    {
        r = rank;
    }
    
    /**
     * Get the bid hand.
     * 
     * @return The bid hand.
     */
    public Hand getHand()
    {
        return h;
    }
    
    /**
     * Get the size of the bid hand.
     * 
     * @return The size of the bid hand.
     */
    public int getCount()
    {
        return h.size();
    }
    
    /**
     * Get the rank of the bid.
     * 
     * @return The bid rank.
     */
    public Card.Rank getRank()
    {
        return r;
    }
    
    /**
     * Get a string representation of the bid.
     * 
     * @return A string representation of the bid.
     */
    @Override
    public String toString()
    {
        return h.size() + " x " + r;
    }

}
