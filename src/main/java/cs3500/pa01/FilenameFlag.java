package cs3500.pa01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The `filenameFlag` class represents a sorting strategy
 * based on the filename of Markdown files.
 * It implements the `SortingOrder` interface and
 * extends the `Comparator of type MarkdownFile` class.
 * The `filenameFlag` class provides methods to
 * sort a list of MarkdownFile objects based on their filenames.
 */
public class FilenameFlag implements SortingOrder, Comparator<MarkdownFile> {

  private ArrayList<MarkdownFile> listOfFiles;


  /**
   * Constructs a new filenameFlag object.
   *
   * @param listOfFiles The list of MarkdownFile objects to be sorted.
   */
  FilenameFlag(ArrayList<MarkdownFile> listOfFiles) {
    this.listOfFiles = listOfFiles;
  }

  /**
   * Sorts the list of MarkdownFile objects based on filename.
   *
   * @return The sorted list of MarkdownFile objects.
   * @throws RuntimeException if the list of MarkdownFile objects is empty.
   */
  public ArrayList<MarkdownFile> sort() {

    if (this.listOfFiles.isEmpty()) {
      throw new RuntimeException("Trying to sort an empty list (Filename)");
    }

    ArrayList<MarkdownFile> sortedList =
        new ArrayList<MarkdownFile>(this.listOfFiles);

    Collections.sort(sortedList, this);
    return sortedList;
  }

  /**
   * Compares two MarkdownFile objects based on their filenames.
   *
   * @param p1 The first MarkdownFile object to compare.
   * @param p2 The second MarkdownFile object to compare.
   * @return A negative integer, zero, or a positive integer if p1 is less than,
   *     equal to, or greater than p2, respectively.
   */
  @Override
  public int compare(MarkdownFile p1, MarkdownFile p2) {
    return p1.getName().compareTo(p2.getName());
  }


}
