import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * This class implements MinMaxPriorityQueue interface. MinMaxPriorityQueue
 * is a priority queue data structure that allows addition, and removal of
 * the maximum and minimum priority item.
 * @param <T> generic type
 */
public class MinMaxPriorityQueueImpl<T> implements MinMaxPriorityQueue<T> {
  private Map<Integer, Queue<T>> minPriorityQueue;
  private Map<Integer, Queue<T>> maxPriorityQueue;

  /**
   * Construct a MinMaxPriorityQueueImpl object.
   */
  public MinMaxPriorityQueueImpl() {
    this.minPriorityQueue = new TreeMap<>();
    this.maxPriorityQueue = new TreeMap<>(Collections.reverseOrder());
  }

  /**
   * Add an item with the given priority into the queue. If the item is a null
   * object, this method will throw IllegalArgumentException.
   * @param item the item to add
   * @param priority priority of item
   */
  public void add(T item,int priority) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (!this.minPriorityQueue.containsKey(priority)) {
      Queue<T> queue = new LinkedList<>();
      this.minPriorityQueue.put(priority, queue);
      this.maxPriorityQueue.put(priority, queue);
    }
    this.minPriorityQueue.get(priority).add(item);
  }

  /**
   * Remove and return the item with the minimum priority (as defined by the
   * lowest numeric priority). If such an item does not exist, this method
   * should return null.
   * @return the minimum priority item or null if such an item does not exist
   */
  public T minPriorityItem() {
    if (this.minPriorityQueue.size() == 0) {
      return null;
    }
    Integer minKey = (Integer) ((TreeMap) this.minPriorityQueue).firstKey();
    T result = this.minPriorityQueue.get(minKey).poll();
    if (this.minPriorityQueue.get(minKey).size() == 0) {
      this.minPriorityQueue.remove(minKey);
      this.maxPriorityQueue.remove(minKey);
    }
    return result;
  }

  /**
   * Remove and return the item with the maximum priority (as defined by the
   * highest numeric priority). If such an item does not exist, this method
   * should return null.
   * @return the minimum priority item or null if such an item does not exist
   */
  public T maxPriorityItem() {
    if (this.maxPriorityQueue.size() == 0) {
      return null;
    }
    Integer minKey = (Integer) ((TreeMap) this.maxPriorityQueue).firstKey();
    T result = this.maxPriorityQueue.get(minKey).peek();
    this.maxPriorityQueue.get(minKey).poll();
    if (this.maxPriorityQueue.get(minKey).size() == 0) {
      this.minPriorityQueue.remove(minKey);
      this.maxPriorityQueue.remove(minKey);
    }
    return result;
  }
}
