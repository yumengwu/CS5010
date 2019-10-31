package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

import java.time.LocalDate;
import tradestock.view.IView;

/**
 * Determine the total cost basis of a portfolio at a certain date command that implements
 * TradeCommand interface. It enables users to check cost basis of a portfolio by a given date.
 */
public class CheckCostBasisByDate implements TradeCommand<Stock> {
  private LocalDate date;
  String portfolioName;

  /**
   * Constructor of CheckCostBasisByDate, it takes a date and a portfolio name to initialize object
   * of CheckCostBasis.
   * @param date A given date that uses to calculate the total basis cost.
   * @param portfolioName A given portfolio name uses to calculate the basis cost.
   */
  public CheckCostBasisByDate(LocalDate date, String portfolioName) {
    this.portfolioName = portfolioName;
    this.date = date;
  }

  /**
   * Execute the check basis cost by date command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation t, IView view) {
    double price = t.getTotalCostBasisByDate(this.portfolioName, this.date);
    view.outAfterCheckBasisByDate(this.portfolioName, price, date);
  }
}
