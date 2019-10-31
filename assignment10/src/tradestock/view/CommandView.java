package tradestock.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import tradestock.model.stock.IStock;


/**
 * A class that implement IView.
 * It works with controller to "communicate" with users, provide user input and program output.
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
    this.out = out;
    scanner = new Scanner(in);
  }

  /**
   * get the next user input.
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
   * Pop up a message, and accept the user input, the message keeps showing until user type a valid
   * Input.
   * @return user input.
   */
  @Override
  public String chooseStrategyType() {
    while (true) {
      appendMessage("1- create a strategy provide an end date with all stocks have equal weight");
      appendMessage("2- create a strategy provide an end date and specify the weight of every "
          + "stock");
      appendMessage("3- create a strategy without an end date with all stocks have equal weight");
      appendMessage("4- create a strategy without an end date and specify the weight of every "
          + "stock");
      String choice = readNext();
      if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")) {
        return choice;
      }
      else {
        appendMessage("Invalid input, please try again");
      }
    }
  }

  /**
   * Enter a number represent the weight of a stock symbol in a strategy. The method returns until
   * user types a valid decimal. between 0 and 1.
   * @param stockSymbol the stock symbol.
   * @return the weight user typed for the stock symbol.
   */
  @Override
  public double appendAWeight(String stockSymbol) {
    while (true) {
      appendMessage("Enter a weight for stock " + stockSymbol + "(decimal, eg:0.2)");
      try {
        double weight = Double.parseDouble(readNext());
        if (weight >= 0 && weight <= 1) {
          return weight;
        }
        appendMessage("Invalid input, please try again");
      }
      catch (NumberFormatException e) {
        appendMessage("Invalid format of weight");
      }
    }
  }

  /**
   * Tell user to type a number represent the number of stocks. The method returns until user types
   * a valid integer.
   * @return an integer user typed.
   */
  @Override
  public int stockNumber() {
    while (true) {
      appendMessage("Enter the number of stocks in this strategy: ");
      try {
        int input = Integer.parseInt(readNext());
        if (input >= 0) {
          return input;
        }
        appendMessage("Invalid input, please try again");
      }
      catch (NumberFormatException e) {
        appendMessage("Invalid Input, Please try again");
      }
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
    sb.append("5 - Buy stocks by a given number of shares\n");
    sb.append("6 - Buy stocks by a given amount of money\n");
    sb.append("7 - Check total cost basis of a portfolio\n");
    sb.append("8 - Check total cost basis of a portfolio by a given date\n");
    sb.append("9 - Check total value of a portfolio\n");
    sb.append("10 - Check total value of a portfolio by a given date\n");
    sb.append("11 - Set API\n");
    sb.append("12 - Set commission fee\n");
    sb.append("13 - Save portfolio to file\n");
    sb.append("14 - Read a portfolio from file\n");
    sb.append("15 - Add a stock to portfolio\n");
    sb.append("16 - Create an invest strategy\n");
    sb.append("17 - Apply strategy to portfolio\n");
    sb.append("18 - Read A Strategy From File\n");
    sb.append("19 - Save A Strategy To File\n");
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
   * Output hint message of input a local date, read a local date. Check if the date is valid, if
   * yes, return the LocalDate, if no, it will remind user the date is not valid, and remind user
   * to type again.
   * @return local date that read from user input.
   */
  @Override
  public LocalDate appendLocalDate() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    while (true) {
      try {
        appendMessage("Enter a date: yyyy-mm-dd");
        String readTime = readNext();
        LocalDate parseTime = LocalDate.parse(readTime);
        String parsedTime = parseTime.toString();
        if (readTime.equals(parsedTime)) {
          return LocalDate.parse(parsedTime, format);
        }
        else {
          throw new IllegalArgumentException("This date doesn't exist.");
        }
      }
      catch (DateTimeParseException | IllegalArgumentException e) {
        appendMessage("Invalid Date " + e.getMessage() + "\n");
      }
    }
  }

  /**
   * Append a given string to output.
   * @param message a given string.
   */
  public void appendMessage(String message) {
    try {
      out.append(message);
      out.append("\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Append Fail");
    }
  }

  /**
   * Output message after create a portfolio.
   * @param portfolioName Created portfolio name.
   */
  @Override
  public void outAfterPortfolioCreate(String portfolioName) {
    appendMessage("Portfolio " + portfolioName + " created\n");
  }

  /**
   * Output message after buy a stock.
   * @param portfolioName portfolio name that just buy in stock.
   * @param stockSymbol bought stock symbol.
   * @param date the date of bought stock.
   * @param volume the number of bought stocks.
   */
  @Override
  public void outAfterBuyStock(String portfolioName, String stockSymbol, LocalDate date,
      double volume) {
    appendMessage("Buy Stock " + stockSymbol + " " + volume + " " + "shares to " + portfolioName
        + " on " + date.toString());
  }

  /**
   * Output message after check basis.
   * @param portfolioName portfolio name that checked cost basis.
   * @param cost A number represents the cost.
   */
  @Override
  public void outAfterCheckBasis(String portfolioName, double cost) {
    appendMessage("Total cost basis of portfolio " + portfolioName + " by now is $" + cost);
  }

  /**
   * Output message after check basis at a certain date.
   * @param portfolioName portfolio name that checked cost basis.
   * @param cost A number represents the cost.
   * @param date A date represents the check date.
   */
  @Override
  public void outAfterCheckBasisByDate(String portfolioName, double cost, LocalDate date) {
    appendMessage("Total cost basis of " + portfolioName + " at " + date.toString()
        + " is $" + cost + "\n");
  }

  /**
   * Output message after check basis at a certain date.
   * @param portfolioName portfolio name that checked total value.
   * @param value A number represents the value.
   */
  @Override
  public void outAfterCheckTotalValue(String portfolioName, double value) {
    appendMessage("Total value of portfolio " + portfolioName + " by now is $" + value);
  }

  /**
   * Output message after check basis at a certain date.
   * @param portfolioName portfolio name that checked total value.
   * @param value A number represents the value.
   * @param date A date represents the check date.
   */
  @Override
  public void outAfterCheckTotalValueByDate(String portfolioName, double value, LocalDate date) {
    appendMessage("Total value of " + portfolioName + " " + "at " + date + " " + "is $" + value
        + "\n");
  }

  /**
   * Output message get portfolio detail by date.
   * @param list A list that contains the names of Stock and its correspond number of shares in a
   *     portfolio.
   * @param portfolioName The portfolio name that uses to check its detail.
   * @param date The date that use to check a portfolio detail.
   */
  @Override
  public void outGetPortfolioDetailByDate(List<IStock> list, String portfolioName, LocalDate date) {
    StringBuilder sb = new StringBuilder();
    for (IStock is : list) {
      sb.append(is.getStockSymbol()).append(": ");
      sb.append(is.getNumber()).append("\n");
    }
    appendMessage("Detail of portfolio " + portfolioName + " by " + date + " is: \n"
        + sb.toString());
  }

  /**
   * Output message get portfolio detail.
   * @param list A list that contains the names of Stock and its correspond number of shares in a
   *      portfolio.
   * @param portfolioName The portfolio name that uses to check its detail.
   */
  @Override
  public void outGetPortfolioDetail(List<IStock> list, String portfolioName) {
    StringBuilder sb1 = new StringBuilder();
    for (IStock is : list) {
      sb1.append(is.getStockSymbol()).append(": ");
      sb1.append(is.getNumber()).append("\n");
    }
    appendMessage("The detail of " + portfolioName + " is\n" + sb1.toString() + "\n");
  }

  /**
   * Output message show all portfolio name.
   * @param list A list that contains the names of all portfolio.
   */
  @Override
  public void outShowAllPortfolio(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String str: list) {
      sb.append(str).append("\n");
    }
    appendMessage("Following are all portfolios: \n" + sb.toString());
  }

  /**
   * Output a message to remind user commission fee has been reset.
   * @param fee Commission fee.
   */
  @Override
  public void outSetCommissionFee(double fee) {
    appendMessage("Set commission fee as " + fee + "\n");
  }

  /**
   * Output a message to remind user set API success.
   */
  @Override
  public void outSetAPI() {
    appendMessage("API set\n");
  }

  /**
   * Hint message for user to choose API.
   * @return which api the user chose.
   */
  @Override
  public String resetAPI() {
    String next = "";
    while (true) {
      appendMessage("1 - Use Alpha Vantage API\n"
          + "2 - Use Mock API\n");
      next = readNext();
      if (next.equals("1") || next.equals("2")) {
        return next;
      }
      else {
        appendMessage("Invalid command, try again\n");
      }
    }
  }

  /**
   * Show remind message to user to type a commission fee.
   * @return commission fee that user typed.
   */
  @Override
  public double appendSetCommissionFee() {
    while (true) {
      appendMessage("Enter a commission fee: ");
      String next = readNext();
      if (next.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)")) {
        return Double.parseDouble(next);
      }
      else {
        appendMessage("Invalid commission fee, try again.\n");
      }
    }
  }

  /**
   * Show remind message to user to type a file name.
   * @return file name that user typed.
   */
  @Override
  public String appendFileName() {
    appendMessage("Enter the file name: (if file is at/going to a specific path, enter the "
        + "path, too or file will save/read at the current path)");
    return readNext();
  }

  /**
   * Output message to remind user that a portfolio has been saved to a file.
   * @param portfolioName name of the portfolio that has been saved to a file.
   * @param fileName name of the file that store the portfolio.
   */
  @Override
  public void outSavePortfolio(String portfolioName, String fileName) {
    appendMessage("Save portfolio " + portfolioName + " to file " + fileName + "\n");
  }

  /**
   * Output message to remind user that a portfolio has been read to the program from a file.
   * @param fileName name of the file that read the portfolio from.
   */
  @Override
  public void outReadPortfolioFromFile(String fileName) {
    appendMessage("Read portfolio from " + fileName + "\n");
  }

  /**
   * Show hint message to remind user to type a number represent money.
   * @return money that user typed.
   */
  @Override
  public double appendMoney() {
    while (true) {
      appendMessage("Input how much money you want to invest to buy the stock: ");
      String next = readNext();
      if (next.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)")) {
        return Double.parseDouble(next);
      }
      else {
        appendMessage("Invalid commission fee, try again.\n");
      }
    }
  }

  /**
   * Output message that remind users buy by provided certain amount of money success.
   * @param portfolioName name of the portfolio that buy stock in.
   * @param stockSymbol symbol of the stock that buy into portfolio.
   * @param date date of buying the stock.
   * @param money the certain amount money used to buy stock.
   */
  @Override
  public void outBuyAmount(String portfolioName, String stockSymbol, LocalDate date, double money) {
    appendMessage("Buy $" + money + " of " + stockSymbol + " to portfolio " + portfolioName
        + " on " + date.toString() + "\n");
  }

  /**
   * Output a message that tell user to type a start date, and accept the user input. The method
   * returns until the user type a valid date.
   * @return the valid date that user typed.
   */
  @Override
  public LocalDate appendStartDate() {
    while (true) {
      appendMessage("Input a start date: ");
      LocalDate date = appendLocalDate();
      if (date.isAfter(LocalDate.now())) {
        appendMessage("Start cannot be after now");
      }
      else {
        return date;
      }
    }
  }

  /**
   * Output a message that tell user to type an end date, and accept the user input. The method
   * returns until the user type a valid date.
   * @return the valid date that user typed.
   */
  @Override
  public LocalDate appendEndDate() {
    appendMessage("Input an end date: ");
    return appendLocalDate();
  }

  /**
   * Output hint that remind user a stock symbol has been added to the portfolio.
   * @param portfolioName the portfolio name.
   * @param stockSymbol the stock symbol.
   */
  @Override
  public void appendAfterAdd(String portfolioName, String stockSymbol) {
    appendMessage("Add " + stockSymbol + " to " + portfolioName);
  }

  /**
   * Tell user to type a strategy name, and return the user input.
   * @return whatever user typed.
   */
  @Override
  public String appendStrategyName() {
    appendMessage("Input strategy name: ");
    return readNext();
  }

  /**
   * Tell user to type a number represent interval days, and accept user input, the method returns
   * until user types a valid integer.
   * @return the valid integer user typed.
   */
  @Override
  public int interval() {
    while (true) {
      appendMessage("Enter the interval(days): ");
      try {
        int interval = Integer.parseInt(readNext());
        if (interval >= 0) {
          return interval;
        }
        appendMessage("Invalid interval, try again\n");
      }
      catch (NumberFormatException e) {
        appendMessage("Invalid interval, try again\n");
      }
    }
  }

  /**
   * Tell user to type a stock symbol and accept the symbol of a stock.
   * @param i the number help user to type.
   * @return the stock symbol user typed.
   */
  @Override
  public String appendStockList(int i) {
    i = i + 1;
    appendMessage("Append the " + i + "th " + "Stock: ");
    return readNext().toUpperCase();
  }


}
