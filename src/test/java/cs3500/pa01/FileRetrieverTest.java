package cs3500.pa01;


import static java.nio.file.FileVisitResult.CONTINUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the FileRetriever class.
 */
class FileRetrieverTest {
  private FileRetriever testRetriever;
  private ByteArrayOutputStream errStream;
  private Path tempDir;
  private Path file1;
  private Path file2;
  private Path file3;
  private Path file4;
  private Path file5;

  /**
   * initializes the fields before each test.
   */
  @BeforeEach
  void setup() throws IOException {
    this.tempDir = Files.createTempDirectory("test-dir");
    this.testRetriever = new FileRetriever();
    Files.walkFileTree(this.tempDir, this.testRetriever);
    this.errStream = new ByteArrayOutputStream();
    System.setErr(new PrintStream(errStream));
    file1 = Files.createFile(tempDir.resolve("file1.md"));
    file2 = Files.createFile(tempDir.resolve("file2.md"));
    file3 = Files.createFile(tempDir.resolve("file3.md.bak"));
    file4 = Files.createFile(tempDir.resolve("file4"));
    file5 = Files.createDirectory(tempDir.resolve("file5.md"));
  }

  /**
   * cleans up the temporary files.
   *
   * @throws IOException throws an exception if the file does not exist.
   */
  @AfterEach
  public void afterTests() throws IOException {
    Files.deleteIfExists(file1);
    Files.deleteIfExists(file2);
    Files.deleteIfExists(file3);
    Files.deleteIfExists(file4);
    Files.deleteIfExists(file5);
    Files.deleteIfExists(tempDir);
  }

  /**
   * Tests the getFile method.
   *
   * @throws IOException throws an exception if the walkFileTree method fails a visit.
   */
  @Test
  void getFile() throws IOException {

    FileRetriever fileRetriever = new FileRetriever();
    Files.walkFileTree(tempDir, fileRetriever);

    ArrayList<MarkdownFile> files = fileRetriever.getFile();

    assertEquals(2, files.size());

    MarkdownFile markdownFile1 = files.get(0);
    assertEquals(file1.getFileName().toString(), markdownFile1.getName());
    assertTrue(markdownFile1.getPath().endsWith(".md"));
  }

  /**
   * Tests if the walker s visiting a file.
   *
   * @throws IOException throws an exception when there is something wrong with the
   *                     file attribute.
   */
  @Test
  public void testVisitFile() throws IOException {
    FileRetriever fileRetriever = new FileRetriever();
    BasicFileAttributes attributes1 =
        Files.readAttributes(file1, BasicFileAttributes.class);
    BasicFileAttributes attributes2 =
        Files.readAttributes(file2, BasicFileAttributes.class);
    BasicFileAttributes attributes3 =
        Files.readAttributes(file3, BasicFileAttributes.class);
    BasicFileAttributes attributes4 =
        Files.readAttributes(file4, BasicFileAttributes.class);
    BasicFileAttributes attributes5 =
        Files.readAttributes(file5, BasicFileAttributes.class);

    fileRetriever.visitFile(file1, attributes1);
    fileRetriever.visitFile(file2, attributes2);
    fileRetriever.visitFile(file3, attributes3);
    fileRetriever.visitFile(file4, attributes4);
    fileRetriever.visitFile(file5, attributes5);

    ArrayList<MarkdownFile> files = fileRetriever.getFile();

    assertEquals(2, files.size());

    MarkdownFile markdownFile1 = files.get(0);
    assertEquals(file1.getFileName().toString(), markdownFile1.getName());
    assertTrue(markdownFile1.getPath().endsWith(".md"));

    MarkdownFile markdownFile2 = files.get(1);
    assertEquals(file2.getFileName().toString(), markdownFile2.getName());
    assertTrue(markdownFile2.getPath().endsWith(".md"));

    boolean file3Found = false;
    boolean file4Found = false;
    boolean file5Found = false;
    for (MarkdownFile file : files) {
      if (file.getName().equals(file3.getFileName().toString())) {
        file3Found = true;
      } else if (file.getName().equals(file4.getFileName().toString())) {
        file4Found = true;
      } else if (file.getName().equals(file5.getFileName().toString())) {
        file5Found = true;
      }
    }
    assertFalse(file3Found);
    assertFalse(file4Found);
    assertFalse(file5Found);
  }

  /**
   * tests the postVisitDirectory method.
   */
  @Test
  void postVisitDirectory() {
    IOException e = new IOException("This is a test exception.");
    FileVisitResult res = this.testRetriever.postVisitDirectory(this.tempDir, e);
    assertEquals(res, CONTINUE);
  }

  /**
   * tests the preVisitDirectory method.
   */
  @Test
  void preVisitDirectory() throws IOException {
    FileVisitResult res = this.testRetriever.preVisitDirectory(this.tempDir, null);
    assertEquals(res, CONTINUE);
  }


  /**
   * tests the visitFileFailed method.
   */
  @Test
  void visitFileFailed() {
    IOException e = new IOException("This is a test exception.");
    FileVisitResult res = this.testRetriever.visitFileFailed(this.tempDir, e);
    assertEquals(res, CONTINUE);
    assertEquals("File visit failed.\njava.io.IOException: This is a test exception.\n",
        errStream.toString());
  }
}