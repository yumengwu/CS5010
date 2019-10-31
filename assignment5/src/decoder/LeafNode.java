package decoder;

import java.util.Map;

/**
 * This class implement TreeNode interface. A LeafNode object has not children, but has leaf.
 * A GroupNode could return whether it is a leaf node, get this node's symbol, and get children
 * of this node.
 */
public class LeafNode implements TreeNode {
  private char symbol;

  /**
   * Construct a LeafNode object with symbol '\0'.
   */
  public LeafNode() {
    this.symbol = (char) 0;
  }

  /**
   * Construct a LeafNode object with symbol 'c.
   * @param c symbol of this node
   */
  public LeafNode(char c) {
    this.symbol = c;
  }

  /**
   * Return true for LeafNode.
   * @return true
   */
  public boolean isLeaf() {
    return true;
  }

  /**
   * Return this node's symbol.
   * @return this node's symbol
   */
  public char getSymbol() {
    return this.symbol;
  }

  /**
   * Set symbol of this node by given c.
   * @param c symbol to set
   */
  public void setSymbol(char c) {
    this.symbol = c;
  }

  /**
   * Return null for LeafNode.
   * @return null
   */
  public Map<Character, TreeNode> getChild() {
    return null;
  }
}
