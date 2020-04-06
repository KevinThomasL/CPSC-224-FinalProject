import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the OptionsGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment #4
 * sources to cite:
 *
 * @author Nicole Bien
 */
public class OptionsGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel configuration;
    private JLabel diceInPlay;
    private JLabel sidesInPlay;
    private JLabel rollsInPlay;
    private JButton submit;
    private JComboBox diceInput;
    private JComboBox sidesInput;
    private JComboBox rollsInput;
    private static int dice_in_play = 5;
    private static int sides_in_play = 6;
    private static int rolls_in_play = 2;

    /**
     * DVC creates drop down combo menus for user to interact with
     * to allow them to chose configuration for the game
     * @param title of the frame
     */
    public OptionsGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(mainPanel);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        setSize(700, 400);
        setLocation(350, 180);
        setVisible(true);

        String[] diceValues = {"5", "6", "7"};
        diceInput.setModel(new DefaultComboBoxModel<String>(diceValues));
        String[] sideValues = {"6", "8", "12"};
        sidesInput.setModel(new DefaultComboBoxModel<String>(sideValues));
        String[] rollValues = {"2", "4", "6"};
        rollsInput.setModel(new DefaultComboBoxModel<String>(rollValues));

        dice_in_play = Integer.parseInt((String) diceInput.getSelectedItem());
        sides_in_play = Integer.parseInt((String) sidesInput.getSelectedItem());
        rolls_in_play = Integer.parseInt((String) rollsInput.getSelectedItem());

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readConfiguration();
            }
        });
    }

    /**
     * reads the configuration and stores values in global variables
     */
    private void readConfiguration() {
        dice_in_play = Integer.parseInt((String) diceInput.getSelectedItem());
        sides_in_play = Integer.parseInt((String) sidesInput.getSelectedItem());
        rolls_in_play = Integer.parseInt((String) rollsInput.getSelectedItem());
        GameGUI newGame = new GameGUI("YAHTZEE");
        dispose();
    }

    /**
     * returns the number of dice used in game
     * @return dice in game
     */
    public static int getDice_in_play() { return dice_in_play; }

    /**
     * returns the number of sides of dice in game
     * @return sides of dice
     */
    public static int getSides_in_play() { return sides_in_play; }

    /**
     * returns the number of rolls per turn
     * @return rolls per turn in game
     */
    public static int getRolls_in_play() { return rolls_in_play; }

}
