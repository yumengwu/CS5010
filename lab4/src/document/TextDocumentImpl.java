package document;

/**
 * This class implements TextDocument interface. A text document can find its word count,
 *  * return the string, and find the largest common part with another text document.
 */
public class TextDocumentImpl implements TextDocument {
  private String text;

  /**
   * Construct a TextDocumentImpl object with given string. If string is null, this method
   * will throw IllegalArgumentException.
   * @param str given string to initialize the document
   */
  public TextDocumentImpl(String str) {
    if (str == null) {
      throw new IllegalArgumentException("String cannot be null.");
    }
    this.text = str;
  }

  /**
   * Return the number of words in the document.
   * @return number of words
   */
  public int getWordCount() {
    String [] subString = this.text.trim().split("\\s+");
    return subString.length;
  }

  /**
   * Return the text in this document as a String.
   * @return text as a String
   */
  public String getText() {
    return this.text;
  }

  /**
   * Return the longest common substring between the text of the two documents as a string.
   * @param other another text document object
   * @return the longest common substring
   */
  public String commonSubText(TextDocument other) {
    int m = this.text.length();
    String otherString = other.getText();
    int n = otherString.length();
    if (m == 0 || n == 0) {
      return "";
    }
    int [][]lcs = new int[m + 1][n + 1];
    for (int i = 0; i <= m; ++i) {
      for (int j = 0; j <= n; ++j) {
        lcs[i][j] = 0;
      }
    }
    for (int i = 1; i <= m; ++i) {
      if (this.text.charAt(i - 1) == otherString.charAt(1)) {
        lcs[i][1] = 1;
      }
    }
    for (int j = 1; j <= n; ++j) {
      if (this.text.charAt(1) == otherString.charAt(j - 1)) {
        lcs[1][j] = 1;
      }
    }
    for (int i = 1; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) {
        if (this.text.charAt(i - 1) == otherString.charAt(j - 1)) {
          lcs[i][j] = lcs[i - 1][j - 1] + 1;
        }
      }
    }
    StringBuilder sb = new StringBuilder();
    int i = m;
    int j = n;
    int maxVal = 0;
    for (int ii = 1; ii <= m; ++ii) {
      for (int jj = 1; jj <= n; ++jj) {
        if (lcs[ii][jj] > maxVal) {
          i = ii;
          j = jj;
          maxVal = lcs[ii][jj];
        }
      }
    }
    while (i != 0 && j != 0) {
      if (this.text.charAt(i - 1) == otherString.charAt(j - 1)) {
        sb.append(this.text.charAt(i - 1));
        --i;
        --j;
      }
      else {
        break;
      }
    }
    return sb.reverse().toString();
  }
}
