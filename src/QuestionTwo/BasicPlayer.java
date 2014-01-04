/*
 * This program simulates the card game: Cheat.
 */

package QuestionTwo;

/**
 *
 * @author Cypher
 */
public class BasicPlayer implements Player
{
    private Hand playerHand;
    private Strategy strategy;
    private CardGame cardGame;
    private enum action{PASS, PLAY};
    
    public BasicPlayer(Strategy strategy, CardGame cardGame)
    {
        playerHand = new Hand();
        this.strategy = strategy;
        this.cardGame = cardGame;
    }

    @Override
    public void addCard(Card c)
    {
        playerHand.add(c);
        playerHand.sortDescending();
    }

    public Hand getPlayerHand()
    {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand)
    {
        this.playerHand = playerHand;
    }

    @Override
    public void addHand(Hand h)
    {
        playerHand.add(h);
        playerHand.sortDescending();
    }

    @Override
    public int cardsLeft()
    {
        return playerHand.size();
    }

    @Override
    public void setGame(CardGame g)
    {
        this.cardGame = g;
    }

    @Override
    public void setStrategy(Strategy s)
    {
        this.strategy = s;
    }

    @Override
    public Bid playHand(Bid b)
    {
        //so this is where the "player" looks at the last hand played and then plays.....
        Bid myBid = strategy.chooseBid(b, playerHand, strategy.cheat(b, playerHand));
        
        return myBid;
    }

    @Override
    public boolean callCheat(Bid b)
    {
        //this is where the player looks at the last hand played and decided whether to call cheat.)
        return strategy.callCheat(playerHand, b);
    }
    
}
