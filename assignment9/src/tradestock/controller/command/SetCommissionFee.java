package tradestock.controller.command;

import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * This class represents a set commission fee command that implements TradeCommand. It enables users
 * to set different commission fee.
 */
public class SetCommissionFee implements TradeCommand<IStock> {
  private double fee;

  /**
   * Constructor of SetCommissionFee, it takes a double represent the commission fee and initialize
   * the SetCommissionFee object.
   * @param fee commission fee
   */
  public SetCommissionFee(double fee) {
    this.fee = fee;
  }

  /**
   * execute the set commission fee command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation<IStock> t, IView view) {
    t.setCommissionFee(this.fee);
    view.outSetCommissionFee(this.fee);
  }
}
