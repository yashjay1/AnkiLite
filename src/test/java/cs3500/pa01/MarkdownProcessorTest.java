package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the MarkdownProcessor class.
 */
class MarkdownProcessorTest {

  private File inputFile;
  private File outputFile;
  private List<String> lines;

  private String inputFilePath =
      Path.of("testQuestions/MoreTestQs/summary.md").toString();

  /**
   * initializes the fields before each test.
   */
  @BeforeEach
  void setup() throws IOException {
    lines = Files.readAllLines(Path.of("testQuestions/MoreTestQs/summary.md"));
    inputFile = File.createTempFile("input", ".md");
    outputFile = File.createTempFile("output", ".md");
    PrintWriter writer = new PrintWriter("testQuestions/MoreTestQs/QnA.sr");
    writer.close();

  }

  /**
   * cleans up the temporary files.
   */
  @AfterEach
  void after() throws IOException {
    // Delete the temporary files
    inputFile.delete();
    outputFile.delete();

    BufferedWriter writer =
        new BufferedWriter(new FileWriter("testQuestions/MoreTestQs/summary.md"));
    for (String line : lines) {
      writer.write(line);
      writer.newLine();
    }
    writer.close();

  }

  /**
   * Tests the summariseMarkdown method.
   *
   * @throws IOException throws exception if file is not found.
   */
  @Test
  void testSummariseMarkdown() throws IOException {
    FileWriter fileWriter = new FileWriter(inputFile);
    fileWriter.write("# Header 1\n\n[[Link 1]]\n\n[[Link 2]]\n\n# Header 2");
    fileWriter.close();

    MarkdownProcessor constructorTest = new MarkdownProcessor();

    MarkdownProcessor.summariseMarkdown(inputFile.getPath(), outputFile.getPath());

    BufferedReader reader = new BufferedReader(new FileReader(outputFile));
    String outputContent = reader.readLine();
    reader.close();

    assertEquals("# Header 1", outputContent);
  }

  /**
   * Tests the summariseMarkdown method especially multilines.
   *
   * @throws IOException throws exception if file is not found.
   */
  @Test
  void testSummariseMarkdown_MultiLine() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write("[[important ");
    fileWriter.newLine();
    fileWriter.write("statement]]");
    fileWriter.close();

    MarkdownProcessor.summariseMarkdown(inputFile.getPath(), outputFile.getPath());

    BufferedReader reader = new BufferedReader(new FileReader(outputFile));
    String outputContent = reader.readLine();
    reader.close();

    assertEquals("- important statement", outputContent);
  }

  /**
   * Tests the summariseMarkdown method especially multilines.
   *
   * @throws IOException throws exception if file is not found.
   */
  @Test
  void testSummariseMarkdown_ContinuousMultiLine() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write("this is not");
    fileWriter.newLine();
    fileWriter.write("[[this is ");
    fileWriter.newLine();
    fileWriter.write("a very important");
    fileWriter.newLine();
    fileWriter.write(" statement]]");
    fileWriter.newLine();
    fileWriter.write(" an important statement.");
    fileWriter.close();

    MarkdownProcessor.summariseMarkdown(inputFile.getPath(), outputFile.getPath());

    BufferedReader reader = new BufferedReader(new FileReader(outputFile));
    String outputContent = reader.readLine();
    reader.close();

    assertEquals("- this is a very important statement", outputContent);
  }

  /**
   * Tests the summariseMarkdown method with no headers.
   *
   * @throws IOException throws exception if file is not found.
   */
  @Test
  void testSummariseMarkdown_NoHeadersOrLinks() throws IOException {
    FileWriter fileWriter = new FileWriter(inputFile);
    fileWriter.write("Some text without headers or links");
    fileWriter.close();

    MarkdownProcessor.summariseMarkdown(inputFile.getPath(), outputFile.getPath());

    BufferedReader reader = new BufferedReader(new FileReader(outputFile));
    String outputContent = reader.readLine();
    reader.close();

    if (outputContent != null) {
      assertEquals("", outputContent.trim());
    } else {
      assertNull(outputContent);
    }
  }

  /**
   * Tests the summariseMarkdown method when it throws an exception.
   */
  @Test
  void testSummariseMarkdownException() {
    assertThrows(IOException.class,
        () -> MarkdownProcessor.summariseMarkdown("doesntexist.md", "test.md"));
  }

  @Test
  public void testGenerateQnaFile() throws IOException {


    String expectedQnaFilePath =
        Path.of("testQuestions/MoreTestQs/QnA.sr").toString();

    // Checks if the QnA file is empty
    BufferedReader reader = new BufferedReader(new FileReader(expectedQnaFilePath));
    assertNull(reader.readLine());
    reader.close();

    // Checks if the .md file contains a question.
    assertEquals(9, lines.size());
    assertTrue(lines.get(6).contains(":::"));

    MarkdownProcessor generateSrFile = new MarkdownProcessor();
    generateSrFile.generateQnaFile(inputFilePath);

    BufferedReader srReader = new BufferedReader(new FileReader(expectedQnaFilePath));
    assertEquals(
        "~~~System:Marked-Hard~~~ What is stored in the non-volatile memory? ::: Files",
        srReader.readLine());
    srReader.close();


  }
}
