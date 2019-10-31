import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import tradestock.model.getprice.IGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.strategy.IStrategy;
import tradestock.model.tradeoperation.TradeOperation;

/**
 * A mock model that uses to test controller and view.
 */
public class MockModel implements TradeOperation<Stock> {
  private StringBuilder log;

  /**
   * Constructor of MockModel, it initialize the appendable of the mock model.
   * @param log log
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

  @Override
  public void buyAmount(String portfolioName, String stockSymbol, LocalDate date, double money) {
    log.append("Buy " + stockSymbol + " " +  "Money: " + money + " " + "to " + portfolioName
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
  public List<String> getPortfolioList() {
    log.append("get portfolio list\n");
    return new ArrayList<>();
  }

  @Override
  public List<String> getStockList (String portfolioName) {
    return null;
  }

  /**
   * return hint message shows controller calls the correct method in model.
   * @param portfolioName given portfolio name
   * @return hint message of call get portfolio state.
   */
  @Override
  public List<IStock> getPortfolioState(String portfolioName) {
    log.append("get " + portfolioName + " " + "state\n");
    return new ArrayList<>();
  }

  /**
   * return hint message shows controller calls the correct method in model.
   * @param portfolioName given portfolio name
   * @param date given date
   * @return hint message of call get portfolio state.
   */
  @Override
  public List<IStock> getPortfolioStateByDate(String portfolioName, LocalDate date) {
    log.append("get portfolio " + portfolioName + " " + "state by " + date + "\n");
    return new ArrayList<>();
  }

  /**
   * Append hint message that show set API successfully.
   * @param getPrice an IGetPrice object
   */
  @Override
  public void setAPI(IGetPrice getPrice) {
    log.append("setapi");
  }

  /**
   * Append hint message that show set commission fee successfully.
   * @param fee commission fee
   */
  @Override
  public void setCommissionFee(double fee) {
    log.append("set commission fee\n");
  }

  /**
   * Append stock symbol to portfolio.
   * @param portfolioName portfolio name.
   * @param stockSymbol stock symbol.
   */
  @Override
  public void addStock (String portfolioName , String stockSymbol) {
    log.append("add " + stockSymbol + " to " + portfolioName);
  }

  /**
   * Append hint message show get commission fee successfully.
   * @return a mock number.
   */
  @Override
  public double getCommissionFee() {
    log.append("get commission fee\n");
    return 100;
  }

  /**
   * Append hint message show portfolio has been save to file.
   * @param portfolioName given portfolio name
   * @param fileName given save file name
   */
  @Override
  public void savePortfolio(String portfolioName, String fileName) {
    log.append("save " + portfolioName + " to file " + fileName + "\n");
  }

  /**
   * Append hint message show file has been read.
   * @param fileName given file name
   */
  @Override
  public void readPortfolio(String fileName) {
    log.append("read file " + fileName + "\n");
  }

  /**
   * Append strategy name, stock list, money, begin date interval.
   * @param name strategy name.
   * @param stockList stock list.
   * @param money money.
   * @param beginDate begin date.
   * @param day interval.
   */
  @Override
  public void createStrategyNoEndEqualPercent(String name , List<String> stockList , double money ,
      LocalDate beginDate , int day) {
    String stocks = "";
    for (int i = 0; i < stockList.size(); i++) {
      stocks = stocks + stockList.get(i);
    }
    log.append("createStrategyNoEndEqualPercent " + name + " " + stocks + " " + money + " "
        + beginDate.toString() + " " + day + "\n");
  }

  /**
   * Append strategy name, stock list, weight list, money, begin date and interval.
   * @param name strategy name.
   * @param stockList stock list.
   * @param weight weight list.
   * @param money money.
   * @param beginDate begin date.
   * @param day interval.
   */
  @Override
  public void createStrategyNoEndDiffPercent(String name , List<String> stockList ,
      List<Double> weight , double money , LocalDate beginDate , int day) {
    String stocks = "";
    String weights = "";
    for (int i = 0; i < stockList.size(); i++) {
      stocks = stocks + stockList.get(i);
      weights = weights + weight.get(i);
    }
    log.append("createStrategyNoEndDiffPercent" + name + " " + stocks + " " + weights + " " + money
        + " " + beginDate.toString() + day + "\n");
  }

  /**
   * Append strategy name, stock list, money, begin date, end date and interval.
   * @param name strategy name.
   * @param stockList stock list.
   * @param money money.
   * @param beginDate begin date.
   * @param endDate end date.
   * @param day interval.
   */
  @Override
  public void createStrategyEqualPercent(String name , List<String> stockList , double money ,
      LocalDate beginDate , LocalDate endDate , int day) {
    String stocks = "";
    for (int i = 0; i < stockList.size(); i++) {
      stocks = stocks + stockList.get(i);
    }
    log.append("createStrategyEqualPercent " + name + " " + stocks + " " + money + " "
        + beginDate.toString() + endDate.toString() + " " + day + "\n");
  }

  /**
   * Append strategy name, stock list, weight list, money, begin date, end date and day.
   * @param name strategy name.
   * @param stockList stock list.
   * @param weight weight list.
   * @param money money.
   * @param beginDate begin date.
   * @param endDate end date.
   * @param day interval.
   */
  @Override
  public void createStrategyDiffPercent(String name , List<String> stockList ,
      List<Double> weight , double money , LocalDate beginDate , LocalDate endDate , int day) {
    String stocks = "";
    String weights = "";
    for (int i = 0; i < stockList.size(); i++) {
      stocks = stocks + stockList.get(i);
      weights = weights + weight.get(i);
    }
    log.append("createStrategyDiffPercent " + name + " " + stocks + " " + weights + " " + money
        +" " + beginDate.toString() + " " + endDate.toString() + " " + day + "\n");
  }

  /**
   * Append get strategy.
   * @return null in the mock model.
   */
  @Override
  public List<IStrategy> getStrategy() {
    log.append("call get strategy \n");
    return null;
  }

  /**
   * Append portfolio name and strategy name.
   * @param portfolioName portfolio name.
   * @param strategyName strategy name.
   */
  @Override
  public void invest(String portfolioName , String strategyName) {
    log.append("invest " + portfolioName + " " + strategyName + "\n");
  }

  /**
   * Append strategy name, file name.
   * @param strategyName strategy name.
   * @param filename file name.
   */
  @Override
  public void saveStrategy(String strategyName , String filename) {
    log.append("saveStrategy " + strategyName + " " + filename + "\n");
  }

  /**
   * Append readStrategy, file name.
   * @param filename filename.
   */
  @Override
  public void readStrategy(String filename) {
    log.append("readStrategy " + filename + "\n");
  }
}
