package tradestock.model.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import tradestock.model.transactionhistory.ITransactionHistory;
import tradestock.model.transactionhistory.TransactionHistory;

/**
 * This class represent a stock object implements IStock interface. A stock object
 * could return stock symbol, return number shares, return number of shares at
 * certain date, return cost of total shares, return cost of total shares at certain
 * date, add transaction history of this stock, and return transaction history
 * information.
 */
public class Stock implements IStock {
  private String stockSymbol;
  private List<ITransactionHistory> histories;

  /**
   * Construct a Stock object with given symbol. If symbol is null or empty string,
   * this method will throw IllegalArgumentException.
   * @param symbol given stock symbol
   */
  public Stock(String symbol) {
    if (symbol == null || symbol.length() == 0) {
      throw new IllegalArgumentException("Stock symbol cannot be null empty string.");
    }
    this.stockSymbol = symbol;
    this.histories = new ArrayList<>();
  }

  /**
   * Return stock symbol.
   * @return stock symbol
   */
  public String getStockSymbol() {
    return this.stockSymbol;
  }

  /**
   * Return total number of shares at present.
   * @return total number of shares at present
   */
  public double getNumber() {
    double num = 0;
    for (ITransactionHistory th : this.histories) {
      if (!th.isSell()) {
        num += th.getNumber();
      }
      else {
        num -= th.getNumber();
      }
    }
    return num;
  }

  /**
   * Return total number of shares at certain date. The transaction records happen
   * later than date will not affect the result.
   * @param date given date
   * @return total number of shares at certain date
   */
  public double getNumberByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    double num = 0;
    for (ITransactionHistory th : this.histories) {
      if (th.getDate().isBefore(date) || th.getDate().isEqual(date)) {
        if (!th.isSell()) {
          num += th.getNumber();
        }
        else {
          num -= th.getNumber();
        }
      }
    }
    return num;
  }

  /**
   * Return cost of total shares at present.
   * @param commissionFee commission fee
   * @return cost of total shares at present
   */
  public double getCost(double commissionFee) {
    return getCostByDate(LocalDate.now(), commissionFee);
  }

  /**
   * Return cost of total shares at certain date. The transaction records happen
   * later than date will not affect the result.
   * @param date given date
   * @param commissionFee commission fee
   * @return cost of total shares at certain date
   */
  public double getCostByDate(LocalDate date, double commissionFee) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    if (commissionFee < 0) {
      throw new IllegalArgumentException("Commission fee cannot be negative.");
    }
    double cost = 0.0;
    for (ITransactionHistory th : this.histories) {
      if (th.getDate().isBefore(date) || th.getDate().isEqual(date)) {
        if (!th.isSell()) {
          cost += th.getCost() * th.getNumber();
        }
        else {
          cost -= th.getCost() * th.getNumber();
        }
        cost += commissionFee;
      }
    }
    return cost;
  }

  /**
   * Add a transaction record of this stock.
   * @param date date of transaction
   * @param isSell true if sell, false if buy
   * @param number number of shares to operate
   * @param cost price of each share
   */
  public void addTransactionHistory(LocalDate date, boolean isSell, double number, double cost) {
    try {
      histories.add(new TransactionHistory(date, isSell, number, cost));
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Cannot transaction: " + e.getMessage());
    }
  }

  /**
   * Private helper method to sort history by compare date.
   */
  private void sortHistory() {
    Collections.sort(this.histories, new Comparator<ITransactionHistory>() {
      @Override
      public int compare(ITransactionHistory o1, ITransactionHistory o2) {
        if (o1.getDate().isBefore(o2.getDate())) {
          return -1;
        }
        else if (o1.getDate().isEqual(o2.getDate())) {
          return 0;
        }
        else {
          return 1;
        }
      }
    });
  }

  /**
   * Return transaction history information as List.
   * @return transaction history information
   */
  public List<ITransactionHistory> getTransactionHistory() {
    List<ITransactionHistory> list = new LinkedList<>();
    for (ITransactionHistory th : this.histories) {
      list.add(new TransactionHistory(th.getDate(), th.isSell(), th.getNumber(), th.getCost()));
    }
    return list;
  }
}
