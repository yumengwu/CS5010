import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import bignumber.ListNode;
import bignumber.ListElementNode;
import bignumber.ListEmptyNode;

/**
 * A Junit class for ListNode interface.
 */
public class ListNodeTest {
  private ListNode<Byte> listNode;

  @Test
  public void testConstruct1() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    assertEquals("321", listNode.toString());
  }

  @Test
  public void testConstruct2() {
    listNode = new ListEmptyNode<>();
    assertEquals("", listNode.toString());
  }

  @Test
  public void testConstruct3() {
    listNode = new ListElementNode<>((byte)2,
            new ListElementNode<>((byte)3,
                    new ListEmptyNode<>()));
    assertEquals("32", listNode.toString());
  }

  @Test
  public void testGetSize1() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    assertEquals((byte)3, listNode.getSize());
  }

  @Test
  public void testGetSize2() {
    listNode = new ListEmptyNode<>();
    assertEquals((byte)0, listNode.getSize());
  }

  @Test
  public void testAddFront1() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    listNode = listNode.addFront((byte)4);
    assertEquals("3214", listNode.toString());
  }

  @Test
  public void testAddFront2() {
    listNode = new ListEmptyNode<>();
    listNode = listNode.addFront((byte)4);
    assertEquals("4", listNode.toString());
  }

  @Test
  public void testAddFront3() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    listNode = listNode.addFront((byte)4);
    listNode = listNode.addFront((byte)5);
    assertEquals("32145", listNode.toString());
  }

  @Test
  public void testAddBack1() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    listNode = listNode.addBack((byte)4);
    assertEquals("4321", listNode.toString());
  }

  @Test
  public void testAddBack2() {
    listNode = new ListEmptyNode<>();
    listNode = listNode.addBack((byte)4);
    assertEquals("4", listNode.toString());
  }

  @Test
  public void testAddBack3() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    listNode = listNode.addBack((byte)4);
    listNode = listNode.addBack((byte)5);
    assertEquals("54321", listNode.toString());
  }

  @Test
  public void testRemoveFront1() {
    listNode = new ListElementNode<>((byte)1,
            new ListElementNode<>((byte)2,
                    new ListElementNode<>((byte)3,
                            new ListEmptyNode<>())));
    listNode = listNode.removeFront();
    assertEquals("32", listNode.toString());
  }

  @Test
  public void testRemoveFront2() {
    listNode = new ListElementNode<>((byte)3, new ListEmptyNode<>());
    listNode = listNode.removeFront();
    assertEquals("", listNode.toString());
  }

  @Test
  public void testRemoveFront3() {
    listNode = new ListEmptyNode<>();
    listNode = listNode.removeFront();
    assertEquals("", listNode.toString());
  }

  @Test
  public void testGet1() {
    listNode = new ListEmptyNode<>();
    for (int i = 1; i < 10; ++i) {
      listNode = listNode.addFront((byte)i);
    }
    assertEquals((byte)9, (byte)listNode.get(0));
  }

  @Test
  public void testGet2() {
    listNode = new ListEmptyNode<>();
    for (int i = 1; i < 10; ++i) {
      listNode = listNode.addFront((byte)i);
    }
    try {
      listNode.get(-1);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testGet3() {
    listNode = new ListEmptyNode<>();
    for (int i = 1; i < 10; ++i) {
      listNode = listNode.addFront((byte)i);
    }
    try {
      listNode.get(10);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testGet4() {
    listNode = new ListEmptyNode<>();
    try {
      listNode.get(0);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testSet1() {
    listNode = new ListEmptyNode<>();
    for (int i = 1; i < 10; ++i) {
      listNode = listNode.addFront((byte)i);
    }
    listNode.set(0,(byte)0);
    assertEquals((byte)0, (byte)listNode.get(0));
  }

  @Test
  public void testSet2() {
    listNode = new ListEmptyNode<>();
    for (int i = 1; i < 10; ++i) {
      listNode = listNode.addFront((byte)i);
    }
    try {
      listNode.set(-1, (byte)1);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testSet3() {
    listNode = new ListEmptyNode<>();
    for (int i = 1; i < 10; ++i) {
      listNode = listNode.addFront((byte)i);
    }
    try {
      listNode.set(10, (byte)1);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }

  @Test
  public void testSet4() {
    listNode = new ListEmptyNode<>();
    try {
      listNode.set(0, (byte)1);
      fail();
    }
    catch (IllegalArgumentException e) {
      //
    }
  }
}