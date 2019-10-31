package tradestock.model.getprice;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to get price of a stock at certain day and implements
 * IGetPrice interface.
 */
public class GetPrice implements IGetPrice {
  private final String apiKey = "0T48IAP8OM8GF5E5";
  private Map<String, Map<LocalDate, Double>> price;
  private Map<String, LocalDate> lastDate;

  /**
   * Construct a GetPrice object.
   */
  public GetPrice() {
    this.price = new HashMap<>();
    this.lastDate = new HashMap<>();
  }

  /**
   * Private helper method to add records of a stock.
   * @param stockSymbol given stock symbol
   */
  private void addStock(String stockSymbol) {
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + this.apiKey + "&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    Map<LocalDate, Double> stockPriceWithDate = parseData(output.toString());
    LocalDate last = null;
    for (LocalDate d : stockPriceWithDate.keySet()) {
      if (last == null) {
        last = d;
      }
      else if (last.isBefore(d)) {
        last = d;
      }
    }
    this.price.put(stockSymbol, stockPriceWithDate);
    this.lastDate.put(stockSymbol, last);
  }

  /**
   * Private helper method to parse data from api and return a Map of Date to price.
   * In this implement, stock price is close price at certain date.
   * @param priceDate data from api
   * @return Map after data is parsed
   */
  private Map<LocalDate, Double> parseData(String priceDate) {
    Map<LocalDate, Double> stockPrice = new HashMap<>();
    String[] oneRecord = priceDate.split("\n");
    for (int i = 1; i < oneRecord.length; ++i) {
      String[] line = oneRecord[i].split(",");
      LocalDate date = LocalDate.parse(line[0]);
      // Set open price as stock price.
      double p = Double.parseDouble(line[1]);
      stockPrice.put(date, p);
    }
    return stockPrice;
  }

  /**
   * Return the latest date of a stock. If stockSymbol is null of empty string,
   * this method will throw IllegalArgumentException. If there is no record for
   * given stock, this method will throw IllegalArgumentException.
   * @param stockSymbol given stock symbol
   * @return latest date of record
   */
  public LocalDate getLastDate(String stockSymbol) {
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock symbol cannot be null or empty string.");
    }
    if (!this.lastDate.containsKey(stockSymbol)) {
      throw new IllegalArgumentException("No such stock.");
    }
    return lastDate.get(stockSymbol);
  }

  /**
   * Return the price of a stock at certain day. If stockSymbol is null or
   * empty string, or date is null, this method will throw
   * IllegalArgumentException. For a valid stock, if there is no value at
   * date, this will return 0. The price that get returned is the open price of that day.
   * @param stockSymbol given stock symbol
   * @param date certain date of price
   * @return the price of a stock at certain day
   */
  public double getPrice(String stockSymbol, LocalDate date) {
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock symbol cannot be null or empty string.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    if (!this.price.containsKey(stockSymbol)) {
      try {
        addStock(stockSymbol);
      }
      catch (RuntimeException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
    Double result = this.price.get(stockSymbol).get(date);
    if (result == null) {
      return 0;
    }
    return result;
  }
}
