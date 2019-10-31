package bignumber;

/**
 * This interface represent a BigNumber interface. A BigNumber could get number of its digits,
 * left shift, right shift, add a single digit, add another BigNumber, get digit at index, copy
 * this BigNumber, and compare with another BigNumber.
 */
public interface BigNumber {
  /**
   * Return the number of digits in this BigNumber.
   *
   * @return the number of digits in this BigNumber
   */
  int length();

  /**
   * Left Shift this BigNumber by argument. If argument is negative, the result is like right shift
   * that numbers. This method will change the object itself.
   *
   * @param num number of left shift
   */
  void shiftLeft(int num);

  /**
   * Right Shift this BigNumber by argument. If argument is negative, the result is like left shift
   * that numbers. If current length is 1 and num is greater or equal to 1, the result will be 0.
   * This method will change the object itself.
   *
   * @param num number of right shift
   */
  void shiftRight(int num);

  /**
   * Add a single digit to this number. If digit is invalid, this method will throw
   * IllegalArgumentException. This method will change object itself.
   *
   * @param digit the single digit add to this number
   * @throws IllegalArgumentException throws if digit is invalid
   */
  void addDigit(int digit) throws IllegalArgumentException;

  /**
   * Get digit at index. If index is out of range, this method will throw IllegalArgumentException.
   *
   * @param index the index of which digit will return
   * @return the digit at index
   * @throws IllegalArgumentException throws if index if out of range
   */
  int getDigitAt(int index) throws IllegalArgumentException;

  /**
   * Copy an identical and independent copy of this number.
   *
   * @return copy of this number
   */
  BigNumber copy();

  /**
   * Add another to this number and return the sum. This method will not change these two number.
   *
   * @param bigNumber another number to add
   * @return the sum of two number
   */
  BigNumber add(BigNumber bigNumber);

  /**
   * Compare two numbers.
   *
   * @param bigNumber another number to compare
   * @return -1 if this number is less, 1 if this number is greater, and 0 if two numbers are equal
   */
  int compareTo(BigNumber bigNumber);

}
