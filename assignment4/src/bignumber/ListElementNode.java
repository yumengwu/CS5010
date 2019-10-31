package bignumber;

import java.util.function.Function;

/**
 * This class implements method in ListNode interface. A list node could get its size, add node
 * at front or at back, remove node at back, get node at index, set node at index, get the next
 * node, and convert list to a string.
 * @param <T> type of node data
 */
public class ListElementNode<T> implements ListNode<T> {
  public T data;
  public ListNode<T> rest;

  /**
   * Construct a ListElement node with given data and the rest node.
   * @param b data of this node
   * @param rest the next node
   */
  public ListElementNode(T b, ListNode<T> rest) {
    this.data = b;
    this.rest = rest;
  }

  /**
   * General reduce higher order function. This will return a value from list as function defined.
   * @param identity initial value in recursion
   * @param op a function take two value into one value
   * @param <R> type of node data
   * @return same type with input of op
   */
  public <R> R reduce(R identity, BinaryFunction<R> op) {
    return op.apply(identity, this.rest.reduce(identity, op));
  }

  /**
   * Return length of list.
   * @return length of list
   */
  public int getSize() {
    //return 1 + this.rest.getSize();
    return this.reduce(0, (acc, item) -> {
      acc += item + 1;
      return acc;
    });
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
    this.rest = this.rest.addBack(obj);
    return this;
  }

  /**
   * Remove the first node of list and return the modified list.
   * @return the resulting list
   */
  public ListNode<T> removeFront() {
    return this.rest;
  }

  /**
   * Convert all the node data in list to one string and return it.
   * @return all the node data in list as string
   */
  public String toString() {
    return this.rest.toString() + this.data.toString();
  }

  /**
   * Return node data at index. If index is invalid, this method will throw
   * IllegalArgumentException.
   * @param index position of node
   * @return data of node at index
   * @throws IllegalArgumentException throws if index is out of range
   */
  public T get(int index) throws IllegalArgumentException {
    if (index < 0) {
      throw new IllegalArgumentException("Index out of range");
    }
    if (index == 0) {
      return this.data;
    }
    else {
      return this.rest.get(index - 1);
    }
  }

  /**
   * Set data of node at index. If index is invalid, this method will throw
   * IllegalArgumentException.
   * @param index position of node which will be set
   * @param b new value of node at index
   * @throws IllegalArgumentException throws if index is out of range
   */
  public void set(int index, T b) throws IllegalArgumentException {
    if (index < 0) {
      throw new IllegalArgumentException("Wrong index");
    }
    if (index == 0) {
      this.data = b;
    }
    else {
      this.rest.set(index - 1, b);
    }
  }

  /**
   * Return the next node.
   * @return the next node
   */
  public final ListNode<T> next() {
    return this.rest;
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
  public <R> ListElementNode<R> map(Function<T, R> convert) {
    return new ListElementNode<>(convert.apply(this.data), this.rest.map(convert));
  }
}
