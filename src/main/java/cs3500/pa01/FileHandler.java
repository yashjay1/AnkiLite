package cs3500.pa01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles certain methods relating to Files
 */
public class FileHandler {

  private String outPath;

  FileHandler(String outPath) {

    this.outPath = outPath;
  }

  /**
   * Used for creating a new file with a given name in
   * the given path.
   *
   * @param path     The path to where you want the file
   * @param fileName the name of the newly created file
   * @return A newly created file with the given name.
   */
  public static File createFile(String path, String fileName) {

    String outputFilePath = path + fileName;
    File output = new File(outputFilePath);

    try {
      boolean createdFile = output.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(
          "File may already exist or the path provided is invalid.");
    }

    return output;
  }

  /**
   * Combines the given files into a new single file.
   *
   * @param paths      The list of paths of the files that needs to be combined.
   * @param nameOfFile The name of the file with all the contents combined into it.
   */
  public void combine(ArrayList<MarkdownFile> paths, String nameOfFile) {

    File summaryFile = FileHandler.createFile(outPath, ("/" + nameOfFile));

    this.outPath = summaryFile.getPath().toString();

    for (MarkdownFile p : paths) {
      try (BufferedReader reader = new BufferedReader(new FileReader(p.getPath()));
           BufferedWriter writer = new BufferedWriter(
               new FileWriter(this.outPath, true))) {

        String line;

        while ((line = reader.readLine()) != null) {
          writer.write(line);
          writer.newLine();
        }
      } catch (IOException e) {
        throw new RuntimeException(
            "Something might be wrong with the provided list of paths");
      }
    }
  }

  /**
   * Reads the content of a file and returns it as a string.
   *
   * @param inputFilePath the path of the input file
   * @param lineSeparator the line separator to use for concatenating lines
   * @return the content of the file as a string
   * @throws IOException if an I/O error occurs while reading the file
   */
  public static String toString(String inputFilePath, String lineSeparator) throws IOException {
    String content = "";
    List<String> lines = Files.readAllLines(Path.of(inputFilePath));
    for (String line : lines) {
      content = content + line + lineSeparator;
    }

    return content;
  }
}

