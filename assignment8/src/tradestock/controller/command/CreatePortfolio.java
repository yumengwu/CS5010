package tradestock.controller.command;

import tradestock.model.tradeoperation.TradeOperation;
import tradestock.model.stock.Stock;

/**
 * Create Portfolio command that implements TradeCommand interface. It enables users to create a
 * portfolio.
 */
public class CreatePortfolio implements TradeCommand<Stock> {

  String portfolioName;

  /**
   * Constructor of CreatePortfolio, it takes a portfolio name and initialize the object of
   * CreatePortfolio.
   * @param portfolioName A portfolio name that to be created.
   */
  public CreatePortfolio(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  /**
   * Execute create portfolio command.
   * @param t A TradeOperation that uses to execute the command.
   * @return Message after execute create portfolio command.
   */
  @Override
  public String execute(TradeOperation<Stock> t) {
    t.createPortfolio(this.portfolioName);
    return "Portfolio " + this.portfolioName + " created" + '\n';
  }
}
