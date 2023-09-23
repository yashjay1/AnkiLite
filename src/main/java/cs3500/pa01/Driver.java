package cs3500.pa01;

import java.io.IOException;

/**
 * This is the main driver of this project.
 */

public class Driver {

  /**
   * Used for initializing all the classes needed to summarise Markdown files.
   *
   * @param args accepts three arguments: Input File Path, Sorting Flag, Output file path.
   * @throws IOException throws exception when an illegal argument is given.
   */
  public static void main(String[] args) throws IOException {

    Controller main;

    if (args.length == 3) {
      main = new Controller(args);

    } else if (args.length == 0) {

      main = new Controller();
    } else {
      throw new IllegalArgumentException(
          "Provide 3 arguments for the summarizer or no arguments for Anki lite.");
    }
  }
}