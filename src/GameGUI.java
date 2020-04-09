import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents the GameGUI / brain of game.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class GameGUI extends JFrame {

    private static int dice_in_play = OptionsGUI.getDice_in_play();
    private static int sides_of_dice = OptionsGUI.getSides_in_play();
    private static int rolls_of_dice = OptionsGUI.getRolls_in_play();
    private JPanel mainPanel;
    private static Hand diceInHand;
    private static int turn = 1;
    private static ArrayList<Integer> checkboxes = new ArrayList<Integer>();
    private GridBagConstraints c = new GridBagConstraints();
    private ScorecardGUI scorecard;
    static Score determineScore;
    private Font f1 = new Font("Courier New", Font.PLAIN,  14);
    private JLabel[] diceLBZ;
    private JCheckBox[] checkLBZ;
    private JButton rollAgain;
    private JButton nextTurnButton;
    static boolean goHit = false;

    /**
     * DVC creates the panel with dice, checkboxes, and buttons
     * @param title of the frame
     */
    public GameGUI (String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(mainPanel);
        setSize(700,200);
        setLocation(350, 180);
        setVisible(true);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.fill = GridBagConstraints.HORIZONTAL;

        Score.createScorecard();
        Score.readScorecard();
        Score.usedScoreCardLines.clear();
        scorecard = new ScorecardGUI("SCORECARD");
        scorecard.setVisible(false);
        diceInHand = new Hand();
        determineScore = new Score(diceInHand.getHand());
        diceLBZ = new JLabel[dice_in_play];
        checkLBZ = new JCheckBox[dice_in_play];

        rollDice(); // create all dice
        createCheckBoxes(); // create check boxes
        createRollButton(); // create roll again button
        createScorecardButton(); // create scorecard button
        createNextTurnButton(); // create next turn button
    }

    /**
     * creates the dice dependent on number of dice user chose to play with
     */
    public void rollDice() {
        for (int i = 1; i <= dice_in_play; i++) {
            diceLBZ[i - 1] = new JLabel();
            diceLBZ[i - 1].setIcon(getScaledImageIcon("dice" + diceInHand.getHand().get(i - 1) + ".png"));
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
            diceLBZ[i - 1].setIcon(getScaledImageIcon("dice" + diceInHand.getHand().get(i - 1) + ".png"));
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
        rollAgain = new JButton("ROLL");
        rollAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findSelectedBoxes();
                if (Score.usedScoreCardLines.size() > Score.getScorecardLineList().size()) { // if no more spots
                    JOptionPane.showMessageDialog(mainPanel, "GAME OVER");
                    scorecard.setVisible(true);
                } else if(turn == rolls_of_dice || checkboxes.size() == dice_in_play) {
                    rollAgain.setVisible(false);
                    if (turn == rolls_of_dice)
                        JOptionPane.showMessageDialog(mainPanel, "OUT OF ROLLS");
                    diceInHand.sortHand();
                    updateDice();
                    disableCheckBoxes();
                    scorecard.setVisible(true);
                    determineScore = new Score(diceInHand.getHand());
                    scorecard.displayScoreOptions(determineScore.displayScoreOptions());
                } else {
                    if (turn < rolls_of_dice && checkboxes.size() != dice_in_play) {
                        for (int i = 0; i < dice_in_play; i++) { // rolls the ones unchecked
                            if (!checkboxes.contains(i)) { // roll!
                                diceInHand.changeOneDice(i);
                            }
                        }
                        updateDice();
                        turn++;
                    }
                }
            }
        });
        rollAgain.setFont(f1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(rollAgain, c);
    }

    /**
     * displays scorecard from ScorecardGUI class when clicked
     */
    public void createScorecardButton() {
        JButton scorecardButton = new JButton("SCORECARD");
        scorecardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scorecard.setVisible(true);
                scorecard.displayScores();
            }
        });
        scorecardButton.setFont(f1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 1;
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
                if (goHit) {
                    if (turn == rolls_of_dice || checkboxes.size() == dice_in_play) {
                        turn = 1; // reset turn
                        resetCheckBoxes(); // resets all check boxes
                        diceInHand = new Hand(); // create new hand
                        updateDice(); // re rolls all dice
                        rollAgain.setVisible(true);
                        goHit = false;
                    }
                }
            }
        });
        nextTurnButton.setFont(f1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.weightx = 0.0;
        c.gridwidth = dice_in_play - 3;
        c.gridx = 3;
        c.gridy = 2;
        mainPanel.add(nextTurnButton, c);
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

}

