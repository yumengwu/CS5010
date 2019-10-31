package lookandsay;

import java.math.BigInteger;

/**
 * This class represent a look and say number object implements RIterator. A look-and-say
 * sequence is a sequence of numbers. Given the current number, the next number is obtained
 * by reading the current number out loud, writing what we say.
 */
public class LookAndSayIterator implements RIterator<BigInteger> {
  private final BigInteger start;
  private final BigInteger end;
  private BigInteger currentNumber;
  private final int largestDigit = 100;

  /**
   * Private method to get default largest number in look and say sequence.
   * @return default largest number in sequence
   */
  private BigInteger getLargestEndValue() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < largestDigit; ++i) {
      sb.append("9");
    }
    return new BigInteger(sb.toString());
  }

  /**
   * Construct a LookAndSayIterator object with given start seed and end value. If start seed
   * is not positive, or start seed is bigger than end value, this method will throw
   * IllegalArgumentException.
   * @param startSeed start seed
   * @param endValue end value
   */
  public LookAndSayIterator(BigInteger startSeed, BigInteger endValue) {
    if (startSeed.compareTo(new BigInteger("0")) < 0) {
      throw new IllegalArgumentException("Start seed must be positive.");
    }
    if (startSeed.compareTo(endValue) >= 0) {
      throw new IllegalArgumentException("Start seed must less than end value.");
    }
    this.start = startSeed;
    this.end = endValue;
    this.currentNumber = this.start;
  }

  /**
   * Construct a LookAndSayIterator object with given start seed and end value will be
   * initialized as default number 100 9s. If start seed is not positive, or start seed
   * is bigger than end value, this method will throw IllegalArgumentException.
   * @param startSeed start seed
   */
  public LookAndSayIterator(BigInteger startSeed) {
    if (startSeed.compareTo(new BigInteger("0")) < 0) {
      throw new IllegalArgumentException("Start seed must be positive.");
    }
    this.start = startSeed;
    this.end = getLargestEndValue();
    if (this.start.compareTo(this.end) >= 0) {
      throw new IllegalArgumentException("Start seed must less than end value.");
    }
    this.currentNumber = this.start;
  }

  /**
   * Construct a LookAndSayIterator object. Start seed will be initialized as 1, and end
   * will be initialized as 100 9s.
   */
  public LookAndSayIterator() {
    this.start = new BigInteger("1");
    this.end = getLargestEndValue();
    this.currentNumber = this.start;
  }

  /**
   * Private helper method to get next number in sequence.
   * @return next number in sequence
   */
  private BigInteger getNextNumber() {
    StringBuilder sb = new StringBuilder();
    String currentStr = this.currentNumber.toString();
    int count = 0;
    char c = currentStr.charAt(0);
    for (int i = 0; i < currentStr.length(); ++i) {
      if (currentStr.charAt(i) == c) {
        ++count;
      }
      else {
        sb.append(count).append(c);
        count = 1;
        c = currentStr.charAt(i);
      }
    }
    sb.append(count).append(c);
    return new BigInteger(sb.toString());
  }

  /**
   * Return the current number in the sequence and revert to the previous number in the sequence.
   * @return current number
   */
  public BigInteger prev() {
    if (!hasPrevious()) {
      return this.currentNumber;
    }
    StringBuilder sb = new StringBuilder();
    BigInteger curr = this.currentNumber;
    String currentStr = this.currentNumber.toString();
    for (int i = 0; i < currentStr.length(); i += 2) {
      int count = currentStr.charAt(i) - '0';
      for (int j = 0; j < count; ++j) {
        sb.append(currentStr.charAt(i + 1));
      }
    }
    this.currentNumber = new BigInteger(sb.toString());
    return curr;
  }

  /**
   * Return true if it is possible to go back one step, false otherwise.
   * @return true if it is possible to go back one step, false otherwise
   */
  public boolean hasPrevious() {
    return this.currentNumber.toString().length() % 2 == 0;
  }

  /**
   * Return the current number in the sequence and advance to the next number.
   * @return current number
   */
  public BigInteger next() {
    BigInteger curr = this.currentNumber;
    this.currentNumber = getNextNumber();
    return curr;
  }

  /**
   * Return true if the next number to be returned is still lesser than end, false otherwise.
   * @return true if the next number to be returned is still lesser than end, false otherwise
   */
  public boolean hasNext() {
    return this.currentNumber.compareTo(this.end) <= 0;
  }
}
