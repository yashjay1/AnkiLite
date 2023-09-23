package cs3500.pa01;

import java.io.File;
import java.nio.file.Path;

/**
 * A class to verify file path and directory path based on given criteria.
 */
public class FileVerifier {
  private final String inputDir;
  private final String operation;
  private final String outputDir;

  /**
   * Constructs a FileVerifier object with the specified variables.
   *
   * @param inputDir  the file path to verify
   * @param operation the operation to verify ("filename", "modified", or "created"
   *                  - case-insensitive)
   * @param outputDir the directory path to verify
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  public FileVerifier(String inputDir, String operation, String outputDir)
      throws IllegalArgumentException {
    verifyDirectoryPath(Path.of(inputDir).toAbsolutePath().toString());
    verifyOperation(operation);
    verifyDirectoryPath(Path.of(outputDir).toAbsolutePath().toString());

    this.inputDir = (Path.of(inputDir).toAbsolutePath().toString());
    this.operation = operation.toLowerCase();
    this.outputDir = Path.of(outputDir).toAbsolutePath().toString();
  }

  /**
   * Verifies if the provided operation is valid.
   *
   * @param operation the operation to verify
   * @throws IllegalArgumentException if the operation is invalid
   */
  private void verifyOperation(String operation) throws IllegalArgumentException {
    String lowerCaseOperation = operation.toLowerCase();
    if (!lowerCaseOperation.equals("filename")
        && !lowerCaseOperation.equals("modified")
        && !lowerCaseOperation.equals("created")) {
      throw new IllegalArgumentException("Invalid operation: " + operation);
    }
  }

  /**
   * Verifies if the provided directory path is valid.
   *
   * @param directoryPath the directory path to verify
   * @throws IllegalArgumentException if the directory path is invalid
   */
  private void verifyDirectoryPath(String directoryPath) throws IllegalArgumentException {
    File directory = new File(directoryPath);
    if (!directory.isDirectory()) {
      throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
    }
  }

  public String getInputDir() {
    return inputDir;
  }

  public String getOperation() {
    return operation;
  }

  public String getOutputDir() {
    return outputDir;
  }
}
