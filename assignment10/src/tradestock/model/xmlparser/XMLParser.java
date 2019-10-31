package tradestock.model.xmlparser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import tradestock.model.getprice.MockGetPrice;
import tradestock.model.portfolio.IPortfolio;
import tradestock.model.portfolio.Portfolio;
import tradestock.model.stock.IStock;

/**
 * This method will parse given string to IPortfolio object. Format of input XML file
 * string are defined in TradeModel.
 */
public class XMLParser implements IXMLParser {
  /**
   * Private enum to define state when processing string.
   */
  enum ProcessState {
    INIT, WAIT_STOCK, WAIT_TRANSACTION, WAIT_DATE, WAIT_SELL, WAIT_VOLUME, WAIT_COST, END
  }

  private String content;
  private List<String> result;

  private ProcessState state;

  private LocalDate localDate;
  private boolean isSell;
  private double volume;
  private double cost;

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
  public XMLParser(String str) {
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
   * Private method process init state. This state will get portfolio name.
   * @param count counter
   * @param sb save portfolio name
   * @return index of next line in this.result.
   */
  private int processInit(int count, StringBuilder sb) {
    String line = this.result.get(count);
    if (!line.equals("start:portfolio")) {
      throw new IllegalArgumentException("Not start with portfolio tag.");
    }
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:portfolio-name")) {
      throw new IllegalArgumentException("Not find portfolio-name tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if (line.indexOf("char:") != 0 || line.substring(5).length() == 0) {
      throw new IllegalArgumentException("Not find portfolio name.");
    }
    sb.append(line.substring(5).trim());
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("end:portfolio-name")) {
      throw new IllegalArgumentException("Not find portfolio-name end tag.");
    }
    this.state = ProcessState.WAIT_STOCK;
    return ++count;
  }

  /**
   * Private method to process stock state. This state will get stock symbol.
   * @param count counter
   * @param sb save stock symbol
   * @return index of next line in this.result.
   */
  private int processStock(int count, StringBuilder sb) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (line.equals("end:portfolio")) {
      this.state = ProcessState.END;
      return count;
    }
    if (!line.equals("start:stock")) {
      throw new IllegalArgumentException("Not find stock tag.");
    }
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:stock-symbol")) {
      throw new IllegalArgumentException("Not find stock-symbol tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if (line.indexOf("char:") != 0 || line.substring(5).length() == 0) {
      throw new IllegalArgumentException("Not find stock symbol.");
    }
    sb.append(line.substring(5).trim());
    line = this.result.get(++count);
    if (!line.equals("end:stock-symbol")) {
      throw new IllegalArgumentException("Not find stock-symbol end tag.");
    }
    this.state = ProcessState.WAIT_TRANSACTION;
    return ++count;
  }

  /**
   * Private method to process transaction state.
   * @param count counter
   * @return index of next line in this.result.
   */
  private int processTransaction(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (line.equals("end:stock")) {
      this.state = ProcessState.WAIT_STOCK;
      return ++count;
    }
    if (!line.equals("start:transaction-history")) {
      throw new IllegalArgumentException("Not find transaction-history tag.");
    }
    this.state = ProcessState.WAIT_DATE;
    return ++count;
  }

  /**
   * Ptivate method to process date.
   * @param count counter
   * @return index of next line in this.result.
   */
  private int processDate(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:date")) {
      throw new IllegalArgumentException("Not find date tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).length() == 0) {
      throw new IllegalArgumentException("No date.");
    }
    String strDate = line.substring(5).trim();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      localDate = LocalDate.parse(strDate, format);
    }
    catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Illegal date: " + e.getMessage());
    }
    if (Integer.parseInt(strDate.substring(0, 4)) != localDate.getYear()
            || Integer.parseInt(strDate.substring(5, 7)) != localDate.getMonthValue()
            || Integer.parseInt(strDate.substring(8,10)) != localDate.getDayOfMonth()) {
      throw new IllegalArgumentException("Illegal date: date dose not exist.");
    }
    line = this.result.get(++count);
    if (!line.equals("end:date")) {
      throw new IllegalArgumentException("Not find date end tag.");
    }
    this.state = ProcessState.WAIT_SELL;
    return ++count;
  }

  /**
   * Ptivate method to process sell.
   * @param count counter
   * @return index of next line in this.result.
   */
  private int processSell(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:sell")) {
      throw new IllegalArgumentException("Not find sell tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).length() == 0) {
      throw new IllegalArgumentException("No sell.");
    }
    line = line.substring(5).trim();
    if (line.equals("true")) {
      this.isSell = true;
    }
    else if (line.equals("false")) {
      this.isSell = false;
    }
    else {
      throw new IllegalArgumentException("Not a boolean.");
    }
    line = this.result.get(++count);
    if (!line.equals("end:sell")) {
      throw new IllegalArgumentException("Not find sell end tag.");
    }
    this.state = ProcessState.WAIT_VOLUME;
    return ++count;
  }

  /**
   * Ptivate method to process volume.
   * @param count counter
   * @return index of next line in this.result.
   */
  private int processVolume(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:volume")) {
      throw new IllegalArgumentException("Not find volume tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).length() == 0) {
      throw new IllegalArgumentException("No volume.");
    }
    line = line.substring(5).trim();
    try {
      this.volume = Double.parseDouble(line);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Not a double: " + e.getMessage());
    }
    line = this.result.get(++count);
    if (!line.equals("end:volume")) {
      throw new IllegalArgumentException("Not find volume end tag.");
    }
    this.state = ProcessState.WAIT_COST;
    return ++count;
  }

  /**
   * Ptivate method to process cost.
   * @param count counter
   * @return index of next line in this.result.
   */
  private int processCost(int count) {
    String line = this.result.get(count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("start:cost")) {
      throw new IllegalArgumentException("Not find cost tag.");
    }
    line = this.result.get(++count).replace("\t", "").trim();
    if ((line.indexOf("char:") != 0) || line.substring(5).length() == 0) {
      throw new IllegalArgumentException("No cost.");
    }
    line = line.substring(5).trim();
    try {
      this.cost = Double.parseDouble(line);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Not a double: " + e.getMessage());
    }
    line = this.result.get(++count);
    if (!line.equals("end:cost")) {
      throw new IllegalArgumentException("Not find cost end tag.");
    }
    line = this.result.get(++count);
    if (line.indexOf("char:") == 0) {
      checkEmptyLine(line);
      line = this.result.get(++count);
    }
    if (!line.equals("end:transaction-history")) {
      throw new IllegalArgumentException("Not find transaction-history end tag.");
    }
    this.state = ProcessState.WAIT_TRANSACTION;
    return ++count;
  }

  /**
   * Return IPortfolio object after parsing input string.
   * @return IPortfolio object
   */
  public IPortfolio parse() {
    this.state = ProcessState.INIT;
    int count = 0;
    StringBuilder newName = new StringBuilder();
    Map<String, IStock> newStockMap = new HashMap<>();
    StringBuilder currStock = new StringBuilder();
    IPortfolio portfolio = new Portfolio("init", new MockGetPrice());
    while (count < this.result.size()) {
      switch (this.state) {
        case INIT:
          count = processInit(count, newName);
          portfolio = new Portfolio(newName.toString(), new MockGetPrice());
          break;
        case WAIT_STOCK:
          currStock = new StringBuilder();
          count = processStock(count, currStock);
          if (state == ProcessState.END) {
            break;
          }
          //newStockMap.put(currStock.toString(), new Stock(currStock.toString()));
          break;
        case WAIT_TRANSACTION:
          count = processTransaction(count);
          break;
        case WAIT_DATE:
          count = processDate(count);
          break;
        case WAIT_SELL:
          count = processSell(count);
          break;
        case WAIT_VOLUME:
          count = processVolume(count);
          break;
        case WAIT_COST:
          count = processCost(count);
          portfolio.addTransaction(currStock.toString(),
                  this.localDate, this.isSell, this.volume, this.cost);
          break;
        case END:
          ++count;
          break;
        default:
          break;
      }
    }
    return portfolio;
  }
}
