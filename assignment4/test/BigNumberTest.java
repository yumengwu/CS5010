import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bignumber.BigNumber;
import bignumber.BigNumberImpl;

/**
 * A Junit class for BigNumber interface.
 */
public class BigNumberTest {
  @Test
  public void testConstructorWithNoArgument() {
    BigNumber bigNumber = new BigNumberImpl();
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testConstructWithValidArgument1() {
    BigNumber bigNumber = new BigNumberImpl("0");
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testConstructWithValidArgument2() {
    BigNumber bigNumber = new BigNumberImpl("00");
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testConstructWithValidArgument3() {
    BigNumber bigNumber = new BigNumberImpl("001");
    assertEquals("1", bigNumber.toString());
  }

  @Test
  public void testConstructWithValidArgument4() {
    BigNumber bigNumber = new BigNumberImpl("1234567890");
    assertEquals("1234567890", bigNumber.toString());
  }

  @Test
  public void testConstructWithValidArgument5() {
    BigNumber bigNumber = new BigNumberImpl("123456789012345678901234567890");
    assertEquals("123456789012345678901234567890", bigNumber.toString());
  }

  @Test
  public void testConstructWithInvalidArgument1() {
    try {
      BigNumber bigNumber = new BigNumberImpl("");
      assertEquals("0", bigNumber.toString());
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal number", e.getMessage());
    }
  }

  @Test
  public void testConstructWithInvalidArgument2() {
    try {
      BigNumber bigNumber = new BigNumberImpl("0-");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid character: -", e.getMessage());
    }
  }

  @Test
  public void testConstructWithInvalidArgument3() {
    try {
      BigNumber bigNumber = new BigNumberImpl("12345@");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid character: @", e.getMessage());
    }
  }

  @Test
  public void testConstructWithInvalidArgument4() {
    try {
      BigNumber bigNumber = new BigNumberImpl("1234-00");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid character: -", e.getMessage());
    }
  }

  @Test
  public void testConstructWithInvalidArgument5() {
    try {
      BigNumber bigNumber = new BigNumberImpl("0$01234");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid character: $", e.getMessage());
    }
  }

  @Test
  public void testLength1() {
    BigNumber bigNumber = new BigNumberImpl();
    assertEquals(1, bigNumber.length());
  }

  @Test
  public void testLength2() {
    BigNumber bigNumber = new BigNumberImpl("0");
    assertEquals(1, bigNumber.length());
  }

  @Test
  public void testLength3() {
    BigNumber bigNumber = new BigNumberImpl("9");
    assertEquals(1, bigNumber.length());
  }

  @Test
  public void testLength4() {
    BigNumber bigNumber = new BigNumberImpl("123456789");
    assertEquals(9, bigNumber.length());
  }

  @Test
  public void testLength5() {
    BigNumber bigNumber = new BigNumberImpl("123456789987654321123456789");
    assertEquals(27, bigNumber.length());
  }

  @Test
  public void testShiftLeftZero1() {
    BigNumber bigNumber = new BigNumberImpl("0");
    bigNumber.shiftLeft(0);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftLeftZero2() {
    BigNumber bigNumber = new BigNumberImpl("0");
    bigNumber.shiftLeft(3);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftLeftZero3() {
    BigNumber bigNumber = new BigNumberImpl("0");
    bigNumber.shiftLeft(-3);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithPositiveArgument1() {
    BigNumber bigNumber = new BigNumberImpl("123");
    bigNumber.shiftLeft(0);
    assertEquals("123", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithPositiveArgument2() {
    BigNumber bigNumber = new BigNumberImpl("123");
    bigNumber.shiftLeft(2);
    assertEquals("12300", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithPositiveArgument3() {
    BigNumber bigNumber = new BigNumberImpl("1989");
    bigNumber.shiftLeft(20);
    assertEquals("198900000000000000000000", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithPositiveArgument4() {
    BigNumber bigNumber = new BigNumberImpl("12345678901234567890");
    bigNumber.shiftLeft(30);
    assertEquals("12345678901234567890000000000000000000000000000000",
            bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithNegativeArgument1() {
    BigNumber bigNumber = new BigNumberImpl("123");
    bigNumber.shiftLeft(-1);
    assertEquals("12", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithNegativeArgument2() {
    BigNumber bigNumber = new BigNumberImpl("123");
    bigNumber.shiftLeft(-5);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftLeftWithNegativeArgument3() {
    BigNumber bigNumber = new BigNumberImpl("12345678901234567890");
    bigNumber.shiftLeft(-20);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftLeftMultiTimes1() {
    BigNumber bigNumber = new BigNumberImpl("123");
    for (int i = 0; i < 10; ++i) {
      bigNumber.shiftLeft(2);
    }
    assertEquals("12300000000000000000000", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNonNegativeArgument1() {
    BigNumber bigNumber = new BigNumberImpl("0");
    bigNumber.shiftRight(0);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNonNegativeArgument2() {
    BigNumber bigNumber = new BigNumberImpl("12345");
    bigNumber.shiftRight(0);
    assertEquals("12345", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNonNegativeArgument3() {
    BigNumber bigNumber = new BigNumberImpl("1111111111");
    bigNumber.shiftRight(5);
    assertEquals("11111", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNonNegativeArgument4() {
    BigNumber bigNumber = new BigNumberImpl("1234567890123456789012345");
    bigNumber.shiftRight(25);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNonNegativeArgument5() {
    BigNumber bigNumber = new BigNumberImpl("1234567890123456789012345");
    bigNumber.shiftRight(35);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNegativeArgument1() {
    BigNumber bigNumber = new BigNumberImpl("0");
    bigNumber.shiftRight(-5);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNegativeArgument2() {
    BigNumber bigNumber = new BigNumberImpl("1234");
    bigNumber.shiftRight(-5);
    assertEquals("123400000", bigNumber.toString());
  }

  @Test
  public void testShiftRightWithNegativeArgument3() {
    BigNumber bigNumber = new BigNumberImpl("1234567890123456789012345678901234567890");
    bigNumber.shiftRight(-20);
    assertEquals("123456789012345678901234567890123456789000000000000000000000",
            bigNumber.toString());
  }

  @Test
  public void testShiftRightMultiTimes1() {
    BigNumber bigNumber = new BigNumberImpl("1234567890123456789012345678901234567890");
    for (int i = 0; i < 15; ++i) {
      bigNumber.shiftRight(2);
    }
    assertEquals("1234567890", bigNumber.toString());
  }

  @Test
  public void testShiftRightMultiTimes2() {
    BigNumber bigNumber = new BigNumberImpl("1234567890123456789012345678901234567890");
    for (int i = 0; i < 20; ++i) {
      bigNumber.shiftRight(2);
    }
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithoutInvalidDigit1() {
    BigNumber bigNumber = new BigNumberImpl("1234");
    try {
      bigNumber.addDigit(-1);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid digit: digit must be between 0~9", e.getMessage());
    }
  }

  @Test
  public void testAddDigitWithoutInvalidDigit2() {
    BigNumber bigNumber = new BigNumberImpl("1234");
    try {
      bigNumber.addDigit(10);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid digit: digit must be between 0~9", e.getMessage());
    }
  }

  @Test
  public void testAddDigitWithoutCarry1() {
    BigNumber bigNumber = new BigNumberImpl("0");
    bigNumber.addDigit(0);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithoutCarry2() {
    BigNumber bigNumber = new BigNumberImpl();
    bigNumber.addDigit(0);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithoutCarry3() {
    BigNumber bigNumber = new BigNumberImpl();
    bigNumber.addDigit(5);
    assertEquals("5", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithoutCarry4() {
    BigNumber bigNumber = new BigNumberImpl("12345678901234567890");
    bigNumber.addDigit(3);
    assertEquals("12345678901234567893", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithCarry1() {
    BigNumber bigNumber = new BigNumberImpl("9");
    bigNumber.addDigit(1);
    assertEquals("10", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithCarry2() {
    BigNumber bigNumber = new BigNumberImpl("123456789");
    bigNumber.addDigit(5);
    assertEquals("123456794", bigNumber.toString());
  }

  @Test
  public void testAddDigitWithCarry3() {
    BigNumber bigNumber = new BigNumberImpl("99999999");
    bigNumber.addDigit(1);
    assertEquals("100000000", bigNumber.toString());
  }

  @Test
  public void testAddDigitMultiTimes1() {
    BigNumber bigNumber = new BigNumberImpl("9");
    for (int i = 0; i < 100; ++i) {
      bigNumber.addDigit(9);
    }
    assertEquals("909", bigNumber.toString());
  }

  @Test
  public void testAddDigitMultiTimes2() {
    BigNumber bigNumber = new BigNumberImpl("10000");
    for (int i = 0; i < 200; ++i) {
      bigNumber.addDigit(9);
    }
    assertEquals("11800", bigNumber.toString());
  }

  @Test
  public void testGetDigitWithInvalidIndex1() {
    BigNumber bigNumber = new BigNumberImpl();
    int res = bigNumber.getDigitAt(1);
    assertEquals(0, res);
  }

  @Test
  public void testGetDigitWithInvalidIndex2() {
    BigNumber bigNumber = new BigNumberImpl();
    try {
      int res = bigNumber.getDigitAt(-2);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testGetDigitWithInvalidIndex3() {
    BigNumber bigNumber = new BigNumberImpl("1234567890");
    int res = bigNumber.getDigitAt(10);
    assertEquals(0, res);
  }

  @Test
  public void testGetDigitWithInvalidIndex4() {
    BigNumber bigNumber = new BigNumberImpl("1234567890");
    try {
      int res = bigNumber.getDigitAt(-1);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testGetDigitWithValidIndex1() {
    BigNumber bigNumber = new BigNumberImpl();
    try {
      int res = bigNumber.getDigitAt(0);
      assertEquals(0, res);
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGetDigitWithValidIndex2() {
    BigNumber bigNumber = new BigNumberImpl("1234567890");
    try {
      int res = bigNumber.getDigitAt(9);
      assertEquals(1, res);
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testGet1() {
    BigNumber bigNumber = new BigNumberImpl();
    bigNumber.shiftRight(1);
    bigNumber.shiftLeft(1);
    bigNumber.shiftLeft(1);
    assertEquals(0, bigNumber.getDigitAt(0));
  }

  @Test
  public void testAdd1() {
    BigNumber bigNumber = new BigNumberImpl();
    BigNumber other = new BigNumberImpl("0");
    bigNumber = bigNumber.add(other);
    assertEquals("0", bigNumber.toString());
  }

  @Test
  public void testAdd2() {
    BigNumber bigNumber = new BigNumberImpl("99999");
    BigNumber other = new BigNumberImpl("20");
    bigNumber = bigNumber.add(other);
    assertEquals("100019", bigNumber.toString());
  }

  @Test
  public void testAdd3() {
    BigNumber bigNumber = new BigNumberImpl("1234567");
    BigNumber other = new BigNumberImpl("7654321");
    BigNumber result = bigNumber.add(other);
    assertEquals("8888888", result.toString());
    result = other.add(bigNumber);
    assertEquals("8888888", result.toString());
  }

  @Test
  public void testAdd4() {
    BigNumber bigNumber = new BigNumberImpl("1414213562");
    BigNumber other = new BigNumberImpl("314159265358979323846264");
    bigNumber = bigNumber.add(other);
    assertEquals("314159265358980738059826", bigNumber.toString());
  }

  @Test
  public void testAdd5() {
    BigNumber bigNumber = new BigNumberImpl("12345");
    BigNumber other = new BigNumberImpl();
    bigNumber = bigNumber.add(other);
    assertEquals("12345", bigNumber.toString());
  }

  @Test
  public void testAddMultiTimes1() {
    BigNumber bigNumber = new BigNumberImpl("90929742682568171411200080598672");
    BigNumber other = new BigNumberImpl("69897000433601876457513110645907");
    assertEquals("90929742682568171411200080598672", bigNumber.toString());
    assertEquals("69897000433601876457513110645907", other.toString());
    for (int i = 0; i < 3; ++i) {
      bigNumber = bigNumber.add(bigNumber);
    }
    for (int i = 0; i < 5; ++i) {
      bigNumber = bigNumber.add(other);
    }
    assertEquals("1076922943628554753577166198018911", bigNumber.toString());
  }

  @Test
  public void testCopy1() {
    BigNumber bigNumber = new BigNumberImpl("12345");
    BigNumber other = bigNumber.copy();
    assertEquals("12345", other.toString());
  }

  @Test
  public void testCopy2() {
    BigNumber bigNumber = new BigNumberImpl();
    BigNumber other = bigNumber.copy();
    assertEquals("0", other.toString());
  }

  @Test
  public void testCopy3() {
    BigNumber bigNumber = new BigNumberImpl("713203972898282067932365875624223");
    BigNumber other = bigNumber.copy();
    assertEquals("713203972898282067932365875624223", other.toString());
  }

  @Test
  public void testCompareEqual1() {
    BigNumber bigNumber = new BigNumberImpl("12345");
    BigNumber other = new BigNumberImpl("12345");
    assertEquals(0, bigNumber.compareTo(other));
    assertEquals(0, other.compareTo(bigNumber));
    assertEquals(0, bigNumber.compareTo(bigNumber));
  }

  @Test
  public void testCompareEqual2() {
    BigNumber bigNumber = new BigNumberImpl("0");
    BigNumber other = new BigNumberImpl("0");
    assertEquals(0, bigNumber.compareTo(other));
    assertEquals(0, other.compareTo(bigNumber));
    assertEquals(0, bigNumber.compareTo(bigNumber));
  }

  @Test
  public void testCompareEqual3() {
    BigNumber bigNumber = new BigNumberImpl("0");
    BigNumber other = new BigNumberImpl();
    assertEquals(0, bigNumber.compareTo(other));
  }

  @Test
  public void testCompareEqual4() {
    BigNumber bigNumber = new BigNumberImpl("713203972898282067932365875624223");
    BigNumber other = new BigNumberImpl("713203972898282067932365875624223");
    assertEquals(0, bigNumber.compareTo(other));
  }

  @Test
  public void testCompareUnequalWithSameLength1() {
    BigNumber bigNumber = new BigNumberImpl("0");
    BigNumber other = new BigNumberImpl("2");
    assertEquals(-1, bigNumber.compareTo(other));
    assertEquals(1, other.compareTo(bigNumber));
  }

  @Test
  public void testCompareUnequalWithSameLength2() {
    BigNumber bigNumber = new BigNumberImpl("713203972898282067932365875624223");
    BigNumber other = new BigNumberImpl("713203972898282067932365875624222");
    assertEquals(1, bigNumber.compareTo(other));
    assertEquals(-1, other.compareTo(bigNumber));
  }

  @Test
  public void testCompareUnequalWithDifferentLength3() {
    BigNumber bigNumber = new BigNumberImpl("713203972898282067932365875624223");
    BigNumber other = new BigNumberImpl("7132039728982820679323658756242");
    assertEquals(1, bigNumber.compareTo(other));
    assertEquals(-1, other.compareTo(bigNumber));
  }

  @Test
  public void testCompareUnequalWithDifferentLength4() {
    BigNumber bigNumber = new BigNumberImpl("180000000000");
    BigNumber other = new BigNumberImpl("179999999999");
    assertEquals(1, bigNumber.compareTo(other));
    assertEquals(-1, other.compareTo(bigNumber));
  }

  @Test
  public void testEqual1() {
    BigNumber bigNumber = new BigNumberImpl();
    BigNumber other = new BigNumberImpl();
    assertEquals(true, bigNumber.equals(other));
  }

  @Test
  public void testEqual2() {
    BigNumber bigNumber = new BigNumberImpl();
    BigNumber other = new BigNumberImpl("0");
    assertEquals(true, bigNumber.equals(other));
  }

  @Test
  public void testEqual3() {
    BigNumber bigNumber = new BigNumberImpl("12345");
    BigNumber other = new BigNumberImpl("10");
    assertEquals(false, bigNumber.equals(other));
  }
}