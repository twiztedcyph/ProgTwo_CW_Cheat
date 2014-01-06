/*
 * This program simulates the card game: Cheat.
 */

package QuestionTwo;

/**
 * BasicPlayer class.
 * 
 * CMPC2M1Y Programming 2 Coursework.
 * 
 * @author Ian Weeks 6204848
 */
public class BasicPlayer implements Player
{
    private Hand playerHand;
    private Strategy strategy;
    private CardGame cardGame;
    
    /**
     * Player constructor
     * 
     * @param strategy The strategy the player will use.
     * @param cardGame A reference to the card game this player is in.
     */
    public BasicPlayer(Strategy strategy, CardGame cardGame)
    {
        //Initialise the player's hand.
        playerHand = new Hand();
        this.strategy = strategy;
        this.cardGame = cardGame;
    }
    
    /**
     * Add a card to a player's hand.
     * 
     * @param c The card to be added.
     */
    @Override
    public void addCard(Card c)
    {
        playerHand.add(c);
        playerHand.sortDescending();
    }
    
    /**
     * Get the player's hand.
     * 
     * @return The player's hand.
     */
    public Hand getPlayerHand()
    {
        return playerHand;
    }
    
    /**
     * Set the player's hand.
     * 
     * @param playerHand A hand to be set to the player's hand.
     */
    public void setPlayerHand(Hand playerHand)
    {
        this.playerHand = playerHand;
    }
    
    /**
     * Add a hand to the player's current hand.
     * 
     * @param h The hand to be added.
     */
    @Override
    public void addHand(Hand h)
    {
        playerHand.add(h);
        playerHand.sortDescending();
    }
    
    /**
     * Show how many cards the player has.
     * 
     * @return The number of cards the player has.
     */
    @Override
    public int cardsLeft()
    {
        return playerHand.size();
    }
    
    /**
     * Set the game the player is in.
     * 
     * @param g A reference to the game the player is in.
     */
    @Override
    public void setGame(CardGame g)
    {
        this.cardGame = g;
    }
    
    /**
     * Set the strategy the player will use.
     * 
     * @param s A reference to the strategy the player will use.
     */
    @Override
    public void setStrategy(Strategy s)
    {
        this.strategy = s;
    }
    
    /**
     * Get a bid from this player.
     * 
     * @param b The previous bid played.
     * @return This player's bid.
     */
    @Override
    public Bid playHand(Bid b)
    {
        //Bid is decided by the strategy the player uses.
        boolean cheat =  strategy.cheat(b, playerHand);
        Bid myBid = strategy.chooseBid(b, playerHand, cheat);
        return myBid;
    }
    
    /**
     * Ask if a player wants to call cheat or not.
     * 
     * @param b The just played bid.
     * @return True if the player wants to call cheat. False otherwise.
     */
    @Override
    public boolean callCheat(Bid b)
    {
        return strategy.callCheat(playerHand, b);
    }
    
}
