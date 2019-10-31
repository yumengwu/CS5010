package tradestock.model.tradeoperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;
import tradestock.model.portfolio.IPortfolio;
import tradestock.model.portfolio.Portfolio;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.xmlparser.IXMLParser;
import tradestock.model.xmlparser.XMLParser;

/**
 * This class represent a TradeModel implements TradeOperation interface. A TradeOModel
 * could create portfolio, but stocks, get cost, get value, get portfolio list, and get
 * portfolio state.
 */
public class TradeModel implements TradeOperation<Stock> {
  private Map<String, IPortfolio> portfolioMap;
  private IGetPrice getPrice;
  private double commissionFee;

  /**
   * Construct a TradeModel object. Default API is GetPrice object, and default commission
   * fee is 5.
   */
  public TradeModel() {
    this.portfolioMap = new LinkedHashMap<>();
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
   * 	&lt;portfolio-name&gt;p1&lt;/portfolio-name&gt;<br>
   * 	&lt;stock&gt;<br>
   * 		&lt;stock-symbol&gt;MSFT&lt;/stock-symbol&gt;<br>
   * 		&lt;transaction-history&gt;<br>
   * 			&lt;date&gt;2019-03-11&lt;/date&gt;<br>
   * 			&lt;sell&gt;false&lt;/sell&gt;<br>
   * 			&lt;volume&gt;36.0&lt;/volume&gt;<br>
   * 			&lt;cost&gt;100.0&lt;/cost&gt;<br>
   * 		&lt;/transaction-history&gt;<br>
   * 		...<br>
   * 	&lt;/stock&gt;<br>
   * 	...<br>
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
    IPortfolio p = parser.parse();
    this.portfolioMap.put(p.getPortfolioName(), p);
    this.portfolioMap.get(p.getPortfolioName()).setAPI(this.getPrice);
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
