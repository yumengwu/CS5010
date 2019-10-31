package tradestock.model.tradeoperation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.portfolio.IPortfolio;
import tradestock.model.portfolio.Portfolio;
import tradestock.model.stock.Stock;

/**
 * This class represent a TradeModel implements TradeOperation interface. A TradeOModel
 * could create portfolio, but stocks, get cost, get value, get portfolio list, and get
 * portfolio state.
 */
public class TradeModel implements TradeOperation<Stock> {
  private Map<String, IPortfolio> portfolioMap;
  IGetPrice getPrice;

  /**
   * Construct a TradeModel object.
   */
  public TradeModel() {
    this.portfolioMap = new LinkedHashMap<>();
    this.getPrice = new GetPrice();
  }

  /**
   * Private helper method checks if given portfolio name is valid or exists. If not, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio name.
   * @throws IllegalArgumentException throws if portfolio name is not valid or dose not exist.
   */
  private void checkPortfolioName(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || portfolioName.length() == 0) {
      throw new IllegalArgumentException("Portfolio name cannot be null or empty string.");
    }
    if (!this.portfolioMap.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio.");
    }
  }

  /**
   * Private helper method check if a given time is null or after now. If time is null or the time
   * is after now, this method will throw an IllegalArgumentException.
   * @param date a given date.
   * @throws IllegalArgumentException throws if time is null or the time is after now.
   */
  private void checkDate(LocalDate date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Date cannot be after now");
    }
  }

  /**
   * Create a portfolio by given name. If name is null or empty string, or name already exists,
   * this method will throw IllegalArgumentException.
   * @param portfolioName given portfolio name.
   */
  public void createPortfolio(String portfolioName) {
    if (portfolioName == null || portfolioName.length() == 0) {
      throw new IllegalArgumentException("Portfolio name cannot be null or empty string.");
    }
    if (this.portfolioMap.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio name already exists.");
    }
    IPortfolio portfolio = new Portfolio(portfolioName, this.getPrice);
    this.portfolioMap.put(portfolioName, portfolio);
  }

  /**
   * Buy a stock at date. If portfolio name is null or empty string, or portfolio name does not
   * exist, or stock symbol is null, not found or empty string , or date is null or after now,
   * or volume of stock is less than 0, this method will throw IllegalArgumentException.
   * @param portfolioName given portfolio name
   * @param stockSymbol given stock symbol
   * @param date given date
   * @param number given number
   */
  public void buy(String portfolioName, String stockSymbol, LocalDate date, double number) {
    checkPortfolioName(portfolioName);
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock name cannot be null or empty string.");
    }
    checkDate(date);
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      throw new IllegalArgumentException("Cannot buy in holiday.");
    }
    if (number <= 0) {
      throw new IllegalArgumentException("Number must be positive.");
    }
    stockSymbol = stockSymbol.toUpperCase();
    this.portfolioMap.get(portfolioName).addTransaction(stockSymbol, date, false, number);
  }

  /**
   * Return total stocks cost at present in given portfolio. If portfolio dose not exist, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio
   * @return total stocks cost at present
   */
  public double getTotalCostBasis(String portfolioName) {
    checkPortfolioName(portfolioName);
    return this.portfolioMap.get(portfolioName).getStockCost();
  }

  /**
   * Return total stocks cost at certain date in given portfolio. Transactions after given date
   * will not affect the result. If date is null or date is after now, this method will throw
   * IllegalArgumentException.
   * @param portfolioName given portfolio
   * @param date given date
   * @return total stocks cost at certain date
   */
  public double getTotalCostBasisByDate(String portfolioName, LocalDate date) {
    checkPortfolioName(portfolioName);
    checkDate(date);
    return this.portfolioMap.get(portfolioName).getStockCostByDate(date);
  }

  /**
   * Return total stocks value at present in given portfolio. If portfolio dose not exist, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio.
   * @return total stocks value at present.
   */
  public double getTotalValue(String portfolioName) {
    checkPortfolioName(portfolioName);
    return this.portfolioMap.get(portfolioName).getValue();
  }

  /**
   * Return total stocks value at certain date in given portfolio. Transactions after given date
   * will not affect the result. If date is null, or the date is after now, this method will throw
   * IllegalArgumentException.
   * @param portfolioName given portfolio
   * @param date given date
   * @return total stocks value at certain date
   */
  public double getTotalValueByDate(String portfolioName, LocalDate date) {
    checkPortfolioName(portfolioName);
    checkDate(date);
    return this.portfolioMap.get(portfolioName).getValueByDate(date);
  }

  /**
   * Return all the portfolio name as a string. The order of showing the portfolio is the same
   * with add order.
   * @return all the portfolio name
   */
  public String getPortfolioList() {
    StringBuilder sb = new StringBuilder();
    for (String key : this.portfolioMap.keySet()) {
      sb.append(key).append("\n");
    }
    return sb.toString();
  }

  /**
   * Return portfolio state at now.
   * @param portfolioName given portfolio name
   * @return portfolio state of given name
   */
  public String getPortfolioState(String portfolioName) {
    checkPortfolioName(portfolioName);
    return this.portfolioMap.get(portfolioName).getPortfolioState();
  }

  /**
   * Return portfolio state of given name at certain date.
   * @param portfolioName given portfolio name
   * @param date given date
   * @return portfolio state of given name at certain date
   */
  public String getPortfolioStateByDate(String portfolioName, LocalDate date) {
    checkPortfolioName(portfolioName);
    checkDate(date);
    return this.portfolioMap.get(portfolioName).getPortfolioStateByDate(date);
  }

  /**
   * This class is a builder class implements TradeOperationBuilder interface.
   */
  public static class TradeBuilder implements TradeOperationBuilder {
    /**
     * Return a TradeOperation object.
     * @return a TradeOperation object
     */
    public TradeOperation<Stock> build() {
      return new TradeModel();
    }
  }

  /**
   * Return a TradeBuilder object.
   * @return a TradeBuilder object
   */
  public static TradeBuilder getBuilder() {
    return new TradeBuilder();
  }
}
