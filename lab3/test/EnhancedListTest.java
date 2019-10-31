import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * A JUnit test class for EnhancedList interface.
 */
public class EnhancedListTest {
  @Test
  public void testMutableConstructor1() {
    EnhancedList<Integer> enhancedList = new MutableList<>();
    ListADT<Integer> listADT = new ListADTImpl<>();
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testMutableConstructor2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testMutableConstructor3() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 20; ++i) {
      listADT.add(i, i);
    }
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testConstructWithNullListADT1() {
    try {
      EnhancedList<Integer> enhancedList = new MutableList<>(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("ListADT cannot be null", e.getMessage());
    }
  }

  @Test
  public void testConstructWithNullListADT2() {
    try {
      EnhancedList<Integer> enhancedList = new ImmutableList<>(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("ListADT cannot be null", e.getMessage());
    }
  }

  @Test
  public void testImmutableConstructor1() {
    EnhancedList<Integer> enhancedList = new ImmutableList<>();
    ListADT<Integer> listADT = new ListADTImpl<>();
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testImmutableConstructor2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 20; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testMutableAddFront1() {
    ListADT<Character> listADT = new ListADTImpl<>();
    EnhancedList<Character> enhancedList = new MutableList<>();
    for (int i = 0; i < 20; ++i) {
      listADT.addFront((char) (i + 'a'));
      enhancedList.addFront((char) (i + 'a'));
    }
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testMutableAddFront2() {
    ListADT<Character> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront((char) (i + 'a'));
    }
    EnhancedList<Character> enhancedList = new MutableList<>(listADT);
    for (int i = 0; i < 10; ++i) {
      listADT.addFront((char) (i + 'a'));
      enhancedList.addFront((char) (i + 'a'));
    }
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testImmutableAddFront1() {
    EnhancedList<Character> enhancedList = new ImmutableList<>();
    try {
      enhancedList.addFront('a');
      fail();
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
  }

  @Test
  public void testImmutableAddFront2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    try {
      enhancedList.addFront(0);
      fail();
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
  }

  @Test
  public void testMutableAddBack1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new MutableList<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addBack(i);
      enhancedList.addBack(i);
    }
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testMutableAddBack2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addBack(i);
    }
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    for (int i = 0; i < 20; ++i) {
      listADT.addBack(i);
      enhancedList.addBack(i);
    }
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testImmutableAddBack1() {
    try {
      EnhancedList<Integer> enhancedList = new ImmutableList<>();
      enhancedList.addBack(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
  }

  @Test
  public void testImmutableAddBack2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    try {
      enhancedList.addBack(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
  }

  @Test
  public void testValidMutableAdd1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new MutableList<>();
    for (int i = 0; i < 10; ++i) {
      listADT.add(i, i + 1);
      enhancedList.add(i, i + 1);
    }
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testValidMutableAdd2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.add(i, i + 1);
    }
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    for (int i = 0; i < 10; ++i) {
      listADT.add(i + 1, i * 2);
      enhancedList.add(i + 1, i * 2);
    }
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testInvalidMutableAdd1() {
    EnhancedList<Integer> enhancedList = new MutableList<>();
    try {
      enhancedList.add(1, 1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index to add an element", e.getMessage());
    }
  }

  @Test
  public void testImmutableAdd1() {
    EnhancedList<Integer> enhancedList = new ImmutableList<>();
    try {
      enhancedList.add(0, 0);
      fail();
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
  }

  @Test
  public void testImmutableAdd2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.add(i, i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    try {
      enhancedList.add(0, 0);
      fail();
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
  }

  @Test
  public void testMutableRemove1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    listADT.remove(10);
    EnhancedList<Integer> enhancedList = new MutableList<>();
    enhancedList.remove(10);
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testMutableRemove2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    listADT.remove(5);
    enhancedList.remove(5);
    assertEquals(listADT.toString(), enhancedList.toString());
    listADT.remove(15);
    enhancedList.remove(15);
    assertEquals(listADT.toString(), enhancedList.toString());
  }

  @Test
  public void testImmutableRemove1() {
    EnhancedList<Integer> enhancedList = new ImmutableList<>();
    try {
      enhancedList.remove(10);
      fail();
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
  }

  @Test
  public void testImmutableRemove2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 20; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    try {
      enhancedList.remove(10);
      fail();
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
  }

  @Test
  public void testMutableGetSize1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new MutableList<>();
    assertEquals(listADT.getSize(), enhancedList.getSize());
  }

  @Test
  public void testMutableGetSize2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new MutableList<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront(i);
      enhancedList.addFront(i);
    }
    assertEquals(listADT.getSize(), enhancedList.getSize());
  }

  @Test
  public void testMutableGetSize3() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    assertEquals(listADT.getSize(), enhancedList.getSize());
  }

  @Test
  public void testImmutableGetSize1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new ImmutableList<>();
    assertEquals(listADT.getSize(), enhancedList.getSize());
  }

  @Test
  public void testImmutableGetSize2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    assertEquals(listADT.getSize(), enhancedList.getSize());
  }

  @Test
  public void testMutableGet1() {
    EnhancedList<Integer> enhancedList = new MutableList<>();
    try {
      enhancedList.get(0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testMutableGet2() {
    EnhancedList<Integer> enhancedList = new MutableList<>();
    for (int i = 0; i < 10; ++i) {
      enhancedList.addBack(i);
    }
    assertTrue(5 == enhancedList.get(5));
  }

  @Test
  public void testMutableGet3() {
    EnhancedList<Integer> enhancedList = new MutableList<>();
    for (int i = 0; i < 10; ++i) {
      enhancedList.addBack(i);
    }
    try {
      enhancedList.get(-1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testMutableGet4() {
    EnhancedList<Integer> enhancedList = new MutableList<>();
    for (int i = 0; i < 10; ++i) {
      enhancedList.addBack(i);
    }
    try {
      enhancedList.get(12);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testImmutableGet1() {
    EnhancedList<Integer> enhancedList = new ImmutableList<>();
    try {
      enhancedList.get(0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testImmutableGet2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addBack(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    assertTrue(5 == enhancedList.get(5));
  }

  @Test
  public void testImmutableGet3() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addBack(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    try {
      enhancedList.get(-1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testImmutableGet4() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 10; ++i) {
      listADT.addBack(i);
    }
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    try {
      enhancedList.get(11);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testMutableMap1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    ListADT<Integer> listADTAfterMap = listADT.map(s -> s + 1);
    ListADT<Integer> enhancedListAfterMap = enhancedList.map(s -> s + 1);
    assertEquals(listADTAfterMap.toString(), enhancedListAfterMap.toString());
    try {
      listADTAfterMap.addBack(0);
      enhancedListAfterMap.addBack(0);
      listADTAfterMap.add(0, 0);
      enhancedListAfterMap.add(0, 0);
      listADTAfterMap.addFront(1);
      enhancedListAfterMap.addFront(1);
      listADTAfterMap.remove(0);
      enhancedListAfterMap.remove(0);
      assertEquals(listADTAfterMap.toString(), enhancedListAfterMap.toString());
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testMutableMap2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 20; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> enhancedList = new MutableList<>(listADT);
    ListADT<Character> listADTAfterMap = listADT.map(s -> (char) (s + 'a'));
    ListADT<Character> enhancedListAfterMap = enhancedList.map(s -> (char) (s + 'a'));
    assertEquals(listADTAfterMap.toString(), enhancedListAfterMap.toString());
    try {
      listADTAfterMap.addBack('a');
      enhancedListAfterMap.addBack('a');
      listADTAfterMap.add(0, 'b');
      enhancedListAfterMap.add(0, 'b');
      listADTAfterMap.addFront('1');
      enhancedListAfterMap.addFront('1');
      listADTAfterMap.remove('a');
      enhancedListAfterMap.remove('a');
      assertEquals(listADTAfterMap.toString(), enhancedListAfterMap.toString());
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testImmutableMap1() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    ListADT<Integer> listADTAfterMap = listADT.map(s -> s + 2);
    ListADT<Integer> enhancedListAfterMap = enhancedList.map(s -> s + 2);
    assertEquals(listADTAfterMap.toString(), enhancedListAfterMap.toString());
    try {
      enhancedListAfterMap.addBack(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
    try {
      enhancedListAfterMap.add(0, 0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
    try {
      enhancedListAfterMap.addFront(1);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
    try {
      enhancedListAfterMap.remove(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
  }

  @Test
  public void testImmutableMap2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    EnhancedList<Integer> enhancedList = new ImmutableList<>(listADT);
    ListADT<String> listADTAfterMap = listADT.map(s -> s.toString());
    ListADT<String> enhancedListAfterMap = enhancedList.map(s -> s.toString());
    assertEquals(listADTAfterMap.toString(), enhancedListAfterMap.toString());
    try {
      enhancedListAfterMap.addBack("0");
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
    try {
      enhancedListAfterMap.add(0, "0");
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
    try {
      enhancedListAfterMap.addFront("0");
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
    try {
      enhancedListAfterMap.remove("0");
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
  }

  @Test
  public void testMutableGetCounterPart1() {
    EnhancedList<Integer> mutableList = new MutableList<>();
    EnhancedList<Integer> immutableList = mutableList.getCounterPart();
    assertEquals(mutableList.toString(), immutableList.toString());
    try {
      immutableList.addBack(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
    try {
      immutableList.add(0, 0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
    try {
      immutableList.addFront(1);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
    try {
      immutableList.remove(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
    mutableList.addFront(1234);
    assertEquals(false, mutableList.toString().equals(immutableList.toString()));
  }

  @Test
  public void testMutableGetCounterPart2() {
    EnhancedList<Integer> mutableList = new MutableList<>();
    for (int i = 0; i < 20; ++i) {
      mutableList.addFront(i);
    }
    EnhancedList<Integer> immutableList = mutableList.getCounterPart();
    assertEquals(mutableList.toString(), immutableList.toString());
    try {
      immutableList.addBack(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
    try {
      immutableList.add(0, 0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
    try {
      immutableList.addFront(1);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
    try {
      immutableList.remove(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
    mutableList.addFront(1234);
    assertEquals(false, mutableList.toString().equals(immutableList.toString()));
  }

  @Test
  public void testImmutableGetCounterPart1() {
    EnhancedList<Integer> immutableList = new ImmutableList<>();
    EnhancedList<Integer> mutableList = immutableList.getCounterPart();
    assertEquals(immutableList.toString(), mutableList.toString());
    mutableList.add(0, 0);
    assertEquals(false, immutableList.toString().equals(mutableList.toString()));
  }

  @Test
  public void testImmutableGetCounterPart2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 20; ++i) {
      listADT.add(i, i);
    }
    EnhancedList<Integer> immutableList = new ImmutableList<>(listADT);
    EnhancedList<Integer> mutableList = immutableList.getCounterPart();
    assertEquals(immutableList.toString(), mutableList.toString());
    mutableList.add(0, 0);
    assertEquals(false, immutableList.toString().equals(mutableList.toString()));
  }

  @Test
  public void testMultiGetCounterPart1() {
    EnhancedList<Integer> mutableList = new MutableList<>();
    for (int i = 0; i < 20; ++i) {
      mutableList.addFront(i);
    }
    EnhancedList<Integer> immutableList = mutableList.getCounterPart();
    EnhancedList<Integer> mutableList2 = immutableList.getCounterPart();
    try {
      mutableList.addFront(0);
      mutableList2.addFront(0);
      assertEquals(mutableList.toString(), mutableList2.toString());
    }
    catch (UnsupportedOperationException e) {
      fail();
    }
  }

  @Test
  public void testMultiGetCounterPart2() {
    ListADT<Integer> listADT = new ListADTImpl<>();
    for (int i = 0; i < 20; ++i) {
      listADT.addFront(i);
    }
    EnhancedList<Integer> immutableList = new ImmutableList<>(listADT);
    EnhancedList<Integer> mutableList = immutableList.getCounterPart();
    EnhancedList<Integer> immutableList2 = mutableList.getCounterPart();
    try {
      immutableList.addBack(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add back", e.getMessage());
    }
    try {
      immutableList.add(0, 0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add", e.getMessage());
    }
    try {
      immutableList.addFront(1);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot add front", e.getMessage());
    }
    try {
      immutableList.remove(0);
    }
    catch (UnsupportedOperationException e) {
      assertEquals("Immutable list cannot remove", e.getMessage());
    }
  }
}