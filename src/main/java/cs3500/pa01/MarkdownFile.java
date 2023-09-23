package cs3500.pa01;

import java.nio.file.attribute.FileTime;

/**
 * Represents a markdown file with information about its creation time, last modification time,
 * name, and path.
 */
public class MarkdownFile {

  private FileTime createdAt;
  private FileTime lastModified;
  private String name;
  private String path;

  /**
   * Constructs a MarkdownFile object representing an actual file.
   *
   * @param createdAt     the creation time of the file
   * @param lastModified  the last modification time of the file
   * @param name          the name of the file
   * @param path          the path of the file
   */
  public MarkdownFile(FileTime createdAt, FileTime lastModified, String name, String path) {
    this.createdAt = createdAt;
    this.lastModified = lastModified;
    this.name = name;
    this.path = path;
  }

  /**
   * Constructs a MarkdownFile object for testing the lastModifiedFlag and createdAtFlag.
   *
   * @param time  the file time to be used for both creation and last modification times
   */
  public MarkdownFile(FileTime time) {
    this.createdAt = time;
    this.lastModified = time;
    this.name = null;
    this.path = null;
  }

  /**
   * Constructs a MarkdownFile object for testing the filenameFlag.
   *
   * @param name  the name of the file
   */
  public MarkdownFile(String name) {
    this.createdAt = null;
    this.lastModified = null;
    this.name = name;
    this.path = null;
  }

  /**
   * Returns the creation time of the markdown file.
   *
   * @return the creation time of the file
   */
  public FileTime getCreatedAt() {
    return this.createdAt;
  }

  /**
   * Returns the last modification time of the markdown file.
   *
   * @return the last modification time of the file
   */
  public FileTime getLastModified() {
    return this.lastModified;
  }

  /**
   * Returns the name of the markdown file.
   *
   * @return the name of the file
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the path of the markdown file.
   *
   * @return the path of the file
   */
  public String getPath() {
    return this.path;
  }
}
