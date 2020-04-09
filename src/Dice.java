import java.util.Random;

/**
 * This class represents the attributes of a dice.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class Dice {

    private static int dice_faces;
    private static int face_rolled;

    /**
     * DVC of Dice is initialized with face_rolled initialized with a random number.
     */
    public Dice() {
        dice_faces = GameGUI.getSides_of_dice();
        face_rolled = roll_dice();
    }

    /**
     * simulates rolling a dice
     * @return a random value of the dice (returns random position of the dice_faces)
     */
    private static int roll_dice() {
        Random random = new Random();
        int randomRoll = random.nextInt(dice_faces);
        return randomRoll + 1;
    }

    /**
     * gets the value of the face rolled
     * @return value of face_rolled
     */
    public int getFace_rolled() {
        return face_rolled;
    }

    /**
     * Overrides the toString to output the upward face of the dice
     * @return face_rolled which is the value of the face rolled
     */
    @Override
    public String toString() {
        return String.valueOf(face_rolled);
    }
}
