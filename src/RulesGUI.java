import javax.swing.*;

/**
 * This class represents the RulesGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class RulesGUI extends JFrame {
    private JPanel mainPanel;
    private JTextPane YAHTZEERULESTheObjectiveTextPane;
    private JTextField textField1;

    /**
     * DVC creates a panel of a textpane with all rules of yahtzee on it
     * @param title of the frame
     */
    public RulesGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        setSize(700, 750);
        setLocation(350, 170);
        setVisible(true);
    }
}
