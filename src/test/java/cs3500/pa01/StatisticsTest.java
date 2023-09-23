package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatisticsTest {

  @Test
  void testConstructor() {
    Statistics test = new Statistics();
  }

  @Test
  void updateHardToEasy() {
    assertEquals(0, Statistics.calculateStats().get(0));
    Statistics.updateHardToEasy();
    Statistics.updateHardToEasy();
    assertEquals(2, Statistics.calculateStats().get(0));
  }

  @Test
  void updateEasyToHard() {
    assertEquals(0, Statistics.calculateStats().get(1));
    Statistics.updateEasyToHard();
    Statistics.updateEasyToHard();
    assertEquals(2, Statistics.calculateStats().get(1));
  }

  @Test
  void setNumOfHardQuestion() {
    Statistics.setNumOfHardQuestion(0);
    assertEquals(0, Statistics.calculateStats().get(2));
    Statistics.setNumOfHardQuestion(74);
    assertEquals(74, Statistics.calculateStats().get(2));
  }

  @Test
  void setNumOfEasyQuestion() {
    Statistics.setNumOfEasyQuestion(0);
    assertEquals(0, Statistics.calculateStats().get(3));
    Statistics.setNumOfEasyQuestion(43);
    assertEquals(43, Statistics.calculateStats().get(3));
  }

  @Test
  void calculateStats() {
    assertEquals(2, Statistics.calculateStats().get(0));
    assertEquals(2, Statistics.calculateStats().get(1));
    assertEquals(0, Statistics.calculateStats().get(2));
    assertEquals(0, Statistics.calculateStats().get(3));

    Statistics.updateHardToEasy();
    Statistics.updateHardToEasy();
    Statistics.updateHardToEasy();
    Statistics.updateEasyToHard();
    Statistics.updateEasyToHard();
    Statistics.updateEasyToHard();
    Statistics.updateEasyToHard();
    Statistics.setNumOfHardQuestion(92);
    Statistics.setNumOfEasyQuestion(23);

    assertEquals(new ArrayList<Integer>(Arrays.asList(5, 6, 92, 23)), Statistics.calculateStats());


  }
}