import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the WelcomePageGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment #4
 * sources to cite:
 *
 * @author Nicole Bien
 */
public class WelcomePageGUI extends JFrame{
    private JPanel mainPanel;
    private JButton RULESButton;
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

    public static void main(String[] args) {
        JFrame frame = new WelcomePageGUI("YAHTZEE");
        frame.setVisible(true);
    }
}
