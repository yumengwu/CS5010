package tradestock.model.strategy;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface represent a strategy. A strategy has strategy name, stock list, weight
 * list, begin date, end date, amount and day.
 */
public interface IStrategy {
  /**
   * Return strategy name.
   * @return strategy name
   */
  String getStrategyName();

  /**
   * Set stocks and weights. If stocks or weights is null or empty list, or number of stock
   * and weight is different, or sum of weights is over 1, then this method will throw
   * IllegalArgumentException.
   * @param stocks stock list
   * @param weights weight list
   */
  void setStocks(List<String> stocks, List<Double> weights);

  /**
   * Return stock list.
   * @return stock list
   */
  List<String> getStockList();

  /**
   * Return weight list.
   * @return weight list
   */
  List<Double> getWeight();

  /**
   * Set begin date.
   * @param date begin date
   */
  void setBeginDate(LocalDate date);

  /**
   * Return begin date.
   * @return begin date
   */
  LocalDate getBeginDate();

  /**
   * Set end date.
   * @param date end date
   */
  void setEndDate(LocalDate date);

  /**
   * Return end date.
   * @return end date
   */
  LocalDate getEndDate();

  /**
   * Set amount.
   * @param amount amount
   */
  void setAmount(double amount);

  /**
   * Return amount.
   * @return amount
   */
  Double getAmount();

  /**
   * Set day.
   * @param day day
   */
  void setDay(int day);

  /**
   * Return day.
   * @return day
   */
  Integer getDay();
}
