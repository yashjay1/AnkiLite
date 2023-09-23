package cs3500.pa01;

import java.util.ArrayList;

/**
 * The SortingOrder interface represents a sorting order for a collection of Markdown
 * files.
 */
public interface SortingOrder {

  /**
   * Sorts the collection of Markdown files according to the sorting order.
   *
   * @return the sorted ArrayList of MarkdownFile objects.
   */
  ArrayList<MarkdownFile> sort();

  /**
   * Compares two Markdown files based on the sorting order.
   *
   * @param md1 the first MarkdownFile to compare.
   * @param md2 the second MarkdownFile to compare.
   * @return a negative integer if md1 is less than md2, zero if they are equal,
   *     or a positive integer if md1 is greater than md2.
   */
  int compare(MarkdownFile md1, MarkdownFile md2);
}
