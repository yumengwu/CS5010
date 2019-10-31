import java.util.function.Function;

/**
 * This class represent a mutable list object. Mutable list extends ListADTImpl and
 * implements EnhancedList interface. A mutable list object will return a mutable list
 * after map and get a immutable counterpart.
 * @param <T> generic type
 */
public class MutableList<T> extends ListADTImpl<T> implements EnhancedList<T> {
  /**
   * Construct a MutableList object same as ListADTImpl class.
   */
  public MutableList() {
    super();
  }

  /**
   * Construct a MutableList object from a given ListADT object.
   * @param other given ListADT object to construct MutableList
   */
  public MutableList(ListADT<T> other) {
    super();
    if (other == null) {
      throw new IllegalArgumentException("ListADT cannot be null");
    }
    int size = other.getSize();
    for (int i = 0; i < size; ++i) {
      super.add(i, other.get(i));
    }
  }

  /**
   * This method is for using higher-ordered function and will return a MutableList.
   * @param converter the function that converts T into R
   * @param <R> the type of data in the resulting list
   * @return resulting list
   */
  public <R> ListADT<R> map(Function<T, R> converter) {
    return new MutableList<>(super.map(converter));
  }

  /**
   * This method will return a counterpart of this object. Counterpart of a mutable
   * list is a immutable list.
   * @return counterpart of this object
   */
  public EnhancedList<T> getCounterPart() {
    return new ImmutableList<>(this);
  }
}
