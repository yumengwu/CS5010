package tradestock.model.strategy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represent a strategy. A strategy has strategy name, stock list, weight
 * list, begin date, end date, amount and day.
 */
public class Strategy implements IStrategy {
  private String strategyName;
  private List<String> stockList;
  private List<Double> weight;
  private LocalDate beginDate;
  private LocalDate endDate;
  private Double amount;
  private Integer day;

  /**
   * Construct a strategy object with given name.
   * @param name strategy name
   */
  public Strategy(String name) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Strategy name cannot be null or empty string.");
    }
    this.strategyName = name;
    this.stockList = null;
    this.weight = null;
    this.beginDate = null;
    this.endDate = null;
    this.amount = null;
    this.day = null;
  }

  /**
   * Return strategy name.
   * @return strategy name
   */
  public String getStrategyName() {
    return this.strategyName;
  }

  /**
   * Private method to check whether stock list has duplicate stocks.
   * @param list stock list
   */
  private void checkDuplicateStock(List<String> list) {
    Set<String> stockSet = new HashSet<>();
    for (String s : list) {
      if (s == null || s.length() == 0 || stockSet.contains(s.toUpperCase())) {
        throw new IllegalArgumentException("Stock list is invalid.");
      }
      stockSet.add(s.toUpperCase());
    }
  }

  /**
   * Set stocks and weights. If stocks or weights is null or empty list, or number of stock
   * and weight is different, or sum of weights is over 1, then this method will throw
   * IllegalArgumentException.
   * @param stocks stock list
   * @param weights weight list
   */
  public void setStocks(List<String> stocks, List<Double> weights) {
    if (stocks == null || weights == null || stocks.size() == 0 || weights.size() == 0
            || stocks.size() != weights.size()) {
      throw new IllegalArgumentException("Invalid stock weight list.");
    }
    checkDuplicateStock(stocks);
    double total = 0.0;
    for (double d : weights) {
      total += d;
    }
    if (total > 1) {
      throw new IllegalArgumentException("Sum of weight cannot be greater than 1.");
    }
    this.stockList = stocks;
    this.weight = weights;
  }

  /**
   * Return stock list.
   * @return stock list
   */
  public List<String> getStockList() {
    return this.stockList;
  }

  /**
   * Return weight list.
   * @return weight list
   */
  public List<Double> getWeight() {
    return this.weight;
  }

  /**
   * Set begin date.
   * @param date begin date
   */
  public void setBeginDate(LocalDate date) {
    if (date == null || date.isAfter(LocalDate.now())
            || (this.endDate != null && date.isAfter(this.endDate))) {
      throw new IllegalArgumentException("Invalid begin date.");
    }
    this.beginDate = date;
  }

  /**
   * Return begin date.
   * @return begin date
   */
  public LocalDate getBeginDate() {
    return this.beginDate;
  }

  /**
   * Set end date.
   * @param date end date
   */
  public void setEndDate(LocalDate date) {
    if (date == null || (this.beginDate != null && date.isBefore(this.beginDate))) {
      throw new IllegalArgumentException("Invalid end date.");
    }
    this.endDate = date;
  }

  /**
   * Return end date.
   * @return end date
   */
  public LocalDate getEndDate() {
    return this.endDate;
  }

  /**
   * Set amount.
   * @param amount amount
   */
  public void setAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive.");
    }
    this.amount = amount;
  }

  /**
   * Return amount.
   * @return amount
   */
  public Double getAmount() {
    return this.amount;
  }

  /**
   * Set day.
   * @param day day
   */
  public void setDay(int day) {
    if (day <= 0) {
      throw new IllegalArgumentException("Day must be positive.");
    }
    this.day = day;
  }

  /**
   * Return day.
   * @return day
   */
  public Integer getDay() {
    return this.day;
  }
}
