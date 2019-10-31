package tradestock.model.transactionhistory;

import java.time.LocalDate;

/**
 * This interface represents a ITransactionHistory. ITransactionHistory is used in
 * Stock object to record user's action like buy or sell stock. A ITransactionHistory
 * could get date, get number, get cost, and get state whether the transaction is sell
 * or buy.
 */
public interface ITransactionHistory {
  /**
   * Return the date of transaction record as a LocalDate object.
   * @return the date of transaction record
   */
  LocalDate getDate();

  /**
   * Return true if sell, or false if buy.
   * @return true if sell, or false if buy
   */
  boolean isSell();

  /**
   * Return the number of shares in this transaction record.
   * @return the number of shares
   */
  double getNumber();

  /**
   * Return the cost of each share in this transaction at record date.
   * @return the cost of each share
   */
  double getCost();
}
