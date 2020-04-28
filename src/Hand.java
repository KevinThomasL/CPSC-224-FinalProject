import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class represents the Hand of dice.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class Hand {

    private int dice_in_play;
    private static ArrayList<Integer> random_hand = new ArrayList<Integer>();

    /**
     * DVC of the Hand class initially creates a randomly rolled hand of dice in play if the hand is empty
     * @param  diceInPlay the number of dice in play
     */
    public Hand(int diceInPlay) {
        dice_in_play = diceInPlay;
        if (!random_hand.isEmpty())
            random_hand.clear();
        for (int i = 0; i < dice_in_play; i++)
            random_hand.add(new Dice().getFace_rolled());
    }

    /**
     * gets the numbers of dice in play or in the players hand
     *
     * @return dice_in_play - the numbers of dice being rolled
     */
    public int getNumDice() {
        return dice_in_play;
    }

    /**
     * retrieves the hand of dice
     *
     * @return random_hand which is the hand of dice
     */
    public ArrayList<Integer> getHand() {
        return random_hand;
    }

    /**
     * allows for one of the dice in the hand to be changed
     *
     * @param index is the index of the dice being changed in the hand
     * @return an updated hand with a new dice changed in the hand
     */
    public ArrayList<Integer> changeOneDice(int index) {
        ArrayList<Integer> updated_hand = random_hand;
        Random random = new Random();
        int randomRoll = random.nextInt(GameGUI.getSides_of_dice());
        updated_hand.set(index, randomRoll + 1);
        return updated_hand;
    }

    /**
     * sorts the hand of dice in ascending order
     */
    public void sortHand() {
        Collections.sort(random_hand);
    }

    /**
     * Overrides the toString to output the hand of dice
     *
     * @return printedHead
     */
    @Override
    public String toString() {
        String printedHand = "";
        for (int i = 0; i < dice_in_play; i++) {
            printedHand += random_hand.get(i) + " ";
        }
        return printedHand;
    }
}
