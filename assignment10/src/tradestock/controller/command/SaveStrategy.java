package tradestock.controller.command;

import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * SaveStrategy command implements TradeCommand, it enables user to save a strategy to a file.
 */
public class SaveStrategy implements TradeCommand {

  private String fileName;
  private String strategyName;

  /**
   * Constructor of SaveStrategy, it initializes the SaveStrategy object.
   * @param fileName the file name that use to save the strategy.
   * @param strategyName the strategy name that to be saved in the file.
   */
  public SaveStrategy(String fileName, String strategyName) {
    this.fileName = fileName;
    this.strategyName = strategyName;
  }

  /**
   * Execute SaveStrategy command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation t , IView view) {
    t.saveStrategy(strategyName, fileName);
    view.appendMessage("Save Strategy " + strategyName + " to " + fileName);
  }
}
