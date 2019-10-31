import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
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
    getPrice.getPrice("GOOG", LocalDate.of(2019, 3, 14));
    getPrice.getPrice("MSFT", LocalDate.of(2019, 3, 14));
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
  public void testOutput() {
    IPortfolio portfolio = new Portfolio("portfolio", getPrice);
    assertEquals("portfolio", portfolio.getPortfolioName());
    assertEquals(0, portfolio.getStockNumber(), 1e-6);
    assertEquals(0, portfolio.getStockNumberByDate(LocalDate.now()), 1e-6);
    assertEquals(0, portfolio.getStockCost(), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(LocalDate.now()), 1e-6);
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
    assertEquals(477595.557, portfolio.getStockCost(), 1e-6);
    assertEquals(346118.557, portfolio.getStockCostByDate(
            LocalDate.of(2019, 3, 12)), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(
            LocalDate.of(2018, 1, 1)), 1e-6);
    //assertEquals(513087.084, portfolio.getValue(), 1e-6);
    assertEquals(380548.684, portfolio.getValueByDate(
            LocalDate.of(2019, 3, 12)), 1e-6);
    assertEquals(0, portfolio.getValueByDate(
            LocalDate.of(2018, 1, 1)), 1e-6);
    assertEquals("MSFT: 200.0\nGOOG: 413.4\n", portfolio.getPortfolioState());
  }
}