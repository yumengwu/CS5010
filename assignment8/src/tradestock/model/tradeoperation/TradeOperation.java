package tradestock.model.tradeoperation;

import java.time.LocalDate;

/**
 * This interface represent a TradeOperation. A TradeOperation model could create portfolio,
 * but stocks, get cost, get value, get portfolio list, and get portfolio state.
 * @param <K> object type
 */
public interface TradeOperation<K> {
  /**
   * Create a portfolio by given name. If name is null or empty string, or name already exists,
   * this method will throw IllegalArgumentException.
   * @param portfolioName portfolio name
   */
  void createPortfolio(String portfolioName);

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
   * Return all the portfolio name as a string.
   * @return all the portfolio name
   */
  String getPortfolioList();

  /**
   * Return portfolio state of given name.
   * @param portfolioName given portfolio name
   * @return portfolio state of given name
   */
  String getPortfolioState(String portfolioName);

  /**
   * Return portfolio state of given name at certain date.
   * @param portfolioName given portfolio name
   * @param date given date
   * @return portfolio state of given name at certain date
   */
  String getPortfolioStateByDate(String portfolioName, LocalDate date);
}
