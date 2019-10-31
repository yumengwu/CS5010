package tradestock.model.tradeoperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.portfolio.IPortfolio;
import tradestock.model.portfolio.Portfolio;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.strategy.IStrategy;
import tradestock.model.strategy.Strategy;
import tradestock.model.xmlparser.IXMLParser;
import tradestock.model.xmlparser.XMLParser;
import tradestock.model.xmlparser.XMLStrategy;

/**
 * This class represent a TradeModel implements TradeOperation interface. A TradeOModel
 * could create portfolio, but stocks, get cost, get value, get portfolio list, get
 * portfolio state add empty stock, create strategy, apply strategy, save and read
 * strategy.
 */
public class TradeModel implements TradeOperation<Stock> {
  private Map<String, IPortfolio> portfolioMap;
  private Map<String, IStrategy> strategyMap;
  private IGetPrice getPrice;
  private double commissionFee;

  /**
   * Construct a TradeModel object. Default API is GetPrice object, and default commission
   * fee is 5.
   */
  public TradeModel() {
    this.portfolioMap = new LinkedHashMap<>();
    this.strategyMap = new LinkedHashMap<>();
    this.getPrice = new GetPrice();
    this.commissionFee = 5;
  }

  /**
   * Construct a TradeModel object by given api and commission fee. If api is null,
   * or commission fee is negative, this method will throw IllegalArgumentException.
   * @param api an IGetPrice object
   * @param fee commission fee
   */
  public TradeModel(IGetPrice api, double fee) {
    if (api == null) {
      throw new IllegalArgumentException("API object cannot be null.");
    }
    if (fee < 0) {
      throw new IllegalArgumentException("Commission fee cannot be negative.");
    }
    this.portfolioMap = new LinkedHashMap<>();
    this.strategyMap = new LinkedHashMap<>();
    this.getPrice = api;
    this.commissionFee = fee;
  }

  /**
   * Set API. If api is null, this method will throw IllegalArgumentException.
   * @param api an IGetPrice object
   */
  public void setAPI(IGetPrice api) {
    if (api == null) {
      throw new IllegalArgumentException("API object cannot be null.");
    }
    for (String key : this.portfolioMap.keySet()) {
      this.portfolioMap.get(key).setAPI(api);
    }
    this.getPrice = api;
  }

  /**
   * Private helper method checks if given portfolio name is valid or exists. If not, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio name.
   * @throws IllegalArgumentException throws if portfolio name is not valid or dose not exist.
   */
  private void checkPortfolioName(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || portfolioName.length() == 0) {
      throw new IllegalArgumentException("Portfolio name cannot be null or empty string.");
    }
    if (!this.portfolioMap.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio.");
    }
  }

  /**
   * Return commission fee.
   * @return commission fee
   */
  public double getCommissionFee() {
    return this.commissionFee;
  }

  /**
   * Set commission fee. If fee is negative, this method will throw IllegalArgumentException.
   * @param fee commission fee
   */
  public void setCommissionFee(double fee) {
    if (fee >= 0) {
      this.commissionFee = fee;
    }
    else {
      throw new IllegalArgumentException("Commission fee cannot be negative.");
    }
  }

  /**
   * Add an empty stock to given portfolio.
   * @param portfolioName portfolio name
   * @param stockSymbol stock symbol
   */
  public void addStock(String portfolioName, String stockSymbol) {
    checkPortfolioName(portfolioName);
    this.portfolioMap.get(portfolioName).addStock(stockSymbol);
  }

  /**
   * Private helper method check if a given time is null or after now. If time is null or the time
   * is after now, this method will throw an IllegalArgumentException.
   * @param date a given date.
   * @throws IllegalArgumentException throws if time is null or the time is after now.
   */
  private void checkDate(LocalDate date) throws IllegalArgumentException {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Date cannot be after now");
    }
  }

  /**
   * Create a portfolio by given name. If name is null or empty string, or name already exists,
   * this method will throw IllegalArgumentException.
   * @param portfolioName given portfolio name.
   */
  public void createPortfolio(String portfolioName) {
    if (portfolioName == null || portfolioName.length() == 0) {
      throw new IllegalArgumentException("Portfolio name cannot be null or empty string.");
    }
    if (this.portfolioMap.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio name already exists.");
    }
    IPortfolio portfolio = new Portfolio(portfolioName, this.getPrice);
    this.portfolioMap.put(portfolioName, portfolio);
  }

  /**
   * Buy a stock at date. If portfolio name is null or empty string, or portfolio name does not
   * exist, or stock symbol is null, not found or empty string , or date is null or after now,
   * or volume of stock is less than 0, this method will throw IllegalArgumentException.
   * @param portfolioName given portfolio name
   * @param stockSymbol given stock symbol
   * @param date given date
   * @param number given number
   */
  public void buy(String portfolioName, String stockSymbol, LocalDate date, double number) {
    checkPortfolioName(portfolioName);
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock name cannot be null or empty string.");
    }
    checkDate(date);
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      throw new IllegalArgumentException("Cannot buy in holiday.");
    }
    if (number <= 0) {
      throw new IllegalArgumentException("Number must be positive.");
    }
    stockSymbol = stockSymbol.toUpperCase();
    this.portfolioMap.get(portfolioName).addTransaction(stockSymbol, date, false, number);
  }

  /**
   * Buy a stock at date. If portfolio name is null or empty string, or portfolio name does not
   * exist, or stock symbol is null or empty string, or date is null, this method will throw
   * IllegalArgumentException. This method will buy stocks by given money to use.
   * @param portfolioName given portfolio name
   * @param stockSymbol given stock symbol
   * @param date given date
   * @param money given money
   */
  public void buyAmount(String portfolioName, String stockSymbol, LocalDate date, double money) {
    checkPortfolioName(portfolioName);
    if (stockSymbol == null || stockSymbol.length() == 0) {
      throw new IllegalArgumentException("Stock name cannot be null or empty string.");
    }
    checkDate(date);
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      throw new IllegalArgumentException("Cannot buy in holiday.");
    }
    if (money <= 0) {
      throw new IllegalArgumentException("Money must be positive.");
    }
    stockSymbol = stockSymbol.toUpperCase();
    double volume = money / this.getPrice.getPrice(stockSymbol, date);
    this.portfolioMap.get(portfolioName).addTransaction(stockSymbol, date, false, volume);
  }

  /**
   * Return total stocks cost at present in given portfolio. If portfolio dose not exist, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio
   * @return total stocks cost at present
   */
  public double getTotalCostBasis(String portfolioName) {
    checkPortfolioName(portfolioName);
    return this.portfolioMap.get(portfolioName).getStockCost(this.commissionFee);
  }

  /**
   * Return total stocks cost at certain date in given portfolio. Transactions after given date
   * will not affect the result. If date is null or date is after now, this method will throw
   * IllegalArgumentException.
   * @param portfolioName given portfolio
   * @param date given date
   * @return total stocks cost at certain date
   */
  public double getTotalCostBasisByDate(String portfolioName, LocalDate date) {
    checkPortfolioName(portfolioName);
    checkDate(date);
    return this.portfolioMap.get(portfolioName).getStockCostByDate(date, this.commissionFee);
  }

  /**
   * Return total stocks value at present in given portfolio. If portfolio dose not exist, this
   * method will throw IllegalArgumentException.
   * @param portfolioName given portfolio.
   * @return total stocks value at present.
   */
  public double getTotalValue(String portfolioName) {
    checkPortfolioName(portfolioName);
    return this.portfolioMap.get(portfolioName).getValue();
  }

  /**
   * Return total stocks value at certain date in given portfolio. Transactions after given date
   * will not affect the result. If date is null, or the date is after now, this method will throw
   * IllegalArgumentException.
   * @param portfolioName given portfolio
   * @param date given date
   * @return total stocks value at certain date
   */
  public double getTotalValueByDate(String portfolioName, LocalDate date) {
    checkPortfolioName(portfolioName);
    checkDate(date);
    return this.portfolioMap.get(portfolioName).getValueByDate(date);
  }

  /**
   * Return all the portfolio name as a List of String.
   * @return all the portfolio name
   */
  public List<String> getPortfolioList() {
    List<String> list = new ArrayList<>();
    for (String key : this.portfolioMap.keySet()) {
      list.add(key);
    }
    return list;
  }

  /**
   * Return a stock list as string list from given portfolio.
   * @param portfolioName given portfolio name
   * @return a stock list
   */
  public List<String> getStockList(String portfolioName) {
    checkPortfolioName(portfolioName);
    return this.portfolioMap.get(portfolioName).getStockList();
  }

  /**
   * Return portfolio state at now.
   * @param portfolioName given portfolio name
   * @return portfolio state of given name
   */
  public List<IStock> getPortfolioState(String portfolioName) {
    checkPortfolioName(portfolioName);
    return getPortfolioStateByDate(portfolioName, LocalDate.now());
  }

  /**
   * Return portfolio state of given name at certain date.
   * @param portfolioName given portfolio name
   * @param date given date
   * @return portfolio state of given name at certain date
   */
  public List<IStock> getPortfolioStateByDate(String portfolioName, LocalDate date) {
    checkPortfolioName(portfolioName);
    checkDate(date);
    return this.portfolioMap.get(portfolioName).getPortfolioStateByDate(date);
  }

  /**
   * Private method to get all the characters in given file.
   * @param filepath file name
   * @return all the characters as a string
   */
  private String getFileContent(String filepath) {
    String encode = "UTF-8";
    File file = new File(filepath);
    Long fileLength = file.length();
    byte[] fileContent = new byte[fileLength.intValue()];
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(fileContent);
      in.close();
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    try {
      return new String(fileContent, encode);
    }
    catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Save a portfolio into a XML file by given portfolio name and file name.
   * If the file name already exists, this method will overwrite the file.
   * Save portfolio name, all stock symbols and its amount and price of each share of stock
   * @param portfolioName given portfolio name
   * @param fileName given save file name
   */
  public void savePortfolio(String portfolioName, String fileName) {
    checkPortfolioName(portfolioName);
    if (fileName == null || fileName.length() == 0) {
      throw new IllegalArgumentException("File name cannot be null or empty string.");
    }
    this.portfolioMap.get(portfolioName).savePortfolio(fileName);
  }

  /**
   * Read a XML file to get a portfolio. If the file dose not exist, this method
   * will throw IllegalArgumentException. The format of file must be as follows:
   * (cost is the price of each share)<br>
   * &lt;portfolio&gt;<br>
   * &lt;portfolio-name&gt;p1&lt;/portfolio-name&gt;<br>
   * &lt;stock&gt;<br>
   * &lt;stock-symbol&gt;MSFT&lt;/stock-symbol&gt;<br>
   * &lt;transaction-history&gt;<br>
   * &lt;date&gt;2019-03-11&lt;/date&gt;<br>
   * &lt;sell&gt;false&lt;/sell&gt;<br>
   * &lt;volume&gt;36.0&lt;/volume&gt;<br>
   * &lt;cost&gt;100.0&lt;/cost&gt;<br>
   * &lt;/transaction-history&gt;<br>
   * ...<br>
   * &lt;/stock&gt;<br>
   * ...<br>
   * &lt;/portfolio&gt;<br>
   * All the tags must be lower case. All content must in root tag portfolio. User
   * cannot change order of tags. A portfolio can have one or more stocks, and a stock
   * can have one or more transaction-history. Format of date must be yyyy-mm-dd. Sell
   * must be true or false. Other tags are invalid.
   * If portfolio name already exists, the method will overwrite portfolio in map.
   * @param fileName given file name
   */
  public void readPortfolio(String fileName) {
    if (fileName == null || fileName.length() == 0) {
      throw new IllegalArgumentException("File name cannot be null or empty string.");
    }
    String content = getFileContent(fileName);
    IXMLParser parser = new XMLParser(content);
    IPortfolio p = (IPortfolio) parser.parse();
    this.portfolioMap.put(p.getPortfolioName(), p);
    this.portfolioMap.get(p.getPortfolioName()).setAPI(this.getPrice);
  }

  /**
   * Create a strategy with equal percent and without end date. If stocks are duplicate
   * or begin date is in the future, this method will throw IllegalArgumentException.
   * @param name strategy name
   * @param stockList stock list
   * @param money total money
   * @param beginDate begin date
   * @param day days between two but operation
   */
  public void createStrategyNoEndEqualPercent(String name, List<String> stockList,
                                              double money, LocalDate beginDate, int day) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid strategy name.");
    }
    if (this.strategyMap.containsKey(name)) {
      throw new IllegalArgumentException("Strategy already exists.");
    }
    if (stockList == null || stockList.size() == 0) {
      throw new IllegalArgumentException("Invalid stock list.");
    }
    if (money <= 0) {
      throw new IllegalArgumentException("Invalid money.");
    }
    if (beginDate == null || beginDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Invalid begin date.");
    }
    if (day <= 0) {
      throw new IllegalArgumentException("Invalid day.");
    }
    List<Double> weight = new ArrayList<>();
    for (int i = 0; i < stockList.size(); ++i) {
      weight.add(1.0 / stockList.size());
    }
    IStrategy strategy = new Strategy(name);
    strategy.setStocks(stockList, weight);
    strategy.setAmount(money);
    strategy.setBeginDate(beginDate);
    strategy.setDay(day);
    this.strategyMap.put(name, strategy);
  }

  /**
   * Create a strategy with without end date. If stocks are duplicate or begin date is
   * in the future, this method will throw IllegalArgumentException.
   * @param name strategy name
   * @param stockList stock list
   * @param weight weight list
   * @param money total money
   * @param beginDate begin date
   * @param day days between two but operation
   */
  public void createStrategyNoEndDiffPercent(String name, List<String> stockList,
                                             List<Double> weight, double money,
                                             LocalDate beginDate, int day) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid strategy name.");
    }
    if (this.strategyMap.containsKey(name)) {
      throw new IllegalArgumentException("Strategy already exists.");
    }
    if (stockList == null || stockList.size() == 0) {
      throw new IllegalArgumentException("Invalid stock list.");
    }
    if (weight == null || weight.size() != stockList.size()) {
      throw new IllegalArgumentException("Invalid weight list.");
    }
    if (money <= 0) {
      throw new IllegalArgumentException("Invalid money.");
    }
    if (beginDate == null || beginDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Invalid begin date.");
    }
    if (day <= 0) {
      throw new IllegalArgumentException("Invalid day.");
    }
    IStrategy strategy = new Strategy(name);
    strategy.setStocks(stockList, weight);
    strategy.setAmount(money);
    strategy.setBeginDate(beginDate);
    strategy.setDay(day);
    this.strategyMap.put(name, strategy);
  }

  /**
   * Create a strategy with equal percent and with end date. If stocks are duplicate
   * or begin date is in the future, this method will throw IllegalArgumentException.
   * End date will be the last date to buy stocks.
   * @param name strategy name
   * @param stockList stock list
   * @param money total money
   * @param beginDate begin date
   * @param endDate end date
   * @param day days between two but operation
   */
  public void createStrategyEqualPercent(String name, List<String> stockList, double money,
                                         LocalDate beginDate, LocalDate endDate, int day) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid strategy name.");
    }
    if (this.strategyMap.containsKey(name)) {
      throw new IllegalArgumentException("Strategy already exists.");
    }
    if (stockList == null || stockList.size() == 0) {
      throw new IllegalArgumentException("Invalid stock list.");
    }
    if (money <= 0) {
      throw new IllegalArgumentException("Invalid money.");
    }
    if (beginDate == null || beginDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Invalid begin date.");
    }
    if (endDate == null || endDate.isBefore(beginDate)) {
      throw new IllegalArgumentException("Invalid end date.");
    }
    if (day <= 0) {
      throw new IllegalArgumentException("Invalid day.");
    }
    List<Double> weight = new ArrayList<>();
    for (int i = 0; i < stockList.size(); ++i) {
      weight.add(1.0 / stockList.size());
    }
    IStrategy strategy = new Strategy(name);
    strategy.setStocks(stockList, weight);
    strategy.setAmount(money);
    strategy.setBeginDate(beginDate);
    strategy.setEndDate(endDate);
    strategy.setDay(day);
    this.strategyMap.put(name, strategy);
  }

  /**
   * Create a strategy with with end date. If stocks are duplicate or begin date is
   * in the future, this method will throw IllegalArgumentException. End date will
   * be the last date to buy stocks.
   * @param name strategy name
   * @param stockList stock list
   * @param weight weight list
   * @param money total money
   * @param beginDate begin date
   * @param endDate end date
   * @param day days between two but operation
   */
  public void createStrategyDiffPercent(String name, List<String> stockList,
                                        List<Double> weight, double money,
                      LocalDate beginDate, LocalDate endDate, int day) {
    if (name == null || name.length() == 0) {
      throw new IllegalArgumentException("Invalid strategy name.");
    }
    if (this.strategyMap.containsKey(name)) {
      throw new IllegalArgumentException("Strategy already exists.");
    }
    if (stockList == null || stockList.size() == 0) {
      throw new IllegalArgumentException("Invalid stock list.");
    }
    if (weight == null || weight.size() != stockList.size()) {
      throw new IllegalArgumentException("Invalid weight list.");
    }
    if (money <= 0) {
      throw new IllegalArgumentException("Invalid money.");
    }
    if (beginDate == null || beginDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Invalid begin date.");
    }
    if (endDate == null || endDate.isBefore(beginDate)) {
      throw new IllegalArgumentException("Invalid end date.");
    }
    if (day <= 0) {
      throw new IllegalArgumentException("Invalid day.");
    }
    IStrategy strategy = new Strategy(name);
    strategy.setStocks(stockList, weight);
    strategy.setAmount(money);
    strategy.setBeginDate(beginDate);
    strategy.setEndDate(endDate);
    strategy.setDay(day);
    this.strategyMap.put(name, strategy);
  }

  /**
   * Return all the strategy as a list.
   * @return strategy list
   */
  public List<IStrategy> getStrategy() {
    List<IStrategy> list = new ArrayList<>();
    for (String s : this.strategyMap.keySet()) {
      list.add(this.strategyMap.get(s));
    }
    return list;
  }

  /**
   * Private method to buy list of stocks at certain date.
   * @param portfolioName portfolio name
   * @param stock stock list
   * @param weight weight list
   * @param date date
   * @param money total money
   */
  private void buyOnce(String portfolioName, List<String> stock, List<Double> weight,
                       LocalDate date, double money) {
    for (int i = 0; i < stock.size(); ++i) {
      if (Math.abs(weight.get(i) - 0) > 1e-4) {
        this.buyAmount(portfolioName, stock.get(i), date, money * weight.get(i));
      }
    }
  }

  /**
   * Calculate the next available date to buy stocks. If given date is available, this
   * method will return original date.
   * @param stockName stock symbol
   * @param date original date
   * @return next available date
   */
  private LocalDate nextAvailableDate(String stockName, LocalDate date) {
    if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
      date = date.plusDays(2);
    }
    else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
      date = date.plusDays(1);
    }
    if (date.isAfter(LocalDate.now())) {
      return date;
    }
    while (Math.abs(this.getPrice.getPrice(stockName, date) - 0) < 1e-6) {
      date = date.plusDays(1);
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
        date.plusDays(2);
      }
      if (date.isAfter(LocalDate.now())) {
        break;
      }
    }
    return date;
  }

  /**
   * Buy list of stocks multi times.
   * @param portfolioName portfolio name
   * @param stock stock list
   * @param weight weight list
   * @param beginDate begin date
   * @param endDate end date
   * @param day days between buy operation
   * @param money total money
   */
  private void buyPeriodic(String portfolioName, List<String> stock, List<Double> weight,
                           LocalDate beginDate, LocalDate endDate, int day, double money) {
    LocalDate currDate = beginDate;
    if (endDate == null) {
      endDate = LocalDate.now();
    }
    while (true) {
      currDate = nextAvailableDate(stock.get(0), currDate);
      if (currDate.isAfter(endDate) || currDate.isAfter(LocalDate.now())) {
        break;
      }
      buyOnce(portfolioName, stock, weight, currDate, money);
      currDate = currDate.plusDays(day);
    }
  }

  /**
   * Check if stocks between strategy and portfolio match.
   * @param portfolioName portfolio name
   * @param stocks stock list
   */
  private void checkStocks(String portfolioName, List<String> stocks) {
    Set<String> stockSet = new HashSet<>();
    List<String> list = this.getStockList(portfolioName);
    for (String s : list) {
      stockSet.add(s.toUpperCase());
    }
    if (stocks.size() != stockSet.size()) {
      throw new IllegalArgumentException("Stocks between portfolio and strategy do not match.");
    }
    for (String s : stocks) {
      if (!stockSet.contains(s.toUpperCase())) {
        throw new IllegalArgumentException("Stocks between portfolio and strategy do not match.");
      }
    }
  }

  /**
   * Apply an existing strategy to an existing portfolio. All the stocks in portfolio
   * and strategy must be the same.
   * @param portfolioName portfolio name
   * @param strategyName strategy name
   */
  public void invest(String portfolioName, String strategyName) {
    checkPortfolioName(portfolioName);
    if (strategyName == null || !this.strategyMap.containsKey(strategyName)) {
      throw new IllegalArgumentException("Invalid strategy name.");
    }
    IStrategy strategy = this.strategyMap.get(strategyName);
    checkStocks(portfolioName, strategy.getStockList());
    buyPeriodic(portfolioName, strategy.getStockList(), strategy.getWeight(),
            strategy.getBeginDate(), strategy.getEndDate(), strategy.getDay(),
            strategy.getAmount());
  }

  /**
   * Save strategy to a file.
   * @param strategyName strategy name
   * @param filename filename
   */
  public void saveStrategy(String strategyName, String filename) {
    if (strategyName == null || !this.strategyMap.containsKey(strategyName)) {
      throw new IllegalArgumentException("Invalid strategy name.");
    }
    if (filename == null || filename.length() == 0) {
      throw new IllegalArgumentException("File name cannot be null or empty string.");
    }
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(filename);
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Save fail: " + e.getMessage());
    }
    IStrategy strategy = this.strategyMap.get(strategyName);
    writer.println("<strategy>");
    writer.println("\t<strategy-name>" + strategyName + "</strategy-name>");
    writer.println("\t<stock-list>");
    for (int i = 0; i < strategy.getStockList().size(); ++i) {
      writer.println("\t\t<stock>");
      writer.println("\t\t\t<stock-symbol>" + strategy.getStockList().get(i) + "</stock-symbol>");
      writer.println("\t\t\t<stock-weight>" + strategy.getWeight().get(i) + "</stock-weight>");
      writer.println("\t\t</stock>");
    }
    writer.println("\t</stock-list>");
    writer.println("\t<begin-date>" + strategy.getBeginDate().toString() + "</begin-date>");
    writer.println("\t<end-date>"
            + (strategy.getEndDate() == null ? "" : strategy.getEndDate().toString())
            + "</end-date>");
    writer.println("\t<amount>" + strategy.getAmount() + "</amount>");
    writer.println("\t<day>" + strategy.getDay() + "</day>");
    writer.println("</strategy>");
    writer.close();
  }

  /**
   * Read a file to get strategy.
   * @param fileName file name
   */
  public void readStrategy(String fileName) {
    if (fileName == null || fileName.length() == 0) {
      throw new IllegalArgumentException("File name cannot be null or empty string.");
    }
    String content = getFileContent(fileName);
    IXMLParser parser = new XMLStrategy(content);
    IStrategy strategy = (IStrategy) parser.parse();
    this.strategyMap.put(strategy.getStrategyName(), strategy);
  }

  /**
   * This class is a builder class implements TradeOperationBuilder interface.
   */
  public static class TradeBuilder implements TradeOperationBuilder {
    private IGetPrice getPrice;
    private double commissionFee;

    /**
     * Construct a TradeBuilder with default variable. Default API is GetPrice,
     * and default commission fee is 5.
     */
    public TradeBuilder() {
      getPrice = new GetPrice();
      commissionFee = 5;
    }

    /**
     * Set API object. If API is null, this method will throw IllegalArgumentException.
     * @param api given API object
     * @return this object
     */
    public TradeOperationBuilder setAPI(IGetPrice api) {
      if (api == null) {
        throw new IllegalArgumentException("API object cannot be null.");
      }
      this.getPrice = api;
      return this;
    }

    /**
     * Set commission fee. If commission fee is negative, this method will throw
     * IllegalArgumentException.
     * @param fee commission fee
     * @return this object
     */
    public TradeOperationBuilder setCommissionFee(double fee) {
      if (fee < 0) {
        throw new IllegalArgumentException("Commission fee cannot be negative.");
      }
      this.commissionFee = fee;
      return this;
    }

    /**
     * Return a TradeOperation object.
     * @return a TradeOperation object
     */
    public TradeOperation<Stock> build() {
      return new TradeModel(getPrice, commissionFee);
    }
  }

  /**
   * Return a TradeBuilder object.
   * @return a TradeBuilder object
   */
  public static TradeBuilder getBuilder() {
    return new TradeBuilder();
  }
}
