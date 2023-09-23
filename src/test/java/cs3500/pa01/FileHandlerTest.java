package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the FileHandler class.
 */
class FileHandlerTest {

  @Test
  void randomstuff() {

  }


  private File fileInput1;
  private File fileInput2;

  /**
   * initializes the fields before each test.
   */
  @BeforeEach
  void setup() throws IOException {
    fileInput1 = File.createTempFile("input1", ".md");
    fileInput2 = File.createTempFile("input2", ".md");
  }

  /**
   * cleans up the temporary files.
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @AfterEach
  void after() throws IOException {
    Files.deleteIfExists(Path.of(fileInput1.getAbsolutePath()));
    Files.deleteIfExists(Path.of(fileInput2.getAbsolutePath()));
  }

  /**
   * tests to see if a file is being created as expected.
   */
  @Test
  void createFile() {
    File tempDir = new File(System.getProperty("java.io.tmpdir"));
    String testFileName = "test.md";
    File createdFile =
        FileHandler.createFile(tempDir.getAbsolutePath() + File.separator, testFileName);
    assertTrue(createdFile.exists());
    createdFile.delete();
  }

  /**
   * tests to see if the createFile method is throwing an exception is throw when an
   * illegal path is given.
   */
  @Test
  void createFileExceptionTest() {
    assertThrows(RuntimeException.class,
        () -> FileHandler.createFile("/this/path/does/not/exist", "test.md"));
  }

  /**
   * tests the combine method.
   *
   * @throws IOException throws an exception if there is no file to write to.
   */
  @Test
  void testCombine() throws IOException {

    BufferedWriter writer =
        new BufferedWriter(new FileWriter(fileInput1.getAbsolutePath()));
    writer.write("this is from test1 file + ");
    writer.close();
    writer = new BufferedWriter(new FileWriter(fileInput2.getAbsolutePath()));
    writer.write("this is from test2 file");
    writer.close();

    FileTime time = FileTime.from(Instant.parse("2023-05-14T12:00:00Z"));
    MarkdownFile md1 = new MarkdownFile(time, time, "input1.md",
        fileInput1.getAbsolutePath());
    MarkdownFile md2 = new MarkdownFile(time, time, "input2.md",
        fileInput2.getAbsolutePath());
    ArrayList<MarkdownFile> mdList = new ArrayList<>(Arrays.asList(md1, md2));

    FileHandler tester =
        new FileHandler(fileInput1.getParent());

    tester.combine(mdList, "/testerCombine.md");


    BufferedReader reader =
        new BufferedReader(new FileReader(fileInput1.getParent() + "/testerCombine.md"));

    String outputContent = reader.readLine() + reader.readLine();
    reader.close();
    assertEquals("this is from test1 file + this is from test2 file", outputContent);

  }

  /**
   * tests if the combine method is throwing an exception when it needs to.
   */
  @Test
  void testCombineException() {

    FileTime time = FileTime.from(Instant.parse("2023-05-14T12:00:00Z"));
    MarkdownFile md1 = new MarkdownFile(time, time, "input1.md", "does/not/exist"
    );
    MarkdownFile md2 = new MarkdownFile(time, time, "input2.md",
        fileInput2.getAbsolutePath());
    ArrayList<MarkdownFile> mdList = new ArrayList<>(Arrays.asList(md1, md2));

    FileHandler tester =
        new FileHandler(fileInput1.getParent());


    assertThrows(RuntimeException.class,
        () -> tester.combine(mdList, "test.md"));
  }
}