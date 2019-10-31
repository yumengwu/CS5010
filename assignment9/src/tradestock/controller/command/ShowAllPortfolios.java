package tradestock.controller.command;

import java.util.List;
import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Show all portfolio command that implements TradeCommand interface. It enables user to see all
 * portfolios.
 */
public class ShowAllPortfolios implements TradeCommand<IStock> {

  /**
   * Constructor of ShowAllPortfolios, it is empty, because the class doesn't have any
   * private variables to initialize.
   */
  public ShowAllPortfolios() {
    // empty constructor.
  }

  /**
   * Execute get all portfolio command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation<IStock> t, IView view) {
    List<String> list = t.getPortfolioList();
    view.outShowAllPortfolio(list);
  }

}
