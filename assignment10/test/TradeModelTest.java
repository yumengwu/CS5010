import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.getprice.MockGetPrice;
import tradestock.model.portfolio.IPortfolio;
import tradestock.model.portfolio.Portfolio;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.strategy.IStrategy;
import tradestock.model.strategy.Strategy;
import tradestock.model.tradeoperation.TradeModel;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.model.transactionhistory.ITransactionHistory;
import tradestock.model.transactionhistory.TransactionHistory;
import tradestock.model.xmlparser.XMLParser;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * A Junit test.
 */
public class TradeModelTest {

  @Test
  public void testConstruct1() {
    try {
      TradeModel model = new TradeModel(null , 10);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("API object cannot be null." , e.getMessage());
    }
  }

  @Test
  public void testConstruct2() {
    try {
      TradeModel model = new TradeModel(new MockGetPrice() , -10);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative." , e.getMessage());
    }
  }

  @Test
  public void testConstruct3() {
    try {
      TradeModel model = new TradeModel(new MockGetPrice() , 10);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testConstruct4() {
    try {
      TradeOperation model = TradeModel.getBuilder().setAPI(null).build();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("API object cannot be null." , e.getMessage());
    }
  }

  @Test
  public void testConstruct5() {
    try {
      TradeOperation model = TradeModel.getBuilder().setCommissionFee(-1).build();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative." , e.getMessage());
    }
  }

  @Test
  public void testConstruct6() {
    try {
      TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).
          setCommissionFee(1).build();
    } catch (IllegalArgumentException e) {
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
    assertEquals("name1" , pList.get(0));
    assertEquals("name2" , pList.get(1));
    assertEquals("name3" , pList.get(2));
  }

  @Test
  public void testCreatePortfolioInvalid1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioInvalid2() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createPortfolio("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioInvalid3() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createPortfolio("name1");
      model.createPortfolio("name1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name already exists." , e.getMessage());
    }
  }

  @Test
  public void testSetAPIInvalid() {
    TradeOperation model = TradeModel.getBuilder().build();
    try {
      model.setAPI(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("API object cannot be null." , e.getMessage());
    }
  }

  @Test
  public void testCommissionFeeValid() {
    TradeOperation model = TradeModel.getBuilder().build();
    assertEquals(5 , model.getCommissionFee() , 1e-6);
    model.setCommissionFee(2);
    assertEquals(2 , model.getCommissionFee() , 1e-6);
  }

  @Test
  public void testCommissionFeeInvalid() {
    TradeOperation model = TradeModel.getBuilder().build();
    try {
      model.setCommissionFee(-1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Commission fee cannot be negative." , e.getMessage());
    }
  }

  @Test
  public void testShowDetailOfPortfolio() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 14) , 20);
    model.buy("name" , "amzn" ,
        LocalDate.of(2019 , 02 , 14) , 20);
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 15) , 20);
    List<IStock> stockList = model.getPortfolioState("name");
    assertEquals("GOOG" , stockList.get(0).getStockSymbol());
    assertEquals(40 , stockList.get(0).getNumber() , 1e-6);
    assertEquals("AMZN" , stockList.get(1).getStockSymbol());
    assertEquals(20 , stockList.get(1).getNumber() , 1e-6);
    stockList = model.getPortfolioStateByDate("name" ,
        LocalDate.of(2019 , 2 , 14));
    assertEquals("GOOG" , stockList.get(0).getStockSymbol());
    assertEquals(20 , stockList.get(0).getNumber() , 1e-6);
    assertEquals("AMZN" , stockList.get(1).getStockSymbol());
    assertEquals(20 , stockList.get(1).getNumber() , 1e-6);
    stockList = model.getPortfolioStateByDate("name" ,
        LocalDate.of(2019 , 2 , 15));
    assertEquals("GOOG" , stockList.get(0).getStockSymbol());
    assertEquals(40 , stockList.get(0).getNumber() , 1e-6);
    assertEquals("AMZN" , stockList.get(1).getStockSymbol());
    assertEquals(20 , stockList.get(1).getNumber() , 1e-6);
    stockList = model.getPortfolioState("name1");
    assertEquals(0 , stockList.size());
    stockList = model.getPortfolioStateByDate("name1" ,
        LocalDate.of(2019 , 03 , 02));
    assertEquals(0 , stockList.size());
    try {
      model.getPortfolioState("nnnnnn");
      fail("no such portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("nnnnnnn" ,
          LocalDate.of(2039 , 04 , 01));
      fail("no such portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("name" ,
          LocalDate.of(2039 , 04 , 01));
      fail("no such portfolio");
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now" , e.getMessage());
    }
  }

  @Test
  public void testPublicMethod() {
    TradeOperation model = TradeModel.getBuilder().setCommissionFee(0).build();
    model.createPortfolio("p1");
    model.createPortfolio("p2");
    assertEquals(0 , model.getTotalCostBasis("p1") , 1e-6);
    assertEquals(0 , model.getTotalCostBasis("p2") , 1e-6);

    assertEquals(0 ,
        model.getTotalCostBasisByDate("p1" , LocalDate.now()) , 1e-6);
    assertEquals(0 ,
        model.getTotalCostBasisByDate("p2" , LocalDate.now()) , 1e-6);
    try {
      model.getTotalCostBasisByDate(null , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("" , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("p3" , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("p1" , null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null." , e.getMessage());
    }
    try {
      model.getTotalValue(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalValue("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalValue("p3");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    assertEquals(0 , model.getTotalValueByDate("p1" ,
        LocalDate.now()) , 1e-6);
    assertEquals(0 , model.getTotalValueByDate("p1" ,
        LocalDate.now()) , 1e-6);
    try {
      model.getTotalValueByDate(null , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalValueByDate("" , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalValueByDate("p3" , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getTotalValueByDate("p1" , null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null." , e.getMessage());
    }
    List<IStock> stockList = model.getPortfolioState("p1");
    assertEquals(0 , stockList.size());
    stockList = model.getPortfolioState("p2");
    assertEquals(0 , stockList.size());
    try {
      model.getPortfolioState(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getPortfolioState("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getPortfolioState("p3");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    model.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 5) , 100);
    model.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 6) , 100);
    model.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 12) , 100);
    model.buy("p1" , "MSFT" ,
        LocalDate.of(2019 , 3 , 12) , 100);
    model.buy("p1" , "MSFT" ,
        LocalDate.of(2019 , 3 , 6) , 100);
    model.buy("p2" , "GOOG" ,
        LocalDate.of(2019 , 3 , 12) , 50);
    model.buy("p2" , "GOOG" ,
        LocalDate.of(2019 , 3 , 5) , 50);
    model.buy("p2" , "AAPL" ,
        LocalDate.of(2019 , 3 , 13) , 50);
    model.buy("p2" , "AAPL" ,
        LocalDate.of(2019 , 3 , 12) , 50);
    List<String> portfolioList = model.getPortfolioList();
    assertEquals("p1" , portfolioList.get(0));
    assertEquals("p2" , portfolioList.get(1));
    assertEquals(373846.0 , model.getTotalCostBasis("p1") , 1e-6);
    assertEquals(135892.5 , model.getTotalCostBasis("p2") , 1e-6);
    assertEquals(0.0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(0.0 , model.getTotalCostBasisByDate("p2" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(373846.0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.now()) , 1e-6);
    assertEquals(135892.5 , model.getTotalCostBasisByDate("p2" ,
        LocalDate.now()) , 1e-6);
    assertEquals(243164.0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    assertEquals(58101.5 , model.getTotalCostBasisByDate("p2" ,
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    //assertEquals(378792.0, model.getTotalValue("p1"), 1e-6);
    //assertEquals(137228.0, model.getTotalValue("p2"), 1e-6);
    assertEquals(0 , model.getTotalValueByDate("p1" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(0 , model.getTotalValueByDate("p2" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    //assertEquals(378792.0, model.getTotalValueByDate("p1",LocalDate.now()), 1e-6);
    //assertEquals(137228.0, model.getTotalValueByDate("p2",LocalDate.now()), 1e-6);
    assertEquals(246435.0 , model.getTotalValueByDate("p1" ,
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    assertEquals(58788.0 , model.getTotalValueByDate("p2" ,
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    stockList = model.getPortfolioState("p1");
    assertEquals("MSFT" , stockList.get(0).getStockSymbol());
    assertEquals(200 , stockList.get(0).getNumber() , 1e-6);
    assertEquals("GOOG" , stockList.get(1).getStockSymbol());
    assertEquals(300 , stockList.get(1).getNumber() , 1e-6);
    stockList = model.getPortfolioState("p2");
    assertEquals("GOOG" , stockList.get(0).getStockSymbol());
    assertEquals(100 , stockList.get(0).getNumber() , 1e-6);
    assertEquals("AAPL" , stockList.get(1).getStockSymbol());
    assertEquals(100 , stockList.get(1).getNumber() , 1e-6);
    try {
      model.getPortfolioState(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getPortfolioState("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getPortfolioState("p3");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    stockList = model.getPortfolioStateByDate("p1" ,
        LocalDate.of(2018 , 1 , 1));
    assertEquals(0 , stockList.size());
    stockList = model.getPortfolioStateByDate("p1" ,
        LocalDate.of(2018 , 1 , 1));
    assertEquals(0 , stockList.size());
    stockList = model.getPortfolioStateByDate("p1" ,
        LocalDate.of(2019 , 3 , 11));
    assertEquals("MSFT" , stockList.get(0).getStockSymbol());
    assertEquals(100 , stockList.get(0).getNumberByDate(
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    assertEquals("GOOG" , stockList.get(1).getStockSymbol());
    assertEquals(200 , stockList.get(1).getNumberByDate(
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    stockList = model.getPortfolioStateByDate("p2" ,
        LocalDate.of(2019 , 3 , 11));
    assertEquals("GOOG" , stockList.get(0).getStockSymbol());
    assertEquals(50 , stockList.get(0).getNumberByDate(
        LocalDate.of(2019 , 3 , 11)) , 1e-6);
    stockList = model.getPortfolioStateByDate("p1" , LocalDate.now());
    assertEquals("MSFT" , stockList.get(0).getStockSymbol());
    assertEquals(200 , stockList.get(0).getNumberByDate(LocalDate.now()) , 1e-6);
    assertEquals("GOOG" , stockList.get(1).getStockSymbol());
    assertEquals(300 , stockList.get(1).getNumberByDate(LocalDate.now()) , 1e-6);
    stockList = model.getPortfolioStateByDate("p2" , LocalDate.now());
    assertEquals("GOOG" , stockList.get(0).getStockSymbol());
    assertEquals(100 , stockList.get(0).getNumberByDate(LocalDate.now()) , 1e-6);
    assertEquals("AAPL" , stockList.get(1).getStockSymbol());
    assertEquals(100 , stockList.get(1).getNumberByDate(LocalDate.now()) , 1e-6);
    try {
      model.getPortfolioStateByDate(null , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("" , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("p3" , LocalDate.now());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getPortfolioStateByDate("p1" , null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null." , e.getMessage());
    }
    try {
      model.buy(null , "GOOG" ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.buy("p1" , null ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.buy("p3" , "GOOG" ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.buy("p1" , "GOOG" , null , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null." , e.getMessage());
    }
    try {
      model.buy("p1" , "GOOG" ,
          LocalDate.of(2019 , 3 , 16) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot buy in holiday." , e.getMessage());
    }
    try {
      model.buy("p1" , "GOOG" ,
          LocalDate.of(2019 , 3 , 14) , -100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Number must be positive." , e.getMessage());
    }
  }

  @Test
  public void getCostBasisByDateTest() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    model.createPortfolio("name2");
    model.createPortfolio("name3");
    assertEquals(0 , model.getTotalCostBasis("name") , 1e-6);
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 14) , 350);
    model.buy("name" , "goog" ,
        LocalDate.of(2019 , 02 , 15) , 200);
    model.buy("name2" , "goog" ,
        LocalDate.of(2019 , 02 , 15) , 350);
    model.buy("name3" , "goog" ,
        LocalDate.of(2019 , 03 , 15) , 350);
    assertEquals(35005.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 02 , 14)) , 0.0001);
    assertEquals(35005.0 , model.getTotalCostBasisByDate("name2" ,
        LocalDate.of(2019 , 03 , 16)) , 0.001);
    assertEquals(35005.0 , model.getTotalCostBasisByDate("name3" ,
        LocalDate.of(2019 , 03 , 16)) , 0.001);
    assertEquals(55010.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 2 , 15)) , 0.0001);
    assertEquals(0.0 , model.getTotalValueByDate("name" ,
        LocalDate.of(2019 , 01 , 05)) , 0.001);
    assertEquals(55010.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 03 , 02)) , 0.001);
    assertEquals(0.0 , model.getTotalCostBasisByDate("name1" ,
        LocalDate.of(2019 , 03 , 02)) , 0.001);
    try {
      model.getTotalCostBasisByDate("name" ,
          LocalDate.of(2019 , 05 , 15));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now" , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("naaaaaa" ,
          LocalDate.of(2019 , 02 , 15));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate(null ,
          LocalDate.of(2019 , 03 , 02));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("" ,
          LocalDate.of(2019 , 03 , 02));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    model.setCommissionFee(2);
    assertEquals(35002.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 02 , 14)) , 0.0001);
    assertEquals(35002.0 , model.getTotalCostBasisByDate("name2" ,
        LocalDate.of(2019 , 03 , 16)) , 0.001);
    assertEquals(35002.0 , model.getTotalCostBasisByDate("name3" ,
        LocalDate.of(2019 , 03 , 16)) , 0.001);
    assertEquals(55004.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 2 , 15)) , 0.0001);
    assertEquals(0.0 , model.getTotalValueByDate("name" ,
        LocalDate.of(2019 , 01 , 05)) , 0.001);
    assertEquals(55004.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 03 , 02)) , 0.001);
    assertEquals(0.0 , model.getTotalCostBasisByDate("name1" ,
        LocalDate.of(2019 , 03 , 02)) , 0.001);
    try {
      model.getTotalCostBasisByDate("name" ,
          LocalDate.of(2019 , 05 , 15));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now" , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("naaaaaa" ,
          LocalDate.of(2019 , 02 , 15));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate(null ,
          LocalDate.of(2019 , 03 , 02));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalCostBasisByDate("" ,
          LocalDate.of(2019 , 03 , 02));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
  }

  @Test
  public void testCostBasisBeforeBuyDate() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    assertEquals(0 , model.getTotalCostBasis("name") , 0.0001);
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 14) , 350);
    assertEquals(0.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 02 , 01)) , 0.001);
  }

  @Test
  public void getCostBasisTest() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    assertEquals(0 , model.getTotalCostBasis("name") , 0.0001);
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 14) , 350);
    model.buy("name" , "goog" ,
        LocalDate.of(2019 , 02 , 15) , 200);
    assertEquals(35005.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 02 , 14)) , 0.0001);
    assertEquals(55010.0 , model.getTotalCostBasis("name") , 0.0001);
    assertEquals(0.0 , model.getTotalCostBasis("name1") , 0.0001);
    try {
      model.getTotalCostBasis(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalCostBasis("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.getTotalCostBasis("p3");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
  }

  @Test
  public void getTotalValueByDate() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.createPortfolio("name1");
    model.createPortfolio("name2");
    assertEquals(0.0 , model.getTotalValueByDate("name" ,
        LocalDate.of(2019 , 03 , 12)) , 0.001);
    model.buy("name2" , "GOOG" ,
        LocalDate.of(2019 , 02 , 14) , 20);
    assertEquals(2005.0 , model.getTotalCostBasis("name2") , 0.001);
    assertEquals(2000.0 , model.getTotalValueByDate("name2" ,
        LocalDate.of(2019 , 03 , 12)) , 0.001);
    assertEquals(2000.0 , model.getTotalValueByDate("name2" ,
        LocalDate.of(2019 , 03 , 19)) , 0.001);
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 14) , 20);
    model.buy("name1" , "GOOG" ,
        LocalDate.of(2019 , 03 , 15) , 20);
    assertEquals(2005.0 , model.getTotalCostBasisByDate("name" ,
        LocalDate.of(2019 , 03 , 18)) , 0.0001);
    assertEquals(2005.0 , model.getTotalCostBasisByDate("name1" ,
        LocalDate.of(2019 , 03 , 18)) , 0.0001);
    model.buy("name" , "amzn" ,
        LocalDate.of(2019 , 02 , 14) , 20);
    model.buy("name" , "GOOG" ,
        LocalDate.of(2019 , 02 , 15) , 20);
    model.buy("name1" , "goog" ,
        LocalDate.of(2019 , 02 , 19) , 20);
    assertEquals(6000.0 , model.getTotalValueByDate("name" ,
        LocalDate.of(2019 , 03 , 18)) , 0.001);
    try {
      model.getTotalValueByDate("naaa" ,
          LocalDate.of(2019 , 03 , 11));
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.getTotalValueByDate("name" , null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null." , e.getMessage());
    }
    try {
      model.getTotalValueByDate("name" ,
          LocalDate.of(2030 , 03 , 10));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Date cannot be after now" , e.getMessage());
    }
  }

  @Test
  public void testBuyAmount() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("name");
    model.buyAmount("name" , "GOOG" ,
        LocalDate.of(2019 , 3 , 12) , 1000);
    model.buyAmount("name" , "GOOG" ,
        LocalDate.of(2019 , 3 , 13) , 1500);
    assertEquals(2510 , model.getTotalCostBasis("name") , 1e-6);
    model.buy("name" , "MSFT" ,
        LocalDate.of(2019 , 3 , 12) , 100);
    assertEquals(12515 , model.getTotalCostBasis("name") , 1e-6);
    try {
      model.buyAmount("" , "GOOG" ,
          LocalDate.of(2019 , 3 , 11) , 1000);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.buyAmount("abc" , "GOOG" ,
          LocalDate.of(2019 , 3 , 11) , 1000);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.buyAmount("name" , "" ,
          LocalDate.of(2019 , 3 , 11) , 1000);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.buyAmount("name" , "GOOG" ,
          LocalDate.of(2019 , 3 , 31) , 1000);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot buy in holiday." , e.getMessage());
    }
    try {
      model.buyAmount("name" , "GOOG" ,
          LocalDate.of(2019 , 3 , 29) , -1000);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Money must be positive." , e.getMessage());
    }
  }

  @Test
  public void testSaveInvalid() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.setAPI(new MockGetPrice());
    model.createPortfolio("p1");
    model.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 11) , 100);
    model.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 12) , 123);
    model.buy("p1" , "MSFT" ,
        LocalDate.of(2019 , 3 , 11) , 36);
    model.buy("p1" , "MSFT" ,
        LocalDate.of(2019 , 3 , 12) , 63);
    model.buy("p1" , "AAPL" ,
        LocalDate.of(2019 , 3 , 11) , 55);
    try {
      model.savePortfolio(null , "p1.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.savePortfolio("" , "pp.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.savePortfolio("abcd" , "pp.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
    try {
      model.savePortfolio("p1" , null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.savePortfolio("p1" , "");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string." , e.getMessage());
    }
  }

  @Test
  public void testReadInvalid() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.readPortfolio("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null or empty string." , e.getMessage());
    }
    try {
      model.readPortfolio("abcdefghijk.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("abcdefghijk.xml (No such file or directory)" , e.getMessage());
    }
  }

  @Test
  public void testSaveReadFile() {
    TradeOperation model1 = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model1.setAPI(new MockGetPrice());
    model1.createPortfolio("p1");
    model1.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 11) , 100);
    model1.buy("p1" , "GOOG" ,
        LocalDate.of(2019 , 3 , 12) , 123);
    model1.buy("p1" , "MSFT" ,
        LocalDate.of(2019 , 3 , 11) , 36);
    model1.buy("p1" , "MSFT" ,
        LocalDate.of(2019 , 3 , 12) , 63);
    model1.buy("p1" , "AAPL" ,
        LocalDate.of(2019 , 3 , 11) , 55);
    model1.savePortfolio("p1" , "p1.xml");
    TradeOperation model2 = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model2.readPortfolio("p1.xml");
    assertEquals(model1.getPortfolioList().get(0) , model2.getPortfolioList().get(0));
    List<IStock> stock1 = model1.getPortfolioState("p1");
    List<IStock> stock2 = model2.getPortfolioState("p1");
    assertEquals(stock1.size() , stock2.size());
    for ( int i = 0; i < stock1.size(); ++i ) {
      assertEquals(stock1.get(i).getStockSymbol() , stock2.get(i).getStockSymbol());
      assertEquals(stock1.get(i).getNumber() , stock2.get(i).getNumber() , 1e-6);
      assertEquals(stock1.get(i).getNumberByDate(LocalDate.of(2019 , 1 , 1)) ,
          stock2.get(i).getNumberByDate(
              LocalDate.of(2019 , 1 , 1)) , 1e-6);
      assertEquals(stock1.get(i).getCost(0) ,
          stock2.get(i).getCost(0) , 1e-6);
      assertEquals(stock1.get(i).getCostByDate(
          LocalDate.of(2019 , 1 , 1) , 0) ,
          stock2.get(i).getCostByDate(
              LocalDate.of(2019 , 1 , 1) , 0) , 1e-6);
    }
  }

  @Test
  public void testInvalidCreateStrategy() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.createStrategyDiffPercent(null , null , null , 0 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.createStrategyEqualPercent(null , null , 0 , null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent(null , null , null , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent(null , null , 0 , null ,
          0);
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }

    try {
      model.createStrategyDiffPercent("" , null , null , 0 ,
          null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.createStrategyEqualPercent("" , null , 0 , null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("" , null , null , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent("" , null , 0 , null ,
          0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }

    try {
      model.createStrategyDiffPercent("s1" , null , null , 0 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }
    try {
      model.createStrategyEqualPercent("s1" , null , 0 , null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , null , null , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent("s1" , null , 0 , null ,
          0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }

    try {
      model.createStrategyDiffPercent("s1" , new ArrayList<>() , null , 0 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }
    try {
      model.createStrategyEqualPercent("s1" , new ArrayList<>() , 0 , null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , new ArrayList<>() ,null , 0,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent("s1" , new ArrayList<>() , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid stock list." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyDiffPercent("s1" , stockList , null , 0 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyNoEndDiffPercent("s1" , stockList , null , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyDiffPercent("s1" , stockList , new ArrayList<>() , 0 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyNoEndDiffPercent("s1" , stockList , new ArrayList<>() , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 0 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid money." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyEqualPercent("s1" , stockList , 0 , null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid money." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 0 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid money." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyNoEndEqualPercent("s1" , stockList , 0 , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid money." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          null , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyEqualPercent("s1" , stockList , 100 , null ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 100 ,
          null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyNoEndEqualPercent("s1" , stockList , 100 , null ,
          0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 10 , 10) , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 10 , 10) , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 10 , 10) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyNoEndEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 10 , 10) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid begin date." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid end date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) , null , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid end date." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) ,
          LocalDate.of(2018 , 12 , 31) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid end date." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) ,
          LocalDate.of(2018 , 12 , 31) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid end date." , e.getMessage());
    }

    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) ,
          LocalDate.of(2019 , 12 , 31) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid day." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) ,
          LocalDate.of(2019 , 12 , 31) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid day." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      List<Double> weightList = new LinkedList<>();
      weightList.add(0.1);
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid day." , e.getMessage());
    }
    try {
      List<String> stockList = new LinkedList<>();
      stockList.add("goog");
      model.createStrategyNoEndEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) , 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid day." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyDuplicateName1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.5);
    try {
      model.createStrategyDiffPercent("s1" , stockList , weightList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      model.createStrategyDiffPercent("s1" , stockList , weightList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyDuplicateName2() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    try {
      model.createStrategyEqualPercent("s1" , stockList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      model.createStrategyEqualPercent("s1" , stockList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyDuplicateName3() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.5);
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyDuplicateName4() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    try {
      model.createStrategyNoEndEqualPercent("s1" , stockList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      model.createStrategyNoEndEqualPercent("s1" , stockList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyDuplicateName5() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.5);
    model.createStrategyDiffPercent("s1" , stockList , weightList , 1000 ,
        LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
    try {
      model.createStrategyEqualPercent("s1" , stockList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent("s1" , stockList , 1000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Strategy already exists." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyInvalidStock1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.1);
    try {
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
    try {
      model.createStrategyEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyInvalidStock2() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    stockList.add("GOOG");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.1);
    weightList.add(0.2);
    try {
      model.createStrategyDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
    try {
      model.createStrategyEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 100 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
    try {
      model.createStrategyNoEndEqualPercent("s1" , stockList , 100 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stock list is invalid." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyInvalidWeight1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    List<Double> weightList = new LinkedList<>();
    try {
      model.createStrategyDiffPercent("s1" , stockList , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }
    stockList.add("AAPL");
    weightList.add(0.1);
    try {
      model.createStrategyDiffPercent("s1" , stockList , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid weight list." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyInvalidWeight2() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("goog");
    stockList.add("amzn");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.6);
    weightList.add(0.5);
    try {
      model.createStrategyDiffPercent("s1" , stockList , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Sum of weight cannot be greater than 1." , e.getMessage());
    }
    try {
      model.createStrategyNoEndDiffPercent("s1" , stockList , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Sum of weight cannot be greater than 1." , e.getMessage());
    }
  }

  @Test
  public void testCreateStrategyValid() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList1 = new LinkedList<>();
    List<String> stockList2 = new LinkedList<>();
    stockList1.add("goog");
    stockList1.add("aapl");
    stockList2.add("amzn");
    stockList2.add("msft");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.4);
    weightList.add(0.6);
    try {
      model.createStrategyDiffPercent("s1" , stockList1 , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 10);
      model.createStrategyEqualPercent("s2" , stockList1 , 10000 ,
          LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 10);
      model.createStrategyNoEndDiffPercent("s3" , stockList2 , weightList , 10000 ,
          LocalDate.of(2019 , 1 , 1) , 10);
      model.createStrategyNoEndEqualPercent("s4" , stockList2 , 10000 ,
          LocalDate.of(2019 , 1 , 1) , 10);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testSaveStrategy1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.saveStrategy("s1" , "s1.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    List<String> stockList1 = new LinkedList<>();
    stockList1.add("goog");
    stockList1.add("aapl");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.4);
    weightList.add(0.6);
    model.createStrategyDiffPercent("s1" , stockList1 , weightList , 10000 ,
        LocalDate.of(2019 , 1 , 1) , LocalDate.now() , 10);
    try {
      model.saveStrategy(null , "s1.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.saveStrategy("" , "s1.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    try {
      model.saveStrategy("s2" , "s1.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
    model.saveStrategy("s1" , "s1.xml");
    String content = getFileContent("s1.xml");
    String expect = "<strategy>\n" +
        "\t<strategy-name>s1</strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>goog</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>aapl</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2019-01-01</begin-date>\n" +
        "\t<end-date>2019-04-17</end-date>\n" +
        "\t<amount>10000.0</amount>\n" +
        "\t<day>10</day>\n" +
        "</strategy>\n";
    assertEquals(expect , content);
  }

  @Test
  public void testReadStrategy1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("GOOG");
    stockList.add("AAPL");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.4);
    weightList.add(0.6);
    model.createStrategyDiffPercent("s1" , stockList , weightList , 20000 ,
        LocalDate.of(2018 , 4 , 1) ,
        LocalDate.of(2019 , 4 , 1) , 30);
    model.saveStrategy("s1" , "s1.xml");
    TradeOperation model2 = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model2.readStrategy("s1.xml");
    List<IStrategy> s1 = model.getStrategy();
    List<IStrategy> s2 = model2.getStrategy();
    assertEquals(s1.size() , s2.size());
    assertEquals(s1.get(0).getStrategyName() , s2.get(0).getStrategyName());
    assertEquals(s1.get(0).getBeginDate().toString() , s2.get(0).getBeginDate().toString());
    assertEquals(s1.get(0).getAmount() , s2.get(0).getAmount());
    assertEquals(s1.get(0).getDay() , s2.get(0).getDay());
    assertEquals(s1.get(0).getStockList().size() , s2.get(0).getStockList().size());
    assertEquals(s1.get(0).getStockList().contains("GOOG") ,
        s2.get(0).getStockList().contains("GOOG"));
    assertEquals(s1.get(0).getStockList().contains("AAPL") ,
        s2.get(0).getStockList().contains("AAPL"));
  }

  @Test
  public void testReadStrategy2() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol> GOOG </stock-symbol>\n" +
        "\t\t\t<stock-weight> 0.4 </stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol> AAPL </stock-symbol>\n" +
        "\t\t\t<stock-weight> 0.6 </stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date> 2018-04-01 </begin-date>\n" +
        "\t<end-date> 2019-04-01 </end-date>\n" +
        "\t<amount> 20000.0 </amount>\n" +
        "\t<day> 30 </day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.readStrategy("test.xml");
    List<IStrategy> list = model.getStrategy();
    assertEquals(1 , list.size());
    assertEquals("s1" , list.get(0).getStrategyName());
    assertEquals(2 , list.get(0).getStockList().size());
    assertEquals("2018-04-01" , list.get(0).getBeginDate().toString());
    assertEquals("2019-04-01" , list.get(0).getEndDate().toString());
    assertEquals(20000.0 , list.get(0).getAmount() , 1e-6);
    assertEquals(new Integer(30) , list.get(0).getDay());
  }

  @Test
  public void testReadStrategy3() {
    String content = "<strateg>\n" +
        "\t<strategy-name>s1</strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strateg>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not start with strategy tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy4() {
    String content = "<strategy>\n" +
        "\t<strategy>s1</strategy>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find with strategy-name tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy5() {
    String content = "<strategy>\n" +
        "\t<strategy-name> \t </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find strategy name." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy6() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock->\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock->\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find stock-list tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy7() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stoc>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stoc>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find stock tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy8() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbo>GOOG</stock-symbo>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find stock-symbol tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy9() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol> </stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find stock symbol name." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy10() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weigh>0.4</stock-weigh>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find stock-weight tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy11() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight> </stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not find stock-weight end tag." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy12() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>abc</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid double." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy13() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.5</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-01</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Sum of weight cannot be greater than 1." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy14() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date></begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No date." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy15() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2018-04-1</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Illegal date: Text '2018-04-1' could not be parsed at index 8" ,
          e.getMessage());
    }
  }

  @Test
  public void testReadStrategy16() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2019-02-29</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Illegal date: date dose not exist." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy17() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2019-02-28</begin-date>\n" +
        "\t<end-date>2019-04-1</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Illegal date: Text '2019-04-1' could not be parsed at index 8" ,
          e.getMessage());
    }
  }

  @Test
  public void testReadStrategy18() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2019-02-28</begin-date>\n" +
        "\t<end-date>2019-04-31</end-date>\n" +
        "\t<amount>20000.0</amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Illegal date: date dose not exist." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy19() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2019-02-28</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount> </amount>\n" +
        "\t<day>30</day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No amount." , e.getMessage());
    }
  }

  @Test
  public void testReadStrategy20() {
    String content = "<strategy>\n" +
        "\t<strategy-name> s1 </strategy-name>\n" +
        "\t<stock-list>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>GOOG</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.4</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t\t<stock>\n" +
        "\t\t\t<stock-symbol>AAPL</stock-symbol>\n" +
        "\t\t\t<stock-weight>0.6</stock-weight>\n" +
        "\t\t</stock>\n" +
        "\t</stock-list>\n" +
        "\t<begin-date>2019-02-28</begin-date>\n" +
        "\t<end-date>2019-04-01</end-date>\n" +
        "\t<amount>20000</amount>\n" +
        "\t<day></day>\n" +
        "</strategy>";
    PrintWriter writer;
    try {
      writer = new PrintWriter("test.xml");
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      fail();
    }
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.readStrategy("test.xml");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No day." , e.getMessage());
    }
  }

  @Test
  public void testInvalidInvest1() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    try {
      model.invest("p1" , "s1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No such portfolio." , e.getMessage());
    }
  }

  @Test
  public void testInvalidInvest2() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("p1");
    try {
      model.invest("p1" , "s1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid strategy name." , e.getMessage());
    }
  }

  @Test
  public void testInvalidInvest3() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("p1");
    model.addStock("p1" , "GOOG");
    model.addStock("p1" , "AMZN");
    List<String> stockList = new LinkedList<>();
    stockList.add("GOOG");
    model.createStrategyNoEndEqualPercent("s1" , stockList , 10000 , LocalDate.of(2019 , 1 , 1) ,
        10);
    try {
      model.invest("p1" , "s1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stocks between portfolio and strategy do not match." , e.getMessage());
    }
  }

  @Test
  public void testInvalidInvest4() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    model.createPortfolio("p1");
    model.addStock("p1" , "GOOG");
    List<String> stockList = new LinkedList<>();
    stockList.add("GOOG");
    stockList.add("AMZN");
    model.createStrategyNoEndEqualPercent("s1" , stockList , 10000 ,
        LocalDate.of(2019 , 1 , 1) , 10);
    try {
      model.invest("p1" , "s1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Stocks between portfolio and strategy do not match." , e.getMessage());
    }
  }

  @Test
  public void testInvest1() {
    TradeOperation model = TradeModel.getBuilder().setCommissionFee(0).setAPI(
        new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("GOOG");
    stockList.add("AAPL");
    List<Double> weightList = new LinkedList<>();
    weightList.add(0.4);
    weightList.add(0.6);
    model.createStrategyDiffPercent("s1" , stockList , weightList , 20000 ,
        LocalDate.of(2018 , 4 , 1) ,
        LocalDate.of(2019 , 4 , 1) , 30);
    model.createPortfolio("p1");
    model.addStock("p1" , "GOOG");
    model.addStock("p1" , "AAPL");
    model.invest("p1" , "s1");
    assertEquals(2 , model.getStockList("p1").size());
    assertEquals(true , model.getStockList("p1").contains("GOOG"));
    assertEquals(true , model.getStockList("p1").contains("AAPL"));
    assertEquals(260000.0 , model.getTotalCostBasis("p1") , 1e-6);
    assertEquals(0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(260000.0 , model.getTotalValue("p1") , 1e-6);
    assertEquals(0 , model.getTotalValueByDate("p1" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(200000.0 , model.getTotalValueByDate("p1" ,
        LocalDate.of(2019 , 1 , 1)) , 1e-6);
  }

  @Test
  public void testInvest2() {
    TradeOperation model = TradeModel.getBuilder().setCommissionFee(0).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("GOOG");
    stockList.add("AMZN");
    model.createStrategyEqualPercent("s1" , stockList , 20000 ,
        LocalDate.of(2018 , 12 , 24) ,
        LocalDate.of(2018 , 12 , 26) , 1);
    model.createPortfolio("p1");
    model.addStock("p1" , "GOOG");
    model.addStock("p1" , "AMZN");
    model.invest("p1" , "s1");
    model.savePortfolio("p1" , "p1.xml");
    assertEquals(2 , model.getStockList("p1").size());
    assertEquals(true , model.getStockList("p1").contains("GOOG"));
    assertEquals(true , model.getStockList("p1").contains("AMZN"));
    List<IStock> stockList1 = model.getPortfolioState("p1");
    System.out.println(stockList1.get(0).getStockSymbol() + stockList1.get(0).getNumber());
    System.out.println(stockList1.get(1).getStockSymbol() + stockList1.get(1).getNumber());
    assertEquals(40000.0 , model.getTotalCostBasis("p1") , 1e-6);
    assertEquals(0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(20000.0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2018 , 12 , 25)) , 1e-6);
  }

  @Test
  public void testInvest3() {
    TradeOperation model = TradeModel.getBuilder().setAPI(new MockGetPrice()).build();
    List<String> stockList = new LinkedList<>();
    stockList.add("GOOG");
    stockList.add("AAPL");
    model.createStrategyNoEndEqualPercent("s1" , stockList , 20000 ,
        LocalDate.of(2018 , 12 , 24) , 1);
    model.createPortfolio("p1");
    model.addStock("p1" , "GOOG");
    model.addStock("p1" , "AAPL");
    model.invest("p1" , "s1");
    assertEquals(true , model.getStockList("p1").contains("GOOG"));
    assertEquals(true , model.getStockList("p1").contains("AAPL"));
    assertEquals(1660830.0 , model.getTotalCostBasis("p1") , 1e-6);
    assertEquals(0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2018 , 1 , 1)) , 1e-6);
    assertEquals(120060.0 , model.getTotalCostBasisByDate("p1" ,
        LocalDate.of(2018 , 12 , 31)) , 1e-6);
  }

  private String getFileContent(String filepath) {
    String encode = "UTF-8";
    File file = new File(filepath);
    Long fileLength = file.length();
    byte[] fileContent = new byte[ fileLength.intValue() ];
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(fileContent);
      in.close();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    try {
      return new String(fileContent , encode);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}