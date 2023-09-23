package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the FileVerifier class.
 */
class FileVerifierTest {
  private final String validDirPath = "sampleFiles";
  private final String invalidDirPath = "path/to/invalid/directory";
  private final String validOperation = "filename";
  private final String invalidOperation = "invalidOperation";

  @Test
  void constructor_ValidArguments_NoExceptionsThrown() {
    Assertions.assertDoesNotThrow(() ->
        new FileVerifier(validDirPath, validOperation, validDirPath));
  }

  @Test
  void constructor_InvalidInputDirectory_ExceptionThrown() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        new FileVerifier(invalidDirPath, validOperation, validDirPath));
  }

  @Test
  void constructor_InvalidOutputDirectory_ExceptionThrown() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        new FileVerifier(validDirPath, validOperation, invalidDirPath));
  }

  @Test
  void constructor_InvalidOperation_ExceptionThrown() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        new FileVerifier(validDirPath, invalidOperation, validDirPath));
  }

  @Test
  void getInputDir_ReturnsCorrectInputDir() {
    FileVerifier verifier = new FileVerifier(validDirPath, validOperation, validDirPath);
    Assertions.assertEquals(Path.of(validDirPath).toAbsolutePath().toString(),
        verifier.getInputDir());
  }

  @Test
  void getOperation_ReturnsCorrectOperation() {
    FileVerifier verifierFileName = new FileVerifier(validDirPath, validOperation, validDirPath);
    Assertions.assertEquals(validOperation, verifierFileName.getOperation());
    FileVerifier verifierModified = new FileVerifier(validDirPath, "modified", validDirPath);
    Assertions.assertEquals("modified", verifierModified.getOperation());
    FileVerifier verifierCreated = new FileVerifier(validDirPath, "created", validDirPath);
    Assertions.assertEquals("created", verifierCreated.getOperation());
  }

  @Test
  void getOutputDir_ReturnsCorrectOutputDir() {
    FileVerifier verifier = new FileVerifier(validDirPath, validOperation, validDirPath);
    Assertions.assertEquals(Path.of(validDirPath).toAbsolutePath().toString(),
        verifier.getOutputDir());
  }
}