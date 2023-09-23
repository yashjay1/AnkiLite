package cs3500.pa01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The modifiedFlag class represents a sorting order based
 * on the last modified timestamp of Markdown files.
 * It implements the SortingOrder interface and
 * uses the Comparator of type MarkdownFile interface for comparing Markdown files.
 */
public class ModifiedFlag implements SortingOrder, Comparator<MarkdownFile> {

  private ArrayList<MarkdownFile> listOfFiles;

  /**
   * Constructs a ModifiedFlag object with the provided list of MarkdownFile objects.
   *
   * @param listOfFiles the list of MarkdownFile objects to be sorted
   */
  ModifiedFlag(ArrayList<MarkdownFile> listOfFiles) {
    this.listOfFiles = listOfFiles;
  }

  /**
   * Sorts the list of MarkdownFile objects based on their last modified timestamp.
   *
   * @return the sorted list of MarkdownFile objects
   * @throws RuntimeException if the list is empty
   */
  public ArrayList<MarkdownFile> sort() {

    if (this.listOfFiles.isEmpty()) {
      throw new RuntimeException("Trying to sort an empty list (Modified)");
    }

    ArrayList<MarkdownFile> sortedList =
        new ArrayList<MarkdownFile>(this.listOfFiles);

    Collections.sort(sortedList, this);
    return sortedList;
  }

  /**
   * Compares two MarkdownFile objects based on their last modified timestamp.
   *
   * @param p1 the first MarkdownFile object to compare
   * @param p2 the second MarkdownFile object to compare
   * @return a negative integer if p1's last modified timestamp is less than p2's,
   *     zero if they are equal, or a positive integer if p1's last modified timestamp
   *     is greater than p2's
   */
  @Override
  public int compare(MarkdownFile p1, MarkdownFile p2) {
    return Long.compare(p1.getLastModified().toMillis(), p2.getLastModified().toMillis());
  }
}
