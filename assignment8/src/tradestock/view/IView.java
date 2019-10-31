package tradestock.view;

import java.time.LocalDate;


/**
 * A class that implement IView.
 * It contains getInput(Scanner) to get the next user input,
 * commandPrompt() to print the command message,
 * print(String) to print a String,
 * appendPortfolioName(Scanner) that use to output hint message of input a portfolio name, and
 * read a portfolio name;
 * appendStockSymbol(Scanner) that use to output hint message of input a stock symbol and
 * read a stock symbol,
 * appendVolume(Scanner) that use to output hint message of input a volume of stock and
 * read a volume number,
 * appendLocalDate(Scanner) that use to output hint message of input a local date and
 * read a local date.
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

}
