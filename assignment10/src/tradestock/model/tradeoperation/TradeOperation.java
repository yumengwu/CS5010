package tradestock.model.tradeoperation;

import java.time.LocalDate;
import java.util.List;

import tradestock.model.getprice.IGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.strategy.IStrategy;

/**
 * This interface represent a TradeOperation. A TradeOperation model could create portfolio,
 * but stocks, get cost, get value, get portfolio list, get portfolio state, add empty stock,
 * create strategy, apply strategy, save and read strategy.
 * @param <K> object type
 */
public interface TradeOperation<K> {
  /**
   * Set API. If api is null, this method will throw IllegalArgumentException.
   * @param getPrice an IGetPrice object
   */
  void setAPI(IGetPrice getPrice);

  /**
   * Create a portfolio by given name. If name is null or empty string, or name already exists,
   * this method will throw IllegalArgumentException.
   * @param portfolioName portfolio name
   */
  void createPortfolio(String portfolioName);

  /**
   * Return commission fee.
   * @return commission fee
   */
  double getCommissionFee();

  /**
   * Set commission fee. If fee is negative, this method will throw IllegalArgumentException.
   * @param fee commission fee
   */
  void setCommissionFee(double fee);

  /**
   * Add an empty stock to given portfolio.
   * @param portfolioName portfolio name
   * @param stockSymbol stock symbol
   */
  void addStock(String portfolioName, String stockSymbol);

  /**
   * Buy a stock at date. If portfolio name is null or empty string, or portfolio name does not
   * exist, or stock symbol is null or empty string, or date is null, this method will throw
   * IllegalArgumentException.
   * @param portfolioName given portfolio name
   * @param stockSymbol given stock symbol
   * @param date given date
   * @param number given number
   */
  void buy(String portfolioName, String stockSymbol, LocalDate date, double number);

  /**
   * Buy a stock at date. If portfolio name is null or empty string, or portfolio name does not
   * exist, or stock symbol is null or empty string, or date is null, this method will throw
   * IllegalArgumentException. This method will buy stocks by given money to use.
   * @param portfolioName given portfolio name
   * @param stockSymbol given stock symbol
   * @param date given date
   * @param money given money
   */
  void buyAmount(String portfolioName, String stockSymbol, LocalDate date, double money);

  /**
   * Return total stocks cost at present in given portfolio. If portfolio dose not exist, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio
   * @return total stocks cost at present
   */
  double getTotalCostBasis(String portfolioName);

  /**
   * Return total stocks cost at certain date in given portfolio. Transactions after given date
   * will not affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param portfolioName given portfolio
   * @param date given date
   * @return total stocks cost at certain date
   */
  double getTotalCostBasisByDate(String portfolioName, LocalDate date);

  /**
   * Return total stocks value at present in given portfolio. If portfolio dose not exist, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio
   * @return total stocks value at present
   */
  double getTotalValue(String portfolioName);

  /**
   * Return total stocks value at certain date in given portfolio. Transactions after given date
   * will not affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param portfolioName given portfolio
   * @param date given date
   * @return total stocks value at certain date
   */
  double getTotalValueByDate(String portfolioName, LocalDate date);

  /**
   * Return all the portfolio name as a List of String.
   * @return all the portfolio name
   */
  List<String> getPortfolioList();

  /**
   * Return a stock list as string list from given portfolio.
   * @param portfolioName given portfolio name
   * @return a stock list
   */
  List<String> getStockList(String portfolioName);

  /**
   * Return portfolio state of given name.
   * @param portfolioName given portfolio name
   * @return portfolio state of given name
   */
  List<IStock> getPortfolioState(String portfolioName);

  /**
   * Return portfolio state of given name at certain date.
   * @param portfolioName given portfolio name
   * @param date given date
   * @return portfolio state of given name at certain date
   */
  List<IStock> getPortfolioStateByDate(String portfolioName, LocalDate date);

  /**
   * Save a portfolio into a XML file by given portfolio name and file name.
   * If the file name already exists, this method will overwrite the file.
   * @param portfolioName given portfolio name
   * @param fileName given save file name
   */
  void savePortfolio(String portfolioName, String fileName);

  /**
   * Read a XML file to get a portfolio. If the file dose not exist, this method
   * will throw IllegalArgumentException. The format of file must be as follows:
   * (cost is the price of each share)<br>
   * &lt;portfolio&gt;<br>
   * &lt;portfolio-name&gt;p1&lt;/portfolio-name&gt;<br>
   * &lt;stock&gt;<br>
   * &lt;stock-symbol&gt;MSFT&lt;/stock-symbol&gt;<br>
   * &lt;transaction-history&gt;<br>
   * &lt;date&gt;2019-03-11&lt;/date&gt;<br>
   * &lt;sell&gt;false&lt;/sell&gt;<br>
   * &lt;volume&gt;36.0&lt;/volume&gt;<br>
   * &lt;cost&gt;100.0&lt;/cost&gt;<br>
   * &lt;/transaction-history&gt;<br>
   * ...<br>
   * &lt;/stock&gt;<br>
   * ...<br>
   * &lt;/portfolio&gt;<br>
   * All the tags must be lower case. All content must in root tag portfolio. User
   * cannot change order of tags. A portfolio can have one or more stocks, and a stock
   * can have one or more transaction-history. Format of date must be yyyy-mm-dd. Sell
   * must be true or false. Other tags are invalid.
   * If portfolio name already exists, the method will overwrite portfolio in map.
   * @param fileName given file name
   */
  void readPortfolio(String fileName);

  /**
   * Create a strategy with equal percent and without end date. If stocks are duplicate
   * or begin date is in the future, this method will throw IllegalArgumentException.
   * @param name strategy name
   * @param stockList stock list
   * @param money total money
   * @param beginDate begin date
   * @param day days between two but operation
   */
  void createStrategyNoEndEqualPercent(String name, List<String> stockList, double money,
                      LocalDate beginDate, int day);

  /**
   * Create a strategy with without end date. If stocks are duplicate or begin date is
   * in the future, this method will throw IllegalArgumentException.
   * @param name strategy name
   * @param stockList stock list
   * @param weight weight list
   * @param money total money
   * @param beginDate begin date
   * @param day days between two but operation
   */
  void createStrategyNoEndDiffPercent(String name, List<String> stockList, List<Double> weight,
                                      double money,
                      LocalDate beginDate, int day);

  /**
   * Create a strategy with equal percent and with end date. If stocks are duplicate
   * or begin date is in the future, this method will throw IllegalArgumentException.
   * End date will be the last date to buy stocks.
   * @param name strategy name
   * @param stockList stock list
   * @param money total money
   * @param beginDate begin date
   * @param endDate end date
   * @param day days between two but operation
   */
  void createStrategyEqualPercent(String name, List<String> stockList, double money,
                                  LocalDate beginDate,
                      LocalDate endDate, int day);

  /**
   * Create a strategy with with end date. If stocks are duplicate or begin date is
   * in the future, this method will throw IllegalArgumentException. End date will
   * be the last date to buy stocks.
   * @param name strategy name
   * @param stockList stock list
   * @param weight weight list
   * @param money total money
   * @param beginDate begin date
   * @param endDate end date
   * @param day days between two but operation
   */
  void createStrategyDiffPercent(String name, List<String> stockList, List<Double> weight,
                                 double money,
                      LocalDate beginDate, LocalDate endDate, int day);

  /**
   * Return all the strategy as a list.
   * @return strategy list
   */
  List<IStrategy> getStrategy();

  /**
   * Apply an existing strategy to an existing portfolio. All the stocks in portfolio
   * and strategy must be the same.
   * @param portfolioName portfolio name
   * @param strategyName strategy name
   */
  void invest(String portfolioName, String strategyName);

  /**
   * Save strategy to a file.
   * @param strategyName strategy name
   * @param filename filename
   */
  void saveStrategy(String strategyName, String filename);

  /**
   * Read a file to get strategy.
   * @param filename file name
   */
  void readStrategy(String filename);
}
