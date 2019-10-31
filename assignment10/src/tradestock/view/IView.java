package tradestock.view;

import java.time.LocalDate;

import java.util.List;

import tradestock.model.stock.IStock;

/**
 * An interface of View.
 * It works with controller to "communicate" with users, provide user input and program output.
 */
public interface IView {

  /**
   * Get the next user input.
   * @return next user input.
   */
  String getInput();

  /**
   * Print the prompt command.
   */
  void commandPrompt();

  /**
   * Append a given string to output.
   * @param s a given string.
   */
  void appendMessage(String s);

  /**
   * Add a portfolio name from user input.
   * @return the portfolio name that read from user input.
   */
  String appendPortfolioName();

  /**
   * Add a stock symbol from user input.
   * @return the stock symbol that read from user input.
   */
  String appendStockSymbol();

  /**
   * Add a volume number from user input.
   * @return the volume number that read from user input.
   */
  double appendVolume();

  /**
   * Add a local date from user input.
   * @return the local date that read from user input.
   */
  LocalDate appendLocalDate();

  /**
   * Output the message after a portfolio has been created.
   * @param portfolioName Created portfolio name.
   */
  void outAfterPortfolioCreate(String portfolioName);

  /**
   * Output message after buy a stock.
   * @param portfolioName portfolio name that just buy in stock.
   * @param stockSymbol bought stock symbol.
   * @param date the date of bought stock.
   * @param volume the number of bought stocks.
   */
  void outAfterBuyStock(String portfolioName, String stockSymbol, LocalDate date, double volume);

  /**
   * Output message after check basis.
   * @param portfolioName portfolio name that checked cost basis.
   * @param cost A number represents the cost.
   */
  void outAfterCheckBasis(String portfolioName, double cost);

  /**
   * Output message after check basis at a certain date.
   * @param portfolioName portfolio name that checked cost basis.
   * @param cost A number represents the cost.
   * @param date A date represents the check date.
   */
  void outAfterCheckBasisByDate(String portfolioName, double cost, LocalDate date);

  /**
   * Output message after check total value.
   * @param portfolioName portfolio name that checked total value.
   * @param value A number represents the value.
   */
  void outAfterCheckTotalValue(String portfolioName, double value);

  /**
   * Output message after check total value at a certain date.
   * @param portfolioName portfolio name that checked total value.
   * @param value A number represents the value.
   * @param date A date represents the check date.
   */
  void outAfterCheckTotalValueByDate(String portfolioName, double value, LocalDate date);

  /**
   * Output message get portfolio detail by date.
   * @param list A list that contains the names of Stock and its correspond number of shares in a
   *     portfolio.
   * @param portfolioName The portfolio name that uses to check its detail.
   * @param date The date that use to check a portfolio detail.
   */
  void outGetPortfolioDetailByDate(List<IStock> list, String portfolioName, LocalDate date);

  /**
   * Output message get portfolio detail.
   * @param list A list that contains the names of Stock and its correspond number of shares in a
   *      portfolio.
   * @param portfolioName The portfolio name that uses to check its detail.
   */
  void outGetPortfolioDetail(List<IStock> list, String portfolioName);

  /**
   * Output message show all portfolio name.
   * @param list A list that contains the names of all portfolio.
   */
  void outShowAllPortfolio(List<String> list);

  /**
   * Output a message to remind user commission fee has been reset.
   * @param fee Commission fee.
   */
  void outSetCommissionFee(double fee);

  /**
   * Output a message to remind user set API success.
   */
  void outSetAPI();

  /**
   * Hint message for user to choose API.
   * @return which api the user chose.
   */
  String resetAPI();

  /**
   * Show remind message to user to type a commission fee.
   * @return commission fee that user typed.
   */
  double appendSetCommissionFee();

  /**
   * Show remind message to user to type a file name.
   * @return file name that user typed.
   */
  String appendFileName();

  /**
   * Output message to remind user that a portfolio has been saved to a file.
   * @param portfolioName name of the portfolio that has been saved to a file.
   * @param fileName name of the file that store the portfolio.
   */
  void outSavePortfolio(String portfolioName, String fileName);

  /**
   * Output message to remind user that a portfolio has been read to the program from a file.
   * @param fileName name of the file that read the portfolio from.
   */
  void outReadPortfolioFromFile(String fileName);

  /**
   * Show hint message to remind user to type a number represent money.
   * @return money that user typed.
   */
  double appendMoney();

  /**
   * Output message that remind users buy by provided certain amount of money success.
   * @param portfolioName name of the portfolio that buy stock in.
   * @param stockSymbol symbol of the stock that buy into portfolio.
   * @param date date of buying the stock.
   * @param money the certain amount money used to buy stock.
   */
  void outBuyAmount(String portfolioName, String stockSymbol, LocalDate date, double money);

  /**
   * Output a message that tell user to type a start date, and accept the user input. The method
   * returns until the user type a valid date.
   * @return the valid date that user typed.
   */
  LocalDate appendStartDate();

  /**
   * Output a message that tell user to type an end date, and accept the user input. The method
   * returns until the user type a valid date.
   * @return the valid date that user typed.
   */
  LocalDate appendEndDate();

  /**
   * Output hint that remind user a stock symbol has been added to the portfolio.
   * @param portfolioName the portfolio name.
   * @param stockSymbol the stock symbol.
   */
  void appendAfterAdd(String portfolioName, String stockSymbol);

  /**
   * Pop up a message, and accept the user input, the message keeps showing until user type a valid
   * Input.
   * @return user input.
   */
  String chooseStrategyType();

  /**
   * Tell user to type a number represent the number of stocks. The method returns until user types
   * a valid integer.
   * @return an integer user typed.
   */
  int stockNumber();

  /**
   * Tell user to type a strategy name, and return the user input.
   * @return whatever user typed.
   */
  String appendStrategyName();

  /**
   * Tell user to type a number represent interval days, and accept user input, the method returns
   * until user types a valid integer.
   * @return the valid integer user typed.
   */
  int interval();

  /**
   * Enter a number represent the weight of a stock symbol in a strategy. The method returns until
   * user types a valid decimal. between 0 and 1.
   * @param stockSymbol the stock symbol.
   * @return the weight user typed for the stock symbol.
   */
  double appendAWeight(String stockSymbol);

  /**
   * Tell user to type a stock symbol and accept the symbol of a stock.
   * @param i the number help user to type.
   * @return the stock symbol user typed.
   */
  String appendStockList(int i);
}
