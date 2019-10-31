/**
 * This interface represent an EnhancedList and extends ListADT interface. This interface
 * has getCounterPart method to return a mutable list counterpart for a immutable list, or
 * return an immutable list counterpart for a mutable list.
 * @param <T> generic type
 */
public interface EnhancedList<T> extends ListADT<T> {
  /**
   * This method will return a counterpart of this object. If this object is a mutable list,
   * then return object is an immutable list. If this object is an immutable list, then return
   * object is a mutable list.
   * @return counterpart of this object
   */
  EnhancedList<T> getCounterPart();
}
