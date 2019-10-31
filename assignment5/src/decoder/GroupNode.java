package decoder;

import java.util.Map;
import java.util.HashMap;

/**
 * This class implement TreeNode interface. A GroupNode object has not symbol, but has children.
 * A GroupNode could return whether it is a leaf node, get this node's symbol, and get children of
 * this node.
 */
public class GroupNode implements TreeNode {
  private Map<Character, TreeNode> children;

  /**
   * Construct a GroupNode object. Children will be initialized as an empty HashMap object.
   */
  public GroupNode() {
    this.children = new HashMap<>();
  }

  /**
   * Return false for GroupNode.
   * @return false
   */
  public boolean isLeaf() {
    return false;
  }

  /**
   * Return '\0' for GroupNode.
   * @return '\0'
   */
  public char getSymbol() {
    return (char) 0;
  }

  /**
   * Return this node's children.
   * @return this node's children
   */
  public Map<Character, TreeNode> getChild() {
    return this.children;
  }
}
