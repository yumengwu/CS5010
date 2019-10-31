import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;

/**
 * A JUnit test class for IStock interface.
 */
public class IStockTest {
  @Test
  public void testInvalidConstruct1() {
    try {
      IStock stock = new Stock(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock symbol cannot be null empty string.", e.getMessage());
    }
  }

  @Test
  public void testInvalidConstruct2() {
    try {
      IStock stock = new Stock("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock symbol cannot be null empty string.", e.getMessage());
    }
  }

  @Test
  public void testGetSymbol() {
    IStock stock = new Stock("GOOG");
    assertEquals("GOOG", stock.getStockSymbol());
  }

  @Test
  public void testValidOperation() {
    IStock stock = new Stock("GOOG");
    assertEquals("GOOG", stock.getStockSymbol());
    assertEquals(0, stock.getNumber(), 1e-6);
    assertEquals(0, stock.getCost(), 1e-6);
    assertEquals("Stock symbol: GOOG\n", stock.getTransactionHistory());
    stock.addTransactionHistory(LocalDate.of(2013, 5,2),
            false, 100.5, 100);
    stock.addTransactionHistory(LocalDate.of(2016, 7,4),
            false, 199.5, 200);
    stock.addTransactionHistory(LocalDate.of(2011, 1,22),
            false, 10.5, 10);
    stock.addTransactionHistory(LocalDate.of(2013, 12,2),
            true, 10.5, 100);
    stock.addTransactionHistory(LocalDate.of(2015, 9,11),
            true, 50.5, 150);
    assertEquals(249.5, stock.getNumber(), 1e-6);
    assertEquals(0, stock.getNumberByDate(
            LocalDate.of(2000, 1, 1)), 1e-6);
    assertEquals(249.5, stock.getNumberByDate(
            LocalDate.of(2018, 1, 1)), 1e-6);
    assertEquals(100.5, stock.getNumberByDate(
            LocalDate.of(2015, 1, 1)), 1e-6);
    assertEquals(50, stock.getNumberByDate(
            LocalDate.of(2016, 1, 1)), 1e-6);
    assertEquals(41430.0, stock.getCost(), 1e-6);
    assertEquals(0, stock.getCostByDate(
            LocalDate.of(2000, 1, 1)), 1e-6);
    assertEquals(41430.0, stock.getCostByDate(
            LocalDate.of(2018, 1, 1)), 1e-6);
    assertEquals(9105.0, stock.getCostByDate(
            LocalDate.of(2015, 1, 1)), 1e-6);
    assertEquals(1530.0, stock.getCostByDate(
            LocalDate.of(2016, 1, 1)), 1e-6);
    assertEquals("Stock symbol: GOOG\n" +
            "Date: 2011-01-22, bought 10.5 shares, cost: 10.0 for each share\n" +
            "Date: 2013-05-02, bought 100.5 shares, cost: 100.0 for each share\n" +
            "Date: 2013-12-02, sold 10.5 shares, cost: 100.0 for each share\n" +
            "Date: 2015-09-11, sold 50.5 shares, cost: 150.0 for each share\n" +
            "Date: 2016-07-04, bought 199.5 shares, cost: 200.0 for each share\n",
            stock.getTransactionHistory());
    try {
      stock.getCostByDate(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    try {
      stock.getNumberByDate(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
  }
}