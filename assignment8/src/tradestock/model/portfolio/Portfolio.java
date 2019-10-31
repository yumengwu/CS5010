package tradestock.model.portfolio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import tradestock.model.getprice.IGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;

/**
 * This class represent a Portfolio implements IPortfolio. A portfolio of stocks is simply
 * a collection of stocks (e.g. 10 shares of company X, 10 shares of company Y, etc.). A
 * portfolio object could get portfolio name, get total stocks number, get stocks number
 * at certain date, get stocks cost, get stocks cost at certain date, get total value of
 * stocks, get total value at certain date, add a transaction record, and get portfolio
 * state as string.
 */
public class Portfolio implements IPortfolio {
  private String portfolioName;
  private Map<String, IStock> stockMap;
  private IGetPrice stockPriceRecord;

  /**
   * Construct a portfolio object with given name and GetPrice object. If name is null or
   * empty string, or parse is null, this method will throw IllegalArgumentException.
   * @param name portfolio name
   * @param parse GetPrice object
   */
  public Portfolio(String name, IGetPrice parse) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Portfolio name cannot be null or empty string.");
    }
    if (parse == null) {
      throw new IllegalArgumentException("GetPrice object cannot be null.");
    }
    this.portfolioName = name;
    this.stockMap = new HashMap<>();
    this.stockPriceRecord = parse;
  }

  /**
   * Return the name of portfolio.
   * @return the name of portfolio
   */
  public String getPortfolioName() {
    return this.portfolioName;
  }

  /**
   * Return total stocks number at present.
   * @return total stocks number at present
   */
  public double getStockNumber() {
    double num = 0;
    for (String key : this.stockMap.keySet()) {
      num += this.stockMap.get(key).getNumber();
    }
    return num;
  }

  /**
   * Return total stocks number at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get stocks number
   * @return total stocks number at certain date
   */
  public double getStockNumberByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    double num = 0;
    for (String key : this.stockMap.keySet()) {
      num += this.stockMap.get(key).getNumberByDate(date);
    }
    return num;
  }

  /**
   * Return total stocks cost at present.
   * @return total stocks cost at present
   */
  public double getStockCost() {
    double cost = 0.0;
    for (String key : this.stockMap.keySet()) {
      cost += this.stockMap.get(key).getCost();
    }
    return cost;
  }

  /**
   * Return total stocks cost at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get stocks cost
   * @return total stocks cost at certain date
   */
  public double getStockCostByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    double cost = 0.0;
    for (String key : this.stockMap.keySet()) {
      cost += this.stockMap.get(key).getCostByDate(date);
    }
    return cost;
  }

  /**
   * Return total stocks value at present.
   * @return total stocks value at present
   */
  public double getValue() {
    return getValueByDate(LocalDate.now());
  }

  /**
   * Return total stocks value at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get stocks value
   * @return total stocks value at certain date
   */
  public double getValueByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
      date = date.minusDays(1);
    }
    else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      date = date.minusDays(2);
    }
    double value = 0.0;
    for (String key : this.stockMap.keySet()) {
      value += this.stockMap.get(key).getNumberByDate(date)
              * this.stockPriceRecord.getPrice(key, date);
    }
    return value;
  }

  /**
   * Private helper method adds transaction record when parameters are valid. When
   * selling stocks, if number of stocks is bigger than current stock number, this
   * method will throw IllegalStateException.
   * @param stockSymbol given stock symbol to add transaction record
   * @param date given date of transaction
   * @param isSell true if sell, or false if buy
   * @param number number of stocks to operate
   * @param cost cost of stock at date
   */
  private void addTransactionHelper(
          String stockSymbol, LocalDate date, boolean isSell, double number, double cost) {
    if (this.stockMap.containsKey(stockSymbol)) {
      IStock stock = this.stockMap.get(stockSymbol);
      if (isSell) {
        if (stock.getNumberByDate(date) < number) {
          throw new IllegalStateException("Cannot sell stock.");
        }
        else {
          stock.addTransactionHistory(date, true, number, cost);
        }
      }
      else {
        stock.addTransactionHistory(date, false, number, cost);
      }
    }
    else {
      IStock stock = new Stock(stockSymbol);
      if (isSell) {
        throw new IllegalStateException("Cannot sell stock.");
      }
      else {
        stock.addTransactionHistory(date, false, number, cost);
        this.stockMap.put(stockSymbol, stock);
      }
    }
  }

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
  public void addTransaction(
          String stockSymbol, LocalDate date, boolean isSell, double number) {
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock symbol cannot be null or empty string.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    if (number <= 0) {
      throw new IllegalArgumentException("Number must be positive.");
    }
    double cost = this.stockPriceRecord.getPrice(stockSymbol, date);
    addTransactionHelper(stockSymbol, date, isSell, number, cost);
  }

  /**
   * Return portfolio state as string at present.
   * @return portfolio state at present
   */
  public String getPortfolioState() {
    StringBuilder sb = new StringBuilder();
    for (String key : this.stockMap.keySet()) {
      if (this.stockMap.get(key).getNumber() > 0) {
        sb.append(this.stockMap.get(key).getStockSymbol()).append(": ");
        sb.append(this.stockMap.get(key).getNumber()).append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Return portfolio state as string at certain date. Transactions after given date will not
   * affect the result. If date is null, this method will throw IllegalArgumentException.
   * @param date given date to get portfolio state
   * @return portfolio state at certain date
   */
  public String getPortfolioStateByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    StringBuilder sb = new StringBuilder();
    for (String key : this.stockMap.keySet()) {
      if (this.stockMap.get(key).getNumberByDate(date) > 0) {
        sb.append(this.stockMap.get(key).getStockSymbol()).append(": ");
        sb.append(this.stockMap.get(key).getNumberByDate(date)).append("\n");
      }
    }
    return sb.toString();
  }
}
