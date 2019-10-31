package decoder;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

/**
 * This class implement methods in Decoder interface. offer a single constructor that takes the
 * coding symbols as a single string. This class should build a coding tree and then use it to
 * decode a message using the above process. It may not have any other public methods.
 */
public class DecoderImpl implements Decoder {
  private TreeNode root;
  private HashSet<Character> symbolSet;
  private HashSet<Character> codeSet;

  /**
   * Construct a DecoderImpl by given codeSymbol string. Each character in codeSymbol string define
   * a code symbol. If codeSymbol is null or empty string, or contains duplicate chararctors, this
   * method will throw IllegalStateException.
   * @param codeSymbol define code symbols
   */
  public DecoderImpl(String codeSymbol) {
    if (codeSymbol == null || codeSymbol.length() == 0) {
      throw new IllegalStateException("Code symbol cannot be null or empty string");
    }
    this.symbolSet = new HashSet<>();
    this.codeSet = new HashSet<>();
    this.root = new GroupNode();
    for (int i = 0; i < codeSymbol.length(); ++i) {
      if (this.codeSet.contains(codeSymbol.charAt(i))) {
        throw new IllegalStateException("Code symbol is redundant");
      }
      this.codeSet.add(codeSymbol.charAt(i));
    }
  }

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
  public void addCode(char symbol, String code) throws IllegalStateException {
    if (symbol == '\0') {
      throw new IllegalStateException("Illegal symbol");
    }
    if (code == null || code.length() == 0) {
      throw new IllegalStateException("Code cannot be null or empty string");
    }
    for (int i = 0; i < code.length(); ++i) {
      if (!this.codeSet.contains(code.charAt(i))) {
        throw new IllegalStateException("Cannot find code symbol");
      }
    }
    TreeNode current = this.root;
    for (int i = 0; i < code.length() - 1; ++i) {
      /*if (!this.codeSet.contains(code.charAt(i))) {
        throw new IllegalStateException("Cannot find code symbol");
      }*/
      if (current.getChild().get(code.charAt(i)) == null) {
        current.getChild().put(code.charAt(i), new GroupNode());
      }
      if (current.getChild().get(code.charAt(i)).isLeaf()) {
        throw new IllegalStateException("Fail to add code");
      }
      current = current.getChild().get(code.charAt(i));
    }
    char lastSymbol = code.charAt(code.length() - 1);
    if (!this.codeSet.contains(lastSymbol)) {
      throw new IllegalStateException("Cannot find code symbol");
    }
    if (current.getChild().get(lastSymbol) != null) {
      throw new IllegalStateException("Same symbol with different code already exists");
    }
    current.getChild().put(lastSymbol, new LeafNode(symbol));
    this.symbolSet.add(symbol);
  }

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
  public String decode(String msg) throws IllegalStateException {
    if (msg == null || msg.length() == 0) {
      throw new IllegalStateException("Message cannot be null or empty string");
    }
    StringBuffer code = new StringBuffer();
    TreeNode current = this.root;
    for (int i = 0; i < msg.length(); ++i) {
      if (!this.codeSet.contains(msg.charAt(i))) {
        throw new IllegalStateException("Message contains illegal symbol");
      }
      current = current.getChild().get(msg.charAt(i));
      if (current == null) {
        throw new IllegalStateException("Cannot find code");
      }
      if (current.isLeaf()) {
        code.append(current.getSymbol());
        current = this.root;
      }
    }
    if (!current.isLeaf() && current != this.root) {
      throw new IllegalStateException("Decode fail");
    }
    return code.toString();
  }

  /**
   * Private method to recursively get all codes. There are three input: a TreeNode, a List saving
   * characters from root to current node, a List store result codes.
   * @param node current node
   * @param codeList characters from root to current status
   * @param result result string list
   */
  private void allCodesHelper(TreeNode node, List<Character> codeList, List<String> result) {
    if (node.isLeaf()) {
      StringBuffer code = new StringBuffer();
      if (result.size() != 0) {
        code.append('\n');
      }
      code.append(node.getSymbol());
      code.append(":");
      for (int i = 0; i < codeList.size(); ++i) {
        code.append(codeList.get(i));
      }
      result.add(code.toString());
      codeList.remove(codeList.size() - 1);
    }
    else if (!node.isLeaf() && node.getChild().size() == 0) {
      codeList.remove(codeList.size() - 1);
    }
    else {
      for (char c : node.getChild().keySet()) {
        codeList.add(c);
        allCodesHelper(node.getChild().get(c), codeList, result);
      }
      if (codeList.size() > 0) {
        codeList.remove(codeList.size() - 1);
      }
    }
  }

  /**
   * This method will return all the symbols and codes in code tree added by addCode. For each
   * line, if symbol is x and code is yyy, then this line will be x:yyy, with a '\n' in the end
   * of line if this line is not the last line.
   * @return all the symbols and codes in code tree.
   */
  public String allCodes() {
    if (root.getChild().size() == 0) {
      return "";
    }
    List<Character> codes = new ArrayList<>();
    List<String> res = new ArrayList<>();
    allCodesHelper(this.root, codes, res);
    StringBuffer str = new StringBuffer();
    for (String string : res) {
      str.append(string);
    }
    return str.toString();
  }

  /**
   * Private method to recursively check whether this code tree starts at this node is complete.
   * Input is current TreeNode and return whether code tree of this node's children is complete.
   * @param node current node
   * @return true if code tree is complete, false otherwise
   */
  private boolean isCodeCompleteHelper(TreeNode node) {
    if (node.isLeaf()) {
      return true;
    }
    if (node.getChild().size() != this.codeSet.size()) {
      return false;
    }
    for (char c : node.getChild().keySet()) {
      if (!isCodeCompleteHelper(node.getChild().get(c))) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method will return true if code tree is complete, or false otherwise. A code is said
   * to be complete if every valid encoded message can be successfully decoded. If the decoding
   * is done by using a coding tree, then this condition is fulfilled if the coding tree is full
   * (i.e. every non-leaf node has exactly the same number of children, equal to the number of
   * coding symbols).
   * @return true if code tree is complete, or false otherwise
   */
  public boolean isCodeComplete() {
    return isCodeCompleteHelper(this.root);
  }
}
