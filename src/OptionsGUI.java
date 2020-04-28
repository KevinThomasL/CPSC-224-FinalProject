import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the OptionsGUI.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class OptionsGUI extends JFrame {
    /**
     * JPanel for the main panel of the OptionsGUI
     */
    private JPanel mainPanel;
    /**
     * label to let the player know that they are in the configurations frame
     */
    private JLabel configuration;
    /**
     * label to prompt user to select the number of players to be in the game
     */
    private JLabel numPlayers;
    /**
     * label used to prompt the user to select the number of dice in play
     */
    private JLabel diceInPlay;
    /**
     * label used to prompt the user to select the number of dice in play
     */
    private JLabel rollsInPlay;
    /**
     * JLabel that saved the user configurations when clicked and creates a GameGUI object
     */
    private JButton submit;
    /**
     * ComboBox for the number of dice in play
     */
    private JComboBox diceInput;
    /**
     * Combo box for the number of players in the game
     */
    private JComboBox playersInput;
    /**
     * Combo Box for the number of rolls per turn
     */
    private JComboBox rollsInput;
    /**
     * dice in play
     */
    private int dice_in_play = 5;
    /**
     * number of sides on a dice
     */
    private int sides_in_play = 7;
    /**
     * number of rolls per turn
     */
    private int rolls_in_play = 2;
    /**
     * number of players in the game
     */
    private int num_of_players = 1;

    /**
     * DVC creates drop down combo menus for user to interact with
     * to allow them to chose configuration for the game
     * @param title of the frame
     */
    public OptionsGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(mainPanel);
        setSize(700, 400);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        setLocationRelativeTo(null);
        setVisible(true);

        String[] sideValues = {"1", "2", "3", "4", "5", "6", "7", "8"};
        playersInput.setModel(new DefaultComboBoxModel<String>(sideValues));
        String[] diceValues = {"7", "10", "12"};
        diceInput.setModel(new DefaultComboBoxModel<String>(diceValues));
        String[] rollValues = {"3", "4", "6"};
        rollsInput.setModel(new DefaultComboBoxModel<String>(rollValues));

        num_of_players = Integer.parseInt((String) playersInput.getSelectedItem());
        dice_in_play = Integer.parseInt((String) diceInput.getSelectedItem());
        rolls_in_play = Integer.parseInt((String) rollsInput.getSelectedItem());
        System.out.println("Options created");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readConfiguration();
                dispose();
            }
        });
    }

    /**
     * reads the configuration and stores values in global variables
     */
    private void readConfiguration() {
        num_of_players = Integer.parseInt((String) playersInput.getSelectedItem());
        dice_in_play = Integer.parseInt((String) diceInput.getSelectedItem());
        rolls_in_play = Integer.parseInt((String) rollsInput.getSelectedItem());
        GameGUI newGame = new GameGUI("RAINBOW YAHTZEE", num_of_players, dice_in_play, rolls_in_play);
        dispose();
    }

}
