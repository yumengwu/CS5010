package tradestock.controller.command;

import java.time.LocalDate;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

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
   * @return message after execute the buy stock command. Buy how many shares of what stock to
   *     which portfolio at what time.
   */
  @Override
  public String execute(TradeOperation<Stock> t) {
    int year = date.getYear();
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();
    t.buy(this.portfolioName, this.stockSymbol, LocalDate.of(year, month, day), this.volume);
    return "Buy Stock " + stockSymbol + " " + this.volume + " " + "shares to " + portfolioName
        + " on " + this.date.toString();
  }
}
