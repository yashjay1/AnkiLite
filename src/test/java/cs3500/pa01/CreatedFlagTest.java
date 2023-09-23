package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A class that tests the functionality of the CreatedFlag class.
 */
class CreatedFlagTest {

  private MarkdownFile mdFile1;
  private MarkdownFile mdFile2;
  private MarkdownFile mdFile3;
  private MarkdownFile mdFile4;

  private FileTime lastCreated1;
  private FileTime lastCreated2;
  private FileTime lastCreated3;
  private FileTime lastCreated4;
  private ArrayList<MarkdownFile> unsortedList;
  private ArrayList<MarkdownFile> sortedList;
  private ArrayList<MarkdownFile> emptyList;
  private ArrayList<MarkdownFile> oneList;
  private SortingOrder sorter;
  private SortingOrder emptyCreatedSorter;
  private SortingOrder oneSorter;

  /**
   * initializes the fields before each test.
   */
  @BeforeEach
  void setup() {
    lastCreated1 = FileTime.from(Instant.parse("2023-05-14T12:00:00Z"));
    lastCreated2 = FileTime.from(Instant.parse("2023-06-23T12:00:00Z"));
    lastCreated3 = FileTime.from(Instant.parse("2023-06-29T12:00:00Z"));
    lastCreated4 = FileTime.from(Instant.parse("2023-09-03T12:00:00Z"));

    mdFile1 = new MarkdownFile(lastCreated1);
    mdFile2 = new MarkdownFile(lastCreated2);
    mdFile3 = new MarkdownFile(lastCreated3);
    mdFile4 = new MarkdownFile(lastCreated4);

    sortedList =
        new ArrayList<MarkdownFile>(Arrays.asList(mdFile1, mdFile2, mdFile3, mdFile4));
    unsortedList =
        new ArrayList<MarkdownFile>(Arrays.asList(mdFile3, mdFile1, mdFile4, mdFile2));
    emptyList = new ArrayList<MarkdownFile>();
    oneList = new ArrayList<MarkdownFile>(Arrays.asList(mdFile2));

    sorter = new CreatedFlag(unsortedList);
    emptyCreatedSorter = new CreatedFlag(emptyList);
    oneSorter = new CreatedFlag(oneList);

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
        () -> this.emptyCreatedSorter.sort());
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