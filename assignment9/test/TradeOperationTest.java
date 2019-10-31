import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.MockGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeModel;
import tradestock.model.tradeoperation.TradeOperation;

/**
 * A JUnit test class for TradeOperation interface.
 */
public class TradeOperationTest {
  @Test
  public void testConstruct1() {
    try {
      TradeModel model = new TradeModel(null, 10);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("API object cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testConstruct2() {
    try {
      TradeModel model = new TradeModel(new MockGetPrice(), -10);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void testConstruct3() {
    try {
      TradeModel model = new TradeModel(new MockGetPrice(), 10);
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testConstruct4() {
    try {
      TradeOperation model = TradeModel.getBuilder().setAPI(null).build();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("API object cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testConstruct5() {
    try {
      TradeOperation model = TradeModel.getBuilder().setCommissionFee(-1).build();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void testConstruct6() {
    try {
      TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).
              setCommissionFee(1).build();
    }
    catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testCreatePortfolioValid() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name1");
    model.createPortfolio("name2");
    model.createPortfolio("name3");
    List<String> pList = model.getPortfolioList();
    assertEquals("name1", pList.get(0));
    assertEquals("name2", pList.get(1));
    assertEquals("name3", pList.get(2));
  }

  @Test
  public void testCreatePortfolioInvalid1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createPortfolio(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioInvalid2() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createPortfolio("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioInvalid3() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createPortfolio("name1");
      model.createPortfolio("name1");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name already exists.", e.getMessage());
    }
  }

  @Test
  public void testSetAPIInvalid() {
    TradeOperation model = TradeModel.getBuilder().build();
    try {
      model.setAPI(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("API object cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testCommissionFeeValid() {
    TradeOperation model = TradeModel.getBuilder().build();
    assertEquals(5, model.getCommissionFee(), 1e-6);
    model.setCommissionFee(2);
    assertEquals(2, model.getCommissionFee(), 1e-6);
  }

  @Test
  public void testCommissionFeeInvalid() {
    TradeOperation model = TradeModel.getBuilder().build();
    try {
      model.setCommissionFee(-1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void testShowDetailOfPortfolio() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 14), 20);
    model.buy("name", "amzn",
            LocalDate.of(2019, 02, 14), 20);
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 15), 20);
    List<IStock> stockList = model.getPortfolioState("name");
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals(40, stockList.get(0).getNumber(), 1e-6);
    assertEquals("AMZN", stockList.get(1).getStockSymbol());
    assertEquals(20, stockList.get(1).getNumber(), 1e-6);
    stockList = model.getPortfolioStateByDate("name",
            LocalDate.of(2019, 2, 14));
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals(20, stockList.get(0).getNumber(), 1e-6);
    assertEquals("AMZN", stockList.get(1).getStockSymbol());
    assertEquals(20, stockList.get(1).getNumber(), 1e-6);
    stockList = model.getPortfolioStateByDate("name",
            LocalDate.of(2019, 2, 15));
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals(40, stockList.get(0).getNumber(), 1e-6);
    assertEquals("AMZN", stockList.get(1).getStockSymbol());
    assertEquals(20, stockList.get(1).getNumber(), 1e-6);
    stockList = model.getPortfolioState("name1");
    assertEquals(0, stockList.size());
    stockList = model.getPortfolioStateByDate("name1",
            LocalDate.of(2019,03,02));
    assertEquals(0, stockList.size());
    try {
      model.getPortfolioState("nnnnnn");
      fail("no such portfolio");
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("nnnnnnn",
              LocalDate.of(2039, 04, 01));
      fail("no such portfolio");
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("name",
              LocalDate.of(2039, 04, 01));
      fail("no such portfolio");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
  }

  @Test
  public void testPublicMethods() {
    TradeOperation model = TradeModel.getBuilder().setCommissionFee(0).build();
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
    List<IStock> stockList = model.getPortfolioState("p1");
    assertEquals(0, stockList.size());
    stockList = model.getPortfolioState("p2");
    assertEquals(0, stockList.size());
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
    List<String> portfolioList = model.getPortfolioList();
    assertEquals("p1", portfolioList.get(0));
    assertEquals("p2", portfolioList.get(1));
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
    stockList = model.getPortfolioState("p1");
    assertEquals("MSFT", stockList.get(0).getStockSymbol());
    assertEquals(200, stockList.get(0).getNumber(), 1e-6);
    assertEquals("GOOG", stockList.get(1).getStockSymbol());
    assertEquals(300, stockList.get(1).getNumber(), 1e-6);
    stockList = model.getPortfolioState("p2");
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals(100, stockList.get(0).getNumber(), 1e-6);
    assertEquals("AAPL", stockList.get(1).getStockSymbol());
    assertEquals(100, stockList.get(1).getNumber(), 1e-6);
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
    stockList = model.getPortfolioStateByDate("p1",
            LocalDate.of(2018, 1, 1));
    assertEquals(0, stockList.size());
    stockList = model.getPortfolioStateByDate("p1",
            LocalDate.of(2018, 1, 1));
    assertEquals(0, stockList.size());
    stockList = model.getPortfolioStateByDate("p1",
            LocalDate.of(2019, 3, 11));
    assertEquals("MSFT", stockList.get(0).getStockSymbol());
    assertEquals(100, stockList.get(0).getNumberByDate(
            LocalDate.of(2019, 3, 11)), 1e-6);
    assertEquals("GOOG", stockList.get(1).getStockSymbol());
    assertEquals(200, stockList.get(1).getNumberByDate(
            LocalDate.of(2019, 3, 11)), 1e-6);
    stockList = model.getPortfolioStateByDate("p2",
            LocalDate.of(2019, 3, 11));
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals(50, stockList.get(0).getNumberByDate(
            LocalDate.of(2019, 3, 11)), 1e-6);
    stockList = model.getPortfolioStateByDate("p1", LocalDate.now());
    assertEquals("MSFT", stockList.get(0).getStockSymbol());
    assertEquals(200, stockList.get(0).getNumberByDate(LocalDate.now()), 1e-6);
    assertEquals("GOOG", stockList.get(1).getStockSymbol());
    assertEquals(300, stockList.get(1).getNumberByDate(LocalDate.now()), 1e-6);
    stockList = model.getPortfolioStateByDate("p2", LocalDate.now());
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals(100, stockList.get(0).getNumberByDate(LocalDate.now()), 1e-6);
    assertEquals("AAPL", stockList.get(1).getStockSymbol());
    assertEquals(100, stockList.get(1).getNumberByDate(LocalDate.now()), 1e-6);
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
  public void getCostBasisByDateTest() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    model.createPortfolio("name2");
    model.createPortfolio("name3");
    assertEquals(0, model.getTotalCostBasis("name"), 1e-6);
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 14), 350);
    model.buy("name", "goog",
            LocalDate.of(2019, 02, 15), 200);
    model.buy("name2", "goog",
            LocalDate.of(2019, 02, 15), 350);
    model.buy("name3", "goog",
            LocalDate.of(2019, 03, 15), 350);
    assertEquals(35005.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 02,14)), 0.0001);
    assertEquals(35005.0, model.getTotalCostBasisByDate("name2",
            LocalDate.of(2019, 03,16)), 0.001);
    assertEquals(35005.0, model.getTotalCostBasisByDate("name3",
            LocalDate.of(2019, 03,16)), 0.001);
    assertEquals(55010.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 2,15)), 0.0001);
    assertEquals(0.0, model.getTotalValueByDate("name",
            LocalDate.of(2019,01,05)), 0.001);
    assertEquals(55010.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 03, 02)), 0.001);
    assertEquals(0.0, model.getTotalCostBasisByDate("name1",
            LocalDate.of(2019, 03, 02)), 0.001);
    try {
      model.getTotalCostBasisByDate("name",
              LocalDate.of(2019, 04, 15));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("naaaaaa",
              LocalDate.of(2019, 02, 15));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate(null,
              LocalDate.of(2019, 03, 02));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("",
              LocalDate.of(2019, 03, 02));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    model.setCommissionFee(2);
    assertEquals(35002.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 02,14)), 0.0001);
    assertEquals(35002.0, model.getTotalCostBasisByDate("name2",
            LocalDate.of(2019, 03,16)), 0.001);
    assertEquals(35002.0, model.getTotalCostBasisByDate("name3",
            LocalDate.of(2019, 03,16)), 0.001);
    assertEquals(55004.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 2,15)), 0.0001);
    assertEquals(0.0, model.getTotalValueByDate("name",
            LocalDate.of(2019,01,05)), 0.001);
    assertEquals(55004.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 03, 02)), 0.001);
    assertEquals(0.0, model.getTotalCostBasisByDate("name1",
            LocalDate.of(2019, 03, 02)), 0.001);
    try {
      model.getTotalCostBasisByDate("name",
              LocalDate.of(2019, 04, 15));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("naaaaaa",
              LocalDate.of(2019, 02, 15));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate(null,
              LocalDate.of(2019, 03, 02));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("",
              LocalDate.of(2019, 03, 02));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testCostBasisBeforeBuyDate() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    assertEquals(0, model.getTotalCostBasis("name"), 0.0001);
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 14), 350);
    assertEquals(0.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 02, 01)), 0.001);
  }

  @Test
  public void getCostBasisTest() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    assertEquals(0, model.getTotalCostBasis("name"), 0.0001);
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 14), 350);
    model.buy("name", "goog",
            LocalDate.of(2019, 02, 15), 200);
    assertEquals(35005.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 02,14)), 0.0001);
    assertEquals(55010.0, model.getTotalCostBasis("name"), 0.0001);
    assertEquals(0.0, model.getTotalCostBasis("name1"), 0.0001);
    try {
      model.getTotalCostBasis(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalCostBasis("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.getTotalCostBasis("p3");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
  }

  @Test
  public void getTotalValueByDate() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    model.createPortfolio("name2");
    assertEquals(0.0, model.getTotalValueByDate("name",
            LocalDate.of(2019, 03, 12)), 0.001);
    model.buy("name2", "GOOG",
            LocalDate.of(2019, 02, 14), 20);
    assertEquals(2005.0, model.getTotalCostBasis("name2"), 0.001);
    assertEquals(2000.0, model.getTotalValueByDate("name2",
            LocalDate.of(2019, 03, 12)),0.001);
    assertEquals(2000.0, model.getTotalValueByDate("name2",
            LocalDate.of(2019, 03, 19)), 0.001);
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 14), 20);
    model.buy("name1", "GOOG",
            LocalDate.of(2019, 03, 15), 20);
    assertEquals(2005.0, model.getTotalCostBasisByDate("name",
            LocalDate.of(2019, 03, 18)), 0.0001);
    assertEquals(2005.0, model.getTotalCostBasisByDate("name1",
            LocalDate.of(2019, 03, 18)), 0.0001);
    model.buy("name", "amzn",
            LocalDate.of(2019, 02, 14), 20);
    model.buy("name", "GOOG",
            LocalDate.of(2019, 02, 15), 20);
    model.buy("name1", "goog",
            LocalDate.of(2019, 02, 19), 20);
    assertEquals(6000.0, model.getTotalValueByDate("name",
            LocalDate.of(2019, 03, 18)), 0.001);
    try {
      model.getTotalValueByDate("naaa",
              LocalDate.of(2019, 03, 11));
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.getTotalValueByDate("name", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
    try {
      model.getTotalValueByDate("name",
              LocalDate.of(2030, 03, 10));
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now", e.getMessage());
    }
  }

  @Test
  public void testBuyAmount() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.buyAmount("name", "GOOG",
            LocalDate.of(2019, 3, 12), 1000);
    model.buyAmount("name", "GOOG",
            LocalDate.of(2019, 3, 13), 1500);
    assertEquals(2510, model.getTotalCostBasis("name"), 1e-6);
    model.buy("name", "MSFT",
            LocalDate.of(2019, 3, 12), 100);
    assertEquals(12515, model.getTotalCostBasis("name"), 1e-6);
    try {
      model.buyAmount("", "GOOG",
              LocalDate.of(2019, 3, 11), 1000);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.buyAmount("abc", "GOOG",
              LocalDate.of(2019, 3, 11), 1000);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.buyAmount("name", "",
              LocalDate.of(2019, 3, 11), 1000);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Stock name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.buyAmount("name", "GOOG",
              LocalDate.of(2019, 3, 31), 1000);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Cannot buy in holiday.", e.getMessage());
    }
    try {
      model.buyAmount("name", "GOOG",
              LocalDate.of(2019, 3, 29), -1000);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Money must be positive.", e.getMessage());
    }
  }

  @Test
  public void testSaveInvalid() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.setAPI(new MockGetPrice());
    model.createPortfolio("p1");
    model.buy("p1", "GOOG",
            LocalDate.of(2019, 3, 11), 100);
    model.buy("p1", "GOOG",
            LocalDate.of(2019, 3, 12), 123);
    model.buy("p1", "MSFT",
            LocalDate.of(2019, 3, 11), 36);
    model.buy("p1", "MSFT",
            LocalDate.of(2019, 3, 12), 63);
    model.buy("p1", "AAPL",
            LocalDate.of(2019, 3, 11), 55);
    try {
      model.savePortfolio(null, "p1.xml");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.savePortfolio("", "pp.xml");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.savePortfolio("abcd", "pp.xml");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No such portfolio.", e.getMessage());
    }
    try {
      model.savePortfolio("p1", null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.savePortfolio("p1", "");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testReadInvalid() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readPortfolio(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.readPortfolio("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string.", e.getMessage());
    }
    try {
      model.readPortfolio("abcdefghijk.xml");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("abcdefghijk.xml (No such file or directory)", e.getMessage());
    }
  }

  @Test
  public void testSaveReadFile() {
    TradeOperation model1 = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model1.setAPI(new MockGetPrice());
    model1.createPortfolio("p1");
    model1.buy("p1", "GOOG",
            LocalDate.of(2019, 3, 11), 100);
    model1.buy("p1", "GOOG",
            LocalDate.of(2019, 3, 12), 123);
    model1.buy("p1", "MSFT",
            LocalDate.of(2019, 3, 11), 36);
    model1.buy("p1", "MSFT",
            LocalDate.of(2019, 3, 12), 63);
    model1.buy("p1", "AAPL",
            LocalDate.of(2019, 3, 11), 55);
    model1.savePortfolio("p1", "p1.xml");
    TradeOperation model2 = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model2.readPortfolio("p1.xml");
    assertEquals(model1.getPortfolioList().get(0), model2.getPortfolioList().get(0));
    List<IStock> stock1 = model1.getPortfolioState("p1");
    List<IStock> stock2 = model2.getPortfolioState("p1");
    assertEquals(stock1.size(), stock2.size());
    for (int i = 0; i < stock1.size(); ++i) {
      assertEquals(stock1.get(i).getStockSymbol(), stock2.get(i).getStockSymbol());
      assertEquals(stock1.get(i).getNumber(), stock2.get(i).getNumber(), 1e-6);
      assertEquals(stock1.get(i).getNumberByDate(LocalDate.of(2019, 1, 1)),
              stock2.get(i).getNumberByDate(LocalDate.of(2019, 1, 1)), 1e-6);
      assertEquals(stock1.get(i).getCost(0), stock2.get(i).getCost(0), 1e-6);
      assertEquals(stock1.get(i).getCostByDate(LocalDate.of(2019, 1, 1), 0),
              stock2.get(i).getCostByDate(LocalDate.of(2019, 1, 1), 0), 1e-6);
    }
  }
}