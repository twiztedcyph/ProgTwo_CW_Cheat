/*
 * This program simulates the card game: Cheat.
 */

package QuestionTwo;

import java.util.Iterator;
import java.util.Random;

/**
 * BasicStrategy class.
 * 
 * CMPC2M1Y Programming 2 Coursework.
 * 
 * @author Ian Weeks 6204848
 */
public class BasicStrategy implements Strategy
{
    //Default constructor.
    public BasicStrategy()
    {
    }
    
    /**
     * "Decide" if the player should cheat or not.
     * 
     * @param b The current bid.
     * @param h The players current hand.
     * @return True if the player should cheat. False otherwise.
     */
    @Override
    public boolean cheat(Bid b, Hand h)
    {
        /*
         * Checks how many cards the player has of the current rank
         * and the next rank. If either is greater than zero then the
         * method returns false (don't cheat). If both are zero then 
         * the method returns true (cheat).
         * 
         * The two int variables only serve to shorten the length of
         * the return statement. :p
         */
        int thisRankCount = h.countRank(b.getRank());
        int nextRankCount = h.countRank(b.getRank().getNext());
        return Math.max(thisRankCount, nextRankCount) == 0;
    }
    
    /**
     * "Chooses" the next bid a player will make.
     * 
     * @param b The current bid.
     * @param h The players current hand.
     * @param cheat Selects whether to cheat or not.
     * @return The bid the player will make.
     */
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        //Initialize the return bid and a hand to play.
        Bid myBid = new Bid();
        Hand handToPlay = new Hand();
        if(cheat)
        {
            Random rand = new Random();
            //Choose the random card to be played out of the current hand.
            Card tempCard = h.getHeldCards().get(rand.nextInt(h.size()));
            //Add it to the to be played hand.
            handToPlay.add(tempCard);
            //Remove the card to be played from the player's hand.
            if(!h.remove(handToPlay))
            {
                //For now.. If the card fails to remove then display a message and exit.
                System.out.println("Failed to removed play hand from current hand");
                System.exit(0);
            }
            //Set the Bid hand.
            myBid.setHand(handToPlay);
            switch(rand.nextInt(2))
            {
                //Randomly set the Bid rank to lie about..
                case 0:
                    myBid.setRank(b.getRank().getNext());
                    break;
                case 1:
                    myBid.setRank(b.getRank());
                    break;
                default:
                    /*
                     * If the default case is reached then the laws
                     * of programming have somehow been broken.
                     * Best to exit the program.
                     */
                    System.out.println("Something has gone terribly wrong.");
                    System.exit(0);
            }
            //Return the cheat Bid.
            return myBid;
        }
        //If not cheating.
        
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
                    handToPlay.add(tempCard);
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
                    handToPlay.add(tempCard);
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
        if(!h.remove(handToPlay))
        {
            System.out.println("Failed to removed play hand from current hand");
            System.exit(0);
        }
        myBid.setHand(handToPlay);
        //Return the honest Bid.
        return myBid;
    }
    
    /**
     * Decide whether to call another player a cheat or not.
     * 
     * @param h The players current hand.
     * @param b The hand that was played.
     * @return True if the player should call cheat. False otherwise.
     */
    @Override
    public boolean callCheat(Hand h, Bid b)
    {
        Hand playedHand = b.getHand();
        /*
         * Check the number of cards played of a specific rank.
         * If that number plus the number of cards this player
         * has of that rank is more than 4.... Call cheat.
         * Else don't call cheat.
         */
        return (playedHand.size() + h.countRank(b.getRank())) > 4;
    }

}
