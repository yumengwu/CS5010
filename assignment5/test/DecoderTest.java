import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import decoder.Decoder;
import decoder.DecoderImpl;

/**
 * A Junit test class for Decoder interface.
 */
public class DecoderTest {
  private Decoder decoder;

  @Test
  public void testIllegalConstructInput1() {
    try {
      decoder = new DecoderImpl(null);
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testIllegalConstructInput2() {
    try {
      decoder = new DecoderImpl("");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testIllegalConstructInput3() {
    try {
      decoder = new DecoderImpl("011");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testValidConstructInput1() {
    try {
      decoder = new DecoderImpl("0");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testValidConstructInput2() {
    try {
      decoder = new DecoderImpl("01");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testValidConstructInput3() {
    try {
      decoder = new DecoderImpl("0123456789abcdef");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testInvalidAddCode1() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('\0', "00");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidAddCode2() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', "00");
      decoder.addCode('b', "00");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidAddCode3() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', null);
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidAddCode4() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', "");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidAddCode5() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', "100");
      decoder.addCode('b', "1000");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidAddCode6() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', "2100");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidAddCode7() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', "1002");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testValidAddCode1() {
    decoder = new DecoderImpl("01");
    try {
      decoder.addCode('a', "100");
      decoder.addCode('b', "101");
      decoder.addCode('c', "001");
      decoder.addCode('d', "11");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testValidAddCode2() {
    decoder = new DecoderImpl("0123");
    try {
      decoder.addCode('a', "121");
      decoder.addCode('b', "301");
      decoder.addCode('c', "003");
      decoder.addCode('d', "11");
    }
    catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testAllCodes1() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    assertEquals("a:00\nb:010\nc:011\nd:10\ne:110\nf:1110\ng:1111", decoder.allCodes());
  }

  @Test
  public void testAllCodes2() {
    decoder = new DecoderImpl("01");
    assertEquals("", decoder.allCodes());
  }

  @Test
  public void testAllCodes3() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('a', "10");
    decoder.addCode('e', "110");
    decoder.addCode('a', "1110");
    decoder.addCode('b', "1111");
    assertEquals("a:00\nb:010\nc:011\na:10\ne:110\na:1110\nb:1111", decoder.allCodes());
  }

  @Test
  public void testAllCodes4() {
    decoder = new DecoderImpl("0123");
    decoder.addCode('a', "003");
    decoder.addCode('b', "020");
    decoder.addCode('c', "111");
    decoder.addCode('d', "13");
    decoder.addCode('e', "210");
    decoder.addCode('f', "2130");
    decoder.addCode('g', "33333");
    assertEquals("a:003\nb:020\nc:111\nd:13\ne:210\nf:2130\ng:33333", decoder.allCodes());
  }

  @Test
  public void testIsComplete1() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    assertEquals(true, decoder.isCodeComplete());
  }

  @Test
  public void testIsComplete2() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    assertEquals(false, decoder.isCodeComplete());
  }

  @Test
  public void testIsComplete3() {
    decoder = new DecoderImpl("01");
    assertEquals(false, decoder.isCodeComplete());
  }

  @Test
  public void testInvalidDecode1() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    try {
      decoder.decode(null);
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidDecode2() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    try {
      decoder.decode("");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidDecode3() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    try {
      decoder.decode("0");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testInvalidDecode4() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    try {
      decoder.decode("00000");
      fail();
    }
    catch (IllegalStateException e) {
      //
    }
  }

  @Test
  public void testValidDecode2() {
    decoder = new DecoderImpl("01");
    decoder.addCode('a', "00");
    decoder.addCode('b', "010");
    decoder.addCode('c', "011");
    decoder.addCode('d', "10");
    decoder.addCode('e', "110");
    decoder.addCode('f', "1110");
    decoder.addCode('g', "1111");
    try {
      assertEquals("a", decoder.decode("00"));
      assertEquals("b", decoder.decode("010"));
      assertEquals("c", decoder.decode("011"));
      assertEquals("d", decoder.decode("10"));
      assertEquals("e", decoder.decode("110"));
      assertEquals("f", decoder.decode("1110"));
      assertEquals("g", decoder.decode("1111"));
      assertEquals("gfabcde", decoder.decode("111111100001001110110"));
    }
    catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testValidDecode3() {
    decoder = new DecoderImpl("0123");
    decoder.addCode('a', "0");
    decoder.addCode('b', "1001");
    decoder.addCode('c', "102");
    decoder.addCode('d', "123");
    decoder.addCode('e', "2010");
    decoder.addCode('f', "2323");
    decoder.addCode('g', "2333");
    try {
      assertEquals("a", decoder.decode("0"));
      assertEquals("b", decoder.decode("1001"));
      assertEquals("c", decoder.decode("102"));
      assertEquals("d", decoder.decode("123"));
      assertEquals("e", decoder.decode("2101"));
      assertEquals("f", decoder.decode("2323"));
      assertEquals("g", decoder.decode("2333"));
      assertEquals("gfabcde", decoder.decode("23332323010011021232101"));
    }
    catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }
}