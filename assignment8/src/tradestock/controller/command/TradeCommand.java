package tradestock.controller.command;

import tradestock.model.tradeoperation.TradeOperation;

/**
 * Trade command interface that provide several command of this program. eg: buy stock, check cost
 * basis, check value, create portfolio, get portfolio detail, show all portfolios.
 * @param <K> Given type in implementation class.
 */
public interface TradeCommand<K> {

  /**
   * Execute the command.
   * @param t A TradeOperation that uses to execute the command.
   * @return Message after execute the command.
   */
  String execute(TradeOperation<K> t);
}
