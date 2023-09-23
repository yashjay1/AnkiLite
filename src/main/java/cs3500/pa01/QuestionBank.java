package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents a question bank that holds a collection of questions.
 */
public class QuestionBank {

  private String databasePath;
  private ArrayList<Question> hardQuestions = new ArrayList<Question>();
  private ArrayList<Question> easyQuestions = new ArrayList<Question>();
  private ArrayList<Question> allQuestions = new ArrayList<>();

  /**
   * Constructs a question bank.
   *
   * @param databasePath the path to the database file
   */
  public QuestionBank(String databasePath) {
    this.databasePath = databasePath;
  }


  /**
   * Adds a question to the question bank.
   *
   * @param question the question to add
   */
  public void add(Question question) {
    if (question.getDifficulty().equals(Difficulty.Hard.toString())) {
      hardQuestions.add(question);
    } else {
      easyQuestions.add(question);
    }
  }


  /**
   * Returns the number of hard questions in the question bank.
   *
   * @return the number of hard questions
   */
  public int getHardQuestionsSize() {
    return hardQuestions.size();
  }

  /**
   * Returns the number of easy questions in the question bank.
   *
   * @return the number of easy questions
   */
  public int getEasyQuestionsSize() {
    return easyQuestions.size();
  }


  /**
   * Retrieves a list of random questions from the question bank.
   *
   * @param numberOfRandomQuestions the number of random questions to retrieve
   * @return a list of random questions
   */
  public ArrayList<Question> randomQuestions(int numberOfRandomQuestions) {
    ArrayList<Question> selected = new ArrayList<>();

    int totalQuestions = hardQuestions.size() + easyQuestions.size();

    if (numberOfRandomQuestions >= totalQuestions) {
      selected.addAll(hardQuestions);
      selected.addAll(easyQuestions);
      Collections.shuffle(selected);
    } else {
      int remainingQuestions = numberOfRandomQuestions;

      if (!hardQuestions.isEmpty()) {
        int hardQuestionCount = Math.min(hardQuestions.size(), remainingQuestions);
        ArrayList<Question> hardSelected = randomHardQuestions(hardQuestionCount);
        selected.addAll(hardSelected);
        remainingQuestions -= hardQuestionCount;
      }

      if (remainingQuestions > 0) {
        ArrayList<Question> easySelected = randomEasyQuestions(remainingQuestions);
        selected.addAll(easySelected);
      }
    }
    Collections.shuffle(selected);
    return selected;
  }


  /**
   * Retrieves a list of random hard questions from the hard question bank.
   *
   * @param numberOfRandomQuestions the number of random questions to retrieve
   * @return a list of random hard questions
   */
  private ArrayList<Question> randomHardQuestions(int numberOfRandomQuestions) {
    Random random = new Random();
    ArrayList<Question> selected = new ArrayList<>();

    int totalQuestions = hardQuestions.size();

    for (int i = 0; i < numberOfRandomQuestions; i++) {
      int randomIndex = random.nextInt(totalQuestions - i);
      Question randomQuestion = hardQuestions.get(randomIndex);

      selected.add(randomQuestion);
      hardQuestions.remove(randomIndex);
    }

    return selected;
  }

  /**
   * Retrieves a list of random easy questions from the easy question bank.
   *
   * @param numberOfRandomQuestions the number of random questions to retrieve
   * @return a list of random easy questions
   */
  private ArrayList<Question> randomEasyQuestions(int numberOfRandomQuestions) {
    Random random = new Random();
    ArrayList<Question> selected = new ArrayList<>();

    int totalQuestions = easyQuestions.size();

    for (int i = 0; i < numberOfRandomQuestions; i++) {
      int randomIndex = random.nextInt(totalQuestions - i);
      Question randomQuestion = easyQuestions.get(randomIndex);

      selected.add(randomQuestion);
      easyQuestions.remove(randomIndex);
    }

    return selected;
  }


  /**
   * Loads questions from a database file into the question bank.
   *
   * @throws IOException if an I/O error occurs while reading the file
   */
  void loadQuestions() throws IOException {
    List<String> contents = Files.readAllLines(Path.of(this.databasePath));
    for (String line : contents) {
      int separatorIndex = line.indexOf(":::");
      String question = line.substring(25, separatorIndex);
      String answer = line.substring(separatorIndex + 3);
      String difficulty = line.substring(17, 21);
      Question tempQuestion = new Question(question, answer, difficulty);
      this.add(tempQuestion);
    }
    allQuestions.addAll(hardQuestions);
    allQuestions.addAll(easyQuestions);
  }


  /**
   * Refreshes the question bank by clearing the existing
   * questions and reloading from the database file.
   *
   * @throws IOException if an I/O error occurs while reading the file
   */
  void refresh() throws IOException {
    this.hardQuestions = new ArrayList<>();
    this.easyQuestions = new ArrayList<>();
    this.allQuestions = new ArrayList<>();
    loadQuestions();
  }

  /**
   * Updates the difficulty of a specific question in the question bank.
   *
   * @param question   the question to update
   * @param difficulty the new difficulty level
   * @param filePath   the file path of the question
   */
  public void updateDifficulty(Question question, String difficulty, String filePath) {
    for (Question currentQuestion : this.allQuestions) {
      if (question.equals(currentQuestion)) {
        currentQuestion.updateDifficulty(difficulty, filePath);
      }
    }
  }
}
