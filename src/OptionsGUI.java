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
    private JPanel mainPanel;
    private JLabel configuration;
    private JLabel numPlayers;
    private JLabel diceInPlay;
    private JLabel rollsInPlay;
    private JButton submit;
    private JComboBox diceInput;
    private JComboBox playersInput;
    private JComboBox rollsInput;
    private static int dice_in_play = 5;
    private static int sides_in_play = 7;
    private static int rolls_in_play = 2;
    private static int num_of_players = 1;

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

        String[] sideValues = {"1", "2", "3", "4", "5", "6", "7", "8"};
        playersInput.setModel(new DefaultComboBoxModel<String>(sideValues));
        String[] diceValues = {"7", "10", "12"};
        diceInput.setModel(new DefaultComboBoxModel<String>(diceValues));
        String[] rollValues = {"3", "4", "6"};
        rollsInput.setModel(new DefaultComboBoxModel<String>(rollValues));

        num_of_players = Integer.parseInt((String) playersInput.getSelectedItem());
        dice_in_play = Integer.parseInt((String) diceInput.getSelectedItem());
        rolls_in_play = Integer.parseInt((String) rollsInput.getSelectedItem());

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
        GameGUI newGame = new GameGUI("RAINBOW YAHTZEE");
        dispose();
    }

    /**
     * returns the number of players in game
     * @return players in game
     */
    public static int getNum_of_players() {
        return num_of_players;
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
