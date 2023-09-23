package cs3500.pa01;

/**
 * Represents a question.
 */
public class Question {

  private String question;
  private String answer;
  private String difficulty;

  /**
   * This constructor is used to initialize the fields to represent a question.
   *
   * @param question   Represents the question
   * @param answer     Represents the answer to this question
   * @param difficulty Represents the difficulty of this question.
   */
  Question(String question, String answer, String difficulty) {
    this.question = question;
    this.answer = answer;
    this.difficulty = difficulty;
  }

  /**
   * Returns the question.
   *
   * @return Returns the question.
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Returns the answer.
   *
   * @return Returns the answer.
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Returns the difficulty.
   *
   * @return Returns the difficulty.
   */
  public String getDifficulty() {
    return difficulty;
  }

  /**
   * Checks if this question is equal to the specified question.
   *
   * @param that the question to compare
   * @return true if the questions are equal, false otherwise
   */
  public boolean equals(Question that) {
    return (this.question.equals(that.getQuestion()))
        && (this.answer.equals(that.getAnswer()))
        && (this.difficulty.equals(that.getDifficulty()));
  }

  /**
   * Updates the difficulty of the question it is called on.
   *
   * @param difficulty the difficulty the question should change to.
   * @param filePath   the filepath of the database of where the question is stored.
   */
  public void updateDifficulty(String difficulty, String filePath) {
    if (difficulty.equals(Difficulty.Easy.toString())) {
      if (this.difficulty.equals(Difficulty.Hard.toString())) {
        this.difficulty = Difficulty.Easy.toString();
        MarkdownProcessor.updateText(filePath, this.getQuestion(), "~~~System:Marked-Hard~~~",
            "~~~System:Marked-Easy~~~");
      }
    } else if (difficulty.equals(Difficulty.Hard.toString())) {
      if (this.difficulty.equals(Difficulty.Easy.toString())) {
        this.difficulty = Difficulty.Hard.toString();
        MarkdownProcessor.updateText(filePath, this.getQuestion(), "~~~System:Marked-Easy~~~",
            "~~~System:Marked-Hard~~~");
      }
    }
  }
}
