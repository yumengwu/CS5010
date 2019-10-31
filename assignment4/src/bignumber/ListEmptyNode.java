package bignumber;

import java.util.function.Function;

/**
 * This class implements method in ListNode interface. A list node could get its size, add node
 * at front or at back, remove node at back, get node at index, set node at index, get the next
 * node, and convert list to a string. Empty node dose not have data.
 * @param <T> type of node data
 */
public class ListEmptyNode<T> implements ListNode<T> {

  /**
   * Construct a empty node with doing nothing.
   */
  public ListEmptyNode() {
    /**
     * Empty node will do nothing.
     */
  }

  /**
   * Return length of list. Length of empty node is 0.
   * @return length of list(0)
   */
  public int getSize() {
    return 0;
  }

  /**
   * General reduce higher order function. Empty node will return identity.
   * @param identity initial value in recursion
   * @param op a function take two value into one value
   * @param <R> type of node data
   * @return identity
   */
  public <R> R reduce(R identity, BinaryFunction<R> op) {
    return identity;
  }

  /**
   * Add a node with given value at front of list and return the modified list.
   * @param obj value of new node data
   * @return the resulting list
   */
  public ListNode<T> addFront(T obj) {
    return new ListElementNode<>(obj, this);
  }

  /**
   * Add a node with given value at back of list and return the modified list.
   * @param obj value of new node data
   * @return the resulting list
   */
  public ListNode<T> addBack(T obj) {
    return addFront(obj);
  }

  /**
   * Since empty node dose not have data, this method cannot remove node but return itself.
   * @return the resulting list
   */
  public ListNode<T> removeFront() {
    return this;
  }

  /**
   * Since empty node dose not have data, this method will return empty string.
   * @return empty string
   */
  public String toString() {
    return "";
  }

  /**
   * Get data at index. Since empty node dose not have data and cannot get data, this method will
   * throw IllegalArgumentException
   * @param index position of node
   * @return nothing
   * @throws IllegalArgumentException always throws
   */
  public T get(int index) throws IllegalArgumentException {
    //throw new IllegalArgumentException("Wrong index: " + index);
    return null;
  }

  /**
   * Set data at index. Since empty node dose not have data and cannot set data, this method will
   * throw IllegalArgumentException
   * @param index position of node
   * @throws IllegalArgumentException always throws
   */
  public void set(int index, T b) throws IllegalArgumentException {
    throw new IllegalArgumentException("Index out of range");
  }

  /**
   * Return the next node. Empty node will return itself.
   * @return this object
   */
  public final ListNode<T> next() {
    return this;
  }

  /**
   * A general map higher order function on nodes. This returns a list of
   * identical structure, but each data item of type T converted into R using
   * the provided converter method.
   * @param convert the function needed to convert T into R
   * @param <R> the type of the data in the returned list
   * @return the head of a list that is structurally identical to this list, but
   *         contains data of type R
   */
  @Override
  public <R> ListEmptyNode<R> map(Function<T, R> convert) {
    return new ListEmptyNode<>();
  }
}
