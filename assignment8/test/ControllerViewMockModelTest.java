import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import tradestock.controller.TradeController;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.CommandView;
import tradestock.view.IView;

/**
 * A JUnit test class for ControllerViewMockModel. Because of limit of API, user should run
 * one test method each time.
 */
public class ControllerViewMockModelTest {
  private TradeOperation<Stock> model1;
  private TradeController controller1;
  private IView view1;
  private Readable in;
  private Appendable out;
  private Appendable out1;
  private TradeOperation<Stock> model2;

  @Before
  public void setUp() {
    StringBuilder log = new StringBuilder();
    model1 = new MockModel(log);
    out = new StringBuilder();
  }

  @Test
  public void testInvalidConstructor() {
    try {
      IView view = new CommandView(in, out);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("neither in or out can be null", e.getMessage());
    }
    in = new StringReader("q");

    try {
      IView view1 = new CommandView(in, out1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("neither in or out can be null", e.getMessage());
    }
    try {
      controller1 = new TradeController(model1, view1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("either model or view is null", e.getMessage());
    }
    view1 = new CommandView(in, out);
    try {
      controller1 = new TradeController(model2, view1);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("either model or view is null", e.getMessage());
    }
  }

  @Test
  public void testCreateMultiplePortfoliosThenShow() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio first created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio second created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio third created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio fourth created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Following are all portfolios: \n"
            + "get portfolio list\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("1 first 1 second 1 third 1 fourth 2 q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testShowAllPortfolio() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Following are all portfolios: \n"
            + "get portfolio list\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("2 q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testShowPortfolioDetail() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "The detail of first is\n"
            + "get portfolioState\n"
            + "\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("3 first q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  //Create multiple portfolios, buy stocks into different portfolios, check cost basis and value
  //in different portfolios.
  @Test
  public void complicateTest() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio first created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter the stock symbol: \n"
            + "Enter a number represents volume: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Buy Stock GOOG 300.0 shares to first on 2019-02-14\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio second created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter the stock symbol: \n"
            + "Enter a number represents volume: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Buy Stock MFST 300.0 shares to first on 2019-03-19\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter the stock symbol: \n"
            + "Enter a number represents volume: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Buy Stock AMZN 300.0 shares to second on 2019-02-14\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio third created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter the stock symbol: \n"
            + "Enter a number represents volume: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Buy Stock GOOGL 100.0 shares to third on 2019-02-13\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of first at 2019-02-14 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of first at 2019-03-19 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of portfolio first by now is -500.0\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of second at 2019-02-14 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of second at 2019-03-19 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of portfolio second by now is -500.0\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of third at 2019-02-01 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of third at 2019-03-19 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of portfolio third by now is -500.0\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("1 first 5 first GOOG 300 2019-02-14 1 second "
            + "5 first MFST 300 2019-03-19 5 second amzn 300 2019-02-14 1 third 5 third googl 100 "
            + "2019-02-13 7 2019-02-14 first 7 2019-03-19 first 6 first 7 2019-02-14 second 7 "
            + "2019-03-19 second 6 second 9 2019-02-01 third 9 2019-03-19 third 8 third q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testBuyStocks() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter the stock symbol: \n"
            + "Enter a number represents volume: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Buy Stock GOOG 50.0 shares to second on 2019-02-14\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("5 second GOOG 50 2019-02-14 q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testBuyStockInvalidArgument() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter the stock symbol: \n"
            + "Enter a number represents volume: \n"
            + "Invalid volume, please try again\n"
            + "\n"
            + "Enter a number represents volume: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Invalid Date Text '2019-13-14' could not be parsed: Invalid value for MonthOfYear "
            + "(valid values 1 - 12): 13\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Buy Stock ABAB 50.0 shares to second on 2019-02-04\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("5 second abab -50 50 2019-13-14 2019-02-04 q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testCostBasisByDate() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of second at 2019-02-14 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of second at 2019-02-15 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("7 2019-02-14 second 7 2019-02-15 second q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testCostBasisByDateInvalidDate() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of second at 2030-03-01 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("7 2030-03-01 second q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testCostBasis() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Total cost basis of portfolio second by now is -500.0\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("6 second q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testTotalValueByDifferentDate() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of second at 2019-02-14 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of second at 2019-02-15 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("9 2019-02-14 second 9 2019-02-15 second q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testTotalValueByDateInvalidDate() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Invalid Date Text '2039-32-14' could not be parsed: Invalid value for MonthOfYear "
            + "(valid values 1 - 12): 32\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of second at 2039-02-14 is -500.0\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("9 2039-32-14 2039-02-14 second q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testTotalValue() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Total value of portfolio second by now is -500.0\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("8 second q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testShowAnExistPortfolioDetailByDifferentDate() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Detail of portfolio second by 2019-02-14 is: \n"
            + "get portfolio state\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Detail of portfolio second by 2019-02-15 is: \n"
            + "get portfolio state\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("4 second 2019-02-14 4 second 2019-02-15 q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testShowPortfolioDetailByDateInvalidDate() {
    String except = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Invalid Date Text '2030-15-15' could not be parsed: Invalid value for MonthOfYear "
            + "(valid values 1 - 12): 15\n"
            + "\n"
            + "Enter a date: yyyy-mm-dd\n"
            + "Detail of portfolio second by 2039-03-14 is: \n"
            + "get portfolio state\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Trade quit prematurely.\n"
            + "\n";
    in = new StringReader("4 second 2030-15-15 2039-03-14 q");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    controller1.execute();
    assertEquals(except, out.toString());
  }

  @Test
  public void testRunOutOfInput() {
    String except1 = "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n"
            + "Enter the name for the portfolio: \n"
            + "Portfolio first created\n"
            + "\n"
            + "Please enter the correspond number to execute commands:\n"
            + "1 - Create a new portfolio.\n"
            + "2 - Show all portfolio.\n"
            + "3 - show detail of a portfolio\n"
            + "4 - show detail of a portfolio by a given date\n"
            + "5 - Buy stocks\n"
            + "6 - Check total cost basis of a portfolio\n"
            + "7 - Check total cost basis of a portfolio by a given date\n"
            + "8 - Check total value of a portfolio\n"
            + "9 - Check total value of a portfolio by a given date\n"
            + "q/Q - quit the system.\n"
            + "\n";
    String except = "Appendable Fail.\n";
    in = new StringReader("1 first");
    view1 = new CommandView(in, out);
    controller1 = new TradeController(model1, view1);
    try {
      controller1.execute();
    }
    catch (IllegalStateException e) {
      assertEquals(except, e.getMessage());
    }
    assertEquals(except1, out.toString());
  }
}

