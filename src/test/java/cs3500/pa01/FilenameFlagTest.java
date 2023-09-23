package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the FileNameFlag class.
 */
class FilenameFlagTest {

  private MarkdownFile mdFile1;
  private MarkdownFile mdFile2;
  private MarkdownFile mdFile3;
  private MarkdownFile mdFile4;

  private String filename1;
  private String filename2;
  private String filename3;
  private String filename4;
  private ArrayList<MarkdownFile> unsortedList;
  private ArrayList<MarkdownFile> sortedList;
  private ArrayList<MarkdownFile> emptyList;
  private ArrayList<MarkdownFile> oneList;
  private SortingOrder sorter;
  private SortingOrder emptyNameSorter;
  private SortingOrder oneSorter;

  /**
   * initializes the fields before each test.
   */
  @BeforeEach
  void setup() {
    filename1 = "apple.md";
    filename2 = "ball.md";
    filename3 = "cat.md";
    filename4 = "dog.md";

    mdFile1 = new MarkdownFile(filename1);
    mdFile2 = new MarkdownFile(filename2);
    mdFile3 = new MarkdownFile(filename3);
    mdFile4 = new MarkdownFile(filename4);

    sortedList =
        new ArrayList<MarkdownFile>(Arrays.asList(mdFile1, mdFile2, mdFile3, mdFile4));
    unsortedList =
        new ArrayList<MarkdownFile>(Arrays.asList(mdFile3, mdFile1, mdFile4, mdFile2));
    emptyList = new ArrayList<MarkdownFile>();
    oneList = new ArrayList<MarkdownFile>(Arrays.asList(mdFile2));

    sorter = new FilenameFlag(unsortedList);
    emptyNameSorter = new FilenameFlag(emptyList);
    oneSorter = new FilenameFlag(oneList);

  }

  /**
   * tests the sort method.
   */
  @Test
  void sort() {
    assertEquals(sortedList, sorter.sort());
    assertEquals(oneList, oneSorter.sort());
    assertThrows(
        RuntimeException.class,
        () -> this.emptyNameSorter.sort());
  }

  /**
   * tests the compare method.
   */
  @Test
  void compare() {
    assertEquals(0, sorter.compare(mdFile1, mdFile1));
    assertEquals(-1, sorter.compare(mdFile1, mdFile2));
    assertEquals(1, sorter.compare(mdFile3, mdFile2));

  }
}