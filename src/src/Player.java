import java.util.ArrayList;

public class Player {

  private Hand hand;
  private Score determineScorecard;
  private ScorecardGUI scorecard;
  private int turn;
  private int rolls_of_dice;
  private int diceInHand;

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


  public int getTurn() { return turn; }
  public void setTurn(int new_turn) { turn = new_turn; }
  public void incrementTurn() { turn++; }
  public Hand getHand() { return hand; }
  public void resetHand() { hand = new Hand(diceInHand); }
  public Score getDetermineScorecard() { return determineScorecard; }
  public void setDetermineScorecard() { determineScorecard = new Score(hand.getHand(), diceInHand); }
  public ScorecardGUI getScorecard() { return scorecard; }

}


