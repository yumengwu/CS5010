package tradestock.model.transactionhistory;

import java.time.LocalDate;

/**
 * This class implements ITransactionHistory interface. TransactionHistory is used in
 * Stock object to record user's action like buy or sell stock. A ITransactionHistory
 * could get date, get number, get cost, and get state whether the transaction is sell
 * or buy.
 */
public class TransactionHistory implements ITransactionHistory {
  private LocalDate tradeDate;
  private boolean sell;
  private double number;
  private double cost;

  /**
   * Construct a TransactionHistory object. If date is null, or number is not positive,
   * or cost is not positive, this method will throw IllegalArgumentException.
   * @param date date of transaction
   * @param sell true if sell, false if buy
   * @param number number of shares to operate
   * @param cost price of each share
   */
  public TransactionHistory(LocalDate date, boolean sell, double number, double cost) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    if (number <= 0) {
      throw new IllegalArgumentException("Number must be positive.");
    }
    if (cost <= 0.0) {
      throw new IllegalArgumentException("Cost must be positive.");
    }
    this.tradeDate = date;
    this.sell = sell;
    this.number = number;
    this.cost = cost;
  }

  /**
   * Return the date of transaction record as a LocalDate object.
   * @return the date of transaction record
   */
  public final LocalDate getDate() {
    return this.tradeDate;
  }

  /**
   * Return true if sell, or false if buy.
   * @return true if sell, or false if buy
   */
  public final boolean isSell() {
    return this.sell;
  }

  /**
   * Return the number of shares in this transaction record.
   * @return the number of shares
   */
  public final double getNumber() {
    return this.number;
  }

  /**
   * Return the cost of each share in this transaction at record date.
   * @return the cost of each share
   */
  public final double getCost() {
    return this.cost;
  }
}
