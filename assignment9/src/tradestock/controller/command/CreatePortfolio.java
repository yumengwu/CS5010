package tradestock.controller.command;

import tradestock.model.tradeoperation.TradeOperation;
import tradestock.model.stock.Stock;
import tradestock.view.IView;

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
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation<Stock> t, IView view) {
    t.createPortfolio(this.portfolioName);
    view.outAfterPortfolioCreate(this.portfolioName);
  }
}
