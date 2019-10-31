package tradestock.controller.command;

import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Save portfolio to file command that implement TradeCommand, it enables users to save a portfolio
 * to a file.
 */
public class SavePortfolioToFile implements TradeCommand<IStock> {

  private String portfolioName;
  private String fileName;

  /**
   * Constructor of SavePortfolioToFile, it takes a portfolioName and fileName and initializes a
   * SavePortfolioToFile object.
   * @param portfolioName the name of the portfolio that is going to store to a file.
   * @param fileName the name of the file that is going to store a portfolio.
   */
  public SavePortfolioToFile(String portfolioName, String fileName) {
    this.portfolioName = portfolioName;
    this.fileName = fileName;
  }

  /**
   * Execute save portfolio to file command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation<IStock> t, IView view) {
    t.savePortfolio(this.portfolioName, this.fileName);
    view.outSavePortfolio(this.portfolioName, this.fileName);
  }
}
