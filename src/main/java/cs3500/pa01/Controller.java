package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The Controller class is responsible for running the program and managing the question bank.
 */
public class Controller {

  private QuestionBank questionBank;
  private ArrayList<Question> sessionBank = new ArrayList<>();

  /**
   * Constructs a Controller object and runs the program to generate a
   * markdown summary file and a .sr file with questions.
   *
   * @param args the command line arguments
   * @throws IOException if an I/O error occurs
   */
  Controller(String[] args) throws IOException {
    this.runSummary(args);
  }

  /**
   * Constructs a Controller object and runs the Anki Lite flash cards program.
   *
   * @throws IOException if an I/O error occurs
   */
  Controller() throws IOException {
    this.runAnki();
  }

  /**
   * This method is used to run the program to generate a
   * markdown summary file and a .sr file with questions.
   *
   * @param args accepts three arguments: Input File Path, Sorting Flag, Output file path.
   * @throws IOException throws exception when an illegal argument is given.
   */
  public void runSummary(String[] args) throws IOException {
    String inputDirectory = args[0];
    String sortingOption = args[1];
    String outputDirectory = args[2];
    String summaryFilePath = outputDirectory + "/summary.md";

    Files.deleteIfExists(Path.of(summaryFilePath));

    FileVerifier verification = new FileVerifier(inputDirectory, sortingOption, outputDirectory);
    FileRetriever fileRetriever = new FileRetriever();
    Files.walkFileTree(Path.of(inputDirectory), fileRetriever);

    ArrayList<MarkdownFile> listOfMdFiles = fileRetriever.getFile();
    ArrayList<MarkdownFile> sortedFilePaths = new ArrayList<>();

    switch (sortingOption) {
      case "created":
        sortedFilePaths = new CreatedFlag(listOfMdFiles).sort();
        break;
      case "filename":
        sortedFilePaths = new FilenameFlag(listOfMdFiles).sort();
        break;
      case "modified":
        sortedFilePaths = new ModifiedFlag(listOfMdFiles).sort();
        break;
      default:
        throw new IllegalArgumentException("Invalid sorting option");
    }

    FileHandler temporaryCombinedFile = new FileHandler(inputDirectory);
    temporaryCombinedFile.combine(sortedFilePaths, "temporaryCombinedFile.md");

    MarkdownProcessor.summariseMarkdown(inputDirectory + "/temporaryCombinedFile.md",
        summaryFilePath);

    Files.delete(Path.of(inputDirectory + "/temporaryCombinedFile.md"));
    new MarkdownProcessor().generateQnaFile(summaryFilePath);
  }

  /**
   * This method is used to run the Anki Lite flash cards program
   *
   * @throws IOException throws an I/O Exception if an invalid file path is entered.
   */
  public void runAnki() throws IOException {
    Displayer display = new Displayer();
    int numOfSessionQuestions = display.welcomeScreen();
    String inputPath = display.getSrcPath();
    questionBank = new QuestionBank(inputPath);
    questionBank.loadQuestions();
    sessionBank = questionBank.randomQuestions(numOfSessionQuestions);
    display.setTotalSessionQuestions(sessionBank.size());

    for (int i = 0; i < sessionBank.size(); i++) {
        {
        Question question = sessionBank.get(i);
        switch (display.questionScreen(question)) {
          case 'A':
            display.answerScreen(question);
            i--;
            break;
          case 'E':
            if (!question.getDifficulty().equals(Difficulty.Easy.toString())) {
              questionBank.updateDifficulty(question, Difficulty.Easy.toString(), inputPath);
              Statistics.updateHardToEasy();
            }
            i--;
            break;
          case 'H':
            if (!question.getDifficulty().equals(Difficulty.Hard.toString())) {
              questionBank.updateDifficulty(question, Difficulty.Hard.toString(), inputPath);
              Statistics.updateEasyToHard();
            }
            i--;
            break;
          case 'Q':
            display.updateCurrentCounter();
            break;
          default:
            display.invalidInputScreen();
        }
        }
    }
    questionBank.refresh();
    Statistics.setNumOfHardQuestion(questionBank.getHardQuestionsSize());
    Statistics.setNumOfEasyQuestion(questionBank.getEasyQuestionsSize());
    display.endScreen();
  }
}

