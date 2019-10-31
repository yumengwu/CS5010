package tradestock.model.portfolio;

import java.time.LocalDate;
import java.util.List;

import tradestock.model.getprice.IGetPrice;
import tradestock.model.stock.IStock;

/**
 * This interface represent a IPortfolio. A portfolio of stocks is simply a collection
 * of stocks (e.g. 10 shares of company X, 10 shares of company Y, etc.). A portfolio
 * object could get portfolio name, get total stocks number, get stocks number at certain
 * date, get stocks cost, get stocks cost at certain date, get total value of stocks, get
 * total value at certain date, add a transaction record, and get portfolio state as
 * string.
 */
public interface IPortfolio {
  /**
   * Return the name of portfolio.
   * @return the name of portfolio
   */
  String getPortfolioName();

  /**
   * Set API object used in portfolio. A API object must implements IGetPrice interface.
   * @param api api object
   */
  void setAPI(IGetPrice api);

  /**
   * Return total stocks number at present.
   * @return total stocks number at present
   */
  double getStockNumber();

  /**
   * Return total stocks number at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get stocks number
   * @return total stocks number at certain date
   */
  double getStockNumberByDate(LocalDate date);

  /**
   * Return total stocks cost at present. If commission fee is negative, this method will
   * throw IllegalArgumentException.
   * @param commissionFee commission fee
   * @return total stocks cost at present
   */
  double getStockCost(double commissionFee);

  /**
   * Return total stocks cost at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * If commission fee is negative, this method will throw IllegalArgumentException.
   * @param date given date to get stocks cost
   * @param commissionFee commission fee
   * @return total stocks cost at certain date
   */
  double getStockCostByDate(LocalDate date, double commissionFee);

  /**
   * Return total stocks value at present.
   * @return total stocks value at present
   */
  double getValue();

  /**
   * Return total stocks value at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get stocks value
   * @return total stocks value at certain date
   */
  double getValueByDate(LocalDate date);

  /**
   * Add a transaction history of stocks in this portfolio. If stock symbol is null or
   * empty string, or date is null, or number is not positive. When selling stocks, if
   * number of stocks is bigger than current stock number, this method will throw
   * IllegalStateException.
   * @param stockSymbol given stock symbol to add transaction record
   * @param date given date of transaction
   * @param isSell true if sell, or false if buy
   * @param number number of stocks to operate
   */
  void addTransaction(String stockSymbol, LocalDate date, boolean isSell, double number);

  /**
   * Add a transaction history of stocks in this portfolio. If stock symbol is null or
   * empty string, or date is null, or number is not positive. When selling stocks, if
   * number of stocks is bigger than current stock number, this method will throw
   * IllegalStateException.
   * @param stockSymbol given stock symbol to add transaction record
   * @param date given date of transaction
   * @param isSell true if sell, or false if buy
   * @param number number of stocks to operate
   * @param cost cost of stock
   */
  void addTransaction(String stockSymbol, LocalDate date, boolean isSell, double number, double cost);

  /**
   * Return portfolio state as List of IStock at present.
   * @return portfolio state at present
   */
  List<IStock> getPortfolioState();

  /**
   * Return portfolio state as List at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get portfolio state
   * @return portfolio state at certain date
   */
  List<IStock> getPortfolioStateByDate(LocalDate date);

  /**
   * Save this portfolio as a XML file.
   * @param filename save file path
   */
  void savePortfolio(String filename);
}
