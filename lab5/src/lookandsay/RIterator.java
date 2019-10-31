package lookandsay;

import java.util.Iterator;

/**
 * This interface represent a RIterator extends Iterator for look and say number.
 * @param <T> generic type
 */
public interface RIterator<T> extends Iterator<T> {
  /**
   * Return the current number in the sequence and revert to the previous number
   * in the sequence.
   * @return the current number in the sequence
   */
  T prev();

  /**
   * Return true if it is possible to go back one step, false otherwise.
   * @return true if it is possible to go back one step, false otherwise
   */
  boolean hasPrevious();
}
