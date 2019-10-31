package bignumber;

import java.util.function.Function;

/**
 * This interface represent a general list node interface. A list node could get its size, add node
 * at front or at back, remove node at back, get node at index, set node at index, get the next
 * node, and convert list to a string.
 * @param <T> type of node data
 */
public interface ListNode<T> {
  /**
   * Return length of list.
   * @return length of list
   */
  int getSize();

  /**
   * General reduce higher order function. This will return a value from list as function defined.
   * @param identity initial value in recursion
   * @param op a function take two value into one value
   * @param <R> type of node data
   * @return same type with input of op
   */
  <R> R reduce(R identity, BinaryFunction<R> op);

  /**
   * Add a node with given value at front of list and return the modified list.
   * @param b value of new node data
   * @return the resulting list
   */
  ListNode<T> addFront(T b);

  /**
   * Add a node with given value at back of list and return the modified list.
   * @param b value of new node data
   * @return the resulting list
   */
  ListNode<T> addBack(T b);

  /**
   * Remove the first node of list and return the modified list.
   * @return the resulting list
   */
  ListNode<T> removeFront();

  /**
   * Return node data at index. If index is invalid, this method will throw
   * IllegalArgumentException.
   * @param index position of node
   * @return data of node at index
   * @throws IllegalArgumentException throws if index is out of range
   */
  T get(int index) throws IllegalArgumentException;

  /**
   * Set data of node at index. If index is invalid, this method will throw
   * IllegalArgumentException.
   * @param index position of node which will be set
   * @param b new value of node at index
   * @throws IllegalArgumentException throws if index is out of range
   */
  void set(int index, T b) throws IllegalArgumentException;

  /**
   * Convert all the node data in list to one string and return it.
   * @return all the node data in list as string
   */
  String toString();

  /**
   * Return the next node.
   * @return the next node
   */
  ListNode<T> next();

  /**
   * A general map higher order function on nodes. This returns a list of
   * identical structure, but each data item of type T converted into R using
   * the provided converter method.
   * @param convert the function needed to convert T into R
   * @param <R> the type of the data in the returned list
   * @return the head of a list that is structurally identical to this list, but
   *         contains data of type R
   */
  <R> ListNode<R> map(Function<T, R> convert);
}
