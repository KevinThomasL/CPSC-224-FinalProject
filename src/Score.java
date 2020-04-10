import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This program represents the score card for the game of Rainbow Yahtzee.
 * CPSC 224-01, Spring 2020
 * Programming Assignment Project
 * sources to cite:
 *
 * @author Nicole, Kevin, Eric, Jackson
 */

public class Score {

    private ArrayList<String> colorsList = new ArrayList<String>(Arrays.asList("R", "O", "Y", "G", "B", "I", "V")); // holds values of all colors in order
    private ArrayList<Integer> possibleScoresList = new ArrayList<>(); // keeps running values of possible scores
    private ArrayList<Integer> scorecardScoreList = new ArrayList<>(); // array for running scores of scorecard
    private ArrayList<Integer> usedScoreCardLines = new ArrayList<>(); // running list of used card lines
    private ArrayList<String> scorecardLineList = new ArrayList<>(); // array for line names of scorecard
    private ArrayList<Integer> hand;
    // values for the scorecard
    private Integer subTotal = 0;
    private Integer bonus = 0;
    private Integer upperTotal = 0;
    private Integer lowerTotal = 0;
    private Integer grandTotal = 0;
    // values for number of lower scorecard options scored
    private int foundNumPrimaries = 0;
    private int foundNumSecondaries = 0;
    private int foundNumWarm = 0;
    private int foundNumCold = 0;

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
        System.out.println("Create lines of scorecard");
        for (int i = 1; i <= GameGUI.getSides_of_dice(); i++)
            scorecardLineList.add(colorsList.get(i-1));
//            scorecardLineList.add(Integer.toString(i));
        for (int i = 3; i < GameGUI.getDice_in_play(); i++)
            scorecardLineList.add(i + "C");
        scorecardLineList.add("P");  scorecardLineList.add("S"); scorecardLineList.add("WP");
        scorecardLineList.add("CP"); scorecardLineList.add("C"); scorecardLineList.add("R");
        System.out.println("Size of the scorecard line list after creating: " + scorecardLineList.size());
        for (int i = 0; i < scorecardLineList.size(); i++) scorecardScoreList.add(Integer.valueOf(0));
    }

    /**
     * creates the lines column of the scorecard
     */
    public void createLinesOfScorecard() {
        System.out.println("Create lines of scorecard");
        for (int i = 1; i <= GameGUI.getSides_of_dice(); i++)
            scorecardLineList.add(colorsList.get(i-1));
//            scorecardLineList.add(Integer.toString(i));
        for (int i = 3; i < GameGUI.getDice_in_play(); i++)
            scorecardLineList.add(i + "C");
        scorecardLineList.add("P");  scorecardLineList.add("S"); scorecardLineList.add("WP");
        scorecardLineList.add("CP"); scorecardLineList.add("C"); scorecardLineList.add("R");
        System.out.println("Size of the scorecard line list after creating: " + scorecardLineList.size());
    }

    /**
     * this function overwrites any existing scorecard.txt file with
     * a file ready to start a new game
     */
    public void createScorecard(int player_num) {
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter("./scorecard" + (player_num+1) + ".txt"));
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
    public void readScorecard(int player_num) {
        File file = new File("./scorecard" + (player_num+1) + ".txt");
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < scorecardLineList.size(); i ++) {
                String readLine = sc.next(); // reads column under 'line'
                String readScore = sc.next(); // reads column under 'score'
                try { // tries to set and replace element in array with new value
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
    public void writeScorecard(String lineCode, int player_num) {
        try {
            BufferedWriter buffer = new BufferedWriter(new FileWriter("./scorecard" + (player_num+1) + ".txt"));
            int indexToUpdate = scorecardLineList.indexOf(lineCode);
            if (!usedScoreCardLines.contains(indexToUpdate))
                usedScoreCardLines.add(indexToUpdate);
            System.out.println("# of scored lines: " + usedScoreCardLines.size());
            System.out.println("Index to update: " + indexToUpdate);
            System.out.println("Size of scorecardScoreList: " + scorecardScoreList.size());
            scorecardScoreList.set(indexToUpdate, possibleScoresList.get(indexToUpdate));
            calculateRunningScores();
            for (int i = 0; i < scorecardLineList.size(); i++) {
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
    public String displayScoreCard() {
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
        if (subTotal > 84)
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
                upperScoreCard +=  "Score " + dieValue * currentCount + "\ton the " + colorsList.get(dieValue - 1) + " line\n";
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

        if (primaryFound()) {
            int scoreValue = 25 * foundNumPrimaries;
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("P")))
                lowerScoreCard += "Score " + scoreValue + " on the P line\n";
            possibleScoresList.add(scoreValue);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("P")))
                lowerScoreCard += "Score 0  on the P line\n";
            possibleScoresList.add(0);
        }

        if (secondaryFound()) {
            int scoreValue = 25 * foundNumSecondaries;
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("S")))
                lowerScoreCard += "Score "+ scoreValue + " on the S line\n";
            possibleScoresList.add(scoreValue);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("S")))
                lowerScoreCard += "Score 0  on the S line\n";
            possibleScoresList.add(0);
        }

        if (warmPalletFound()) {
            int scoreValue = 30 * foundNumWarm;
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("WP")))
                lowerScoreCard += "Score " + scoreValue + " on the WP line\n";
            possibleScoresList.add(scoreValue);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("WP")))
                lowerScoreCard += "Score 0  on the WP line\n";
            possibleScoresList.add(0);
        }

        if (coldPalletFound()) {
            int scoreValue = 40 * foundNumCold;
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("CP")))
                lowerScoreCard += "Score " + scoreValue + " on the CP line\n";
            possibleScoresList.add(scoreValue);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("CP")))
                lowerScoreCard += "Score 0  on the CP line\n";
            possibleScoresList.add(0);
        }

        if (!usedScoreCardLines.contains(scorecardLineList.indexOf("C")))
            lowerScoreCard += "Score " + totalAllDice() + " on the C line\n";
        possibleScoresList.add(totalAllDice());

        if (rainbowFound()) {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("R")))
                lowerScoreCard += "Score 80 on the R line\n";
            possibleScoresList.add(80);
        } else {
            if (!usedScoreCardLines.contains(scorecardLineList.indexOf("R")))
                lowerScoreCard += "Score 0  on the R line\n";
            possibleScoresList.add(0);
        }

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
     * determines if there is a primary in the hand
     * @return true if primary (yellow, red, and blue) (dice 3,1,5) present, false otherwise
     */
    private boolean primaryFound() {
        boolean foundPrimary = false;
        boolean isYellow = false;
        int yellowCount = 0;
        boolean isRed = false;
        int redCount = 0;
        boolean isBlue = false;
        int blueCount = 0;

        for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
            if (hand.get(diePosition) == 3) {
                isYellow = true;
                yellowCount += 1;
            }
            if (hand.get(diePosition) == 1) {
                isRed = true;
                redCount += 1;
            }
            if (hand.get(diePosition) == 5) {
                isBlue = true;
                blueCount += 1;
            }
        }

        if(isYellow && isRed && isBlue)
            foundPrimary = true;

        // determines if there are two or three primaries
        if(yellowCount == 2 && redCount == 2 && blueCount == 2)
            foundNumPrimaries = 2;
        else if(yellowCount == 3 && redCount == 3 && blueCount == 3)
            foundNumPrimaries = 3;
        else
            foundNumPrimaries = 1;

        return foundPrimary;
    }

    /**
     * determines if there is a secondary in the hand
     * @return true if secondary (green, orange, and violet) (dice 4,2,7) present, false otherwise
     */
    private boolean secondaryFound() {
        boolean foundSecondary = false;
        boolean isGreen = false;
        int greenCount = 0;
        boolean isOrange = false;
        int orangeCount = 0;
        boolean isViolet = false;
        int violetCount = 0;

        for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
            if (hand.get(diePosition) == 4) {
                isGreen = true;
                greenCount += 1;
            }
            if (hand.get(diePosition) == 2) {
                isOrange = true;
                orangeCount += 1;
            }
            if (hand.get(diePosition) == 7) {
                isViolet = true;
                violetCount += 1;
            }
        }

        if(isGreen && isOrange && isViolet)
            foundSecondary = true;

        // determines if there are two or three secondaries
        if(greenCount == 2 && orangeCount == 2 && violetCount == 2)
            foundNumSecondaries = 2;
        else if(greenCount == 3 && orangeCount == 3 && violetCount == 3)
            foundNumSecondaries = 3;
        else
            foundNumSecondaries = 1;

        return foundSecondary;
    }

    /**
     * determines if there is a warm pallet in the hand
     * @return true if secondary (red, orange, and yellow) (dice 1,2,3) present, false otherwise
     */
    private boolean warmPalletFound() {
        boolean foundWarmPallet = false;
        boolean isRed = false;
        int redCount = 0;
        boolean isOrange = false;
        int orangeCount = 0;
        boolean isYellow = false;
        int yellowCount = 0;

        for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
            if (hand.get(diePosition) == 1) {
                isRed = true;
                redCount += 1;
            }
            if (hand.get(diePosition) == 2) {
                isOrange = true;
                orangeCount += 1;
            }
            if (hand.get(diePosition) == 3) {
                isYellow = true;
                yellowCount += 1;
            }
        }

        if(isRed && isOrange && isYellow)
            foundWarmPallet = true;

        // determines if there are two or three warmPallets
        if(redCount == 2 && orangeCount == 2 && yellowCount == 2)
            foundNumWarm = 2;
        else if(redCount == 3 && orangeCount == 3 && yellowCount == 3)
            foundNumWarm = 3;
        else
            foundNumWarm = 1;

        return foundWarmPallet;
    }

    /**
     * determines if there is a cold pallet in the hand
     * @return true if cold pallet (green, blue, indigo, and violet) present, false otherwise
     */
    private boolean coldPalletFound() {
        boolean foundColdPallet = false;
        boolean isGreen = false;
        int greenCount = 0;
        boolean isBlue = false;
        int blueCount = 0;
        boolean isIndigo = false;
        int indigoCount = 0;
        boolean isViolet = false;
        int violetCount = 0;

        for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
            if (hand.get(diePosition) == 4) {
                isGreen = true;
                greenCount += 1;
            }
            if (hand.get(diePosition) == 5) {
                isBlue = true;
                blueCount += 1;
            }
            if (hand.get(diePosition) == 6) {
                isIndigo = true;
                indigoCount += 1;
            }
            if (hand.get(diePosition) == 7) {
                isViolet = true;
                violetCount += 1;
            }
        }

        if(isGreen && isBlue && isIndigo && isViolet)
            foundColdPallet = true;

        if(greenCount == 2 && blueCount == 2 && indigoCount == 2 && violetCount == 2)
            foundNumCold = 2;
        else
            foundNumCold = 1;

        return foundColdPallet;
    }

    /**
     * determines if there is a rainbow in the hand
     * @return true if rainbow present, false otherwise
     */
    private boolean rainbowFound() {
        boolean foundRainbow = false;
        boolean isRed = false;
        boolean isOrange = false;
        boolean isYellow = false;
        boolean isGreen = false;
        boolean isBlue = false;
        boolean isIndigo = false;
        boolean isViolet = false;

        for (int diePosition = 0; diePosition < GameGUI.getDice_in_play(); diePosition++) {
            if (hand.get(diePosition) == 1)
                isRed = true;
            if (hand.get(diePosition) == 2)
                isOrange = true;
            if (hand.get(diePosition) == 3)
                isYellow = true;
            if (hand.get(diePosition) == 4)
                isGreen = true;
            if (hand.get(diePosition) == 5)
                isBlue = true;
            if (hand.get(diePosition) == 6)
                isIndigo = true;
            if (hand.get(diePosition) == 7)
                isViolet = true;
        }

        if(isRed && isOrange && isYellow && isGreen && isBlue && isIndigo && isViolet)
            foundRainbow = true;

        return foundRainbow;
    }

    /**
     * retrieves scorecardLineList
     * @return scorecardLineList which is the running list of the scorecard line
     */
    public ArrayList<String> getScorecardLineList() {
        return scorecardLineList;
    }

    public ArrayList<Integer> getUsedScoreCardLines() {
        return usedScoreCardLines;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }
}
