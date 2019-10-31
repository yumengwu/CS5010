package bignumber;

import java.util.Objects;

/**
 * This class implements methods in BigNumber interface. A BigNumberImpl could get number of its
 * digits, left shift, right shift, add a single digit, add another BigNumber, get digit at index,
 * copy this BigNumber, and compare with another BigNumber. A BigNumberImpl must start with non-
 * zero character or is number 0.
 */
public class BigNumberImpl implements BigNumber {
  private ListNode<Integer> head;

  /**
   * Construct a BigNumberImpl object. Constructor with no argument will initialize number 0.
   */
  public BigNumberImpl() {
    head = new ListElementNode<>(0, new ListEmptyNode<>());
  }

  /**
   * Construct a BigNumberImpl object. Constructor with a string argument will represent number as
   * string. The digits of this number is stored as little end. If string dose not represent not a
   * valid number, this method will throw IllegalArgumentException.
   * @param str represent a number
   * @throws IllegalArgumentException throws if string dose not represent a valid number
   */
  public BigNumberImpl(String str) throws IllegalArgumentException {
    /**
     * A string with length 0 is not a valid number.
     */
    if (str.length() == 0) {
      //throw new IllegalArgumentException("Illegal number");
      head = new ListElementNode<>(0, new ListEmptyNode<>());
    }
    else {
      head = new ListElementNode<>(str.charAt(0) - '0',
              new ListEmptyNode<>());
      int i = 0;
      /**
       * Find index of the first character in string which is not "0".
       */
      while (i < str.length() && str.charAt(i) == '0') {
        ++i;
      }
      if (i < str.length()) {
        if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
          head.set(0, str.charAt(i) - '0');
        }
        /**
         * If character is invalid digit in number, this method will throw IllegalArgumentException.
         */
        else {
          throw new IllegalArgumentException("Invalid character: " + str.charAt(i));
        }
        for (++i; i < str.length(); ++i) {
          if (str.charAt(i) < '0' || str.charAt(i) > '9') {
            throw new IllegalArgumentException("Invalid character: " + str.charAt(i));
          }
          head = head.addFront(str.charAt(i) - '0');
        }
      }
    }
  }

  /**
   * Return the number of digits in this BigNumber.
   * @return the number of digits in this BigNumber
   */
  public int length() {
    return this.head.getSize();
  }

  /**
   * returns a string representation of this number, as simply the number itself.
   * @return a string representation of this number
   */
  public String toString() {
    return this.head.toString();
  }

  /**
   * Left Shift this BigNumber by argument. If argument is negative, the result is like right
   * shift that numbers. This method will change the object itself.
   * @param num number of left shift
   */
  public void shiftLeft(int num) {
    if (num < 0) {
      this.shiftRight(-num);
    }
    if (!(this.head.next() instanceof ListEmptyNode && this.head.get(0) == 0)) {
      for (int i = 0; i < num; ++i) {
        this.head = this.head.addFront(0);
      }
    }
  }

  /**
   * Right Shift this BigNumber by argument. If argument is negative, the result is like left
   * shift that numbers. If current length is 1 and num is greater or equal to 1, the result will
   * be 0. This method will change the object itself.
   * @param num number of right shift
   */
  public void shiftRight(int num) {
    if (num < 0) {
      this.shiftLeft(-num);
    }
    else if (num >= this.length()) {
      this.head = new ListElementNode<>(0, new ListEmptyNode<>());
    }
    else {
      for (int i = 0; i < num; ++i) {
        this.head = this.head.removeFront();
      }
    }
  }

  /**
   * Add a single digit to this number. If digit is invalid, this method will throw
   * IllegalArgumentException. This method will change object itself.
   * @param digit the single digit add to this number
   * @throws IllegalArgumentException throws if digit is invalid
   */
  public void addDigit(int digit) throws IllegalArgumentException {
    if (digit >= 10 || digit < 0) {
      throw new IllegalArgumentException("Invalid digit: digit must be between 0~9");
    }
    ListNode<Integer> node = head;
    do {
      int newDigit = node.get(0) + digit;
      digit = newDigit / 10;
      node.set(0, newDigit % 10);
      if (digit > 0 && node.next() instanceof ListEmptyNode) {
        node = node.addBack(0);
        node = node.next();
      }
      else {
        node = node.next();
      }
    }
    while (digit != 0);
  }

  /**
   * Get digit at index. If index is out of range, this method will throw IllegalArgumentException.
   * @param index the index of which digit will return
   * @return the digit at index
   * @throws IllegalArgumentException throws if index if out of range
   */
  public int getDigitAt(int index) throws IllegalArgumentException {
    /*if (index < 0) {
      throw new IllegalArgumentException("Wrong index");
    }
    ListNode<Integer> node = this.head;
    for (int i = 0; i < index; ++i) {
      if (node.next() instanceof ListEmptyNode) {
        return 0;
      }
      node = node.next();
    }
    return node.get(0);*/
    Integer res = this.head.get(index);
    if (res == null) {
      return 0;
    }
    else {
      return res;
    }
  }

  /**
   * Add another to this number and return the sum. This method will not change these two number.
   * @param bigNumber another number to add
   * @return the sum of two number
   */
  public BigNumber add(BigNumber bigNumber) {
    int carry = 0;
    int digit;
    BigNumberImpl addResult = new BigNumberImpl();
    addResult.head = addResult.head.removeFront();
    /**
     * Represent current node.
     */
    ListNode<Integer> node1 = this.head;
    ListNode<Integer> node2 = ((BigNumberImpl) bigNumber).head;
    ListNode<Integer> nodeEnd = null;
    while (!(node1 instanceof ListEmptyNode) || !(node2 instanceof ListEmptyNode) || carry != 0) {
      /**
       * If one of node is Empty node and add does not finish, a empty node will treat as 0.
       */
      int b1 = node1 instanceof ListEmptyNode ? 0 : node1.get(0);
      int b2 = node2 instanceof ListEmptyNode ? 0 : node2.get(0);
      int temp = b1 + b2 + carry;
      digit = temp % 10;
      carry = temp / 10;
      if (nodeEnd == null) {
        nodeEnd = new ListElementNode<>(digit, null);
      }
      else {
        ListElementNode<Integer> tempNode = new ListElementNode<>(digit, null);
        ((ListElementNode<Integer>) nodeEnd).rest = tempNode;
        nodeEnd = nodeEnd.next();
      }
      if (addResult.head instanceof ListEmptyNode) {
        addResult.head = nodeEnd;
      }
      //((BigNumberImpl) addResult).head = ((BigNumberImpl) addResult).head.addBack(digit);
      node1 = node1.next();
      node2 = node2.next();
    }
    ((ListElementNode<Integer>) nodeEnd).rest = new ListEmptyNode<>();
    return addResult;
  }

  /**
   * Copy an identical and independent copy of this number.
   * @return copy of this number
   */
  public BigNumber copy() {
    BigNumberImpl bigNumber = new BigNumberImpl();
    bigNumber.head = this.head.map(s -> s);
    return bigNumber;
  }

  /**
   * Compare two numbers.
   * @param bigNumber another number to compare
   * @return -1 if this number is less, 1 if this number is greater, and 0 if two numbers are equal
   */
  public int compareTo(BigNumber bigNumber) {
    String num1 = this.toString();
    String num2 = bigNumber.toString();
    if (num1.length() > num2.length()) {
      return 1;
    }
    else if (num2.length() > num1.length()) {
      return -1;
    }
    else if (num1.equals(num2)) {
      return 0;
    }
    else {
      for (int i = 0; i < num1.length(); ++i) {
        if (num1.charAt(i) > num2.charAt(i)) {
          return 1;
        }
        else if (num1.charAt(i) < num2.charAt(i)) {
          return -1;
        }
      }
      return 0;
    }
  }

  /**
   * Compare two objects. If one of two objects do not implement BigNumber interface, this method
   * will return false. If both objects implement BigNumber interface, then returns true if two
   * numbers are equal, or false if two numbers are not equal.
   * @param obj another object to compare.
   * @return true if two numbers implement BigNumber and are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof BigNumberImpl)) {
      return false;
    }
    return this.compareTo((BigNumberImpl) obj) == 0;
  }

  /**
   * Return hashcode of this object.
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(this.toString());
  }
}
