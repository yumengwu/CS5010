package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

/**
 * Show all portfolio command that implements TradeCommand interface. It enables user to see all
 * portfolios.
 */
public class ShowAllPortfolios implements TradeCommand<Stock> {

  /**
   * Constructor of ShowAllPortfolios, it is empty, because the class doesn't have any
   * private variables to initialize.
   */
  public ShowAllPortfolios() {
    // empty constructor.
  }

  /**
   * Execute show all portfolios command.
   * @param t A TradeOperation that use to execute the command.
   * @return All portfolio names.
   */
  public String execute(TradeOperation<Stock> t) {
    return "Following are all portfolios: \n" + t.getPortfolioList();
  }

}
