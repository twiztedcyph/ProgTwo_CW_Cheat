/*
 * This program simulates the card game: Cheat.
 */
package QuestionTwo;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * HumanStrategy class.
 *
 * CMPC2M1Y Programming 2 Coursework.
 *
 * @author Ian Weeks 6204848
 */
public class HumanStrategy implements Strategy
{
    /*
     * Card and rank regular expressions are used to check that user input
     * is correct.
     */
    final String CARD_REGEX = "(TWO|THREE|FOUR|FIVE|SIX|SEVEN|EIGHT|NINE"
                + "|TEN|JACK|QUEEN|KING|ACE)\\s(OF)\\s"
                + "(CLUBS|HEARTS|DIAMONDS|SPADES)";
    final String RANK_REGEX = "(TWO|THREE|FOUR|FIVE|SIX|SEVEN|EIGHT|NINE"
                + "|TEN|JACK|QUEEN|KING|ACE)";
    //This regex is used to split the user input into a rank and suit.
    final String CARD_SPLIT = "\\s(OF)\\s";
    private Scanner scanner;
    private int bidSize;
    private Bid myBid;
    
    /**
     * Default constructor.
     */
    public HumanStrategy()
    {
        scanner = new Scanner(System.in);
        bidSize = 0;
        myBid = new Bid();
    }
    
    /**
     * Ask the user if he would like to cheat or not.
     * The user is not given this option if he can only play a cheat.
     * 
     * @param b The previous bid.
     * @param h The player's hand.
     * @return True if the user wants or has to cheat. False otherwise.
     */
    @Override
    public boolean cheat(Bid b, Hand h)
    {
        //Present the user with a list of his cards.
        Iterator<Card> it = h.iterator();
        int cardCount = 0;
        System.out.println("The current hand is: " + b.r);
        System.out.print("Your cards are:\n");
        while (it.hasNext())
        {
            System.out.printf("%-3d %s\n", cardCount, it.next());
            cardCount++;
        }
        
        /*
         * Check if the user has not choice but to cheat. If so
         * the user is informed and asked for the next input.
         */
        int thisRankCount = h.countRank(b.getRank());
        int nextRankCount = h.countRank(b.getRank().getNext());
        if(Math.max(thisRankCount, nextRankCount) == 0)
        {
            System.out.println("You have no choice but to cheat.");
            return true;
        }
        
        /*
         * If the user has both options then
         * he can type yes or no to choose one.
         */
        System.out.print("Would you like to cheat? yes/no: ");
        String userInput = scanner.nextLine().toLowerCase().trim();
        while (!userInput.equals("yes") && !userInput.equals("no") 
                && !userInput.equals("y") && !userInput.equals("n"))
        {
            userInput = scanner.nextLine();
        }
        return userInput.equals("yes") || userInput.equals("y");
    }
    
    /**
     * Ask the user to select the bid he will make.
     * 
     * @param b The previous bid.
     * @param h The player's hand.
     * @param cheat If the player is cheating or not.
     * @return The player's selected bid.
     */
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        /*
         * This temp hand will hold cards the player chooses until
         * it can be verified that they are valid choices.
         */
        Hand tempHand = new Hand();
        bidSize = 0;
        if (cheat)
        {
            /*
             * If the player is cheating, ask how many cards he wants to play.
             * Then ask him to enter the cards to be played.
             * Finally ask which rank he would like to say he played.
             */
            Card.Rank tempRank;
            //Ask for the bid size.
            chooseBidSize();
            //Ask for the bid cards.
            tempHand = chooseBidCards();
            //Ensure all the cards to be bid are valid.
            while(!h.remove(tempHand))
            {
                if(bidSize == 1)
                {
                    System.out.println("You do not currently own the card you entered.");
                }else
                {
                    System.out.println("You do not currently own one or more of the cards you entered.");
                }
                tempHand = chooseBidCards();
            }
            
            //Ask for the bid rank.
            tempRank = chooseBidRank();
            //Ensure the bid rank is a valid choice.
            while(!(tempRank == b.getRank() || tempRank == b.getRank().getNext()))
            {
                System.out.println("You may only select between the current or the next rank.");
                tempRank = chooseBidRank();
            }
            //Now that everything is verified... set the return bid and return.
            myBid.setHand(tempHand);
            myBid.setRank(tempRank);
            return myBid;
        }
        //If the player is not cheating.
        
        //Ask the player how many cards he wants to play.
        chooseBidSize();
        
        /*
         * Create two hands containing all playable cards.
         * These will be used to check the player input.
         */
        Hand thisRank = new Hand();
        Hand nextRank = new Hand();
        for (Card card : h)
        {
            if(card.getRank().equals(b.getRank()))
            {
                thisRank.add(card);
            }else if(card.getRank().equals(b.getRank().getNext()))
            {
                nextRank.add(card);
            }
        }
        
        //Ask the player to select his cards.
        tempHand = chooseBidCards();
        //Verify that the input is correct.
        while(!(thisRank.remove(tempHand) || nextRank.remove(tempHand)))
        {
            //If not the user is given another chance to select bid size.
            System.out.println("That is not a valid non cheating play.");
            bidSize = 0;
            chooseBidSize();
            //And is then ask to choose cards again.
            tempHand = chooseBidCards();
        }
        
        //Now that everything is verified... set the return bid and return.
        myBid.setHand(tempHand);
        //The rank is filled in automatically.
        myBid.setRank(tempHand.getHeldCards().get(0).getRank());
        return myBid;
    }
    
    /**
     * Ask the user if he would like to call cheat on the most recent player.
     * 
     * @param h The player's hand.
     * @param b The played bid.
     * @return True if the player calls cheat. False otherwise.
     */
    @Override
    public boolean callCheat(Hand h, Bid b)
    {
        System.out.print("Would you like to call cheat? yes/no: ");
        String userInput = scanner.nextLine().toLowerCase().trim();
        while (!userInput.equals("yes") && !userInput.equals("no") 
                && !userInput.equals("y") && !userInput.equals("n"))
        {
            userInput = scanner.nextLine();
        }
        return userInput.equals("yes") || userInput.equals("y");
    }
    
    private void chooseBidSize()
    {
        // TODO mayber make this method return an int instead.
        /*
         * This method gets the user input on bid size.
         * 
         * Separate method for clarity.
         */
        
        System.out.print("How many cards would you like to play? ");
        //Ensure the input number is in the correct range.
        while (bidSize < 1 || bidSize > 4)
        {
            scanner = new Scanner(System.in);
            try
            {
                //Get the next integer entered by the user.
                bidSize = scanner.nextInt();
                if (bidSize > 4 || bidSize < 1)
                {
                    //If the input isn't in the correct range.
                    //Ask the user to try again.
                    System.out.println("Please enter a number from 1 - 4.");
                    System.out.print("How many cards would you like to play? ");
                }
            } catch (InputMismatchException ime)
            {
                //If the input is not a number, ask the user to try again.
                System.out.println("Please enter a number from 1 - 4.");
                System.out.print("How many cards would you like to play? ");
            }
        }
    }
    
    private Hand chooseBidCards()
    {
        /*
         * This method gets and formats user input on cards.
         * 
         * Separate method for clarity.
         */
        
        String cardString;
        Card.Rank rank;
        Card.Suit suit;
        Card tempCard;
        Hand tempHand = new Hand();
        
        if(bidSize == 1)
        {
            //Separated 1 and >1 cards for gramatical purposes.
            scanner = new Scanner(System.in);
            System.out.print("Please type the card you wish to play: ");
            //Get user text.
            cardString = scanner.nextLine().toUpperCase();
            //Check it against a regular expression.
            while(!cardString.matches(CARD_REGEX))
            {
                //If it doesn't match ask the user to input again.
                System.out.println("That card was not recognised."
                        + "\nType the full name of your card "
                        + "e.g. two of clubs");
                System.out.print("Please type the card you wish to play: ");
                cardString = scanner.nextLine().toUpperCase();
            }
            //Get the rank from the user input.
            rank = Card.Rank.valueOf(cardString.split(CARD_SPLIT)[0]);
            //Get the suit from the user input.
            suit = Card.Suit.valueOf(cardString.split(CARD_SPLIT)[1]);
            //Create a card with the suit and rank
            tempCard = new Card(rank, suit);
            tempHand.add(tempCard);
            //Return the card. 
            return tempHand;
        }
        //If the bid size is greater than one.
        //Again this is done only for gramatical purposes.
        scanner = new Scanner(System.in);
        for (int i = 0; i < bidSize; i++)
        {
            //Ask the user to enter each card.
            System.out.print("Please enter card #" + (i + 1) + ": ");
            cardString = scanner.nextLine().toUpperCase();
            //Check that each entry is valid.
            while(!cardString.matches(CARD_REGEX))
            {
                System.out.println("That card was not recognised."
                        + "\nType in the card you wish to play "
                        + "e.g. two of clubs");
                System.out.print("Please enter card #" + (i + 1) + ": ");
                //If not then ask the user to re-enter.
                cardString = scanner.nextLine().toUpperCase();
            }
            /*
             * Once all the information is validated then 
             * create a card and add it to a temporary hand.
             */
            rank = Card.Rank.valueOf(cardString.split(CARD_SPLIT)[0]);
            suit = Card.Suit.valueOf(cardString.split(CARD_SPLIT)[1]);
            tempCard = new Card(rank, suit);
            tempHand.add(tempCard);
        }
        //Return the temporary hand.
        return tempHand;
    }
    
    /**
     * Ask the player to choose what rank he wants to play.
     * 
     * @return The bid rank a player wants to play.
     */
    public Card.Rank chooseBidRank()
    {
        scanner = new Scanner(System.in);
        System.out.print("Please select the rank you bid. ");
        //Get the rank input from the user.
        String rankString = scanner.nextLine().toUpperCase();
        while(!rankString.matches(RANK_REGEX))
        {
            //Ensure the input is valid. If not ask the user to re-enter.
            System.out.println("That rank was not recognised.");
            System.out.print("Please select the rank you bid. ");
            rankString = scanner.nextLine().toUpperCase();
        }
        //Return the user chosen rank.
        return Card.Rank.valueOf(rankString);
    }
}
