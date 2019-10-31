package tradestock.controller;

import tradestock.controller.command.CheckCostBasis;
import tradestock.controller.command.CheckCostBasisByDate;
import tradestock.controller.command.CheckValue;
import tradestock.controller.command.CheckValueByDate;
import tradestock.controller.command.GetPortfolioDetail;
import tradestock.controller.command.BuyStock;
import tradestock.controller.command.CreatePortfolio;
import tradestock.controller.command.GetPortfolioDetailByDate;
import tradestock.controller.command.ShowAllPortfolios;
import tradestock.controller.command.TradeCommand;

import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

import tradestock.view.IView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This controller implements IController, and it is the controller of trade stock program.
 * go() method uses to start the program.
 */
public class TradeController implements IController<Stock> {
  private TradeOperation<Stock> model;
  private IView view;

  /**
   * Constructor of TradeController, it initialize the TradeController with a TradeOperation and
   * IView. This method will throw a IllegalArgumentException when either TradeOperation or IView
   * is null.
   * @param model A TradeOperation that be used in this program.
   * @param view A IView that be used in this program.
   * @throws IllegalArgumentException If either the model or view is null.
   */
  public TradeController(TradeOperation model, IView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("either model or view is null");
    }
    this.model = model;
    this.view = view;
  }

  /**
   * Method uses to start the program. Give control to controller.
   */
  public void execute() {
    Map<String, Function<String, TradeCommand>> knownCommand = initializeCommandMap();
    while (true) {
      view.commandPrompt();
      String input = view.getInput();
      String ignoreCaseInput = input.toLowerCase();
      if (ignoreCaseInput.startsWith("q")) {
        view.appendMessage("Trade quit prematurely.\n");
        return;
      }
      Function<String, TradeCommand> t = knownCommand.getOrDefault(input, null);
      if (t == null) {
        view.appendMessage("Invalid command\n");
        continue;
      }
      TradeCommand cmd = t.apply(input);
      try {
        String execute = cmd.execute(this.model);
        view.appendMessage(execute);
      }
      catch (IllegalArgumentException e) {
        view.appendMessage("Execution Failed. Please try again " + "Reason: "
            + e.getMessage() + "\n");
      }
    }
  }

  /**
   * private helper that uses to initialize command map.
   * @return initialized command map.
   */
  private Map<String, Function<String, TradeCommand>> initializeCommandMap() {
    Map<String, Function<String, TradeCommand>> commandMap = new HashMap<>();
    commandMap.put("1", s -> new CreatePortfolio(view.appendPortfolioName()));
    commandMap.put("2", s -> new ShowAllPortfolios());
    commandMap.put("3", s -> new GetPortfolioDetail(view.appendPortfolioName()));
    commandMap.put("4", s -> new GetPortfolioDetailByDate(view.appendPortfolioName(),
        view.appendLocalDate()));
    commandMap.put("5", s -> new BuyStock(view.appendPortfolioName(), view.appendStockSymbol(),
        view.appendVolume(), view.appendLocalDate()));
    commandMap.put("6", s -> new CheckCostBasis(view.appendPortfolioName()));
    commandMap.put("7", s -> new CheckCostBasisByDate(view.appendLocalDate(),
        view.appendPortfolioName()));
    commandMap.put("8", s -> new CheckValue(view.appendPortfolioName()));
    commandMap.put("9", s -> new CheckValueByDate(view.appendLocalDate(),
        view.appendPortfolioName()));
    return  commandMap;
  }

}
