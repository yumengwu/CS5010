import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import decoder.HuffmanEncode;
import decoder.HuffmanEncodeImpl;

/**
 * A Junit test class for HuffmanEncode interface.
 */
public class HuffmanEncodeTest {
  private HuffmanEncode he;

  @Test
  public void testIllegalConstructInput1() {
    try {
      he = new HuffmanEncodeImpl(null);
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testIllegalConstructInput2() {
    try {
      he = new HuffmanEncodeImpl("");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testIllegalConstructInput3() {
    try {
      he = new HuffmanEncodeImpl("011");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testValidConstructInput1() {
    try {
      he = new HuffmanEncodeImpl("0");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testValidConstructInput2() {
    try {
      he = new HuffmanEncodeImpl("01");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testValidConstructInput3() {
    try {
      he = new HuffmanEncodeImpl("0123456789abcdef");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testAllCodes1() {
    he = new HuffmanEncodeImpl("01");
    he.generateHuffmanCode("aaaaabbbbcccdde");
    assertEquals("c:00\ne:010\nd:011\nb:10\na:11", he.allCodes());
  }

  @Test
  public void testPassage() {
    he = new HuffmanEncodeImpl("01");
    File file = new File("passage.txt");
    Long fileLength = file.length();
    byte[] content = new byte[fileLength.intValue()];
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(content);
      in.close();
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      return;
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      return;
    }
    String msg = new String(content);
    he.generateHuffmanCode(msg);
    String decodedString1 = he.encode(msg);
    String original = he.decode(decodedString1);
    assertEquals(msg, original);
    he = new HuffmanEncodeImpl("0123456789abcdef");
    he.generateHuffmanCode(msg);
    String decodedString2 = he.encode(msg);
    original = he.decode(decodedString2);
    assertEquals(msg, original);
    try {
      FileWriter writer = new FileWriter("res/encodings.txt");
      writer.write(decodedString1);
      writer.write('\n');
      writer.write(decodedString2);
      writer.close();
    }
    catch (IOException e) {
      return;
    }
  }
}