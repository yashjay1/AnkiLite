package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Tests for the controller class
 */
class ControllerTest {

  /**
   * Tests for the constructors
   */
  @Test
  void constructorTest() {
    assertThrows(NoSuchElementException.class,
        () -> new Controller());
  }

  /**
   * Tests for the runAnki() Method
   */
  @Test
  void testRunAnki() {
    assertThrows(NoSuchElementException.class,
        () -> new Controller().runAnki());
  }

  /**
   * Tests for the runSummary() Method
   */
  @Test
  void testRunSummary() {
    String[] arguments1 = {"Non Existent path", "filename", "Non Existent path"};
    assertThrows(NoSuchElementException.class,
        () -> new Controller().runSummary(arguments1));
  }

}