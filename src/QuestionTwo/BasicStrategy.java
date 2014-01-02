package QuestionTwo;

import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Cypher
 */
public class BasicStrategy implements Strategy
{

    public BasicStrategy()
    {
    }

    @Override
    public boolean cheat(Bid b, Hand h)
    {
        //Decide whether to cheat or not here....
        int playableCards = Math.max(h.countRank(b.getRank()), h.countRank(b.getRank().getNext()));
        return playableCards == 0;
    }

    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        //choose the cards to play here....
        Bid myBid = new Bid();
        Hand handToPlay = new Hand();
        if(cheat)
        {
            Random rand = new Random();
            //Choose the random card to be played...
            Card tempCard = h.getHeldCards().get(rand.nextInt(h.size()));
            handToPlay.add(tempCard);
            myBid.setHand(handToPlay);
            switch(rand.nextInt(2))
            {
                //Randomly select which rank to lie about....
                case 0:
                    myBid.setRank(b.getRank().getNext());
                    break;
                case 1:
                    myBid.setRank(b.getRank());
                    break;
                default:
                    System.out.println("Something has gone terribly wrong.");
                    System.exit(0);
            }
            return myBid;
        }
        
        Card.Rank currentRank = b.getRank();
        Iterator<Card> it = h.iterator();
        if(h.countRank(currentRank) > h.countRank(currentRank.getNext()))
        {
            while(it.hasNext())
            {
                Card tempCard = it.next();
                if(tempCard.getRank().equals(currentRank))
                {
                    handToPlay.add(tempCard);
                }
            }
            myBid.setRank(currentRank);
        }else
        {
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
        myBid.setHand(handToPlay);
        return myBid;
    }

    @Override
    public boolean callCheat(Hand h, Bid b)
    {
        boolean myCall;
        Random rand = new Random();
        
        return rand.nextInt(1000) > 950;
    }

}
