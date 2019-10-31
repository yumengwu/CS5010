package decoder;

/**
 * This interface represent a Decoder interface. Decoding is common operations on data.
 * Given encoded data(string), decoder will transfer it back to its original message.
 * A decoder could add code by given symbol and its code, could decode from given encoded
 * string to its original string, could represent all decode codes, and could show whether
 * this decode tree is complete.
 */
public interface Decoder {
  /**
   * This method could add a symbol and its code to code tree and return nothing. A symbol
   * could have different codes, but one code has only one symbol. If symbol is '\0', or
   * code is null or empty string, or this code has another symbol to decode, or this code
   * is prefix of another code, or this code contains symbol not in code symbol set, this
   * method will throw IllegalStateException.
   * @param symbol the symbol to add, a char
   * @param code the code of symbol, a string
   * @throws IllegalStateException throws if symbol or code is illegal
   */
  void addCode(char symbol, String code) throws IllegalStateException;

  /**
   * This method will take a encoded text as string and return its original message according
   * to symbol and code from addCode method. If input is null or empty string, or input string
   * contain characters not in code symbol set or input string cannot be decoded because the
   * code is not in code tree, this method will throw IllegalStateException.
   * @param msg input string to decode
   * @return original message of input
   * @throws IllegalStateException throws if msg is null or empty string, or contain illegal
   *                               characters, or cnnot be decoded
   */
  String decode(String msg) throws IllegalStateException;

  /**
   * This method will return all the symbols and codes in code tree added by addCode. For each
   * line, if symbol is x and code is yyy, then this line will be x:yyy, with a '\n' in the end
   * of line if this line is not the last line.
   * @return all the symbols and codes in code tree.
   */
  String allCodes();

  /**
   * This method will return true if code tree is complete, or false otherwise. A code is said
   * to be complete if every valid encoded message can be successfully decoded. If the decoding
   * is done by using a coding tree, then this condition is fulfilled if the coding tree is full
   * (i.e. every non-leaf node has exactly the same number of children, equal to the number of
   * coding symbols).
   * @return true if code tree is complete, or false otherwise
   */
  boolean isCodeComplete();
}
