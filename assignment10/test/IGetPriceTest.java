import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.getprice.MockGetPrice;

/**
 * A JUnit test class for IGetPrice interface.
 */
public class IGetPriceTest {
  @Test
  public void test111() {
    IGetPrice getPrice = new GetPrice();
    System.out.println(getPrice.getPrice("GOOG", LocalDate.now()));
  }
  @Test
  public void testAlphaVantage() {
    IGetPrice getPrice = new GetPrice();
    assertEquals(1184.46, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,15)), 1e-6);
    assertEquals(1175.76, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,11)), 1e-6);
  }

  @Test
  public void testMockGetPrice() {
    IGetPrice getPrice = new MockGetPrice();
    assertEquals(100, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,15)), 1e-6);
    assertEquals(100, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,14)), 1e-6);
    assertEquals(100, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,11)), 1e-6);
  }

  @Test
  public void testInvalidInput1() {
    IGetPrice getPrice = new MockGetPrice();
    try {
      getPrice.getPrice(null, LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock symbol cannot be null or empty string.", e.getMessage());
    }
    try {
      getPrice.getPrice("", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock symbol cannot be null or empty string.", e.getMessage());
    }
    try {
      getPrice.getPrice("GOOG", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null", e.getMessage());
    }
    try {
      getPrice.getPrice("GOOG", LocalDate.of(2048, 12, 12));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date does not exist.", e.getMessage());
    }
  }

  @Test
  public void testInvalidInput2() {
    IGetPrice getPrice = new GetPrice();
    try {
      getPrice.getPrice(null, LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock symbol cannot be null or empty string.", e.getMessage());
    }
    try {
      getPrice.getPrice("", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock symbol cannot be null or empty string.", e.getMessage());
    }
    try {
      getPrice.getPrice("GOOG", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null", e.getMessage());
    }
    try {
      getPrice.getPrice("GOOG", LocalDate.of(2048, 12, 12));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date does not exist.", e.getMessage());
    }
  }
}