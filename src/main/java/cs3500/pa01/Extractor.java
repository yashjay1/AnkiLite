package cs3500.pa01;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The Extractor class is responsible for extracting important phrases from a given file.
 */
public class Extractor {
  private final String inputFilePath;

  /**
   * Constructs an Extractor object with the specified input file path.
   *
   * @param inputFilePath the path of the input file
   */
  Extractor(String inputFilePath) {
    this.inputFilePath = inputFilePath;
  }


  /**
   * Extracts important strings from a file based on certain patterns.
   * The file contents are parsed and relevant strings are added to an ArrayList.
   * The file is expected to be in a specific format.
   *
   * @throws IOException If an I/O error occurs while reading from the file.
   */
  public ArrayList<String> extract() throws IOException {
    String fileContents = FileHandler.toString(this.inputFilePath, "|~~new-line~~|");
    StringBuilder contents = new StringBuilder(fileContents);
    ArrayList<String> important = new ArrayList<>();

    int index = 0;

    while (index < fileContents.length()) {
      char currentChar = fileContents.charAt(index);

      if (currentChar == '#') {
        int endIndex = contents.indexOf("|~~new-line~~|", index);

        if (endIndex != -1) {
          important.add("#" + contents.substring(index + 1, endIndex));
          index = endIndex + 1;
          continue;
        }
      } else if (currentChar == '[' && index + 1 < fileContents.length()
          && fileContents.charAt(index + 1) == '[') {
        int endIndex = contents.indexOf("]]", index);

        if (endIndex != -1) {
          String substring = contents.substring(index + 2, endIndex);
          substring = substring.replace("|~~new-line~~|", "");
          important.add(substring);
          index = endIndex + 2;
          continue;
        }
      }
      index++;
    }
    return important;
  }

  /**
   * Extracts the questions from the summary file.
   *
   * @return an ArrayList of questions extracted from the summary file
   * @throws IOException if an I/O error occurs
   */
  public ArrayList<String> extractQnA() throws IOException {
    String filePath = Path.of(this.inputFilePath).getParent().toString();
    FileHandler.createFile(filePath, "/QnA.sr");
    ArrayList<String> questions = new ArrayList<>();

    List<String> lines = Files.readAllLines(Path.of(filePath + "/summary.md"));

    for (String line : lines) {
      if (line.contains(":::")) {
        String excludeBulletLine = line.substring(2);
        questions.add(excludeBulletLine);
      }
    }
    return questions;
  }
}
