import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program represents the score card for the game of Yahtzee.
 * CPSC 224-01, Spring 2020
 * Programming Assignment #4
 * sources to cite:
 *
 * @author Nicole Bien
 */

public class Score {

    private Integer smallStraight = 4;
    private Integer largeStraight = 5;
    private static ArrayList<Integer> possibleScoresList = new ArrayList<>(); // keeps running values of possible scores
    private static ArrayList<Integer> scorecardScoreList = new ArrayList<>(); // array for running scores of scorecard
    static ArrayList<Integer> usedScoreCardLines = new ArrayList<>(); // running list of used card lines
    private static ArrayList<String> scorecardLineList = new ArrayList<>(); // array for line names of scorecard
    private static ArrayList<Integer> hand = new ArrayList<>();
    // values for the scorecard
    private static Integer subTotal = 0;
    private static Integer bonus = 0;
    private static Integer upperTotal = 0;
    private static Integer lowerTotal = 0;
    private static Integer grandTotal = 0;

    /**
     * DVC that outputs to user that they have an empty hand
     */
    public Score() {
        System.out.println("empty hand");
    }

    /**
     * EVC that creates a variable of the dice in hand
     * @param hand represents the dice faces rolled in hand
     */
    public Score(ArrayList<Integer> hand) {
        this.hand = hand;
    }

    /**
     * creates the lines column of the scorecard
     */
    public static void createLinesOfScorecard() {
        for (int i = 1; i <= GameGUI.getSides_of_dice(); i++)
            scorecardLineList.add(Integer.toString(i));
        for (int i = 3; i < GameGUI.getDice_in_play(); i++)
            scorecardLineList.add(i + "C");
        if (GameGUI.getDice_in_play() >= 5) // else, FH is impossible
            scorecardLineList.add("FH");
        scorecardLineList.add("SS");
        scorecardLineList.add("LS");
        scorecardLineList.add("Y");
        scorecardLineList.add("C");
    }

    /**
     * this function overwrites any existing scorecard.txt file with
     * a file ready to start a new game
     */
    public static void createScorecard() {
        try {
            createLinesOfScorecard();
            BufferedWriter buffer = new BufferedWriter(new FileWriter("./scorecard.txt"));
            for (int i = 0; i < scorecardLineList.size(); i++) {
                buffer.write(String.format("%s\t\t\t\t%s", scorecardLineList.get(i), 0));
                buffer.write(System.getProperty("line.separator"));
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function reads the scorecard.txt file
     * stores score values in in a list named scorecardScoreList that aligns with scorecardLineList
     */
    public static void readScorecard() {
        File file = new File("./scorecard.txt");
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < scorecardLineList.size(); i ++) {
                String readLine = sc.next(); // reads column under 'line'
                String readScore = sc.next(); // reads column under 'score'
                try { // try's to set and replace element in array with new value
                    scorecardScoreList.set(i, Integer.parseInt(readScore));

                } catch (IndexOutOfBoundsException e) { // if element doesn't exist, add value to array
                    scorecardScoreList.add(Integer.parseInt(readScore));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
    }

    /**
     * this function overwrites any existing scorecard.txt file with
     * the contents of the scorecardList
     * @param lineCode is the code value for the line column in which the user wants to choose for the scorecard
     */
    public void writeScorecard(String lineCode) {
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter("./scorecard.txt"));
            int indexToUpdate = scorecardLineList.indexOf(lineCode);
            if (!usedScoreCardLines.contains(indexToUpdate))
                usedScoreCardLines.add(indexToUpdate);
            scorecardScoreList.set(indexToUpdate, possibleScoresList.get(indexToUpdate));
            calculateRunningScores();
            for (int i = 0; i < scorecardLineList.size(); i ++) {
                buffer.write(String.format("%s\t\t\t\t%s", scorecardLineList.get(i), scorecardScoreList.get(i)));
                buffer.write(System.getProperty("line.separator"));
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function returns a string rep a scorecard from the scorecardList
     */
    public static String displayScoreCard() {
        String scorecard = ("Line          Score\n");
        scorecard += ("-------------------\n");
        for (int i = 0; i < GameGUI.getSides_of_dice(); i ++)
            scorecard += (String.format("%s\t\t%s", scorecardLineList.get(i), scorecardScoreList.get((i))) + "\n");
        scorecard += ("-------------------\n");
        scorecard += (String.format("%s\t%s", "Sub Total", subTotal) + "\n");
        scorecard += (String.format("%s\t\t%s", "Bonus", bonus) + "\n");
        scorecard += ("-------------------\n");
        scorecard += (String.format("%s\t%s", "Upper Total", upperTotal) + "\n");
        scorecard += ("-------------------\n");
        for (int i = GameGUI.getSides_of_dice(); i < scorecardLineList.size(); i++)
            scorecard += (String.format("%s\t\t%s", scorecardLineList.get(i), scorecardScoreList.get((i))) + "\n");
        scorecard += ("-------------------\n");
        scorecard += (String.format("%s\t%s", "Lower Total", lowerTotal) + "\n");
        scorecard += ("-------------------\n");
        scorecard += (String.format("%s\t%s", "Grand Total", grandTotal) + "\n");

        return scorecard;
    }

    /**
     * displays scorecard options for user to choose from
     * based on the hand that the user has
     */
    public String displayScoreOptions() {
        possibleScoresList.clear();
       return upperScoreCard() + lowerScoreCard();
    }

    /**
     * calculates the running scores for the score card
     */
    public void calculateRunningScores() {
        // subTotal
        subTotal = 0;
        for (int i = 0; i < GameGUI.getSides_of_dice(); i++)
            subTotal += scorecardScoreList.get(i);
        // bonus
        if (subTotal > 63)
            bonus = 35;
        else
            bonus = 0;
        // upper total
        upperTotal = subTotal + bonus;
        // lower total
        lowerTotal = 0;
        for (int i = GameGUI.getSides_of_dice(); i < scorecardLineList.size(); i++)
            lowerTotal += scorecardScoreList.get(i);
        // grand total
        grandTotal = upperTotal + lowerTotal;
    }

    /**
     * represents the upper score card of the game Yahtzee
     * @return a String of the upperScoreCard possible options
     */
    private String upperScoreCard() {
        String upperScoreCard = "";
        for (int dieValue = 1; dieValue <= GameGUI.getSides_of_dice(); dieValue++) {
            int currentCount = 0;
            for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
                if (hand.get(diePosition) == dieValue)
                    currentCount++;
            }
            if (!usedScoreCardLines.contains(dieValue - 1))
                upperScoreCard +=  "Score " + dieValue * currentCount + "  on the " + dieValue + " line\n";
            possibleScoresList.add(dieValue * currentCount);
        }
        return upperScoreCard;
    };

    /**
     * represents the lower score card of the game Yahtzee
     * @return a String of the lowerScoreCard possible options
     */
    private String lowerScoreCard() {
        String lowerScoreCard = "";
        determineSmallAndLargeStraight();

        for (int i = 3; i < GameGUI.getDice_in_play(); i++) {
            if (maxOfAKindFound() >= i) {
                if (!usedScoreCardLines.contains(scorecardLineList.indexOf(i + "C")))
                    lowerScoreCard += "Score " + totalAllDice() + " on the " + i + "C line\n";
                possibleScoresList.add(totalAllDice());
            } else {
                if (!usedScoreCardLines.contains(scorecardLineList.indexOf(i + "C")))
                    lowerScoreCard += "Score 0  on the " + i + "C line\n";
                possibleScoresList.add(0);
            }
        }
        if (GameGUI.getDice_in_play() >= 5) {
            if (fullHouseFound()) {
                if (!usedScoreCardLines.contains(scorecardLineList.indexOf("FH")))
                    lowerScoreCard += "Score 25 on the FH line\n";
                possibleScoresList.add(25);
            } else {
                if (!usedScoreCardLines.contains(scorecardLineList.indexOf("FH")))
                    lowerScoreCard += "Score 0  on the FH line\n";
                possibleScoresList.add(0);
            }
        }

        if (maxStraightFound() >= smallStraight) {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("SS")))
                lowerScoreCard += "Score 30 on the SS line\n";
            possibleScoresList.add(30);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("SS")))
                lowerScoreCard += "Score 0  on the SS line\n";
            possibleScoresList.add(0);
        }

        if (maxStraightFound() >= largeStraight) {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("LS")))
                lowerScoreCard += "Score 40 on the LS line\n";
            possibleScoresList.add(40);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("LS")))
                lowerScoreCard += "Score 0  on the LS line\n";
            possibleScoresList.add(0);
        }

        if (maxOfAKindFound() >= GameGUI.getDice_in_play()) {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("Y")))
                lowerScoreCard += "Score 50 on the Y line\n";
            possibleScoresList.add(50);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("Y")))
                lowerScoreCard += "Score 0  on the Y line\n";
            possibleScoresList.add(0);
        }

        if (!usedScoreCardLines.contains(scorecardLineList.indexOf("C")))
            lowerScoreCard += "Score " + totalAllDice() + " on the C line\n";
        possibleScoresList.add(totalAllDice());

        return lowerScoreCard;
    }

    /**
     * totals the dice in the hand
     * @return the total of dice in hand
     */
    private int totalAllDice() {
        int total = 0;
        for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++)
        {
            total += hand.get(diePosition);
        }
        return total;
    }

    /**
     * determines the max number of a kind is found in a hand
     * @return maxCount - the maximum number of repeated numbers in hand
     */
    private int maxOfAKindFound() {
        int maxCount = 0;
        int currentCount;
        for (int dieValue = 1; dieValue <= GameGUI.getSides_of_dice(); dieValue++) {
            currentCount = 0;
            for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
                if (hand.get(diePosition) == dieValue)
                    currentCount++;
            }
            if (currentCount > maxCount)
                maxCount = currentCount;
        }
        return maxCount;
    }

    /**
     * determines the max number of a straight that is found in hand
     * @return maxLength  - the maximum length of a straight that is found in hand
     */
    private int maxStraightFound() {
        int maxLength = 1;
        int curLength = 1;
        for(int counter = 0; counter < GameGUI.getDice_in_play() - 1; counter++) {
            if (hand.get(counter) + 1 == hand.get(counter + 1))
                curLength++;
            else if (hand.get(counter) + 1 < hand.get(counter + 1))
                curLength = 1;
            if (curLength > maxLength)
                maxLength = curLength;
        }
        return maxLength;
    }

    /**
     * determines if there is a full house in the hand
     * @return true if full house present, false otherwise
     */
    private boolean fullHouseFound() {
        boolean foundFH = false;
        boolean found3K = false;
        boolean found2K = false;
        int currentCount ;

        for (int dieValue = 1; dieValue <= GameGUI.getSides_of_dice(); dieValue++) {
            currentCount = 0;
            for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
                if (hand.get(diePosition) == dieValue)
                    currentCount++;
            }
            if (currentCount == 2)
                found2K = true;
            if (currentCount == 3)
                found3K = true;
        }
        if (found2K && found3K)
            foundFH = true;
        return foundFH;
    }

    /**
     * determines what numbers should be included in the small straight and large straight
     */
    private void determineSmallAndLargeStraight() {
        smallStraight = GameGUI.getSides_of_dice() - 2;
        largeStraight = GameGUI.getSides_of_dice() - 1;
    }

    /**
     * retrieves scorecardLineList
     * @return scorecardLineList which is the running list of the scorecard line
     */
    public static ArrayList<String> getScorecardLineList() {
        return scorecardLineList;
    }

    /**
     * determines if the line code from user is valid
     * @param userInputLineCode line code user entered
     * @return true if valid, false otherwise
     */
    public static boolean validateLineCode(String userInputLineCode) {
        int indexOfLineCode = scorecardLineList.indexOf(userInputLineCode);
        System.out.println("INDEX IS:    ::  "  + indexOfLineCode);
        System.out.println("used " + usedScoreCardLines);
        if (usedScoreCardLines.contains(indexOfLineCode)) {
            return false;
        } else
            return true;
    }
}
