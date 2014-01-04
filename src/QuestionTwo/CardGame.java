/*
 * This program simulates the card game: Cheat.
 */

package QuestionTwo;

public interface CardGame
{

    /**
     * Initialise the card game
     */
    public void initialise();

    /**
     * Plays a single turn of the game
     *
     * @return true if play made
     */
    public boolean playTurn();

    /**
     *
     * @return an integer representing the winner
     */
    public int winner();
}
