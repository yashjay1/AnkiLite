package cs3500.pa01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The CreatedFlag class implements the SortingOrder interface
 * and the Comparator interface for sorting
 * a list of MarkdownFile objects based on their creation time.
 *
 */
public class CreatedFlag implements SortingOrder, Comparator<MarkdownFile> {
  private ArrayList<MarkdownFile> listOfFiles;

  /**
   * Constructs a `createdFlag` object with the specified list of `MarkdownFile` objects.
   *
   * @param listOfFiles The list of `MarkdownFile` objects to be sorted.
   */
  CreatedFlag(ArrayList<MarkdownFile> listOfFiles) {
    this.listOfFiles = listOfFiles;
  }

  /**
   * Sorts the list of `MarkdownFile` objects based on their creation time.
   *
   * @return The sorted list of `MarkdownFile` objects.
   * @throws RuntimeException if attempting to sort an empty list.
   */
  public ArrayList<MarkdownFile> sort() {

    if (this.listOfFiles.isEmpty()) {
      throw new RuntimeException("Trying to sort an empty list (Created)");
    }

    ArrayList<MarkdownFile> sortedList = new ArrayList<>(this.listOfFiles);

    Collections.sort(sortedList, this);
    return sortedList;
  }

  /**
   * Compares two `MarkdownFile` objects based on their creation time.
   *
   * @param p1 The first `MarkdownFile` object to compare.
   * @param p2 The second `MarkdownFile` object to compare.
   * @return a negative integer, zero, or a positive integer as the first argument is less than,
   *     equal to, or greater than the second argument.
   */
  @Override
  public int compare(MarkdownFile p1, MarkdownFile p2) {
    return Long.compare(p1.getCreatedAt().toMillis(), p2.getCreatedAt().toMillis());
  }
}
