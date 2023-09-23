package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class DriverTest {

  private String tempDirPathString;

  /**
   * Tests if the main method is dispatching the correct sorting algorithm.
   */
  @Test
  void mainSorterDispatcher() {

    String[] arguments1 = {tempDirPathString, "filename", tempDirPathString};
    String[] arguments2 = {tempDirPathString, "created", tempDirPathString};
    String[] arguments3 = {tempDirPathString, "modified", tempDirPathString};

    assertThrows(RuntimeException.class,
        () -> Driver.main(arguments1));
    assertThrows(RuntimeException.class,
        () -> Driver.main(arguments2));
    assertThrows(RuntimeException.class,
        () -> Driver.main(arguments3));
  }

  /**
   * tests if the main method throws an exception when illegal arguments are given.
   */
  @Test
  void testMainException() {

    Driver constructor = new Driver();

    String[] arguments1 = {"Non Existent path", "filename", "Non Existent path"};
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(arguments1));

    String[] arguments2 = {"notes-root", "wrong flag", "notes-root"};
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(arguments2));
  }

  @Test
  void noArgumentInput() {
    String[] arguments1 = {};
    assertThrows(NoSuchElementException.class,
        () -> Driver.main(arguments1));
  }

  @Test
  void testTwoArg() {
    String[] arguments1 = {"cat", "dog"};
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(arguments1));
  }
}

