package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class DisplayerTest {

  @Test
  void getSrcPath() {
    Displayer display = new Displayer();
    assertNull(display.getSrcPath());
  }

  @Test
  void updateCurrentCounter() {
    Displayer display = new Displayer();
    assertEquals(1, display.getQuestionCounter());
    display.updateCurrentCounter();
    assertEquals(2, display.getQuestionCounter());
    display.updateCurrentCounter();
    display.updateCurrentCounter();
    assertEquals(4, display.getQuestionCounter());
  }


  @Test
  public void testWelcomeScreen() {
    String filePathInput = "testQuestions/MoreTestQs/QnA.sr";
    String numberOfQuestionsInput = "3";

    String input =
        filePathInput + System.lineSeparator() + numberOfQuestionsInput + System.lineSeparator();
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    Displayer display = new Displayer();
    int result = display.welcomeScreen();

    System.setIn(System.in);

    assertEquals(3, result);
  }


  @Test
  public void testQuestionScreen() {
    String input = "E";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    Displayer display = new Displayer();
    Question question =
        new Question("What is the capital of the United States?", "Washington, D.C.", "Hard");
    char result = display.questionScreen(question);

    System.setIn(System.in);

    assertEquals('E', result);
  }

  @Test
  void answerScreen() {
  }

  @Test
  public void testInvalidInputScreen() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    Displayer display = new Displayer();
    display.invalidInputScreen();

    System.setOut(System.out);

    String expectedOutput = ""

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
        + "*****************************************\n\n";

    String actualOutput = outputStream.toString();

    assertEquals(expectedOutput, actualOutput);
  }


  @Test
  public void testAnswerScreen() {
    Question question = new Question("What is the capital of France?", "Paris", "Easy");

    Displayer display = new Displayer();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    display.answerScreen(question);
    String output = outputStream.toString().trim();

    String expectedOutput = ""
        + "*****************************************\n"
        + "\n"
        + "Question 1 of 0\t\t||\t\tDifficulty: Easy"
        + "\n"
        + "\n"
        + "*****************************************\n"
        + "\n"
        + "\n"
        + "Question: What is the capital of France?\n"
        + "\n"
        + "\n"
        + "Answer: Paris\n"
        + "\n"
        + "\n"
        + "*****************************************\n"
        + "\n"
        + "Mark as [E]asy \t Mark as [H]ard \t Show [A]nswer \t Next [Q]uestion"
        + "\n"
        + "\n"
        + "*****************************************";

    assertEquals(expectedOutput, output);
  }

  @Test
  void setTotalSessionQuestionsTest() {
    Displayer display = new Displayer();
    assertEquals(0, display.getTotalSessionQuestions());
    display.setTotalSessionQuestions(54);
    assertEquals(54, display.getTotalSessionQuestions());

  }

  @Test
  public void testEndScreen() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    System.setOut(printStream);

    Displayer display = new Displayer();

    display.endScreen();

    System.out.flush();
    PrintStream originalPrintStream = System.out;
    System.setOut(originalPrintStream);

    String output = outputStream.toString();

    String expectedOutput = "*****************************************\n"
        + "\n"
        + "Good job on completing this study session!\n"
        + "\n"
        + "*****************************************\n"
        + "\n"
        + "0 Questions went from Hard to Easy\n"
        + "\n"
        + "0 Questions went from Easy to Hard\n"
        + "\n"
        + "There's 0 questions in the Hard Question Bank out of a total of 0 questions.\n"
        + "\n"
        + "There's 0 questions in the Easy Question Bank out of a total of 0 questions.\n"
        + "\n"
        + "*****************************************\n";

    // Compare the expected output with the actual output
    assertEquals(expectedOutput, output);
  }
}