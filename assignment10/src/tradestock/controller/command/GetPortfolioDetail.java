package tradestock.controller.command;

import java.util.List;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

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
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation<Stock> t, IView view) {
    List<IStock> is = t.getPortfolioState(this.portfolioName);
    view.outGetPortfolioDetail(is, this.portfolioName);
  }

}
