import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the WelcomePageGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */
// adding comment to test the new branch
public class WelcomePageGUI extends JFrame{
    /**
     * JPanel for the welcome page
     */
    private JPanel mainPanel;
    /**
     *  JButton that opens the rulebook when clicked
     */
    private JButton RULESButton;
    /**
     * JButton that creates an option JFrame when clicked
     */
    private JButton STARTGAMEButton;

    /**
     * DVC sets up with two buttons (rules and start)
     * @param title of the frame
     */
    public WelcomePageGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        setSize(700, 100);
        setLocation(350, 65);
        setVisible(true);

        // RULES
        RULESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RulesGUI rules = new RulesGUI("RULES");
            }
        });

        // START GAME
        STARTGAMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsGUI options = new OptionsGUI("OPTIONS");
            }
        });
    }

    /**
     * Main function for the entire program
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new WelcomePageGUI("RAINBOW YAHTZEE");
        frame.setVisible(true);
    }
}
