package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionTest {
  private Question bostonCapital;
  private Question delhiCapital;
  private Question delhiCapitalWrongAns;
  private Question delhiCapitalWrongDifficulty;
  private Question indianSiliconValley;
  private List<String> lines;
  private Path qna;

  /**
   * initializes the variables for testing before each test.
   */
  @BeforeEach
  void setUp() throws IOException {
    bostonCapital = new Question("What is the capital city of Massachusetts?", "Boston", "Easy");
    delhiCapital = new Question("What is the capital India?", "New Delhi", "Hard");
    delhiCapitalWrongAns = new Question("What is the capital India?", "Old Delhi", "Hard");
    delhiCapitalWrongDifficulty = new Question("What is the capital India?", "New Delhi", "Easy");
    indianSiliconValley = new Question("What is the Silicon Valley of India?", "Bangalore", "Hard");
    qna = Path.of("testQuestions/QnA.sr");
    lines = Files.readAllLines(Path.of("testQuestions/QnA.sr"));
  }

  /**
   * Tests if the method returns the question of the object.
   */
  @Test
  void getQuestion() {
    assertEquals("What is the capital India?", delhiCapital.getQuestion());
    assertEquals("What is the capital city of Massachusetts?", bostonCapital.getQuestion());
  }

  /**
   * Tests if the method returns the answer of the object.
   */
  @Test
  void getAnswer() {
    assertEquals("New Delhi", delhiCapital.getAnswer());
    assertEquals("Boston", bostonCapital.getAnswer());
  }

  /**
   * Tests if the method returns the difficulty of the object.
   */
  @Test
  void getDifficulty() {
    assertEquals("Easy", bostonCapital.getDifficulty());
    assertEquals("Hard", delhiCapital.getDifficulty());
  }

  /**
   * Tests if two Question objects are equal.
   */
  @Test
  void testEquals() {
    assertTrue(bostonCapital.equals(bostonCapital));
    assertTrue(indianSiliconValley.equals(indianSiliconValley));
    assertFalse(bostonCapital.equals(indianSiliconValley));
    assertFalse(indianSiliconValley.equals(bostonCapital));
    assertFalse(delhiCapital.equals(bostonCapital));
    assertFalse(bostonCapital.equals(delhiCapital));

    assertFalse(delhiCapital.equals(delhiCapitalWrongAns));
    assertFalse(delhiCapital.equals(delhiCapitalWrongDifficulty));
  }

  /**
   * Tests to see if the difficulty is updated.
   */
  @Test
  void updateDifficulty() throws IOException {

    // For Hard to Easy
    assertEquals("Hard", delhiCapital.getDifficulty());
    assertTrue(lines.get(1).contains("~~~System:Marked-Hard~~~"));
    assertFalse(lines.get(1).contains("~~~System:Marked-Easy~~~"));
    delhiCapital.updateDifficulty("Easy", qna.toString());
    assertEquals("Easy", delhiCapital.getDifficulty());

    // For Staying at Easy
    assertEquals("Easy", bostonCapital.getDifficulty());
    assertTrue(lines.get(0).contains("~~~System:Marked-Easy~~~"));
    assertFalse(lines.get(0).contains("~~~System:Marked-Hard~~~"));
    bostonCapital.updateDifficulty("Easy", qna.toString());
    assertEquals("Easy", bostonCapital.getDifficulty());

    // For Easy to Hard
    assertEquals("Easy", bostonCapital.getDifficulty());
    assertTrue(lines.get(0).contains("~~~System:Marked-Easy~~~"));
    assertFalse(lines.get(0).contains("~~~System:Marked-Hard~~~"));
    bostonCapital.updateDifficulty("Hard", qna.toString());
    assertEquals("Hard", bostonCapital.getDifficulty());

    // Staying at Hard difficulty
    assertEquals("Hard", indianSiliconValley.getDifficulty());
    assertTrue(lines.get(2).contains("~~~System:Marked-Hard~~~"));
    assertFalse(lines.get(2).contains("~~~System:Marked-Easy~~~"));
    indianSiliconValley.updateDifficulty("Hard", qna.toString());
    assertEquals("Hard", indianSiliconValley.getDifficulty());

    // Reading the newly modified file.
    List<String> newLines = Files.readAllLines(Path.of("testQuestions/QnA.sr"));

    // For Hard to Easy
    assertTrue(newLines.get(1).contains("~~~System:Marked-Easy~~~"));
    assertFalse(newLines.get(1).contains("~~~System:Marked-Hard~~~"));

    // For Easy to Hard
    assertTrue(newLines.get(0).contains("~~~System:Marked-Hard~~~"));
    assertFalse(newLines.get(0).contains("~~~System:Marked-Easy~~~"));

    // Restoring the file back to the original state.
    Files.deleteIfExists(qna);
    Files.createFile(qna);
    BufferedWriter writer = new BufferedWriter(new FileWriter(qna.toString()));
    for (String line : lines) {
      writer.write(line);
      writer.newLine();

    }
    writer.close();
  }
}