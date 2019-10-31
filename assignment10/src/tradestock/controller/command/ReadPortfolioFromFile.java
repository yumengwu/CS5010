package tradestock.controller.command;

import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Read portfolio from file command that implements TradeCommand, it enables users to read a
 * portfolio information from a file to portfolio.
 */
public class ReadPortfolioFromFile implements TradeCommand<IStock> {

  private String fileName;

  /**
   * Constructor of ReadPortfolioFromFile. It takes a fileName and initialize the object of
   * ReadPortfolioFromFile.
   * @param fileName the file name that read data from.
   */
  public ReadPortfolioFromFile(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Execute a read portfolio from file command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation<IStock> t, IView view) {
    t.readPortfolio(this.fileName);
    view.outReadPortfolioFromFile(this.fileName);
  }
}
