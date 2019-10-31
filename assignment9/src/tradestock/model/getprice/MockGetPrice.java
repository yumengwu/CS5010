package tradestock.model.getprice;

import java.time.LocalDate;

/**
 * This is a mock class implements IGetPrice and will return 100 for any stock and date.
 */
public class MockGetPrice implements IGetPrice {
  /**
   * Return 100 for any stock and date. If stock symbol is null or empty string, or date
   * is null or is in the future, this method will throw IllegalArgumentException.
   * @param stockSymbol given stock symbol
   * @param date certain date of price
   * @return 100
   */
  public double getPrice(String stockSymbol, LocalDate date) {
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock symbol cannot be null or empty string.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Date does not exist.");
    }
    return 100;
  }

  /**
   * Return now.
   * @param stockSymbol given stock symbol
   * @return now
   */
  public LocalDate getLastDate(String stockSymbol) {
    return LocalDate.now();
  }
}
