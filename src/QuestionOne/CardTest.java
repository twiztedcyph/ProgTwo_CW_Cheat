/*
 * This program simulates a deck of cards.
 */

package QuestionOne;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * CardTest class.
 * 
 * CMPC2M1Y Programming 2 Coursework.
 * 
 * @author Ian Weeks 6204848
 */
public class CardTest
{
    private Deck deck;
    private ArrayList<Hand> hands;
    private Scanner scanner = new Scanner(System.in);
    
    /**
     * Run all the tests in this class. Each test number directly relates to
     * the question number under the CardTest section of the coursework.
     * 
     * @throws IOException An IO error has occurred.
     * @throws FileNotFoundException The file name used cannot be opened.
     * @throws ClassNotFoundException The class being loaded cannot be found.
     */
    public void runAll() throws
            IOException, 
            FileNotFoundException, 
            ClassNotFoundException
    {
        partOne();
        nextTask("Suffle deck and deal cards?");
        partTwo();
        nextTask("Save all hands to file?");
        partThree();
        nextTask("Display suit information?");
        partFour();
        nextTask("Display straight and flush information?");
        partFive();
        nextTask("Sort hands as specified? ");
        partSix();
        nextTask("Load and print saved hands from file?");
        partSeven();
        nextTask("Move all hand one cards to hand two?");
        partEight();
        nextTask("Rearrange all cards so each hand has a single suit?");
        partNine();
    }
    
    /**
     * Create an un-shuffled deck of cards. Use an iterator to traverse the
     * cards in deal order and then in odd/even order.
     */
    public void partOne()
    {
        /*
         * Print the cards in four columns so they can all be seen easily.
         * Number each card so the order is clear.
         */
        int cardCounter = 1;
        System.out.println("Deal order");
        deck = new Deck();
        //Get the standard deck iterator.
        Iterator it = deck.iterator();
        while(it.hasNext())
        {
            System.out.printf("%2d:  %s\t",cardCounter, it.next());
            //Four columns.
            if(cardCounter % 4 == 0)
            {
                System.out.println();
            }
            cardCounter++;
        }
        //Ask if the user wants to see the next part.
        nextTask("Print even/odd? ");
        System.out.println("\nOddEven order");
        //Reset the card counter.
        cardCounter = 1;
        //Get the odd/even iterator.
        it = deck.getOddEvenIterator();
        while(it.hasNext())
        {
            System.out.printf("%2d:  %s\t",cardCounter, it.next());
            if(cardCounter % 4 == 0)
            {
                System.out.println();
            }
            cardCounter++;
        }
    }
    
    /**
     * Shuffle the deck and deal all the cards our to the four hands.
     * Use a for each loop to print the cards in each hand.
     */
    public void partTwo()
    {
        //Initialize the list of Hands.
        hands = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            //Add four hands.
            hands.add(new Hand());
        }
        
        //Shuffle the deck.
        deck.shuffle();
        
        //Dealing is done only using the size() and deal() methods.
        while(deck.getSize() > 0)
        {
            /*
             * Try catch block incase the deck runs out of cards during a loop.
             *   **Obviously won't happen with a full deck and four hands**
             */
            try
            {
                //For each dealing.
                for (Hand hand : hands)
                {
                    hand.add(deck.deal());
                }
            } catch (Deck.EmptyDeckException e)
            {
                //Message if exception is caught.
                System.out.println("\nDealing stopped. "
                        + "The deck is now empty.");
            }
        }
        System.out.println();
        int handCounter = 1;
        /*
         * For each print of each hand. Could not think of a way to print
         * each hand in its own column(for easier viewing) using for each...
         */
        for (Hand hand : hands)
        {
            //Print the hand information.
            System.out.printf("Hand %d contains:\n", handCounter);
            System.out.println(hand);
            handCounter++;
        }
    }
    
    /**
     * Save all the hands to file.
     * 
     * @throws FileNotFoundException The file name used cannot be opened.
     * @throws IOException An IO error has occurred.
     */
    public void partThree() throws FileNotFoundException, IOException
    {
        FileOutputStream fos;
        ObjectOutputStream oos;
        
        //String builder to print the results of the save opperation.
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nHands saved to files:\n");
        
        //Hand counter to make each file name unique.
        int handCounter = 1;
        for (Hand hand : hands)
        {
            //Set the file name for each hand.
            String fileName = "hand" + handCounter + ".ser";
            //Initialize the file output stream.
            fos = new FileOutputStream(fileName);
            //Initialize the object output stream.
            oos = new ObjectOutputStream(fos);
            //Write the object to file.
            oos.writeObject(hand);
            //Close the object output stream after each use.
            oos.close();
            handCounter++;
            //Add a record of the saved object to the result string.
            stringBuilder.append(String.format("%s\n", fileName));
        }
        //Print out the results of the save.
        System.out.println(stringBuilder.toString());
    }
    
    /**
     * Display the number of each suit and rank 
     * and the total of the values in each hand.
     */
    public void partFour()
    {
        System.out.println("\nSuit count.");
        System.out.printf("Hand one:\t\t"
                + "Hand two:\t\t"
                + "Hand three:\t\t"
                + "Hand four:\n");
        for (Card.Suit suit : Card.Suit.values())
        {
            for (Hand hand : hands)
            {
                //Print suit information for each hand.
                System.out.printf("%8s: %d\t\t", suit, hand.countSuit(suit));
            }
            System.out.println("");
        }
        
        //Ask the user if he wants to continue.
        nextTask("Display rank information?");
        
        System.out.println("\nRank count.");
        System.out.printf("Hand one:\t\t"
                + "Hand two:\t\t"
                + "Hand three:\t\t"
                + "Hand four:\n");
        for (Card.Rank rank : Card.Rank.values())
        {
            for (Hand hand : hands)
            {
                //Print rank information for each hand.
                System.out.printf("%7s: %d\t\t", rank, hand.countRank(rank));
            }
            System.out.println("");
        }
        
        //Ask the user if he wants to continue.
        nextTask("Display value totals information?");
        
        System.out.println("\nHand total values.");
        System.out.printf("Hand one:\t\t"
                + "Hand two:\t\t"
                + "Hand three:\t\t"
                + "Hand four:\n");
        
        for (Hand hand : hands)
        {
            //Print hand value information for each hand.
            System.out.printf("%5d\t\t\t", hand.handValue());
        }
        System.out.println("");
    }
    
    /**
     * Display whether each hand is a straight or a flush.
     */
    public void partFive()
    {
        System.out.println("\nStraight.");
        System.out.printf("Hand one:\t\t"
                + "Hand two:\t\t"
                + "Hand three:\t\t"
                + "Hand four:\n");
        for (Hand hand : hands)
        {
            System.out.printf("%5s\t\t\t", hand.isStraight());
        }
        
        System.out.println("\n\nFlush.");
        System.out.printf("Hand one:\t\t"
                + "Hand two:\t\t"
                + "Hand three:\t\t"
                + "Hand four:\n");
        for (Hand hand : hands)
        {
            System.out.printf("%5s\t\t\t", hand.isFlush());
        }
        System.out.println();
    }
    
    /**
     * Sort the first two hands into ascending order.
     * Sort the third into descending order.
     * Sort the fourth into suit order.
     */
    public void partSix()
    {
        System.out.println();
        
        /*
         * For loop sorts the first two hands (ascending order).
         * The sortAscending() method from the Hand class is used.
         */
        for (int i = 0; i < 2; i++)
        {
            hands.get(i).sortAscending();
        }
        
        /*
         * Third hand sorted (descending order).
         * The sortDescending method from the hand class is used.
         */
        hands.get(2).sortDescending();
        
        /*
         * Fourth hand sorted into suit order. The suit 
         * comparator from the card class is used for this.
         */
        Collections.sort(hands.get(3).getHeldCards(), Card.getCompareSuit());
        
        printAllHands();
    }
    
    /**
     * Load the hands from file.
     * This means the hands will be as they were at part three.
     * 
     * @throws IOException An IO error has occurred.
     * @throws FileNotFoundException The file name used cannot be opened.
     * @throws ClassNotFoundException The class being loaded cannot be found.
     */
    public void partSeven() throws FileNotFoundException, 
            IOException, 
            ClassNotFoundException
    {
        FileInputStream fis;
        ObjectInputStream ois;
        
        for (int i = 0; i < 4; i++)
        {
            //Initialise the file input stream with the correct file name.
            fis = new FileInputStream("hand" + (i + 1) + ".ser");
            //Initialize the object input stream.
            ois = new ObjectInputStream(fis);
            //Load the file.
            hands.set(i, (Hand) ois.readObject());
        }
        System.out.println("Hands loaded from file.");
        printAllHands();
    }
    
    /**
     * Iterate over the first hand with an iterator, removing each
     * card and adding it to the second hand.
     */
    public void partEight()
    {
        //Initialise an iterator for the first hand.
        Iterator<Card> it = hands.get(0).iterator();
        while(it.hasNext())
        {
            //Add each card to the second hand.
            hands.get(1).add(it.next());
            //Then remove the card.
            // TODO change this part if you can.
            it.remove();
        }
        printAllHands();
    }
    
    /**
     * Rearrange the cards in the hands so that each hand contains all 13
     * cards of a single suit in ascending order.
     */
    public void partNine()
    {
        //Initialise an iterator for the third hand.
        Iterator<Card> it = hands.get(2).iterator();
        //Move all cards to the second hand.
        while(it.hasNext())
        {
            hands.get(1).add(it.next());
            it.remove();
        }
        
        //Do the same for the fourth hand.
        it = hands.get(3).iterator();
        while(it.hasNext())
        {
            hands.get(1).add(it.next());
            it.remove();
        }
        
        /*
         * Iterate through the second hand where all the cards are.
         * Send all clubs to the first hand, all hearts to the third
         * hand and all spades to the fourth hand. The second hand is
         * left with all the diamonds.
         */
        it = hands.get(1).iterator();
        while(it.hasNext())
        {
            //TempCard used instead of calling it.next().
            Card tempCard = it.next();
            switch(tempCard.getSuit())
            {
                case CLUBS:
                    hands.get(0).add(tempCard);
                    it.remove();
                    break;
                case DIAMONDS:
                    //Diamonds stay in hand two.
                    break;
                case HEARTS:
                    hands.get(2).add(tempCard);
                    it.remove();
                    break;
                case SPADES:
                    hands.get(3).add(tempCard);
                    it.remove();
                    break;
                default:
                    System.out.println("Something has gone "
                            + "terribly wrong. FLEE!");
                    System.exit(0);
            }
        }
        
        //Sort all the hands in ascending order.
        for (Hand hand : hands)
        {
            hand.sortAscending();
        }
        
        //Print them out for the user.
        printAllHands();
    }
    
    /**
     * Display a message and ask the user if he would like to continue.
     * 
     * @param message The message to be displayed.
     */
    private void nextTask(String message)
    {
        /*
         * Add the "y/n" to the end of the passed message.
         * A scanner is used to check the user's input.
         */
        System.out.print("\n" + message + " y/n: ");
        String userInput = scanner.next();
        while(!userInput.equals("y") && !userInput.equals("n"))
        {
            /*
             * If the input criteria is not met the
             * user is asked to make a choice agian. 
             */
            System.out.print(message + " y/n: ");
            userInput = scanner.next();
        }
        if(userInput.equals("n"))
        {
            //Exit if the input was "n".
            System.out.println("Exiting. Goodbye.");
            System.exit(0);
        }
    }
    
    /**
     * Print the cards in each hand. Each hand has its own column.
     */
    private void printAllHands()
    {
        //List of card iterators.
        ArrayList<Iterator<Card>> itList = new ArrayList<>();
        int largestHand = 0;
        for (Hand hand : hands)
        {
            /*
             * Initialize the iterators and get the size of the
             * largest hand.
             */
            itList.add(hand.iterator());
            if(hand.size() > largestHand)
            {
                largestHand = hand.size();
            }
        }
        
        System.out.printf("Hand one:\t\t"
                + "Hand two:\t\t"
                + "Hand three:\t\t"
                + "Hand four:\n");
        
        /*
         * Outer loop ensures all cards are printed 
         * even if hands are not the same size.
         */
        for (int i = 0; i < largestHand; i++)
        {
           //Inner loop iterates through each iterator :P. 
            for (Iterator it : itList)
            {
                if(it.hasNext())
                {
                    //If there is another card print it.
                    System.out.print(it.next() + "\t");
                }else
                {
                    //Else just print spaces.
                    System.out.print("\t\t\t");
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args)
    {
        CardTest cardTest = new CardTest();
        try
        {
            cardTest.runAll();
        } catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("Something has gone wrong. Please try agian. Exiting");
            System.exit(0);
        }
    }
}
