/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
