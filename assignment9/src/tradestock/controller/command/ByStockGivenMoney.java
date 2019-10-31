package tradestock.controller.command;

import java.time.LocalDate;
import tradestock.model.stock.IStock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Buy stock with an amount of given money command that implements TradeCommand interface.
 * This command enable use to add stock information to one of the portfolios.
 */
public class ByStockGivenMoney implements TradeCommand<IStock> {
  private String portfolioName;
  String stockSymbol;
  double money;
  LocalDate date;

  /**
   * Constructor of BuyStockGivenMoney, it initialize BuyStockGivenMoney object.
   * @param portfolioName portfolio name that use to buy stock.
   * @param stockSymbol stock symbol that user is going to buy.
   * @param money amount of money that use to invest.
   * @param date date that user buy the stock.
   */
  public ByStockGivenMoney(String portfolioName, String stockSymbol, double money, LocalDate date) {
    this.portfolioName = portfolioName;
    this.stockSymbol = stockSymbol;
    this.money = money;
    this.date = date;
  }

  /**
   * Execute buy stock command with a given amount.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation<IStock> t, IView view) {
    t.buyAmount(portfolioName, stockSymbol, date, money);
    view.outBuyAmount(portfolioName, stockSymbol, date, money);
  }
}
