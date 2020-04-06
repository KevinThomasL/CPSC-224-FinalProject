import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the ScorecardGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment #4
 * sources to cite:
 *
 * @author Nicole Bien
 */
public class ScorecardGUI extends JFrame{
    private JPanel mainPanel;
    private JTextPane scores;
    private JTextField lineText;
    private JButton GOButton;
    private JTextField userInput;
    private String lineCode = "";

    /**
     * DVC sets up the scorecard panel with all components invisible to user
     * @param title is the title of the frame
     */
    public ScorecardGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        setSize(180, 450);
        setLocation(  1075, 65);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        lineText.setVisible(false);
        userInput.setVisible(false);
        GOButton.setVisible(false);
    }

    /**
     * displays scores to the user. no interaction is necessary
     */
    public void displayScores() {
        setSize(180, 550);
        scores.setText(Score.displayScoreCard());
        lineText.setVisible(false);
        userInput.setVisible(false);
        GOButton.setVisible(false);
    }

    /**
     * displays possible scores for the hand. allows user to choose line
     * @param text the text representing possible scores
     */
    public void displayScoreOptions(String text) {
        setSize(250, 400);
        scores.setText(text);
        lineText.setVisible(true);
        userInput.setVisible(true);
        GOButton.setVisible(true);

        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineCode = userInput.getText();
                if (Score.getScorecardLineList().contains(lineCode)) {
                    GameGUI.determineScore.writeScorecard(lineCode);
                    GameGUI.goHit = true;
                    setVisible(false);
                }
            }
        });
    }
}
