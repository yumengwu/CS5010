import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.getprice.MockGetPrice;
import tradestock.model.portfolio.IPortfolio;
import tradestock.model.portfolio.Portfolio;

/**
 * A JUnit test class for IPortfolio interface.
 */
public class IPortfolioTest {
  private IGetPrice getPrice;

  @Before
  public void setUp() {
    getPrice = new GetPrice();
    //getPrice.getPrice("GOOG", LocalDate.of(2019, 3, 14));
    //getPrice.getPrice("MSFT", LocalDate.of(2019, 3, 14));
  }

  @Test
  public void testInvalidConstruct() {
    try {
      IPortfolio portfolio = new Portfolio(null, getPrice);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      IPortfolio portfolio = new Portfolio("", getPrice);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      IPortfolio portfolio = new Portfolio("p1", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("GetPrice object cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testOutput1() {
    IPortfolio portfolio = new Portfolio("p1", getPrice);
    assertEquals("p1", portfolio.getPortfolioName());
    assertEquals(0, portfolio.getStockNumber(), 1e-6);
    assertEquals(0, portfolio.getStockNumberByDate(LocalDate.now()), 1e-6);
    assertEquals(0, portfolio.getStockCost(0), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(LocalDate.now(), 0), 1e-6);
    assertEquals(0, portfolio.getValue(), 1e-6);
    assertEquals(0, portfolio.getValueByDate(LocalDate.now()), 1e-6);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2018, 11, 1), false, 100);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019, 1, 7), false, 200);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019,3,11), false, 66.7);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019,3,12), true, 53.3);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019,3,13), false, 100);
    portfolio.addTransaction("MSFT",
            LocalDate.of(2018, 11, 1), false, 100);
    portfolio.addTransaction("MSFT",
            LocalDate.of(2019, 3, 13), false, 100);
    assertEquals(613.4, portfolio.getStockNumber(), 1e-6);
    assertEquals(0,
            portfolio.getStockNumberByDate(LocalDate.of(2018,1,1)), 1e-6);
    assertEquals(413.4,
            portfolio.getStockNumberByDate(LocalDate.of(2019, 3, 12)), 1e-6);
    assertEquals(476877.632, portfolio.getStockCost(0), 1e-6);
    assertEquals(346095.632, portfolio.getStockCostByDate(
            LocalDate.of(2019, 3, 12), 0), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(
            LocalDate.of(2018, 1, 1), 0), 1e-6);
    //assertEquals(513087.084, portfolio.getValue(), 1e-6);
    assertEquals(385310.88, portfolio.getValueByDate(
            LocalDate.of(2019, 3, 12)), 1e-6);
    assertEquals(0, portfolio.getValueByDate(
            LocalDate.of(2018, 1, 1)), 1e-6);
    portfolio.savePortfolio("p1.xml");
  }

  @Test
  public void testOutput2() {
    IPortfolio portfolio = new Portfolio("p1", new MockGetPrice());
    assertEquals("p1", portfolio.getPortfolioName());
    assertEquals(0, portfolio.getStockNumber(), 1e-6);
    assertEquals(0, portfolio.getStockNumberByDate(LocalDate.now()), 1e-6);
    assertEquals(0, portfolio.getStockCost(0), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(LocalDate.now(), 0), 1e-6);
    assertEquals(0, portfolio.getValue(), 1e-6);
    assertEquals(0, portfolio.getValueByDate(LocalDate.now()), 1e-6);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2018, 11, 1), false, 100);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019, 1, 7), false, 200);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019,3,11), false, 66.7);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019,3,12), true, 53.3);
    portfolio.addTransaction("GOOG",
            LocalDate.of(2019,3,13), false, 100);
    portfolio.addTransaction("MSFT",
            LocalDate.of(2018, 11, 1), false, 100);
    portfolio.addTransaction("MSFT",
            LocalDate.of(2019, 3, 13), false, 100);
    assertEquals(613.4, portfolio.getStockNumber(), 1e-6);
    assertEquals(0,
            portfolio.getStockNumberByDate(LocalDate.of(2018,1,1)), 1e-6);
    assertEquals(413.4,
            portfolio.getStockNumberByDate(LocalDate.of(2019, 3, 12)), 1e-6);
    assertEquals(61340.0, portfolio.getStockCost(0), 1e-6);
    assertEquals(61375.0, portfolio.getStockCost(5), 1e-6);
    assertEquals(41340.0, portfolio.getStockCostByDate(
            LocalDate.of(2019, 3, 12), 0), 1e-6);
    assertEquals(41365.0, portfolio.getStockCostByDate(
            LocalDate.of(2019, 3, 12), 5), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(
            LocalDate.of(2018, 1, 1), 0), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(
            LocalDate.of(2018, 1, 1), 5), 1e-6);
    //assertEquals(513087.084, portfolio.getValue(), 1e-6);
    assertEquals(41340.0, portfolio.getValueByDate(
            LocalDate.of(2019, 3, 12)), 1e-6);
    assertEquals(0, portfolio.getValueByDate(
            LocalDate.of(2018, 1, 1)), 1e-6);
    portfolio.savePortfolio("p1.xml");
  }
}