import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.transactionhistory.ITransactionHistory;

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
    assertEquals(0, stock.getCost(0), 1e-6);
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
    assertEquals(41430.0, stock.getCost(0), 1e-6);
    assertEquals(0, stock.getCostByDate(
            LocalDate.of(2000, 1, 1), 0), 1e-6);
    assertEquals(0, stock.getCostByDate(
            LocalDate.of(2000, 1, 1), 5), 1e-6);
    assertEquals(41430.0, stock.getCostByDate(
            LocalDate.of(2018, 1, 1), 0), 1e-6);
    assertEquals(41455.0, stock.getCostByDate(
            LocalDate.of(2018, 1, 1), 5), 1e-6);
    assertEquals(9105.0, stock.getCostByDate(
            LocalDate.of(2015, 1, 1), 0), 1e-6);
    assertEquals(9120.0, stock.getCostByDate(
            LocalDate.of(2015, 1, 1), 5), 1e-6);
    assertEquals(1530.0, stock.getCostByDate(
            LocalDate.of(2016, 1, 1), 0), 1e-6);
    assertEquals(1550.0, stock.getCostByDate(
            LocalDate.of(2016, 1, 1), 5), 1e-6);
    List<ITransactionHistory> historyList = stock.getTransactionHistory();
    assertEquals("2013-05-02", historyList.get(0).getDate().toString());
    assertEquals(false, historyList.get(0).isSell());
    assertEquals(100.5, historyList.get(0).getNumber(), 1e-6);
    assertEquals(100, historyList.get(0).getCost(), 1e-6);
    assertEquals("2016-07-04", historyList.get(1).getDate().toString());
    assertEquals(false, historyList.get(1).isSell());
    assertEquals(199.5, historyList.get(1).getNumber(), 1e-6);
    assertEquals(200, historyList.get(1).getCost(), 1e-6);
    assertEquals("2011-01-22", historyList.get(2).getDate().toString());
    assertEquals(false, historyList.get(2).isSell());
    assertEquals(10.5, historyList.get(2).getNumber(), 1e-6);
    assertEquals(10, historyList.get(2).getCost(), 1e-6);
    assertEquals("2013-12-02", historyList.get(3).getDate().toString());
    assertEquals(true, historyList.get(3).isSell());
    assertEquals(10.5, historyList.get(3).getNumber(), 1e-6);
    assertEquals(100, historyList.get(3).getCost(), 1e-6);
    assertEquals("2015-09-11", historyList.get(4).getDate().toString());
    assertEquals(true, historyList.get(4).isSell());
    assertEquals(50.5, historyList.get(4).getNumber(), 1e-6);
    assertEquals(150, historyList.get(4).getCost(), 1e-6);
    try {
      stock.getCostByDate(null, 0);
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
    try {
      stock.getCost(-1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative.", e.getMessage());
    }
  }
}