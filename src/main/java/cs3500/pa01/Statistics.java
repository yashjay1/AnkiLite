package cs3500.pa01;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Statistics class keeps track of statistics related to questions.
 */
public class Statistics {
  private static int hardToEasy = 0;
  private static int easyToHard = 0;
  private static int numOfHardQuestion = 0;
  private static int numOfEasyQuestion = 0;

  /**
   * Groups all the statistics together to return in the form of a list.
   *
   * @return a list of integers that contain stats on question that
   *     have gone from hard to easy, easy to hard, size of the hard question bank,
   *     and the size of the easy question bank
   */
  public static ArrayList<Integer> calculateStats() {
    ArrayList<Integer> allStats = new ArrayList<>(
        Arrays.asList(hardToEasy, easyToHard, numOfHardQuestion, numOfEasyQuestion));
    return allStats;
  }

  /**
   * Updates the counter for hard to easy questions
   */
  public static void updateHardToEasy() {
    hardToEasy++;
  }

  /**
   * Updates the counter for easy to hard questions
   */
  public static void updateEasyToHard() {
    easyToHard++;
  }

  /**
   * Sets the number of hard questions in the hard question bank.
   *
   * @param numOfHardQuestion The size of the hard question bank.
   */
  public static void setNumOfHardQuestion(int numOfHardQuestion) {
    Statistics.numOfHardQuestion = numOfHardQuestion;
  }

  /**
   * Sets the number of easy questions in the easy question bank.
   *
   * @param numOfEasyQuestion The size of the easy question bank.
   */
  public static void setNumOfEasyQuestion(int numOfEasyQuestion) {
    Statistics.numOfEasyQuestion = numOfEasyQuestion;
  }

}
