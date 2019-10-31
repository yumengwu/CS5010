import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeModel;
import tradestock.model.tradeoperation.TradeOperation;

/**
 * A JUnit test class for TradeOperation interface.
 */
public class TradeOperationTest {
  private TradeOperation<Stock> test1;
  private TradeOperation<Stock> model;

  @Before
  public void setUp() {
    test1 = new TradeModel();
    model = TradeModel.getBuilder().build();
    test1.createPortfolio("name");
    test1.createPortfolio("name1");
    test1.createPortfolio("name2");
    test1.createPortfolio("name3");
  }

  @Test
  public void testCreatePortfolioInvalid() {
    TradeOperation<Stock> model = TradeModel.getBuilder().build();
    try {
      model.createPortfolio(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.createPortfolio("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.createPortfolio("p1");
      model.createPortfolio("p1");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name already exists.", e.getMessage());
    }
  }

  @Test
  public void createPortfolio() {
    model.createPortfolio("name");
    model.createPortfolio("name1");
    assertEquals("name\nname1\n", model.getPortfolioList());
  }

  @Test
  public void testShowPortfolioList() {
    TradeOperation<Stock> model = TradeModel.getBuilder().build();
    assertEquals("", model.getPortfolioList());
    model.createPortfolio("p1");
    model.createPortfolio("p2");
    assertEquals("p1\np2\n", model.getPortfolioList());
  }

  @Test
  public void testShowDetailOfPortfolio() {
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 20);
    test1.buy("name", "amzn",
        LocalDate.of(2019, 02, 14), 20);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 15), 20);
    assertEquals("GOOG: 40.0\nAMZN: 20.0\n",test1.getPortfolioState("name"));
    assertEquals("GOOG: 20.0\nAMZN: 20.0\n",
            test1.getPortfolioStateByDate("name",
        LocalDate.of(2019, 2, 14)));
    assertEquals("GOOG: 40.0\nAMZN: 20.0\n",
            test1.getPortfolioStateByDate("name",
            LocalDate.of(2019, 2, 15)));
    assertEquals("",test1.getPortfolioState("name1"));
    assertEquals("", test1.getPortfolioStateByDate("name1",
        LocalDate.of(2019,03,02)));
    try {
      test1.getPortfolioState("nnnnnn");
      fail("no such portfolio");
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      test1.getPortfolioStateByDate("nnnnnnn",
          LocalDate.of(2039, 04, 01));
      fail("no such portfolio");
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      test1.getPortfolioStateByDate("name",
          LocalDate.of(2039, 04, 01));
      fail("no such portfolio");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
  }

  @Test
  public void testPublicMethods() {
    TradeOperation model = TradeModel.getBuilder().build();
    model.createPortfolio("p1");
    model.createPortfolio("p2");
    assertEquals(0, model.getTotalCostBasis("p1"), 1e-6);
    assertEquals(0, model.getTotalCostBasis("p2"), 1e-6);

    assertEquals(0,
            model.getTotalCostBasisByDate("p1", LocalDate.now()), 1e-6);
    assertEquals(0,
            model.getTotalCostBasisByDate("p2", LocalDate.now()), 1e-6);
    try {
      model.getTotalCostBasisByDate(null, LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("p3", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("p1", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    try {
      model.getTotalValue(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalValue("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalValue("p3");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    assertEquals(0, model.getTotalValueByDate("p1",
            LocalDate.now()), 1e-6);
    assertEquals(0, model.getTotalValueByDate("p1",
            LocalDate.now()), 1e-6);
    try {
      model.getTotalValueByDate(null, LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalValueByDate("", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalValueByDate("p3", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getTotalValueByDate("p1", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    assertEquals("", model.getPortfolioState("p1"));
    assertEquals("", model.getPortfolioState("p2"));
    try {
      model.getPortfolioState(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getPortfolioState("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getPortfolioState("p3");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    model.buy("p1", "GOOG",
            LocalDate.of(2019, 3,5), 100);
    model.buy("p1", "GOOG",
            LocalDate.of(2019, 3,6), 100);
    model.buy("p1", "GOOG",
            LocalDate.of(2019, 3,12), 100);
    model.buy("p1", "MSFT",
            LocalDate.of(2019, 3,12), 100);
    model.buy("p1", "MSFT",
            LocalDate.of(2019, 3,6), 100);
    model.buy("p2", "GOOG",
            LocalDate.of(2019, 3,12), 50);
    model.buy("p2", "GOOG",
            LocalDate.of(2019, 3,5), 50);
    model.buy("p2", "AAPL",
            LocalDate.of(2019, 3,13), 50);
    model.buy("p2", "AAPL",
            LocalDate.of(2019, 3,12), 50);
    assertEquals("p1\np2\n", model.getPortfolioList());
    assertEquals(371550.0, model.getTotalCostBasis("p1"), 1e-6);
    assertEquals(134528.5, model.getTotalCostBasis("p2"), 1e-6);
    assertEquals(0.0, model.getTotalCostBasisByDate("p1",
            LocalDate.of(2018, 1, 1)), 1e-6);
    assertEquals(0.0, model.getTotalCostBasisByDate("p2",
            LocalDate.of(2018, 1, 1)), 1e-6);
    assertEquals(371550.0, model.getTotalCostBasisByDate("p1",
            LocalDate.now()), 1e-6);
    assertEquals(134528.5, model.getTotalCostBasisByDate("p2",
            LocalDate.now()), 1e-6);
    assertEquals(242442.0, model.getTotalCostBasisByDate("p1",
            LocalDate.of(2019, 3, 11)), 1e-6);
    assertEquals(57503.0, model.getTotalCostBasisByDate("p2",
            LocalDate.of(2019, 3, 11)), 1e-6);
    //assertEquals(378792.0, model.getTotalValue("p1"), 1e-6);
    //assertEquals(137228.0, model.getTotalValue("p2"), 1e-6);
    assertEquals(0, model.getTotalValueByDate("p1",
            LocalDate.of(2018, 1, 1)), 1e-6);
    assertEquals(0, model.getTotalValueByDate("p2",
            LocalDate.of(2018, 1, 1)), 1e-6);
    //assertEquals(378792.0, model.getTotalValueByDate("p1",LocalDate.now()), 1e-6);
    //assertEquals(137228.0, model.getTotalValueByDate("p2",LocalDate.now()), 1e-6);
    assertEquals(239989.0, model.getTotalValueByDate("p1",
            LocalDate.of(2019, 3, 11)), 1e-6);
    assertEquals(57222.5, model.getTotalValueByDate("p2",
            LocalDate.of(2019, 3, 11)), 1e-6);
    assertEquals("MSFT: 200.0\n" +
            "GOOG: 300.0\n", model.getPortfolioState("p1"));
    assertEquals("GOOG: 100.0\n" +
            "AAPL: 100.0\n", model.getPortfolioState("p2"));
    try {
      model.getPortfolioState(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getPortfolioState("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getPortfolioState("p3");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    assertEquals("", model.getPortfolioStateByDate("p1",
            LocalDate.of(2018, 1, 1)));
    assertEquals("", model.getPortfolioStateByDate("p2",
            LocalDate.of(2018, 1, 1)));
    assertEquals("MSFT: 100.0\n" +
            "GOOG: 200.0\n", model.getPortfolioStateByDate("p1",
            LocalDate.of(2019, 3, 11)));
    assertEquals("GOOG: 50.0\n", model.getPortfolioStateByDate("p2",
            LocalDate.of(2019, 3, 11)));
    assertEquals("MSFT: 200.0\n" +
            "GOOG: 300.0\n", model.getPortfolioStateByDate("p1",
            LocalDate.now()));
    assertEquals("GOOG: 100.0\n" +
            "AAPL: 100.0\n", model.getPortfolioStateByDate("p2",
            LocalDate.now()));
    try {
      model.getPortfolioStateByDate(null, LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("p3", LocalDate.now());
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("p1", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    try {
      model.buy(null, "GOOG",
              LocalDate.of(2019, 1, 1), 100);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.buy("p1", null,
              LocalDate.of(2019, 1, 1), 100);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.buy("p3", "GOOG",
              LocalDate.of(2019, 1, 1), 100);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.buy("p1", "GOOG", null, 100);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    try {
      model.buy("p1", "GOOG",
              LocalDate.of(2019, 3, 16), 100);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Cannot buy in holiday.", e.getMessage());
    }
    try {
      model.buy("p1", "GOOG",
              LocalDate.of(2019, 3, 14), -100);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Number must be positive.", e.getMessage());
    }
  }

  @Test
  public void buy() {
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 20);
    test1.buy("name", "amzn",
        LocalDate.of(2019, 02, 14), 20);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 15), 20);
    test1.buy("name1", "goog",
        LocalDate.of(2019, 02, 19), 20);
    assertEquals("GOOG: 40.0\nAMZN: 20.0\n",test1.getPortfolioState("name"));
    assertEquals("GOOG: 20.0\n", test1.getPortfolioState("name1"));
    try {
      test1.buy("aaaa", "goog",
          LocalDate.of(2019, 02, 14), 20);
      fail("No such portfolio.");
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      test1.buy("name", "googeee",
          LocalDate.of(2019, 02, 14), 20);
      fail("No such stock.");
    }
    catch (IllegalArgumentException e) {
      //exception throw by API.
    }
    try {
      test1.buy("name", "goog",
          LocalDate.of(2039, 02, 14), 20);
      fail("Invaid date.");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
  }

  @Test
  public void getCostBasisByDateTest() {
    assertEquals(0, test1.getTotalCostBasis("name"), 0.0001);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 350);
    test1.buy("name", "goog",
        LocalDate.of(2019, 02, 15), 200);
    test1.buy("name2", "goog",
        LocalDate.of(2019, 02, 15), 350);
    test1.buy("name3", "goog",
        LocalDate.of(2019, 03, 15), 350);
    assertEquals(391317.5,test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 02,14)), 0.0001);
    assertEquals(395528.0, test1.getTotalCostBasisByDate("name2",
        LocalDate.of(2019, 03,16)), 0.001);
    assertEquals(417683.00000000006, test1.getTotalCostBasisByDate("name3",
        LocalDate.of(2019, 03,16)), 0.001);
    assertEquals(617333.5, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 2,15)), 0.0001);
    assertEquals(0.0, test1.getTotalValueByDate("name",
        LocalDate.of(2019,01,05)), 0.001);
    assertEquals(617333.5,test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 02)), 0.001);
    assertEquals(0.0,test1.getTotalCostBasisByDate("name1",
        LocalDate.of(2019, 03, 02)), 0.001);
    try {
      test1.getTotalCostBasisByDate("name",
          LocalDate.of(2019, 04, 15));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
    try {
      test1.getTotalCostBasisByDate("naaaaaa",
          LocalDate.of(2019, 02, 15));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      test1.getTotalCostBasisByDate(null,
          LocalDate.of(2019, 03, 02));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      test1.getTotalCostBasisByDate("",
          LocalDate.of(2019, 03, 02));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testCostBasisBeforeBuyDate() {
    assertEquals(0, test1.getTotalCostBasis("name"), 0.0001);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 350);
    assertEquals(0.0, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 02, 01)), 0.001);
  }

  @Test
  public void testCostBasisDifferentDays() {
    assertEquals(0, test1.getTotalCostBasis("name"), 0.0001);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 350);
    assertEquals(391317.5, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 01)), 0.001);
    assertEquals(391317.5, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 02)), 0.001);
    assertEquals(391317.5, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 03)), 0.001);
    assertEquals(391317.5, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 04)), 0.001);
    assertEquals(391317.5, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 12)), 0.001);
  }

  @Test
  public void getCostBasisTest() {
    assertEquals(0, test1.getTotalCostBasis("name"), 0.0001);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 350);
    test1.buy("name", "goog",
        LocalDate.of(2019, 02, 15), 200);
    assertEquals(391317.5,test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 02,14)), 0.0001);
    assertEquals(617333.5, test1.getTotalCostBasis("name"), 0.0001);
    assertEquals(0.0, test1.getTotalCostBasis("name1"), 0.0001);
    try {
      test1.getTotalCostBasis(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      test1.getTotalCostBasis("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      test1.getTotalCostBasis("p3");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
  }

  @Test
  public void getTotalValueByDate() {
    assertEquals(0.0, test1.getTotalValueByDate("name",
        LocalDate.of(2019, 03, 12)), 0.001);
    test1.buy("name2", "GOOG",
        LocalDate.of(2019, 02, 14), 20);
    assertEquals(22361.0, test1.getTotalCostBasis("name2"), 0.001);
    assertEquals(23565.2, test1.getTotalValueByDate("name2",
        LocalDate.of(2019, 03, 12)),0.001);
    assertEquals(23776.199999999997, test1.getTotalValueByDate("name2",
        LocalDate.of(2019, 03, 19)), 0.001);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 14), 20);
    test1.buy("name1", "GOOG",
        LocalDate.of(2019, 03, 15), 20);
    assertEquals(22361.0, test1.getTotalCostBasisByDate("name",
        LocalDate.of(2019, 03, 18)), 0.0001);
    assertEquals(23867.600000000002, test1.getTotalCostBasisByDate("name1",
        LocalDate.of(2019, 03, 18)), 0.0001);
    test1.buy("name", "amzn",
        LocalDate.of(2019, 02, 14), 20);
    test1.buy("name", "GOOG",
        LocalDate.of(2019, 02, 15), 20);
    test1.buy("name1", "goog",
        LocalDate.of(2019, 02, 19), 20);
    assertEquals(81586.0, test1.getTotalValueByDate("name",
        LocalDate.of(2019, 03, 18)), 0.001);
    try {
      test1.getTotalValueByDate("naaa",
          LocalDate.of(2019, 03, 11));
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      test1.getTotalValueByDate("name", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    try {
      test1.getTotalValueByDate("name",
          LocalDate.of(2030, 03, 10));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
  }
}