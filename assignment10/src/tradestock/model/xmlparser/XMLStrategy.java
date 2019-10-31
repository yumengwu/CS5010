package tradestock.model.xmlparser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import tradestock.model.strategy.IStrategy;
import tradestock.model.strategy.Strategy;

/**
 * This method will parse given string to IStrategy object.
 */
public class XMLStrategy implements IXMLParser {
  /**
   * Private enum about process states.
   */
  enum ProcessState {
    INIT, WAIT_STRATEGY_NAME, WAIT_STOCK_LIST, WAIT_STOCK, WAIT_SYMBOL, WAIT_WEIGHT,
    WAIT_BEGIN_DATE, WAIT_END_DATE, WAIT_DAY, WAIT_AMOUNT, END
  }

  private String content;
  public List<String> result;
  private ProcessState state;

  private String strategyName;
  private List<String> stockList;
  private List<Double> weightList;
  private LocalDate beginDate = null;
  private LocalDate endDate = null;
  private int day;
  private double amount;

  private IStrategy strategy;

  /**
   * Private method to check if a tag is valid. A tag must be lower case with only a-z
   * characters.
   * @param tag given tag
   * @return true if tag is valid, false otherwise
   */
  private boolean checkTag(String tag) {
    if (tag.length() < 1) {
      return false;
    }
    char c = tag.charAt(0);
    if (!(c >= 'a' && c <= 'z')) {
      return false;
    }
    for (int i = 1; i < tag.length(); ++i) {
      c = tag.charAt(i);
      if (!(c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '-')) {
        return false;
      }
    }
    return true;
  }

  /**
   * Private method to check if input string is a complete XML file.
   * @return true if input is complete, or false otherwise
   */
  private boolean isComplete() {
    if (this.content.charAt(0) != '<' || this.content.charAt(this.content.length() - 1) != '>') {
      throw new IllegalArgumentException("Invalid XML file.");
    }
    Stack<String> xmlTags = new Stack<>();
    for (int i = 0; i < this.content.length();) {
      String currTag = "";
      int  j = 0;
      for (j = 0; i + j < this.content.length(); ++j) {
        if (this.content.charAt(i + j) == '>') {
          currTag = this.content.substring(i, i + j + 1);
          break;
        }
      }
      String word = "";
      if (currTag.charAt(1) == '/') {
        word = currTag.substring(2, currTag.length() - 1);
        if (!word.equals(xmlTags.peek())) {
          throw new IllegalArgumentException("Invalid end tag: should be "
                  + xmlTags.peek() + ", but got " + word);
        }
        xmlTags.pop();
        if (xmlTags.size() == 0 && i + word.length() + 3 < this.content.length() - 1) {
          throw new IllegalArgumentException("Content after root tag.");
        }
        this.result.add("end:" + word);
      }
      else {
        word = currTag.substring(1, currTag.length() - 1);
        if (checkTag(word)) {
          xmlTags.push(word);
          this.result.add("start:" + word);
        }
        else {
          throw new IllegalArgumentException("Invalid tag.");
        }
      }
      i = i + j + 1;
      for (j = i; j < this.content.length(); ++j) {
        if (this.content.charAt(j) == '<') {
          break;
        }
      }
      word = "";
      if (i != j) {
        word = this.content.substring(i, j);
      }
      if (word.length() > 0) {
        this.result.add("char:" + word);
      }
      i = j;
    }
    return xmlTags.empty();
  }

  /**
   * Construct a XMLParser object with given string. If string is empty, or string is
   * not a complete XML file content, this method will throw IllegalArgumentException.
   * @param str XML file content
   */
  public XMLStrategy(String str) {
    if (str == null || str.length() == 0) {
      throw new IllegalArgumentException("XML file cannot be null or empty string.");
    }
    this.content = str.replace("\n", "").trim();
    this.result = new ArrayList<>();
    if (!isComplete()) {
      throw new IllegalArgumentException("File is not complete.");
    }
  }

  /**
   * Private method to check if there are invalid characters after tags.
   * @param line given string
   */
  private void checkEmptyLine(String line) {
    if (line.substring(5).replace('\t', ' ')
            .replace('\n', ' ').trim().length() != 0) {
      throw new IllegalArgumentException("Invalid characters after tag.");
    }
  }

  /**
   * Process init state.
   * @param count current position
   * @return next position
   */
  private int processInit(int count) {
    String line = this.result.get(count);
    if (!line.equals("start:strategy")) {
      throw new IllegalArgumentException("Not start with strategy tag.");
    }
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    this.state = ProcessState.WAIT_STRATEGY_NAME;
    return count;
  }

  /**
   * Process strategy name state.
   * @param count current position
   * @return next position
   */
  private int processStrategyName(int count) {
    String line = this.result.get(count);
    if (!line.equals("start:strategy-name")) {
      throw new IllegalArgumentException("Not find with strategy-name tag.");
    }
    line = this.result.get(++count).replace("\t", " ").trim();
    if (line.indexOf("char:") != 0 || line.substring(5).trim().length() == 0) {
      throw new IllegalArgumentException("Not find strategy name.");
    }
    this.strategyName = line.substring(5).trim();
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("end:strategy-name")) {
      throw new IllegalArgumentException("Not find strategy-name end tag.");
    }
    this.state = ProcessState.WAIT_STOCK_LIST;
    return ++count;
  }

  /**
   * Process stock list state.
   * @param count current position
   * @return next position
   */
  private int processStockList(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:stock-list")) {
      throw new IllegalArgumentException("Not find stock-list tag.");
    }
    this.stockList = new LinkedList<>();
    this.weightList = new LinkedList<>();
    this.state = ProcessState.WAIT_STOCK;
    return ++count;
  }

  /**
   * Process stock state.
   * @param count current position
   * @return next position
   */
  private int processStock(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (line.equals("end:stock-list")) {
      if (this.stockList.size() == 0) {
        throw new IllegalArgumentException("No stock symbol.");
      }
      this.state = ProcessState.WAIT_BEGIN_DATE;
      return ++count;
    }
    if (!line.equals("start:stock")) {
      throw new IllegalArgumentException("Not find stock tag.");
    }
    this.state = ProcessState.WAIT_SYMBOL;
    return ++count;
  }

  /**
   * Process stock symbol state.
   * @param count current position
   * @return next position
   */
  private int processStockSymbol(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:stock-symbol")) {
      throw new IllegalArgumentException("Not find stock-symbol tag.");
    }
    line = this.result.get(++count).replace("\t", " ").trim();
    if (line.indexOf("char:") != 0 || line.substring(5).trim().length() == 0) {
      throw new IllegalArgumentException("Not find stock symbol name.");
    }
    this.stockList.add(line.substring(5).trim());
    line = this.result.get(++count);
    if (!line.equals("end:stock-symbol")) {
      throw new IllegalArgumentException("Not find stock-symbol end tag.");
    }
    this.state = ProcessState.WAIT_WEIGHT;
    return ++count;
  }

  /**
   * Process weight state.
   * @param count current position
   * @return next position
   */
  private int processStockWeight(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:stock-weight")) {
      throw new IllegalArgumentException("Not find stock-weight tag.");
    }
    line = this.result.get(++count).replace("\t", " ").trim();
    if (line.indexOf("char:") != 0 || line.indexOf("char:") == 0
            && line.substring(5).trim().length() == 0) {
      if (this.stockList.size() != this.weightList.size() + 1 && this.weightList.size() != 0) {
        System.out.println(stockList.size() + " " + weightList.size());
        throw new IllegalArgumentException("Not find stock weight.");
      }
    }
    else {
      try {
        this.weightList.add(Double.parseDouble(line.substring(5).trim()));
      }
      catch (Exception e) {
        throw new IllegalArgumentException("Invalid double.");
      }
      line = this.result.get(++count);
    }
    if (!line.equals("end:stock-weight")) {
      throw new IllegalArgumentException("Not find stock-weight end tag.");
    }
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("end:stock")) {
      throw new IllegalArgumentException("Not find stock end tag.");
    }
    this.state = ProcessState.WAIT_STOCK;
    return ++count;
  }

  /**
   * Check if a date exists.
   * @param strDate given date as string
   * @param localDate date after parsed
   */
  private void checkDate(String strDate, LocalDate localDate) {
    if (Integer.parseInt(strDate.substring(0, 4)) != localDate.getYear()
            || Integer.parseInt(strDate.substring(5, 7)) != localDate.getMonthValue()
            || Integer.parseInt(strDate.substring(8,10)) != localDate.getDayOfMonth()) {
      throw new IllegalArgumentException("Illegal date: date dose not exist.");
    }
  }

  /**
   * Process begin date state.
   * @param count current position
   * @return next position
   */
  private int processBeginDate(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:begin-date")) {
      throw new IllegalArgumentException("Not find begin-date tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).trim().length() == 0) {
      throw new IllegalArgumentException("No date.");
    }
    String strDate = line.substring(5).trim();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      this.beginDate = LocalDate.parse(strDate, format);
    }
    catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Illegal date: " + e.getMessage());
    }
    checkDate(strDate, this.beginDate);
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("end:begin-date")) {
      throw new IllegalArgumentException("Not find begin-date end tag.");
    }
    this.state = ProcessState.WAIT_END_DATE;
    return ++count;
  }

  /**
   * Process end date state.
   * @param count current position
   * @return next position
   */
  private int processEndDate(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:end-date")) {
      throw new IllegalArgumentException("Not find end-date tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if (line.indexOf("char:") != 0 || line.indexOf("char:") == 0
            && line.substring(5).trim().length() == 0) {
      this.endDate = null;
    }
    else {
      String strDate = line.substring(5).trim();
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      try {
        this.endDate = LocalDate.parse(strDate, format);
        if (this.endDate.isBefore(this.beginDate)) {
          throw new IllegalArgumentException("Invalid end date.");
        }
        checkDate(strDate, this.endDate);
      }
      catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Illegal date: " + e.getMessage());
      }
      line = this.result.get(++count);
      if (line.indexOf("char:") == 0) {
        checkEmptyLine(line);
        line = this.result.get(++count);
      }
    }
    if (!line.equals("end:end-date")) {
      throw new IllegalArgumentException("Not find end-date end tag.");
    }
    this.state = ProcessState.WAIT_AMOUNT;
    return ++count;
  }

  /**
   * Process amount state.
   * @param count current position
   * @return next position
   */
  private int processAmount(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:amount")) {
      throw new IllegalArgumentException("Not find amount tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).trim().length() == 0) {
      throw new IllegalArgumentException("No amount.");
    }
    try {
      this.amount = Double.parseDouble(line.substring(5).trim());
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Invalid double: " + e.getMessage());
    }
    line = this.result.get(++count);
    if (!line.equals("end:amount")) {
      throw new IllegalArgumentException("Not find amount end tag.");
    }
    this.state = ProcessState.WAIT_DAY;
    return ++count;
  }

  /**
   * Process day state.
   * @param count current position
   * @return next position
   */
  private int processDay(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:day")) {
      throw new IllegalArgumentException("Not find day tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).trim().length() == 0) {
      throw new IllegalArgumentException("No day.");
    }
    try {
      this.day = Integer.parseInt(line.substring(5).trim());
    }
    catch (Exception e) {
      throw new IllegalArgumentException("Invalid double: " + e.getMessage());
    }
    line = this.result.get(++count);
    if (!line.equals("end:day")) {
      throw new IllegalArgumentException("Not find day end tag.");
    }
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("end:strategy")) {
      throw new IllegalArgumentException("Not find strategy end tag.");
    }
    this.state = ProcessState.END;
    return count;
  }

  /**
   * Process end state.
   * @param count current position
   * @return next position
   */
  private int processEnd(int count) {
    if (this.weightList.size() == 0) {
      for (int i = 0; i < this.stockList.size(); ++i) {
        this.weightList.add(1.0 / this.stockList.size());
      }
    }
    this.strategy = new Strategy(this.strategyName);
    this.strategy.setStocks(this.stockList, this.weightList);
    this.strategy.setBeginDate(this.beginDate);
    if (this.endDate != null) {
      this.strategy.setEndDate(this.endDate);
    }
    this.strategy.setAmount(this.amount);
    this.strategy.setDay(this.day);
    return ++count;
  }

  /**
   * Return IStrategy object after parsing input string.
   * @return IStrategy object
   */
  public IStrategy parse() {
    this.state = ProcessState.INIT;
    int count = 0;
    while (count < this.result.size()) {
      switch (this.state) {
        case INIT:
          count = processInit(count);
          break;
        case WAIT_STRATEGY_NAME:
          count = processStrategyName(count);
          break;
        case WAIT_STOCK_LIST:
          count = processStockList(count);
          break;
        case WAIT_STOCK:
          count = processStock(count);
          break;
        case WAIT_SYMBOL:
          count = processStockSymbol(count);
          break;
        case WAIT_WEIGHT:
          count = processStockWeight(count);
          break;
        case WAIT_BEGIN_DATE:
          count = processBeginDate(count);
          break;
        case WAIT_END_DATE:
          count = processEndDate(count);
          break;
        case WAIT_AMOUNT:
          count = processAmount(count);
          break;
        case WAIT_DAY:
          count = processDay(count);
          break;
        case END:
          count = processEnd(count);
          break;
        default:
          break;
      }
    }
    return this.strategy;
  }
}
