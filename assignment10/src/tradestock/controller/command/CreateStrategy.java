package tradestock.controller.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.IView;

/**
 * CreateStrategy command that implement TradeCommand, it enables user to create a strategy.
 */
public class CreateStrategy implements TradeCommand<Stock> {
  private String option;
  private String strategyName;
  private LocalDate startDate;
  private int number;
  private int interval;
  private double money;

  /**
   * Constructor of AddStockToPortfolio, it initializes the AddStockToPortfolio object.
   * @param option the option that user choose.
   * @param strategyName strategy name that user created.
   * @param number number of stocks in the strategy.
   * @param startDate startDate of the strategy.
   * @param interval interval of the strategy. Invest period.
   * @param money how much money will be invested every circle.
   */
  public CreateStrategy(String option, String strategyName, int number, LocalDate startDate,
      int interval, double money) {
    this.interval = interval;
    this.option = option;
    this.strategyName = strategyName;
    this.startDate = startDate;
    this.number = number;
    this.money = money;
  }

  /**
   * Private helper to check if the start date and end date are reasonable.
   * @param endDate the endDate.
   * @param view the view user uses.
   * @return An valid end date.
   */
  private LocalDate checkEnd(LocalDate endDate, IView view) {
    while (startDate.isAfter(endDate)) {
      view.appendMessage("Start date is after end date!\n");
      startDate = view.appendStartDate();
      endDate = view.appendEndDate();
    }
    return endDate;
  }

  /**
   * Execute AddStockToPortfolio command.
   * @param t A TradeOperation that uses to execute the command.
   * @param view An IView that associate with the program to output message.
   */
  @Override
  public void execute (TradeOperation<Stock> t , IView view) {
    List<String> stockNames = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      stockNames.add(view.appendStockList(i));
    }
    if (option.equals("1")) {
      LocalDate endDate = checkEnd(view.appendEndDate(), view);
      t.createStrategyEqualPercent(strategyName , stockNames , money , startDate , endDate ,
          interval);
    }
    else if (option.equals("2")) {
      LocalDate endDate = checkEnd(view.appendEndDate(), view);
      List<Double> weight = new ArrayList<>();
      for (int i = 0; i < number; i++) {
        weight.add(view.appendAWeight(stockNames.get(i)));
      }
      t.createStrategyDiffPercent(strategyName, stockNames, weight, money, startDate, endDate,
          interval);
    }
    else if (option.equals("3")) {
      t.createStrategyNoEndEqualPercent(strategyName, stockNames, money, startDate, interval);
    }
    else {
      List<Double> weight = new ArrayList<>();
      for (int i = 0; i < number; i++) {
        weight.add(view.appendAWeight(stockNames.get(i)));
      }
      t.createStrategyNoEndDiffPercent(strategyName, stockNames, weight, money, startDate,
          interval);
    }
    view.appendMessage("Strategy " + strategyName + " created");
  }
}
