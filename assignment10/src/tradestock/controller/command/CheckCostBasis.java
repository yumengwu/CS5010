package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * check cost basis command that implements TradeCommand interface. It enables users to check cost
 * basis of a portfolio by now.
 */
public class CheckCostBasis implements TradeCommand<Stock> {

  private String portfolioName;

  /**
   * Constructor of CheckCostBasis, it takes a portfolio name to initialize object
   * of CheckCostBasis.
   * @param portfolioName A given portfolio name uses to calculate the basis cost.
   */
  public CheckCostBasis(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  /**
   * Execute CheckCostBasis command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  public void execute(TradeOperation t, IView view) {
    double cost = t.getTotalCostBasis(this.portfolioName);
    view.outAfterCheckBasis(this.portfolioName, cost);
  }

}
