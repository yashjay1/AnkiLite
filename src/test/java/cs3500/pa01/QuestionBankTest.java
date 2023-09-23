package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionBankTest {

  private Path bank1Path = Path.of("testQuestions/Bank1.sr");
  private Path bank2Path = Path.of("testQuestions/Bank2.sr");
  QuestionBank bank1 = new QuestionBank(bank1Path.toString());
  QuestionBank bank2 = new QuestionBank(bank2Path.toString());
  List<String> lines;
  List<String> lines2;


  @BeforeEach
  void setUp() throws IOException {
    bank1.loadQuestions();
    lines = Files.readAllLines(Path.of("testQuestions/Bank1.sr"));
    lines2 = Files.readAllLines(Path.of("testQuestions/Bank2.sr"));
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(bank1Path);
    Files.createFile(bank1Path);
    BufferedWriter writer = new BufferedWriter(new FileWriter(bank1Path.toString()));
    for (String line : lines) {
      writer.write(line);
      writer.newLine();
    }
    writer.close();


    Files.deleteIfExists(bank2Path);
    Files.createFile(bank2Path);
    BufferedWriter writer2 = new BufferedWriter(new FileWriter(bank2Path.toString()));
    for (String line2 : lines2) {
      writer2.write(line2);
      writer2.newLine();
    }
    writer2.close();
  }

  @Test
  void add() {
    assertEquals(2, bank1.getHardQuestionsSize());
    bank1.add(new Question("National Bird of India?", "Peacock", "Hard"));
    assertEquals(3, bank1.getHardQuestionsSize());
    assertEquals(3, bank1.getEasyQuestionsSize());
    bank1.add(new Question("What is the biggest continent in the world?", "Asia", "Easy"));
    assertEquals(4, bank1.getEasyQuestionsSize());
  }

  @Test
  void getHardQuestionsSize() {
    assertEquals(2, bank1.getHardQuestionsSize());
  }

  @Test
  void getEasyQuestionsSize() {
    assertEquals(3, bank1.getEasyQuestionsSize());
  }

  @Test
  void randomQuestions() {
    ArrayList<Question> list1 = bank1.randomQuestions(2);
    ArrayList<Question> list2 = bank1.randomQuestions(2);
    ArrayList<Question> list3 = bank1.randomQuestions(9);
    ArrayList<Question> list4 = bank1.randomQuestions(9);
    assertFalse(list1.equals(list2));
    assertTrue(list3.equals(list4));
  }

  @Test
  void loadQuestions() throws IOException {
    assertEquals(0, bank2.getEasyQuestionsSize());
    assertEquals(0, bank2.getHardQuestionsSize());
    bank2.loadQuestions();
    assertEquals(3, bank2.getEasyQuestionsSize());
    assertEquals(2, bank2.getHardQuestionsSize());
  }

  @Test
  void refresh() throws IOException {
    bank2.loadQuestions();
    assertEquals(3, bank2.getEasyQuestionsSize());
    assertEquals(2, bank2.getHardQuestionsSize());
    BufferedWriter writer = new BufferedWriter(new FileWriter(bank2Path.toString()));
    for (String line2 : lines2) {
      writer.write(line2);
      writer.newLine();
    }
    writer.write(
        "~~~System:Marked-Hard~~~ What is the capital of Canada?:::The capital is Ottawa.");
    writer.close();
    bank2.refresh();
    assertEquals(3, bank2.getEasyQuestionsSize());
    assertEquals(3, bank2.getHardQuestionsSize());
  }

  @Test
  void updateDifficulty() throws IOException {
    bank2.refresh();
    Question test =
        new Question("Which country is known as the Land of the Midnight Sun?", "Norway.", "Hard");
    List<String> beforeFile = Files.readAllLines(bank2Path);
    assertTrue(beforeFile.get(2).contains("Hard"));
    assertFalse(beforeFile.get(2).contains("Easy"));
    bank2.updateDifficulty(test, "Easy", bank2Path.toString());
    List<String> afterFile = Files.readAllLines(bank2Path);
    assertTrue(afterFile.get(2).contains("Easy"));
    assertFalse(afterFile.get(2).contains("Hard"));
    bank2.updateDifficulty(test, "Hard", bank2Path.toString());
  }
}