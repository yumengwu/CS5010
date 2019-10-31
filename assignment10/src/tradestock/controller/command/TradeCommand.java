package tradestock.controller.command;

import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Trade command interface that provide several command of this program. eg: buy stock, check cost
 * basis, check value, create portfolio, get portfolio detail, show all portfolios.
 * @param <K> Given type in implementation class.
 */
public interface TradeCommand<K> {

  /**
   * Execute the command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  void execute(TradeOperation<K> t, IView view);
}
