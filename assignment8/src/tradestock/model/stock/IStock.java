package tradestock.model.stock;

import java.time.LocalDate;

/**
 * This interface represent a stock. A stock object could return stock symbol,
 * return number shares, return number of shares at certain date, return cost
 * of total shares, return cost of total shares at certain date, add transaction
 * history of this stock, and return transaction history information.
 */
public interface IStock {
  /**
   * Return stock symbol.
   * @return stock symbol
   */
  String getStockSymbol();

  /**
   * Return total number of shares at present.
   * @return total number of shares at present
   */
  double getNumber();

  /**
   * Return total number of shares at certain date. The transaction records happen
   * later than date will not affect the result.
   * @param date given date
   * @return total number of shares at certain date
   */
  double getNumberByDate(LocalDate date);

  /**
   * Return cost of total shares at present.
   * @return cost of total shares at present
   */
  double getCost();

  /**
   * Return cost of total shares at certain date. The transaction records happen
   * later than date will not affect the result.
   * @param date given date
   * @return cost of total shares at certain date
   */
  double getCostByDate(LocalDate date);

  /**
   * Add a transaction record of this stock.
   * @param date date of transaction
   * @param isSell true if sell, false if buy
   * @param number number of shares to operate
   * @param cost price of each share
   */
  void addTransactionHistory(LocalDate date, boolean isSell, double number, double cost);

  /**
   * Return transaction history information.
   * @return transaction history information
   */
  String getTransactionHistory();
}
