package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

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

  public String execute(TradeOperation t) {
    double cost = t.getTotalCostBasis(this.portfolioName);
    return "Total cost basis of portfolio " + portfolioName + " by now is " + cost;
  }

}
