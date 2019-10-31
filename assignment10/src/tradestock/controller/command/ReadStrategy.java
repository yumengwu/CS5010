package tradestock.controller.command;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * ReadStrategy command implements TradeCommand, it enables users to read a strategy from file to
 * the program.
 */
public class ReadStrategy implements TradeCommand<Stock> {

  private String fileName;

  /**
   * Constructor of ReadStrategy, it initializes the ReadStrategy object.
   * @param fileName the name of file that user read strategy from.
   */
  public ReadStrategy(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Execute ReadStrategy command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation<Stock> t , IView view) {
    t.readStrategy(fileName);
    view.appendMessage("Read " + fileName);
  }

}
