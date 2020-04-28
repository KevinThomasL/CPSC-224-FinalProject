import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the ScorecardGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class ScorecardGUI extends JFrame{
    /**
     * JPanel for the mainPanel of the game
     */
    private JPanel mainPanel;
    /**
     * TextPane that displays the player's scores
     */
    private JTextPane scores;
    /**
     * TextField for a line of text
     */
    private JTextField lineText;
    /**
     * When clicked, the player's scoring preference is recorded
     */
    private JButton GOButton;
    /**
     * The user's preference on which line they want to score on
     */
    private JTextField userInput;
    /**
     * Text of userInput
     */
    private String lineCode = "";

    /**
     * DVC sets up the scorecard panel with all components invisible to user
     * @param title is the title of the frame
     */
    public ScorecardGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setContentPane(mainPanel);

        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        setSize(180, 650);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(GameGUI.width() + this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);
        lineText.setVisible(false);
        userInput.setVisible(false);
        GOButton.setVisible(false);
    }

    /**
     * displays scores to the user. no interaction is necessary
     */
    public void displayScores() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        setSize(180, 650);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(GameGUI.width() + this.getSize().width/2, dim.height/2-this.getSize().height/2);
        scores.setText(GameGUI.getCurrPlayerObject().getDetermineScorecard().displayScoreCard());
        lineText.setVisible(false);
        userInput.setVisible(false);
        GOButton.setVisible(false);
    }

    /**
     * displays possible scores for the hand. allows user to choose line
     * @param text the text representing possible scores
     */
    public void displayScoreOptions(String text) {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(250, 425);
        scores.setText(text);
        lineText.setVisible(true);
        userInput.setVisible(true);
        GOButton.setVisible(true);

        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineCode = userInput.getText();
                if (GameGUI.getCurrPlayerObject().getDetermineScorecard().getScorecardLineList().contains(lineCode)) {
                    GameGUI.getCurrPlayerObject().getDetermineScorecard().writeScorecard(lineCode, GameGUI.getCurrPlayer());
                    GameGUI.goHit = true;
                    setVisible(false);
                }
            }
        });
    }
}
