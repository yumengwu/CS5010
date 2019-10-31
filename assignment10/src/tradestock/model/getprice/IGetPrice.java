package tradestock.model.getprice;

import java.time.LocalDate;

/**
 * This interface represents a IGetPrice. A IGetPrice object could get price
 * of a stock at certain date and return the latest record of certain stock.
 */
public interface IGetPrice {
  /**
   * Return the price of a stock at certain day. If stockSymbol is null or
   * empty string, or date is null, this method will throw
   * IllegalArgumentException. For a valid stock, if there is no value at
   * date, this will return 0.
   * @param stockSymbol given stock symbol
   * @param date certain date of price
   * @return the price of a stock at certain day
   */
  double getPrice(String stockSymbol, LocalDate date);

  /**
   * Return the latest date of a stock. If stockSymbol is null of empty string,
   * this method will throw IllegalArgumentException. If there is no record for
   * given stock, this method will throw IllegalArgumentException.
   * @param stockSymbol given stock symbol
   * @return latest date of record
   */
  LocalDate getLastDate(String stockSymbol);
}
