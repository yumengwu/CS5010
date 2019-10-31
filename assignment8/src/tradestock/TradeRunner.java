package tradestock;

import java.io.InputStreamReader;
import tradestock.controller.IController;
import tradestock.controller.TradeController;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeModel;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.CommandView;
import tradestock.view.IView;

/**
 * Runner of the program.
 */
public class TradeRunner {

  /**
   * A main method to run this program.
   * @param args arguments to run the method.
   */
  public static void main(String[] args) {
    TradeOperation<Stock> model = TradeModel.getBuilder().build();
    IView view = new CommandView(new InputStreamReader(System.in), System.out);
    IController<Stock> controller =
        new TradeController(model, view);
    controller.execute();
  }
}
