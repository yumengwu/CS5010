package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * ApplyStrategyToPortfolio command that implements TradeCommand, it enables user to apply a
 * strategy to a portfolio.
 */
public class ApplyStrategyToPortfolio implements TradeCommand<Stock> {

  private String portfolioName;
  private String strategyName;

  /**
   * Constructor of AddStockToPortfolio, it initializes the AddStockToPortfolio object.
   * @param portfolioName the portfolio that apply the strategy.
   * @param strategyName the strategy that be applied.
   */
  public ApplyStrategyToPortfolio(String portfolioName, String strategyName) {
    this.portfolioName = portfolioName;
    this.strategyName = strategyName;
  }

  /**
   * Execute AddStockToPortfolio command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation<Stock> t , IView view) {
    t.invest(portfolioName, strategyName);
    view.appendMessage("Apply " + strategyName + " to " + portfolioName);
  }
}
