/*
 * This program simulates the card game cheat. 
 */

package QuestionTwo;

import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Ian Weeks 6204848
 */
public class ThinkerStrategy implements Strategy
{
    private Random rand;
    
    public ThinkerStrategy()
    {
        rand = new Random();
    }
    
    @Override
    public boolean cheat(Bid b, Hand h)
    {
        int thisRankCount = h.countRank(b.getRank());
        int nextRankCount = h.countRank(b.getRank().getNext());
        boolean validPlay = Math.max(thisRankCount, nextRankCount) > 0;
        if(validPlay)
        {
            int randNumber = rand.nextInt(1000);
            return randNumber > 950;
        }    
        return true;
    }

    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        Bid myBid = new Bid();
        Hand tempHand = new Hand();
        int randNumber;
        
        if(cheat)
        {
            randNumber = rand.nextInt(1000);
            int numberOfCards;
            
            //Decent method of choosing how many cards to play.
            if(randNumber > 975)
            {
                numberOfCards = Math.min(4, h.size());
            }else if(randNumber > 900)
            {
                numberOfCards = Math.min(3, h.size());
            }else if(randNumber > 800)
            {
                numberOfCards = Math.min(2, h.size());
            }else
            {
                numberOfCards = Math.min(1, h.size());
            }
            
            //Somehow choose cards with preference to higher cards.
            for (int i = 0; i < numberOfCards; i++)
            {
                randNumber = rand.nextInt(h.size() / 2);
                tempHand.add(h.remove(randNumber));
            }
            myBid.setHand(tempHand);
            
            if(h.countRank(b.getRank()) > h.countRank(b.getRank().getNext()))
            {
                myBid.setRank(b.getRank());
            }else
            {
                myBid.setRank(b.getRank().getNext());
            }
            return myBid;
        }
        //If not cheating...
        
        //Get the current rank being played.
        Card.Rank currentRank = b.getRank();
        //Initialize an iterator for the player's hand.
        Iterator<Card> it = h.iterator();
        if(h.countRank(currentRank) > 0)
        {
            //If the player has cards of the current rank..
            while(it.hasNext())
            {
                //Add them to the hand to be played.
                Card tempCard = it.next();
                if(tempCard.getRank().equals(currentRank))
                {
                    tempHand.add(tempCard);
                }
            }
            myBid.setRank(currentRank);
        }else
        {
            /*
             * Else do the same as above for the following rank.
             * 
             * The player will have a valid play option as this was
             * checked in the cheat method.
             */
            while(it.hasNext())
            {
                Card tempCard = it.next();
                if(tempCard.getRank().equals(currentRank.getNext()))
                {
                    tempHand.add(tempCard);
                }
            }
            myBid.setRank(currentRank.getNext());
        }
        /*
         * Ensure that the cards to be played 
         * are removed from the players hand.
         * 
         * If not display a message and exit.
         */
        
        // TODO Maybe make an exception for this....
        if(!h.remove(tempHand))
        {
            System.out.println("Failed to removed play hand from current hand");
            System.exit(0);
        }
        myBid.setHand(tempHand);
        //Return the honest Bid.
        return myBid;
    }

    @Override
    public boolean callCheat(Hand h, Bid b)
    {
        
        
        return false;
    }
    
}
