package tradestock.controller;

/**
 * This is the interface of stock controller, it works with TradeOperation and IView.
 * It contains execute() method, that uses to start the controller.
 * @param <K> Given type in implementation class.
 */

public interface IController<K> {

  /**
   * Start the program. Give control to the controller.
   */
  void execute();

}
