package cs3500.pa01;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a displayer for a question-answer game.
 */
public class Displayer {
  private Scanner userInput = new Scanner(System.in);
  private int totalSessionQuestions = 0;
  private int questionCounter = 1;
  private String srcPath;

  /**
   * Retrieves the source path.
   *
   * @return the source path
   */
  public String getSrcPath() {
    return srcPath;
  }

  /**
   * Retrieves the question counter.
   *
   * @return the question counter
   */
  public int getQuestionCounter() {
    return questionCounter;
  }

  /**
   * Updates the current question counter.
   */
  public void updateCurrentCounter() {
    questionCounter++;
  }

  /**
   * Displays the welcome screen and prompts the user for the number of questions.
   *
   * @return the number of questions
   */
  public int welcomeScreen() {
    System.out.println(
        ""
            + "\n*****************************************"
            + "\n"
            + "\n"
            + "Welcome to Anki Lite!"
            + "\n"
            + "\n*****************************************\n"
            + "\n"
            + "INSTRUCTIONS: "
            + "\n"
            + "\n"
            + "The letter inside [ ] is the key to enter to go to the next menu."
            + "\n"
            + "\nFor example: In Next [Q]uestion, you must press Q to go to the"
            + "\n             next question since it is inside the square brackets [ ]."
            + "\n"
            + "\n"
            + "*****************************************"
            + "\n"
            + "\n"
            + "Please enter the path to your .sr file"
            + "\n"
            + "\n"
            + "*****************************************");

    System.out.println("\nPath: \n");
    srcPath = Path.of(userInput.nextLine()).toAbsolutePath().toString();
    System.out.println(
        ""
            + "\n*****************************************\n"
            + "\n"
            + "Path entered: " + srcPath
            + "\n"
            + "\n"
            + "Enter the number of question you'd like to practice!"
            + "\n"
            + "\n"
            + "*****************************************");

    System.out.println("\nNumber of Questions: \n");
    int numberOfQuestion;
    numberOfQuestion = userInput.nextInt();
    return numberOfQuestion;
  }

  /**
   * Displays the question screen and prompts the user for input.
   *
   * @param question the question to be displayed
   * @return the user input
   */
  public char questionScreen(Question question) {
    System.out.println(
        ""
            + "*****************************************\n"
            + "\n"
            + "Question " + questionCounter + " of " + totalSessionQuestions
            + "\t\t||\t\tDifficulty: "
            + question.getDifficulty()
            + "\n"
            + "\n"
            + "*****************************************\n"
            + "\n"
            + "\n"
            + "Question: "
            + question.getQuestion()
            + "\n"
            + "\n"
            + "\n"
            + "*****************************************\n"
            + "\n"
            + "Mark as [E]asy \t Mark as [H]ard \t Show [A]nswer \t Next [Q]uestion"
            + "\n"
            + "\n"
            + "*****************************************\n"
    );


    char input = userInput.next().toUpperCase().charAt(0);
    return input;
  }

  /**
   * Displays the answer screen.
   *
   * @param question the question to display the answer for
   */
  public void answerScreen(Question question) {
    System.out.println(
        ""
            + "*****************************************\n"
            + "\n"
            + "Question " + questionCounter + " of " + totalSessionQuestions
            + "\t\t||\t\tDifficulty: "
            + question.getDifficulty()
            + "\n"
            + "\n"
            + "*****************************************\n"
            + "\n"
            + "\n"
            + "Question: "
            + question.getQuestion()
            + "\n"
            + "\n"
            + "\n"
            + "Answer: "
            + question.getAnswer()
            + "\n"
            + "\n"
            + "\n"
            + "*****************************************\n"
            + "\n"
            + "Mark as [E]asy \t Mark as [H]ard \t Show [A]nswer \t Next [Q]uestion"
            + "\n"
            + "\n"
            + "*****************************************\n"
    );

  }

  /**
   * Displays the invalid input screen.
   */
  public void invalidInputScreen() {
    System.out.println(
        ""

            + "*****************************************\n"
            + "\n"
            + "The key you entered in not a valid response."
            + "\n"
            + "\n"
            + "Remember the keys to press are the characters inside [key]"
            + "\n"
            + "\n"
            + "*****************************************\n"
            + "\n"
            + "Mark as [E]asy \t Mark as [H]ard \t Show [A]nswer \t Next [Q]uestion"
            + "\n"
            + "\n"
            + "*****************************************\n"
    );
  }

  /**
   * Displays the end screen.
   */
  public void endScreen() {
    ArrayList<Integer> stats;
    stats = Statistics.calculateStats();
    int totalNumOfQuestion = stats.get(2) + stats.get(3);
    int hardToEasy = stats.get(0);
    int easyToHard = stats.get(1);
    int totalHardQs = stats.get(2);
    int totalEasyQs = stats.get(3);
    System.out.println(
        ""
            + "*****************************************\n"
            + "\n"
            + "Good job on completing this study session!"
            + "\n"
            + "\n"
            + "*****************************************\n"
            + "\n"
            + hardToEasy + " Questions went from Hard to Easy"
            + "\n"
            + "\n"
            + easyToHard + " Questions went from Easy to Hard"
            + "\n"
            + "\n"
            + "There's " + totalHardQs + " questions in the Hard Question Bank out of a total of "
            + totalNumOfQuestion + " questions."
            + "\n"
            + "\n"
            + "There's " + totalEasyQs + " questions in the Easy Question Bank out of a total of "
            + totalNumOfQuestion + " questions."
            + "\n"
            + "\n*****************************************"
    );
  }

  /**
   * Gets the totalSessionQuestions.
   *
   * @return the total number of session questions.
   */
  public int getTotalSessionQuestions() {
    return totalSessionQuestions;
  }


  /**
   * Sets the totalSessionQuestions field.
   *
   * @param size the size of the session question bank.
   */
  public void setTotalSessionQuestions(int size) {
    this.totalSessionQuestions = size;
  }
}
