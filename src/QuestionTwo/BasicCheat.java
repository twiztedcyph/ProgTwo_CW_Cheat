/*
 * This program simulates the card game: Cheat.
 */
package QuestionTwo;

import java.util.*;

/**
 * BasicCheat class.
 *
 * CMPC2M1Y Programming 2 Coursework.
 *
 * @author Not sure who wrote the initial class.
 * @author Ian Weeks 6204848
 */
public class BasicCheat implements CardGame
{

    private BasicPlayer[] players;
    private int nosPlayers;
    public static final int MINPLAYERS = 5;
    private int currentPlayer;
    private Hand discards;
    private Bid currentBid;
    private ArrayList<Integer> cheatCallers;
    private Random rand;

    /**
     * Default constructor.
     */
    public BasicCheat()
    {
        this(MINPLAYERS);
    }

    /**
     * Set the number of players constructor.
     *
     * @param n The number of players required.
     */
    public BasicCheat(int n)
    {
        nosPlayers = n;
        players = new BasicPlayer[nosPlayers];
        for (int i = 0; i < nosPlayers - 2; i++)
        {
            //Set the player strategy and game reference.
            players[i] = (new BasicPlayer(new BasicStrategy(), this));
        }
        players[nosPlayers - 2] = (new BasicPlayer(new ThinkerStrategy(), this));
        players[nosPlayers - 1] = (new BasicPlayer(new HumanStrategy(), this));
        //Initialise the bid.  This sets the starting rank to two.
        currentBid = new Bid();
        // TODO Make the first player the one with the two of clubs.
        currentPlayer = 0;
    }

    /**
     * A single round of the game.
     *
     * @return True. <Not sure why as this value is never checked.>
     * Look into this at some stage with TA.
     */
    @Override
    public boolean playTurn()
    {
        //Ask player for a play,
        System.out.println("current bid = " + currentBid);
        currentBid = players[currentPlayer].playHand(currentBid);
        System.out.println("updated bid = " + currentBid);
        System.out.println("Player bid = " + currentBid);
        //Add hand played to discard pile
        discards.add(currentBid.getHand());
        //Offer all other players the chance to call cheat
        //TODO rework this section so that the first players dont have the advatage.... Threads or random.
        boolean cheat = false;
        cheatCallers = new ArrayList<>();
        rand = new Random();
        for (int i = 0; i < players.length; i++)
        {
            if (i != currentPlayer && players[i].callCheat(currentBid))
            {
                cheatCallers.add(i);
            }
        }
        
        if (cheatCallers.size() > 0)
        {
            int cheatCallWinner = cheatCallers.get(rand.nextInt(cheatCallers.size()));
            System.out.println("Player " + (currentPlayer + 1) + " called cheat by Player " + (cheatCallWinner + 1));
            if (isCheat(currentBid))
            {
                //CHEAT CALLED CORRECTLY
                //Give the discard pile of cards to currentPlayer who then has to play again                      
                players[currentPlayer].addHand(discards);
                System.out.println("Player " + (currentPlayer + 1) + " cheated!");
                System.out.println("Adding cards to player "
                        + (currentPlayer + 1) + " " + players[currentPlayer]);

            } else
            {
                //CHEAT CALLED INCORRECTLY
                //Give cards to caller i who is new currentPlayer
                System.out.println("Player Honest");
                currentPlayer = cheatCallWinner;
                players[currentPlayer].addHand(discards);
                System.out.println("Adding cards to player "
                        + (currentPlayer + 1) + " " + players[currentPlayer]);
            }
            //If cheat is called, current bid reset to an empty bid with rank two whatever 
            //the outcome
            currentBid = new Bid();
            //Discards now reset to empty	
            discards = new Hand();
        }else
        {
            //Go to the next player
            System.out.println("No Cheat Called");
            //Modular math to "circularize" the players.
            currentPlayer = (currentPlayer + 1) % nosPlayers;
        }
        //Not sure why this returns true.
        return true;
    }

    /**
     * Check if there is a current winner of the game.
     *
     * @return The index of the player who has won. Or -1 otherwise.
     */
    @Override
    public int winner()
    {
        //Iterate through all players.
        for (int i = 0; i < nosPlayers; i++)
        {
            //If a player has no cards left.
            if (players[i].cardsLeft() <= 0)
            {
                //Return the player's index.
                return i;
            }
        }
        //Else return -1;
        return -1;
    }

    /**
     * Initialise a game.
     */
    @Override
    public void initialise()
    {
        //Create and shuffle Deck of cards.
        Deck d = new Deck();
        d.shuffle();
        //Initialize deck iterator.
        Iterator<Card> it = d.iterator();
        int count = 0;
        while (it.hasNext())
        {
            //Deal all the cards to the players.
            players[count % nosPlayers].addCard(it.next());
            it.remove();
            count++;
        }
        //Initialise Discards
        discards = new Hand();
        //Choose first player
        currentPlayer = 0;
        currentBid = new Bid();
        //Set the first bid to TWO.
        currentBid.setRank(Card.Rank.TWO);
    }

    /**
     * The game initialisation loop.
     */
    public void playGame()
    {
        //Initialise the game.
        initialise();
        //Keeps track of the current round of the game.
        int c = 0;
        Scanner in = new Scanner(System.in);
        boolean finished = false;
        //The game loop.
        while (!finished)
        {
            //Play a hand
            System.out.println("Cheat turn for player " + (currentPlayer + 1));
            /*
             * Current player places a bid and others asked if they will call
             * cheat or not.
             */
            playTurn();
            c++;
            System.out.print("Turn " + c + " Complete. Press any key to continue or enter Q to quit>");
            String str = in.nextLine();
            System.out.println("");
            //Allow the user to quit at the end of each turn.
            if (str.equals("Q") || str.equals("q") || str.equals("quit"))
            {
                finished = true;
            }
            //Check if there is a winner.
            if (winner() > 0)
            {
                System.out.println("The Winner is Player " + (winner() + 1));
                finished = true;
            }
        }
    }

    /**
     * Check if a player cheated.
     *
     * @param b The player's played bid.
     * @return True if the player cheated. False otherwise.
     */
    public static boolean isCheat(Bid b)
    {
        for (Card c : b.getHand())
        {
            if (c.getRank() != b.r)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Main method.
     *
     * @param args Input arguments.
     */
    public static void main(String[] args)
    {
        //Initialise and start game.
        BasicCheat cheat = new BasicCheat();
        cheat.playGame();
    }
}
