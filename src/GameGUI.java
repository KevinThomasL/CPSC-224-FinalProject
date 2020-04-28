import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the GameGUI / brain of game.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class GameGUI extends JFrame {

    private static int num_of_players = OptionsGUI.getNum_of_players();
    private static int dice_in_play = OptionsGUI.getDice_in_play();
    private static int sides_of_dice = OptionsGUI.getSides_in_play();
    private static int rolls_of_dice = OptionsGUI.getRolls_in_play();
    private JPanel mainPanel;
    private static ArrayList<Integer> checkboxes = new ArrayList<Integer>();
    private GridBagConstraints c = new GridBagConstraints();
    private Font f1 = new Font("Courier New", Font.PLAIN,  14);
    private JLabel[] diceLBZ;
    private JCheckBox[] checkLBZ;
    private JButton rollAgain;
    private JButton nextTurnButton;
    private JButton scorecardButton;
    static boolean goHit = false;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static int curr_player = 0;

    /**
     * DVC creates the panel with dice, checkboxes, and buttons
     * @param title of the frame
     */
    public GameGUI (String title) {
        super(title);
        for (int i = 0; i < num_of_players; i++) { // initialize player objects
            players.add(new Player(rolls_of_dice, "PLAYER"+(i+1)+" SCORECARD", i));
        }
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(mainPanel);
        determineFrameSize();
        setVisible(true);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.fill = GridBagConstraints.HORIZONTAL;
        diceLBZ = new JLabel[dice_in_play];
        checkLBZ = new JCheckBox[dice_in_play];

        rollDice(); // create all dice
        createCheckBoxes(); // create check boxes
        createRollButton(); // create roll again button
        createScorecardButton(); // create scorecard button
        createNextTurnButton(); // create next turn button
    }

    private void determineFrameSize() {
        if (dice_in_play == 7) {
            setSize(700, 200);
            setLocation(350, 180);
        } else if (dice_in_play == 10) {
            setSize(950, 200);
            setLocation(200, 180);
        } else {
            setSize(1050, 200);
            setLocation(150, 180);
        }
    }

    /**
     * creates the dice dependent on number of dice user chose to play with
     */
    public void rollDice() {
        for (int i = 1; i <= dice_in_play; i++) {
            diceLBZ[i - 1] = new JLabel();
            diceLBZ[i - 1].setIcon(getScaledImageIcon("dice" + players.get(curr_player).getHand().getHand().get(i-1) + ".png"));
            c.weightx = 0.5;
            c.fill = GridBagConstraints.CENTER;
            c.gridx = i - 1;
            c.gridy = 0;
            mainPanel.add(diceLBZ[i - 1], c);
        }
    }

    /**
     * updates the dice images (JLabels) according to given array in Score class
     */
    public void updateDice() {
        for (int i = 1; i <= dice_in_play; i++) {
            diceLBZ[i - 1].setIcon(getScaledImageIcon("dice" + players.get(curr_player).getHand().getHand().get(i-1) + ".png"));
        }
    }

    /**
     * creates the checkboxes that are parallel to the dice
     */
    public void createCheckBoxes() {
        for (int i = 1; i <= dice_in_play; i++) {
            int boxPosition = i - 1;
            checkLBZ[boxPosition] = new JCheckBox();
            c.weightx = 0.5;
            c.fill = GridBagConstraints.CENTER;
            c.gridx = i - 1;
            c.gridy = 1;
            mainPanel.add(checkLBZ[boxPosition], c);
        }
    }

    /**
     * disables checkboxes and sets each one to have not been selected
     */
    public void disableCheckBoxes() {
        for (int i = 1; i <= dice_in_play; i++) {
            checkLBZ[i - 1].setSelected(false);
            checkLBZ[i - 1].setEnabled(false);
        }
    }

    /**
     * stores in an array which boxes are selected to keep for the turn
     */
    public void findSelectedBoxes() {
        for (int i = 1; i <= dice_in_play; i++) {
            if (checkLBZ[i - 1].isSelected())
                checkboxes.add(i - 1);
        }
    }

    /**
     * resets each check box to be visible, enabled, and unchecked
     */
    public void resetCheckBoxes() {
        for (int i = 0; i < dice_in_play; i++) {
            if (checkLBZ[i].isSelected()) {
                checkLBZ[i].setVisible(true);
            }
            checkLBZ[i].setEnabled(true);
        }
        checkboxes.clear();
    }

    /**
     * roll button, when clicked, will react differently dependent on what
     * stage of game user is in
     */
    public void createRollButton() {
        rollAgain = new JButton("ROLLS " + rolls_of_dice);
        rollAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findSelectedBoxes();
                if (players.get(curr_player).getTurn() == rolls_of_dice || checkboxes.size() == dice_in_play) {
                    rollAgain.setVisible(false);
//                    if (players.get(curr_player).getTurn() == rolls_of_dice)
//                        JOptionPane.showMessageDialog(mainPanel, "OUT OF ROLLS");
                    players.get(curr_player).getHand().sortHand();
                    updateDice();
                    disableCheckBoxes();
                    players.get(curr_player).getScorecard().setVisible(true);
                    players.get(curr_player).getScorecard().displayScoreOptions(players.get(curr_player).getDetermineScorecard().displayScoreOptions());
                } else {
                    if (players.get(curr_player).getTurn() < rolls_of_dice && checkboxes.size() != dice_in_play) {
                        for (int i = 0; i < dice_in_play; i++) { // rolls the ones unchecked
                            if (!checkboxes.contains(i)) { // roll!
                                players.get(curr_player).getHand().changeOneDice(i);
                            }
                        }
                        updateDice();
                        rollAgain.setText("ROLLS " + (rolls_of_dice - players.get(curr_player).getTurn()));
                        players.get(curr_player).incrementTurn();
                    }
                }
            }
        });
        rollAgain.setFont(f1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = dice_in_play / 3;
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(rollAgain, c);
    }

    /**
     * displays scorecard from ScorecardGUI class when clicked
     */
    public void createScorecardButton() {
        scorecardButton = new JButton("PLAYER"+(curr_player+1)+" SCORECARD");
        scorecardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                players.get(curr_player).getScorecard().setVisible(true);
                players.get(curr_player).getScorecard().displayScores();
            }
        });
        scorecardButton.setFont(f1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = dice_in_play / 3;
        c.gridx = dice_in_play / 3;
        c.gridy = 2;
        mainPanel.add(scorecardButton, c);
    }

    /**
     * moves to the next hand of the dice when clicked
     * only able to be clicked when its time!
     */
    public void createNextTurnButton() {
        nextTurnButton = new JButton("NEXT TURN");
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasGameEnded()) {
                    int winner = 0;
                    int winning_score = -1;
                    // tie??
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getDetermineScorecard().getGrandTotal() > winning_score) {
                            winner = i;
                            winning_score = players.get(i).getDetermineScorecard().getGrandTotal();
                        }
                    }
                    JOptionPane.showMessageDialog(mainPanel, "GAME OVER! PLAYER"+(winner+1)+" WINS!");
                    players.get(winner).getScorecard().setVisible(true);
                    players.get(winner).getScorecard().displayScores();
                    playAgain();
                } else if (goHit) {
                    if (players.get(curr_player).getTurn() == rolls_of_dice || checkboxes.size() == dice_in_play) {
                        players.get(curr_player).setTurn(1); // reset player's turn
                        resetCheckBoxes(); // resets all check boxes
                        players.get(curr_player).resetHand(); // create new hand
                        if (curr_player == players.size()-1) {
                            curr_player = 0; // end of a round. Go back to the first player's turn
                        } else curr_player++; // round is not over yet. Go to next player's turn
                        updateDice(); // re rolls all dice
                        scorecardButton.setText("PLAYER"+(curr_player+1)+" SCORECARD");
                        players.get(curr_player).getScorecard().setVisible(false);
                        rollAgain.setVisible(true);
                        rollAgain.setText("ROLLS " + rolls_of_dice);
                        goHit = false;
                    }
                } else {
                    if (players.get(curr_player).getTurn() == rolls_of_dice) {
                        players.get(curr_player).getScorecard().setVisible(true);
                        players.get(curr_player).getScorecard().displayScoreOptions(players.get(curr_player).getDetermineScorecard().displayScoreOptions());
                    }
                }
            }
        });
        nextTurnButton.setFont(f1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = dice_in_play - ((dice_in_play / 3) * 2);
        c.gridx = ((dice_in_play / 3) * 2);
        c.gridy = 2;
        mainPanel.add(nextTurnButton, c);
    }

    /**
     * determines whether to play again or not
     */
    private void playAgain() {
        int dialogButton = JOptionPane.showConfirmDialog (null, "PLAY AGAIN?","RAINBOW YAHTZEE",JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION) {
            for (int i = 0; i < players.size(); i++) { // reset game
                players.get(curr_player).getScorecard().setVisible(false);
                players.get(i).getDetermineScorecard().reset();
            }
            players.clear();
            curr_player = 0;
            OptionsGUI options = new OptionsGUI("OPTIONS");
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else {
            System.exit(0);
        }
    }

    /**
     * determines if game has ended
     * @return true if ended, false otherwise
     */
    private boolean hasGameEnded() {
        if (players.get(curr_player).getDetermineScorecard().getUsedScoreCardLines().size() >= players.get(curr_player).getDetermineScorecard().getScorecardLineList().size()
                && (curr_player + 1) == players.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * scales the image passed through the be correct size
     * @param srcImg string of the image name in file (src)
     * @return ImageIcon that is resized to be used
     */
    public ImageIcon getScaledImageIcon(String srcImg){
        ImageIcon icon = new ImageIcon(getClass().getResource(srcImg));
        int width = -1; // -1 to preserve aspect ratio
        int height = -1;
        if (icon.getIconWidth() <= icon.getIconHeight()) {
            height = 80;
        }
        else {
            width = 80;
        }
        // https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image, icon.getDescription());
        return icon;
    }

    /**
     * returns number of dice being played with
     * @return dice in play
     */
    public static int getDice_in_play() { return dice_in_play; }

    /**
     * returns number of sides of die in game
     * @return sides of die
     */
    public static int getSides_of_dice() { return sides_of_dice; }

    public static Player getCurrPlayerObject() { return players.get(curr_player); }
    public static int getCurrPlayer() { return curr_player; }
}

