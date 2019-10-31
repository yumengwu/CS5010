package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

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
   * @return Message of total value of a portfolio by now.
   */
  @Override
  public String execute(TradeOperation t) {
    double value = t.getTotalValue(this.portfolioName);
    return "Total value of portfolio " + portfolioName + " by now is " + value;
  }

}
