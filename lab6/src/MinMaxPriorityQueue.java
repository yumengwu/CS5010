/**
 * This interface represent a MinMaxPriorityQueue. MinMaxPriorityQueue is a
 * priority queue data structure that allows addition, and removal of the
 * maximum and minimum priority item.
 * @param <T> generic type
 */
public interface MinMaxPriorityQueue<T> {
  /**
   * Add an item with the given priority into the queue. If the item is a null
   * object, this method will throw IllegalArgumentException.
   * @param item the item to add
   * @param priority priority of item
   */
  void add(T item,int priority);

  /**
   * Remove and return the item with the minimum priority (as defined by the
   * lowest numeric priority). If such an item does not exist, this method
   * should return null.
   * @return the minimum priority item or null if such an item does not exist
   */
  T minPriorityItem();

  /**
   * Remove and return the item with the maximum priority (as defined by the
   * highest numeric priority). If such an item does not exist, this method
   * should return null.
   * @return the minimum priority item or null if such an item does not exist
   */
  T maxPriorityItem();
}
