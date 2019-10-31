import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.math.BigInteger;

import lookandsay.LookAndSayIterator;
import lookandsay.RIterator;

/**
 * A JUnit test class for RIterator interface.
 */
public class RIteratorTest {
  @Test
  public void testConstructWithNoArg() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator();
    assertEquals("1", bigIntegerRIterator.next().toString());
  }

  @Test
  public void testConstructWithOneArg1() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(new BigInteger("1"));
    assertEquals("1", bigIntegerRIterator.next().toString());
  }

  @Test
  public void testConstructWithOneArg2() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(new BigInteger("1234"));
    assertEquals("1234", bigIntegerRIterator.next().toString());
  }

  @Test
  public void testConstructWithOneArg3() {
    try {
      RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(new BigInteger("-1"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start seed must be positive.", e.getMessage());
    }
  }

  @Test
  public void testConstructWithOneArg4() {
    StringBuilder sb = new StringBuilder();
    sb.append("1");
    for (int i = 0; i < 100; ++i) {
      sb.append("0");
    }
    try {
      RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(
              new BigInteger(sb.toString()));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start seed must less than end value.", e.getMessage());
    }
  }

  @Test
  public void testConstructWithTwoArgs1() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(
            new BigInteger("123"), new BigInteger("99999999999999999999999999999"));
    assertEquals("123", bigIntegerRIterator.next().toString());
  }

  @Test
  public void testConstructWithTwoArgs2() {
    try {
      RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(
              new BigInteger("-11"), new BigInteger("99999999999999999999999999999"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start seed must be positive.", e.getMessage());
    }
  }

  @Test
  public void testConstructWithTwoArgs3() {
    try {
      RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(
              new BigInteger("1234567"), new BigInteger("123456"));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Start seed must less than end value.", e.getMessage());
    }
  }

  @Test
  public void testNext1() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator();
    assertEquals("1", bigIntegerRIterator.next().toString());
    assertEquals("11", bigIntegerRIterator.next().toString());
    assertEquals("21", bigIntegerRIterator.next().toString());
    assertEquals("1211", bigIntegerRIterator.next().toString());
    assertEquals("111221", bigIntegerRIterator.next().toString());
    assertEquals("312211", bigIntegerRIterator.next().toString());
  }

  @Test
  public void testNext2() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(new BigInteger("123"));
    assertEquals("123", bigIntegerRIterator.next().toString());
    assertEquals("111213", bigIntegerRIterator.next().toString());
    assertEquals("31121113", bigIntegerRIterator.next().toString());
    assertEquals("1321123113", bigIntegerRIterator.next().toString());
    assertEquals("1113122112132113", bigIntegerRIterator.next().toString());
    assertEquals("3113112221121113122113", bigIntegerRIterator.next().toString());
    assertEquals("13211321322112311311222113", bigIntegerRIterator.next().toString());
  }

  @Test
  public void testHasNext1() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(
            new BigInteger("1"), new BigInteger("312210"));
    assertEquals(true, bigIntegerRIterator.hasNext());
    bigIntegerRIterator.next();
    assertEquals(true, bigIntegerRIterator.hasNext());
    bigIntegerRIterator.next();
    assertEquals(true, bigIntegerRIterator.hasNext());
    bigIntegerRIterator.next();
    assertEquals(true, bigIntegerRIterator.hasNext());
    bigIntegerRIterator.next();
    assertEquals(true, bigIntegerRIterator.hasNext());
    bigIntegerRIterator.next();
    assertEquals(false, bigIntegerRIterator.hasNext());
  }

  @Test
  public void testPrev1() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator();
    assertEquals("1", bigIntegerRIterator.prev().toString());
    for (int i = 0; i < 5; ++i) {
      bigIntegerRIterator.next();
    }
    assertEquals("312211", bigIntegerRIterator.prev().toString());
    assertEquals("111221", bigIntegerRIterator.prev().toString());
    assertEquals("1211", bigIntegerRIterator.prev().toString());
    assertEquals("21", bigIntegerRIterator.prev().toString());
    assertEquals("11", bigIntegerRIterator.prev().toString());
    assertEquals("1", bigIntegerRIterator.prev().toString());
    assertEquals("1", bigIntegerRIterator.prev().toString());
  }

  @Test
  public void testPrev2() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(
            new BigInteger("132113322211"));
    assertEquals("132113322211", bigIntegerRIterator.prev().toString());
    assertEquals("3113222221", bigIntegerRIterator.prev().toString());
    assertEquals("1113222211", bigIntegerRIterator.prev().toString());
    assertEquals("1322221", bigIntegerRIterator.prev().toString());
    assertEquals("1322221", bigIntegerRIterator.prev().toString());
  }

  @Test
  public void testHasPrev1() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator();
    assertEquals(false, bigIntegerRIterator.hasPrevious());
    for (int i = 0; i < 10; ++i) {
      bigIntegerRIterator.next();
    }
    assertEquals(true, bigIntegerRIterator.hasPrevious());
  }

  @Test
  public void testHasPrev2() {
    RIterator<BigInteger> bigIntegerRIterator = new LookAndSayIterator(new BigInteger("121"));
    assertEquals(false, bigIntegerRIterator.hasPrevious());
  }
}