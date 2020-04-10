import java.util.ArrayList;

public class Player {

  private Hand hand;
  private Score determineScorecard;
  private ScorecardGUI scorecard;
  private int turn;
  private int rolls_of_dice;

  public Player(int rolls_per_hand, String title, int player_num) {
    hand = new Hand();
    scorecard = new ScorecardGUI(title);
    scorecard.setVisible(false);
    determineScorecard = new Score(hand.getHand());
    determineScorecard.createScorecard(player_num);
    determineScorecard.readScorecard(player_num);
    turn = 1;
    rolls_of_dice = rolls_per_hand;
  }


  public int getTurn() { return turn; }
  public void setTurn(int new_turn) { turn = new_turn; }
  public void incrementTurn() { turn++; }
  public Hand getHand() { return hand; }
  public void resetHand() { hand = new Hand(); }
  public Score getDetermineScorecard() { return determineScorecard; }
  public void setDetermineScorecard() { determineScorecard = new Score(hand.getHand()); }
  public ScorecardGUI getScorecard() { return scorecard; }

}


