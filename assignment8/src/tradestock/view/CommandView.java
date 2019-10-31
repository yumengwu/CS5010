package tradestock.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


/**
 * A class that implement IView.
 * It contains getInput() to get the next user input,
 * commandPrompt() to print the command message,
 * appendPortfolioName() that use to output hint message of input a portfolio name, and
 * read a portfolio name;
 * appendStockSymbol() that use to output hint message of input a stock symbol and
 * read a stock symbol,
 * appendVolume() that use to output hint message of input a volume of stock and
 * read a volume number,
 * appendLocalDate() that use to output hint message of input a local date and
 * read a local date.
 */
public class CommandView implements IView {
  //private Readable in;
  private Appendable out;
  private Scanner scanner;

  /**
   * Constructor of CommandView. It initialize CommandView object.
   * @param in A readable that the program use to read user input.
   * @param out An appendable that the program use to output message.
   * @throws IllegalArgumentException when in or out is null.
   */
  public CommandView(Readable in, Appendable out) throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("neither in or out can be null");
    }
    Readable rd = in;
    this.out = out;
    scanner = new Scanner(rd);
  }

  /**
   * Get the next user input.
   * @return the next user input.
   */
  @Override
  public String getInput() {
    return readNext();
  }

  /**
   * A private helper method that use to check if there any exception when get user input.
   * @return he next user input.
   * @throws IllegalStateException when there is an exception when get user input.
   */
  private String readNext() throws IllegalStateException {
    try {
      return this.scanner.next();
    }
    catch (Exception e) {
      throw new IllegalStateException("Appendable Fail.\n");
    }
  }

  /**
   * Print the command message.
   */
  @Override
  public void commandPrompt() {
    StringBuilder sb = new StringBuilder();
    sb.append("Please enter the correspond number to execute commands:\n");
    sb.append("1 - Create a new portfolio.\n");
    sb.append("2 - Show all portfolio.\n");
    sb.append("3 - show detail of a portfolio\n");
    sb.append("4 - show detail of a portfolio by a given date\n");
    sb.append("5 - Buy stocks\n");
    sb.append("6 - Check total cost basis of a portfolio\n");
    sb.append("7 - Check total cost basis of a portfolio by a given date\n");
    sb.append("8 - Check total value of a portfolio\n");
    sb.append("9 - Check total value of a portfolio by a given date\n");
    sb.append("q/Q - quit the system.\n");
    appendMessage(sb.toString());
  }

  /**
   * Output hint message of input a portfolio name, read a portfolio name.
   * @return portfolio name that read from user input.
   */
  @Override
  public String appendPortfolioName() {
    appendMessage("Enter the name for the portfolio: ");
    return readNext();
  }

  /**
   * Output hint message of input a stock symbol, read a stock symbol.
   * @return stock name that read from user input.
   */
  @Override
  public String appendStockSymbol() {
    while (true) {
      appendMessage("Enter the stock symbol: ");
      String transInput = readNext();
      if (transInput.matches("[A-Za-z]+")) {
        return transInput.toUpperCase();
      }
      appendMessage("Invalid stock symbol\n");
    }
  }

  /**
   * Output hint message of input a volume, read a volume number.
   * @return volume number that read from user input.
   */
  @Override
  public double appendVolume() {
    while (true) {
      appendMessage("Enter a number represents volume: ");
      String transInput = readNext();
      if (transInput.matches("[1-9][\\d]*")) {
        return Double.parseDouble(transInput);
      }
      appendMessage("Invalid volume, please try again\n");
    }
  }

  /**
   * Output hint message of input a local date, read a local date.
   * @return local date that read from user input.
   */
  @Override
  public LocalDate appendLocalDate() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    while (true) {
      try {
        appendMessage("Enter a date: yyyy-mm-dd");
        return LocalDate.parse(readNext(), format);
      }
      catch (DateTimeParseException e) {
        appendMessage("Invalid Date " + e.getMessage() + "\n");
      }
    }
  }

  /**
   * Append a given string to output.
   * @param message a given string.
   */
  @Override
  public void appendMessage(String message) {
    try {
      out.append(message);
      out.append("\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Append Fail");
    }
  }

}
