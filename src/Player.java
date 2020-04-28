import java.util.ArrayList;

/**
 * This class represents the Player.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class Player {
  /**
   * the player's hand
   */
  private Hand hand;
  /**
   * the player's Score object
   */
  private Score determineScorecard;
  /**
   * the player's scorecard object
   */
  private ScorecardGUI scorecard;
  /**
   * the player's current turn
   */
  private int turn;
  /**
   * The number of dice that the player has in their hand
   */
  private int rolls_of_dice;
  /**
   * the number of dice in the player's hand
   */
  private int diceInHand;

  /**
   * Constructor for a player object
   * @param rolls_per_hand number of rolls per turn
   * @param title title of the frame
   * @param player_num number of players in the game
   * @param dice_in_hand number of dice in the player's hand
   */
  public Player(int rolls_per_hand, String title, int player_num, int dice_in_hand) {
    diceInHand = dice_in_hand;
    hand = new Hand(diceInHand);
    scorecard = new ScorecardGUI(title);
    scorecard.setVisible(false);
    determineScorecard = new Score(hand.getHand(), diceInHand);
    determineScorecard.createScorecard(player_num);
    determineScorecard.readScorecard(player_num);
    turn = 1;
    rolls_of_dice = rolls_per_hand;
  }

  /**
   * getter function for turn
   * @return turn the current turn
   */
  public int getTurn() { return turn; }

  /**
   * setter function for turn
   * @param new_turn the value that turn is set to
   */
  public void setTurn(int new_turn) { turn = new_turn; }

  /**
   * increments turn by one
   */
  public void incrementTurn() { turn++; }

  /**
   * getter function for the player's hand object
   * @return hand a hand object
   */
  public Hand getHand() { return hand; }

  /**
   * resets the player's hand object
   */
  public void resetHand() { hand = new Hand(diceInHand); }

  /**
   * getter function for determineScorecard
   * @return determineScorecard
   */
  public Score getDetermineScorecard() { return determineScorecard; }

  /**
   * setter function for determineScorecard
   */
  public void setDetermineScorecard() { determineScorecard = new Score(hand.getHand(), diceInHand); }

  /**
   * getter function for the player's scorecard object
   * @return scorecard
   */
  public ScorecardGUI getScorecard() { return scorecard; }

}


