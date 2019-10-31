package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

/**
 * Get portfolio detail command that implement TradeCommand interface. It enables user to get the
 * details of a portfolio by now.
 */
public class GetPortfolioDetail implements TradeCommand<Stock> {

  private String portfolioName;

  /**
   * Constructor of GetPortfolioDetail class. It takes a portfolio name and
   * initializes the object of GetPortfolioDetail.
   * @param portfolioName The portfolio name that user need to know it detail.
   */
  public GetPortfolioDetail(String portfolioName) {
    this.portfolioName = portfolioName;
  }

  /**
   * Execute get portfolio command.
   * @param t A TradeOperation that uses to execute the command.
   * @return detail of a given portfolio.
   */
  public String execute(TradeOperation<Stock> t) {
    return "The detail of " + portfolioName + " is\n" + t.getPortfolioState(portfolioName)
        + "\n";
  }

}
