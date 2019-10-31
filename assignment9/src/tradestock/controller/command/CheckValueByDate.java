package tradestock.controller.command;

import java.time.LocalDate;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.model.stock.Stock;
import tradestock.view.IView;

/**
 * Determine the total value of a portfolio at a certain date command that implements TradeCommand
 * interface. It enables users to check value of a given portfolio by a certain date.
 */
public class CheckValueByDate implements TradeCommand<Stock> {
  private LocalDate date;
  private String portfolioName;

  /**
   * Constructor of CheckValueByDate, it takes a date and a portfolio name to initialize object
   * of CheckValueByDate.
   * @param date A given date that uses to calculate the total value.
   * @param portfolioName A given portfolio name uses to calculate the total value.
   */
  public CheckValueByDate(LocalDate date, String portfolioName) {
    this.date = date;
    this.portfolioName = portfolioName;
  }

  /**
   * Execute the check value by date command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation t, IView view) {
    double value = t.getTotalValueByDate(this.portfolioName, this.date);
    view.outAfterCheckTotalValueByDate(portfolioName, value, date);
  }

}
