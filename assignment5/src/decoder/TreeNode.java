package decoder;

import java.util.Map;

/**
 * This interface represent a TreeNode interface. A tree node could return whether it is a leaf
 * node, get this node's symbol, and get children of this node.
 */
public interface TreeNode {
  /**
   * Return true if this node is a leaf node, false otherwise.
   * @return true if this node is a leaf node, false otherwise
   */
  boolean isLeaf();

  /**
   * Return this node's symbol as a charactor.
   * @return this node's symbol
   */
  char getSymbol();

  /**
   * Return this node's children as a Map object.
   * @return this node's children
   */
  Map<Character, TreeNode> getChild();
}
