package tradestock.controller.command;

import java.time.LocalDate;
import java.util.List;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Get portfolio detail command that implement TradeCommand interface.
 */
public class GetPortfolioDetailByDate implements TradeCommand<Stock> {

  private String portfolioName;
  private LocalDate date;


  /**
   * Constructor of GetPortfolioDetailByDate class. It takes a portfolio name and
   * initializes the object of GetPortfolioDetailByDate.
   * @param portfolioName The portfolio name that user need to know it detail.
   * @param date The given date that use to get portfolio detail.
   */
  public GetPortfolioDetailByDate(String portfolioName, LocalDate date) {
    this.portfolioName = portfolioName;
    this.date = date;
  }

  /**
   * Execute get portfolio by date command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  public void execute(TradeOperation t, IView view) {
    List<IStock> list = t.getPortfolioStateByDate(this.portfolioName, this.date);
    view.outGetPortfolioDetailByDate(list, this.portfolioName, this.date);
  }
}
