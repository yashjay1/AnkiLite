package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

/**
 * This class implements the FileVisitor interface to retrieve
 * all MarkDown files with the .md extension within a directory
 * and any directories in the given directory.
 * The paths of the MarkDown files are stored in the ArrayList mdFiles.
 */
class FileRetriever implements FileVisitor<Path> {

  private ArrayList<MarkdownFile> mdFiles;

  FileRetriever() {
    mdFiles = new ArrayList<MarkdownFile>();
  }

  /**
   * Used for returning the list of paths of .md files when called.
   *
   * @return the list of paths of .md files (Markdown files)
   */
  public ArrayList<MarkdownFile> getFile() {
    return mdFiles;
  }

  /**
   * Visits a file when walking through a directory.
   *
   * @param file the path of the file that was visited.
   * @param attr the file's basic attributes
   * @return Indicates to the file visitor algorithm if it should stop or continue.
   *     It is the result of the visited file.
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    if (attr.isRegularFile() && file.getFileName().toString().endsWith(".md")) {
      MarkdownFile currentMarkdownFile = createMarkdownFile(file, attr);
      mdFiles.add(currentMarkdownFile);

    }
    return CONTINUE;
  }

  /**
   * Specifies what the visitor should do after going through a directory.
   *
   * @param dir  a reference to the directory
   * @param exec {@code null} if the iteration of the directory completes without
   *             an error; otherwise the I/O exception that caused the iteration
   *             of the directory to complete prematurely
   * @return Indicates to the file visitor algorithm continue the file visiting process.
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exec) {
    return CONTINUE;
  }

  /**
   * Specifies what the visitor should do before going through a directory.
   *
   * @param dir   a reference to the directory
   * @param attrs the directory's basic attributes
   * @return Indicates to the file visitor algorithm continue the file visiting process.
   * @throws IOException throws an exception when there's an input issue before visit
   *                     the dir.
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
      throws IOException {
    return CONTINUE;
  }

  /**
   * Specifies what the visitor should do if the process of file visiting fails.
   *
   * @param file a reference to the file
   * @param exc  the I/O exception that prevented the file from being visited
   * @return Indicates to the file visitor algorithm continue the file visiting process
   *     after printing the exception.
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    System.err.println("File visit failed.\n" + exc);
    return CONTINUE;
  }

  /**
   * Creates a new MarkdownFile object based on the specified file path and basic file
   * attributes.
   *
   * @param file The path of the file.
   * @param attr The basic file attributes of the file.
   * @return A new MarkdownFile object with the specified creation time, last modified
   *     time, name, and path.
   */
  public MarkdownFile createMarkdownFile(Path file, BasicFileAttributes attr) {
    FileTime createdAt = attr.creationTime();
    FileTime lastModified = attr.lastModifiedTime();
    String path = file.toString();
    String name = new File(path).getName();

    return new MarkdownFile(createdAt, lastModified, name, path);
  }
}

