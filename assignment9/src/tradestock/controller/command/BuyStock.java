package tradestock.controller.command;

import java.time.LocalDate;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * Buy stock command that implements TradeCommand interface. This command enable use to add stock
 * information to one of their portfolios.
 */
public class BuyStock implements TradeCommand<Stock> {
  String portfolioName;
  String stockSymbol;
  double volume;
  LocalDate date;

  /**
   * Constructor of BuyStock, it initialize BuyStock object.
   * @param portfolioName portfolio name that use to buy stock.
   * @param stockSymbol stock symbol that user is going to buy.
   * @param volume number of stock that user is going to buy.
   * @param date date that user buy the stock.
   */
  public BuyStock(String portfolioName, String stockSymbol, double volume, LocalDate date) {
    this.portfolioName = portfolioName;
    this.stockSymbol = stockSymbol;
    this.volume = volume;
    this.date = date;
  }

  /**
   * Execute buy stock command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute(TradeOperation<Stock> t, IView view) {
    int year = date.getYear();
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();
    LocalDate date = LocalDate.of(year, month, day);
    t.buy(this.portfolioName, this.stockSymbol, date, this.volume);
    view.outAfterBuyStock(this.portfolioName, this.stockSymbol, date, this.volume);
  }
}
