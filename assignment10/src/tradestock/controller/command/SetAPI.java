package tradestock.controller.command;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.getprice.MockGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeOperation;

import tradestock.view.IView;

/**
 * This class represents a command set API that implement TradeCommand, and it enables users to
 * set another API for this program.
 */
public class SetAPI implements TradeCommand<IStock> {

  private IGetPrice getPrice;

  /**
   * This is an Empty Constructor. It doesn't have to initialize anything.
   */
  public SetAPI() {
    //empty constructor
  }

  /**
   * Execute the set API command. Type 1 for Alpha Vantage API, and 2 for mock API.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation<IStock> t, IView view) {
    String option = view.resetAPI();
    if (option.equals("1")) {
      this.getPrice = new GetPrice();
    }
    else {
      this.getPrice = new MockGetPrice();
    }
    t.setAPI(this.getPrice);
    view.outSetAPI();
  }

}
