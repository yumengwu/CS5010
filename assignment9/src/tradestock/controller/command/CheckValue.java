package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Determine the total value of a portfolio at a certain date command that implements TradeCommand
 * interface. It enables users to check value of their portfolios by now.
 */
public class CheckValue implements TradeCommand<Stock> {

  private String portfolioName;

  /**
   * Constructor of CheckValue, it takes a date and a portfolio name to initialize object
   * of CheckValue.
   * @param portfolioName A given portfolio name uses to calculate the total value.
   */
  public CheckValue(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  /**
   * Execute the check value command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation t, IView view) {
    double value = t.getTotalValue(this.portfolioName);
    view.outAfterCheckTotalValue(this.portfolioName, value);
  }

}
