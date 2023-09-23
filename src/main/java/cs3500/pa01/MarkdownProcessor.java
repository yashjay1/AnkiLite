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
 * A class that modifies a Markdown file by summarizing its content.
 */
public class MarkdownProcessor {

  /**
   * Summarizes the Markdown file located at the specified input file path and writes the
   * summarized content to the specified output file path.
   *
   * @param inputFilePath  the path of the input Markdown file to be summarized
   * @param outputFilePath the path of the output file where the summarized content
   *                       will be written
   * @throws IOException if an I/O error occurs
   */
  public static void summariseMarkdown(String inputFilePath, String outputFilePath)
      throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

      Extractor extractSummary = new Extractor(inputFilePath);
      ArrayList<String> contents = extractSummary.extract();
      boolean firstLine = true;
      for (String line : contents) {
        if (line.startsWith("#") && firstLine) {
          firstLine = false;
          writer.write(line);
        } else if (line.startsWith("#") && !firstLine) {
          writer.newLine();
          writer.write(line);
        } else {
          writer.write("- " + line);
        }
        writer.newLine();
      }
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Generates a QnA.sr file based on the input summary Markdown file.
   *
   * @param inputFilePath the path of the input Markdown file
   * @throws IOException if an I/O error occurs
   */
  public void generateQnaFile(String inputFilePath) throws IOException {
    String filePath = Path.of(inputFilePath).getParent().toString();
    String qnaPath = filePath + "/QnA.sr";
    Files.deleteIfExists(Path.of(qnaPath));
    FileHandler.createFile(filePath, "/QnA.sr");

    Extractor srFileGen = new Extractor(inputFilePath);

    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "/QnA.sr"));
    ArrayList<String> questions = srFileGen.extractQnA();

    for (String question : questions) {
      writer.write("~~~System:Marked-Hard~~~ " + question);
      writer.newLine();
    }

    writer.close();
    MarkdownProcessor.clearQuestions(inputFilePath);
  }

  /**
   * Clears the questions in the summary Markdown file by removing lines containing ":::".
   *
   * @param inputFilePath the path of the input Markdown file
   */
  private static void clearQuestions(String inputFilePath) throws IOException {
    File inputFile = new File(inputFilePath);
    File tempFile = File.createTempFile("tempfile", null);

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String line;

    while ((line = reader.readLine()) != null) {
      if (!line.contains(":::")) {
        writer.write(line);
        writer.newLine();
      }
    }

    writer.close();
    reader.close();

    inputFile.delete();
    tempFile.renameTo(inputFile);

  }

  /**
   * Updates the text in the input file by replacing occurrences of the specified target
   * with the provided text.
   *
   * @param inputPath  the path of the input file
   * @param identifier the identifier to search for
   * @param target     the target string to be replaced
   * @param text       the replacement text
   */
  public static void updateText(String inputPath, String identifier, String target, String text) {
    File src = new File(inputPath);
    try {
      List<String> contents = Files.readAllLines(src.toPath());
      BufferedWriter writer = new BufferedWriter(new FileWriter(inputPath));
      for (String line : contents) {
        if (line.contains(identifier)) {
          line = line.replace(target, text);
        }
        writer.write(line);
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


