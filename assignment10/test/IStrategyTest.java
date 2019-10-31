import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import tradestock.model.strategy.IStrategy;
import tradestock.model.strategy.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A JUnit test class for IStrategy interface.
 */
public class IStrategyTest {
  @Test
  public void testConstructor1() {
    try {
      IStrategy strategy = new Strategy(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Strategy name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testConstructor2() {
    try {
      IStrategy strategy = new Strategy("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Strategy name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testStrategyName() {
    IStrategy strategy = new Strategy("s1");
    assertEquals("s1", strategy.getStrategyName());
  }

  @Test
  public void testStocks1() {
    IStrategy strategy = new Strategy("s1");
    List<String> stocks = new LinkedList<>();
    List<Double> weights = new LinkedList<>();
    try {
      strategy.setStocks(stocks, weights);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid stock weight list.", e.getMessage());
    }
  }

  @Test
  public void testStocks2() {
    IStrategy strategy = new Strategy("s1");
    List<String> stocks = new LinkedList<>();
    stocks.add("a");
    stocks.add("b");
    List<Double> weights = new LinkedList<>();
    weights.add(0.1);
    try {
      strategy.setStocks(stocks, weights);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid stock weight list.", e.getMessage());
    }
  }

  @Test
  public void testStocks3() {
    IStrategy strategy = new Strategy("s1");
    List<String> stocks = new LinkedList<>();
    stocks.add("a");
    stocks.add("a");
    List<Double> weights = new LinkedList<>();
    weights.add(0.1);
    weights.add(0.1);
    try {
      strategy.setStocks(stocks, weights);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid.", e.getMessage());
    }
  }

  @Test
  public void testStocks4() {
    IStrategy strategy = new Strategy("s1");
    List<String> stocks = new LinkedList<>();
    stocks.add("a");
    stocks.add("b");
    List<Double> weights = new LinkedList<>();
    weights.add(0.6);
    weights.add(0.6);
    try {
      strategy.setStocks(stocks, weights);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Sum of weight cannot be greater than 1.", e.getMessage());
    }
  }

  @Test
  public void testStocks5() {
    IStrategy strategy = new Strategy("s1");
    List<String> stocks = new LinkedList<>();
    stocks.add("a");
    stocks.add("b");
    List<Double> weights = new LinkedList<>();
    weights.add(0.4);
    weights.add(0.4);
    strategy.setStocks(stocks, weights);
    stocks = strategy.getStockList();
    assertEquals(2, stocks.size());
    assertEquals(true, stocks.contains("a"));
    assertEquals(true, stocks.contains("b"));
  }

  @Test
  public void testBeginDate() {
    IStrategy strategy = new Strategy("s1");
    strategy.setBeginDate(LocalDate.of(2019, 1, 1));
    assertEquals("2019-01-01", strategy.getBeginDate().toString());
  }

  @Test
  public void testEndDate() {
    IStrategy strategy = new Strategy("s1");
    strategy.setEndDate(LocalDate.of(2019, 1, 1));
    assertEquals("2019-01-01", strategy.getEndDate().toString());
  }

  @Test
  public void testAmount() {
    IStrategy strategy = new Strategy("s1");
    strategy.setAmount(10);
    assertEquals(10, strategy.getAmount(), 1e-6);
  }

  @Test
  public void testDay() {
    IStrategy strategy = new Strategy("s1");
    strategy.setDay(10);
    assertEquals(10, strategy.getDay(), 1e-6);
  }
}