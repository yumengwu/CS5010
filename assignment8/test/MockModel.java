import java.time.LocalDate;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

/**
 * A mock model that uses to test controller and view.
 */
public class MockModel implements TradeOperation<Stock> {
  private StringBuilder log;

  /**
   * Constructor of MockModel, it initialize the appendable of the mock model.
   * @param log log of mock model
   */
  public MockModel(StringBuilder log) {
    this.log = log;
  }

  /**
   * A method of mock model, check if the controller give the model the correct portfolio name.
   * @param portfolioName portfolio name
   */
  @Override
  public void createPortfolio(String portfolioName) {
    log.append("Create portfolio: " + portfolioName + "\n");
  }

  /**
   * A method of mock model, check if the controller give the model the correct arguments.
   * @param portfolioName given portfolio name
   * @param stockSymbol given stock symbol
   * @param date given date
   * @param number given number
   */
  @Override
  public void buy(String portfolioName, String stockSymbol, LocalDate date, double number) {
    log.append("Buy " + stockSymbol + " " +  "Volume: " + number + " " + "to " + portfolioName
        + " " + "at " + date.toString());
  }

  /**
   * A method of mock model, check if the controller give the model the correct arguments.
   * @param portfolioName given portfolio
   * @return a random number
   */
  @Override
  public double getTotalCostBasis(String portfolioName) {
    log.append("get total cost basis of portfolio " + portfolioName + "\n");
    return -500;
  }

  /**
   * A method of mock model, check if the controller give the model the correct arguments.
   * @param portfolioName given portfolio
   * @param date given date
   * @return a random number.
   */
  @Override
  public double getTotalCostBasisByDate(String portfolioName, LocalDate date) {
    log.append(" get total cost basis of portfolio " + portfolioName + " " + "by " + date + "\n");
    return -500;
  }

  /**
   * A method of mock model, check if the controller give the model the correct arguments.
   * @param portfolioName given portfolio
   * @return a random number.
   */
  @Override
  public double getTotalValue(String portfolioName) {
    log.append("get total value in portfolio " + portfolioName + "\n");
    return -500;
  }

  /**
   * A method of mock model, check if the controller give the model the correct arguments.
   * @param portfolioName given portfolio
   * @param date given date
   * @return a random number
   */
  @Override
  public double getTotalValueByDate(String portfolioName, LocalDate date) {
    log.append("get total value in portfolio " + portfolioName + " " + "by " + date + "\n");
    return -500;
  }

  /**
   * return hint message shows controller calls the correct method in model.
   * @return hint message of call get portfolio list.
   */
  @Override
  public String getPortfolioList() {
    log.append("get portfolio list\n");
    return "get portfolio list";
  }

  /**
   * return hint message shows controller calls the correct method in model.
   * @param portfolioName given portfolio name
   * @return hint message of call get portfolio state.
   */
  @Override
  public String getPortfolioState(String portfolioName) {
    log.append("get " + portfolioName + " " + "state\n");
    return "get portfolioState\n";
  }

  /**
   * return hint message shows controller calls the correct method in model.
   * @param portfolioName given portfolio name
   * @param date given date
   * @return hint message of call get portfolio state.
   */
  @Override
  public String getPortfolioStateByDate(String portfolioName, LocalDate date) {
    log.append("get portfolio " + portfolioName + " " + "state by " + date + "\n");
    return "get portfolio state";
  }
}
