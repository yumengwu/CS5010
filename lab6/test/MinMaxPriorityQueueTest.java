import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for MinMaxPriorityQueue interface.
 */
public class MinMaxPriorityQueueTest {
  private MinMaxPriorityQueue<String> strQueue;
  private MinMaxPriorityQueue<Integer> intQueue;

  @Before
  public void setUp() {
    strQueue = new MinMaxPriorityQueueImpl<>();
    intQueue = new MinMaxPriorityQueueImpl<>();
  }

  @Test
  public void testEmptyQueue() {
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testInvalidAdd() {
    try {
      strQueue.add(null, 10);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Item cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testMinQueueDifferentItemWithDifferentPriority() {
    strQueue.add("a", 1);
    strQueue.add("b", 2);
    strQueue.add("c", -1);
    strQueue.add("d", 5);
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals("d", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
  }

  @Test
  public void testMaxQueueDifferentItemWithDifferentPriority() {
    strQueue.add("a", 1);
    strQueue.add("b", 2);
    strQueue.add("c", -1);
    strQueue.add("d", 5);
    assertEquals("d", strQueue.maxPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinQueueDifferentItemWithSamePriority1() {
    strQueue.add("a", 10);
    strQueue.add("b", 12);
    strQueue.add("c", 1);
    strQueue.add("d", 1);
    strQueue.add("e", 1);
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("d", strQueue.minPriorityItem());
    assertEquals("e", strQueue.minPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
  }

  @Test
  public void testMinQueueDifferentItemWithSamePriority2() {
    strQueue.add("a", 777);
    strQueue.add("b", 777);
    strQueue.add("c", 777);
    strQueue.add("d", 777);
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("d", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
  }

  @Test
  public void testMinQueueDifferentItemWithSamePriority3() {
    strQueue.add("a", 777);
    strQueue.add("b", 777);
    strQueue.add("c", 777);
    strQueue.add("d", 777);
    strQueue.add("e", 123);
    strQueue.add("f", 123);
    strQueue.add("g", 123);
    strQueue.add("h", 0);
    strQueue.add("i", 0);
    assertEquals("h", strQueue.minPriorityItem());
    assertEquals("i", strQueue.minPriorityItem());
    assertEquals("e", strQueue.minPriorityItem());
    assertEquals("f", strQueue.minPriorityItem());
    assertEquals("g", strQueue.minPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("d", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
  }

  @Test
  public void testMaxQueueDifferentItemWithSamePriority1() {
    strQueue.add("a", 10);
    strQueue.add("b", 12);
    strQueue.add("c", 1);
    strQueue.add("d", 1);
    strQueue.add("e", 1);
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.maxPriorityItem());
    assertEquals("d", strQueue.maxPriorityItem());
    assertEquals("e", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMaxQueueDifferentItemWithSamePriority2() {
    strQueue.add("a", 777);
    strQueue.add("b", 777);
    strQueue.add("c", 777);
    strQueue.add("d", 777);
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.maxPriorityItem());
    assertEquals("d", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMaxQueueDifferentItemWithSamePriority3() {
    strQueue.add("a", 777);
    strQueue.add("b", 777);
    strQueue.add("c", 777);
    strQueue.add("d", 777);
    strQueue.add("e", 123);
    strQueue.add("f", 123);
    strQueue.add("g", 123);
    strQueue.add("h", 0);
    strQueue.add("i", 0);
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.maxPriorityItem());
    assertEquals("d", strQueue.maxPriorityItem());
    assertEquals("e", strQueue.maxPriorityItem());
    assertEquals("f", strQueue.maxPriorityItem());
    assertEquals("g", strQueue.maxPriorityItem());
    assertEquals("h", strQueue.maxPriorityItem());
    assertEquals("i", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinQueueSameItemWithDifferentPriority1() {
    strQueue.add("abc", 100);
    strQueue.add("abc", 10);
    strQueue.add("abc", 1000);
    strQueue.add("x", 50);
    strQueue.add("y", 500);
    assertEquals("abc", strQueue.minPriorityItem());
    assertEquals("x", strQueue.minPriorityItem());
    assertEquals("abc", strQueue.minPriorityItem());
    assertEquals("y", strQueue.minPriorityItem());
    assertEquals("abc", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
  }

  @Test
  public void testMaxQueueSameItemWithDifferentPriority1() {
    strQueue.add("abc", 100);
    strQueue.add("abc", 10);
    strQueue.add("abc", 1000);
    strQueue.add("x", 50);
    strQueue.add("y", 500);
    assertEquals("abc", strQueue.maxPriorityItem());
    assertEquals("y", strQueue.maxPriorityItem());
    assertEquals("abc", strQueue.maxPriorityItem());
    assertEquals("x", strQueue.maxPriorityItem());
    assertEquals("abc", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinQueueAfterEmpty() {
    strQueue.add("a", 100);
    strQueue.add("b", 1);
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    strQueue.add("x", -100);
    strQueue.add("y", 111);
    assertEquals("x", strQueue.minPriorityItem());
    assertEquals("y", strQueue.minPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
  }

  @Test
  public void testMaxQueueAfterEmpty() {
    strQueue.add("a", 100);
    strQueue.add("b", 1);
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
    strQueue.add("x", -100);
    strQueue.add("y", 111);
    assertEquals("y", strQueue.maxPriorityItem());
    assertEquals("x", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinMaxQueueMultiDifferentItemWithDifferentPriority() {
    strQueue.add("a", 0);
    strQueue.add("b", 1);
    strQueue.add("c", -1);
    strQueue.add("e", 2);
    strQueue.add("g", 3);
    strQueue.add("h", -3);
    assertEquals("h", strQueue.minPriorityItem());
    assertEquals("g", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("e", strQueue.maxPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinMaxQueueMultiDifferentItemWithSamePriority1() {
    strQueue.add("c", 0);
    strQueue.add("b", 0);
    strQueue.add("a", 0);
    strQueue.add("d", 0);
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("d", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinMaxQueueMultiDifferentItemWithSamePriority2() {
    strQueue.add("a", 0);
    strQueue.add("b", 0);
    strQueue.add("c", 100);
    strQueue.add("d", -100);
    strQueue.add("e", 200);
    strQueue.add("x", 200);
    strQueue.add("g", 200);
    strQueue.add("f", 200);
    assertEquals("d", strQueue.minPriorityItem());
    assertEquals("e", strQueue.maxPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("x", strQueue.maxPriorityItem());
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals("g", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("f", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test
  public void testMinMaxQueueMultiSameItemWithDifferentPriority1() {
    strQueue.add("a", 0);
    strQueue.add("a", 100);
    strQueue.add("a", 1000);
    strQueue.add("a", 10000);
    strQueue.add("b", 0);
    strQueue.add("b", 100);
    strQueue.add("c", 11);
    strQueue.add("c", 1111);
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("b", strQueue.minPriorityItem());
    assertEquals("c", strQueue.maxPriorityItem());
    assertEquals("c", strQueue.minPriorityItem());
    assertEquals("a", strQueue.maxPriorityItem());
    assertEquals("a", strQueue.minPriorityItem());
    assertEquals("b", strQueue.maxPriorityItem());
    assertEquals(null, strQueue.minPriorityItem());
    assertEquals(null, strQueue.maxPriorityItem());
  }

  @Test(timeout = 500)
  public void testMinQueueMultiTimes1() {
    for (int i = 0; i < 10000; ++i) {
      intQueue.add(i, i);
    }
    for (int i = 0; i < 10000; ++i) {
      assertEquals(new Integer(i), intQueue.minPriorityItem());
    }
    assertEquals(null, intQueue.minPriorityItem());
  }

  @Test(timeout = 500)
  public void testMinQueueMultiTimes2() {
    for (int i = 0; i < 10000; ++i) {
      intQueue.add(i, 10000 - i);
    }
    for (int i = 0; i < 10000; ++i) {
      assertEquals(new Integer(9999 - i), intQueue.minPriorityItem());
    }
    assertEquals(null, intQueue.minPriorityItem());
  }

  @Test(timeout = 500)
  public void testMaxQueueMultiTimes1() {
    for (int i = 0; i < 10000; ++i) {
      intQueue.add(i, i);
    }
    for (int i = 0; i < 10000; ++i) {
      assertEquals(new Integer(9999 - i), intQueue.maxPriorityItem());
    }
    assertEquals(null, intQueue.maxPriorityItem());
  }

  @Test(timeout = 500)
  public void testMaxQueueMultiTimes2() {
    for (int i = 0; i < 10000; ++i) {
      intQueue.add(i, 10000 - i);
    }
    for (int i = 0; i < 10000; ++i) {
      assertEquals(new Integer(i), intQueue.maxPriorityItem());
    }
    assertEquals(null, intQueue.maxPriorityItem());
  }

  @Test(timeout = 500)
  public void testMinMaxQueueMultiTime1() {
    for (int i = 0; i < 100; ++i) {
      for (int j = 0; j < 100; ++j) {
        intQueue.add(i, i);
      }
    }
    for (int i = 0; i < 50; ++i) {
      for (int j = 0; j < 100; ++j) {
        assertEquals(new Integer(i), intQueue.minPriorityItem());
        assertEquals(new Integer(99 - i), intQueue.maxPriorityItem());
      }
    }
  }
}