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
    private Scanner scanner;
    public HumanStrategy()
    {
        scanner = new Scanner(System.in);
    }

    @Override
    public boolean cheat(Bid b, Hand h)
    {
        Iterator<Card> it = h.iterator();
        int cardCount = 0;
        System.out.println("The current hand is: " + b.r);
        System.out.print("Your cards are:\n");
        while(it.hasNext())
        {
            System.out.println(cardCount + ": " + it.next());
            cardCount++;
        }
        System.out.print("Would you like to cheat? yes/no: ");
        String userInput = scanner.nextLine();
        while(!userInput.equals("yes") && !userInput.equals("no"))
        {
            userInput = scanner.nextLine();
        }
        return userInput.equals("yes");
    }

    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat)
    {
        int bidSize = 0;
        Bid myBid = new Bid();
        Hand tempHand = new Hand();
        
        if(cheat)
        {
            System.out.print("How many cards would you like to play? ");
            while(bidSize < 1 || bidSize > 4)
            {
                scanner = new Scanner(System.in);
                try
                {
                    bidSize = scanner.nextInt();
                    if(bidSize > 4 || bidSize < 0)
                    {
                        System.out.println("Only 1 - 4 excepted.");
                        System.out.print("How many cards would you like to play? ");
                    }
                } catch (InputMismatchException ime)
                {
                    System.out.println("Only 1 - 4 excepted.");
                    System.out.print("How many cards would you like to play? ");
                }
            }
            String[] bidIndexString;
            System.out.println("Please enter each index of the " + bidSize + " cards you want to to bid separated by commas.");
            scanner = new Scanner(System.in);
            bidIndexString = scanner.nextLine().trim().split("\\,");
            System.out.println(bidIndexString.length);
            while(bidIndexString.length != bidSize)
            {
                bidIndexString = scanner.nextLine().trim().split("\\,");
            }
            
            for (int i = 0; i < bidSize; i++)
            {
                int cardIndex = Integer.getInteger(bidIndexString[i]);
                tempHand.add(h.getHeldCards().get(cardIndex));
            }
            h.remove(tempHand);
            myBid.setHand(tempHand);
            return myBid;
        }
        //Else if not cheating.
        return myBid;
    }

    @Override
    public boolean callCheat(Hand h, Bid b)
    {
        System.out.print("Would you like to call cheat? yes/no :");
        String userInput = scanner.nextLine();
        while(!userInput.equals("yes") && !userInput.equals("no"))
        {
            userInput = scanner.nextLine();
        }
        return userInput.equals("yes");
    }
    
}
