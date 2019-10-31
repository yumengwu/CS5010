import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import document.TextDocument;
import document.TextDocumentImpl;

/**
 * A JUnit test class for TextDocument interface.
 */
public class TextDocumentTest {
  @Test
  public void testConstructWithNull() {
    try {
      TextDocument td = new TextDocumentImpl(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("String cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testGetText1() {
    TextDocument td = new TextDocumentImpl("");
    assertEquals("", td.getText());
  }

  @Test
  public void testGetText2() {
    TextDocument td = new TextDocumentImpl("abcde");
    assertEquals("abcde", td.getText());
  }

  @Test
  public void testWordCount1() {
    TextDocument td = new TextDocumentImpl("");
    assertEquals(1, td.getWordCount());
  }

  @Test
  public void testWordCount2() {
    TextDocument td = new TextDocumentImpl("ab");
    assertEquals(1, td.getWordCount());
  }

  @Test
  public void testWordCount3() {
    TextDocument td = new TextDocumentImpl("abcd ");
    assertEquals(1, td.getWordCount());
  }

  @Test
  public void testWordCount4() {
    TextDocument td = new TextDocumentImpl("  ab");
    assertEquals(1, td.getWordCount());
  }

  @Test
  public void testWordCount5() {
    TextDocument td = new TextDocumentImpl("ab ab  cd");
    assertEquals(3, td.getWordCount());
  }

  @Test
  public void testWordCount6() {
    TextDocument td = new TextDocumentImpl(" a b  c    d ");
    assertEquals(4, td.getWordCount());
  }

  @Test
  public void testCommonPart1() {
    TextDocument td1 = new TextDocumentImpl("");
    TextDocument td2 = new TextDocumentImpl("");
    assertEquals("", td1.commonSubText(td2));
  }

  @Test
  public void testCommonPart2() {
    TextDocument td1 = new TextDocumentImpl("");
    TextDocument td2 = new TextDocumentImpl("abcd");
    assertEquals("", td1.commonSubText(td2));
  }

  @Test
  public void testCommonPart3() {
    TextDocument td1 = new TextDocumentImpl("abcd");
    TextDocument td2 = new TextDocumentImpl("");
    assertEquals("", td1.commonSubText(td2));
  }

  @Test
  public void testCommonPart4() {
    TextDocument td1 = new TextDocumentImpl("abcde");
    TextDocument td2 = new TextDocumentImpl("fghi");
    assertEquals("", td1.commonSubText(td2));
  }

  @Test
  public void testCommonPart5() {
    TextDocument td1 = new TextDocumentImpl("abcdefg");
    TextDocument td2 = new TextDocumentImpl("bcd");
    assertEquals("bcd", td1.commonSubText(td2));
    assertEquals("bcd", td2.commonSubText(td1));
  }

  @Test
  public void testCommonPart6() {
    TextDocument td1 = new TextDocumentImpl("Process finished with exit code 0");
    TextDocument td2 = new TextDocumentImpl("Process failed with exit code 0");
    assertEquals("ed with exit code 0", td1.commonSubText(td2));
    assertEquals("ed with exit code 0", td2.commonSubText(td1));
  }
}