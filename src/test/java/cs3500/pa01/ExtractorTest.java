package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the Extractor class.
 */
class ExtractorTest {


  private File inputFile;

  /**
   * initializes the fields before each test.
   */
  @BeforeEach
  void setup() throws IOException {
    inputFile = File.createTempFile("input1", ".md");
  }

  /**
   * cleans up the temporary files.
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @AfterEach
  void after() throws IOException {
    Files.deleteIfExists(Path.of(inputFile.getAbsolutePath()));
  }

  /**
   * Tests if the extract() works with headers
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @Test
  void testHeaderExtract() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write(" # Header 0");
    fileWriter.newLine();
    fileWriter.write("this line contains # Header 1");
    fileWriter.newLine();
    fileWriter.write("this line contains ## Header 2");
    fileWriter.newLine();
    fileWriter.write("this line contains ### Header 3");
    fileWriter.newLine();
    fileWriter.write(" #### Header 4");
    fileWriter.close();

    Extractor testExtractor = new Extractor(inputFile.getPath());
    ArrayList<String> extractedStatements = testExtractor.extract();
    ArrayList<String> expectedStatements = new ArrayList<>(
        Arrays.asList("# Header 0", "# Header 1", "## Header 2", "### Header 3",
            "#### Header 4"));

    assertEquals(expectedStatements, extractedStatements);
  }

  /**
   * Tests if the extract() works with nested headers.
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @Test
  void testNestedHeaderExtract() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write(" # Nested");
    fileWriter.newLine();
    fileWriter.write(" ## Header");
    fileWriter.close();

    Extractor testExtractor = new Extractor(inputFile.getPath());
    ArrayList<String> extractedStatements = testExtractor.extract();
    ArrayList<String> expectedStatements =
        new ArrayList<>(Arrays.asList("# Nested", "## Header"));

    assertEquals(expectedStatements, extractedStatements);
  }

  /**
   * Tests if the extract() works by excluding invalid statements.
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @Test
  void testInvalidExtract() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write("Header1");

    Extractor testExtractor = new Extractor(inputFile.getPath());
    ArrayList<String> extractedStatements = testExtractor.extract();
    ArrayList<String> expectedStatements = new ArrayList<>();

    assertEquals(expectedStatements, extractedStatements);

    fileWriter.flush();

    fileWriter.write("\n # header 1");
    fileWriter.newLine();
    fileWriter.write("# header 2");
    fileWriter.close();

    extractedStatements = testExtractor.extract();
    expectedStatements = new ArrayList<>(Arrays.asList("# header 1", "# header 2"));

    assertEquals(expectedStatements, extractedStatements);
  }

  /**
   * Tests if the extract() works for important phrases.
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @Test
  void testPhraseExtract() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write("[[this sentence ]]contains ]]something that [[is[[ very]] [[ important]]");
    fileWriter.close();

    Extractor testExtractor = new Extractor(inputFile.getPath());
    ArrayList<String> extractedStatements = testExtractor.extract();
    ArrayList<String> expectedStatements =
        new ArrayList<>(Arrays.asList("this sentence ", "is[[ very", " important"));

    assertEquals(expectedStatements, extractedStatements);
  }

  /**
   * Tests if the extract() works when important phrases are multi-line
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @Test
  void testDoubleExtractDiffLine() throws IOException {
    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile));
    fileWriter.write("[[this sentence ");
    fileWriter.newLine();
    fileWriter.write("goes to the next line]]");
    fileWriter.close();

    Extractor testExtractor = new Extractor(inputFile.getPath());
    ArrayList<String> extractedStatements = testExtractor.extract();
    ArrayList<String> expectedStatements =
        new ArrayList<>(Arrays.asList("this sentence goes to the next line"));

    assertEquals(expectedStatements, extractedStatements);
  }

  /**
   * Tests if the exception is thrown when an invalid file path is given.
   *
   * @throws IOException thrown when an invalid file path is given.
   */
  @Test
  void extractException() throws IOException {
    Extractor extractor = new Extractor("/not/a/valid/file/path");
    assertThrows(IOException.class,
        () -> extractor.extract());
  }
}