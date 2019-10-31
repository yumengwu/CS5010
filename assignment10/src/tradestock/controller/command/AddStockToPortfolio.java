package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * AddStockToPortfolio command that implements TradeCommand, it enables user to add a stock symbol
 * to portfolio.
 */
public class AddStockToPortfolio implements TradeCommand<Stock> {

  private String stockSymbol;
  private String portfolioName;

  /**
   * Constructor of AddStockToPortfolio, it initializes the object of AddStockToPortfolio.
   * @param stockSymbol stock symbol the be added to the portfolio.
   * @param portfolioName portfolio accept the stock symbol.
   */
  public AddStockToPortfolio(String stockSymbol, String portfolioName) {
    this.stockSymbol = stockSymbol;
    this.portfolioName = portfolioName;
  }

  /**
   * Execute the AddStockToPortfolio command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation t , IView view) {
    t.addStock(portfolioName, stockSymbol);
    view.appendAfterAdd(portfolioName, stockSymbol);
  }
}
