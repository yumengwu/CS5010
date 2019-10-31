package document;

/**
 * This interface represent a text document. A text document can find its word count,
 * return the string, and find the largest common part with another text document.
 */
public interface TextDocument {
  /**
   * Return the number of words in the document.
   * @return number of words
   */
  int getWordCount();

  /**
   * Return the text in this document as a String.
   * @return text as a String
   */
  String getText();

  /**
   * Return the longest common substring between the text of the two documents as a string.
   * @param other another text document object
   * @return the longest common substring
   */
  String commonSubText(TextDocument other);
}
