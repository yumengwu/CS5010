import java.util.function.Function;

/**
 * This class represent an immutable list object. Immutable list implements EnhancedList
 * interface. immutable list contains the same data in the same sequence as the original
 * list, but it cannot be changed. An immutable list object will return an immutable list
 * after map and get a mutable counterpart.
 * @param <T> generic type
 */
public class ImmutableList<T> implements EnhancedList<T> {
  private GenericListADTNode<T> head;

  /**
   * Construct an immutable list with empty node.
   */
  public ImmutableList() {
    this.head = new GenericEmptyNode<>();
  }

  /**
   * Private constructor that is used internally.
   * @param head given GenericListADTNode to construct immutable list
   */
  private ImmutableList(GenericListADTNode<T> head) {
    this.head = head;
  }

  /**
   * Construct an ImmutableList object from a given ListADT object.
   * @param other given ListADT object to construct ImmutableList
   */
  public ImmutableList(ListADT<T> other) {
    if (other == null) {
      throw new IllegalArgumentException("ListADT cannot be null");
    }
    GenericListADTNode<T> otherNode = new GenericEmptyNode<>();
    int size = other.getSize();
    for (int i = 0; i < size; ++i) {
      otherNode = otherNode.add(i, other.get(i));
    }
    this.head = otherNode;
  }

  /**
   * Return the number of objects currently in this list.
   *
   * @return the size of the list
   */
  public int getSize() {
    return head.count();
  }

  /**
   * Since an immutable list cannot be changed and add items in the front, this method
   * will throw UnsupportedOperationException.
   * @param b the object to be added to the front of this list
   */
  public void addFront(T b) {
    throw new UnsupportedOperationException("Immutable list cannot add front");
  }

  /**
   * Since an immutable list cannot be changed and add items in the back, this method
   * will throw UnsupportedOperationException.
   * @param b the object to be added to the front of this list
   */
  public void addBack(T b) {
    throw new UnsupportedOperationException("Immutable list cannot add back");
  }

  /**
   * Since an immutable list cannot be changed and add items in the index, this method
   * will throw UnsupportedOperationException.
   * @param b the object to be added to the front of this list
   */
  public void add(int index, T b) {
    throw new UnsupportedOperationException("Immutable list cannot add");
  }

  /**
   * Since an immutable list cannot be changed and remove items, this method will throw
   * UnsupportedOperationException.
   * @param b the object to be added to the front of this list
   */
  public void remove(T b) {
    throw new UnsupportedOperationException("Immutable list cannot remove");
  }

  /**
   * Get the (index)th object in this list.
   *
   * @param index the index of the object to be returned
   * @return the object at the given index
   * @throws IllegalArgumentException if an invalid index is passed
   */
  public T get(int index) throws IllegalArgumentException {
    if ((index >= 0) && (index < getSize())) {
      return head.get(index);
    } else {
      throw new IllegalArgumentException("Invalid index");
    }

  }

  /**
   * This method is for using higher-ordered function and will return an ImmutableList.
   * @param converter the function that converts T into R
   * @param <R> the type of data in the resulting list
   * @return resulting list
   */
  public <R> ListADT<R> map(Function<T, R> converter) {
    return new ImmutableList<>(this.head.map(converter));
  }

  /**
   * This method will return a counterpart of this object. Counterpart of an immutable
   * list is a mutable list.
   * @return counterpart of this object
   */
  public EnhancedList<T> getCounterPart() {
    return new MutableList<>(this);
  }

  /**
   * Convert this list to string.
   * @return string
   */
  public String toString() {
    return "(" + head.toString() + ")";
  }
}
