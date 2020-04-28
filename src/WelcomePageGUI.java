import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        this.pack();
//        this.pack();
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
        setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2 - dim.height/4);

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
        JFrame frame = new WelcomePageGUI("RAINBOW YAHTZEE");
        frame.setVisible(true);
    }
}
