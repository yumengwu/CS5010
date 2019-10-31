import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import tradestock.model.portfolio.IPortfolio;
import tradestock.model.stock.IStock;
import tradestock.model.xmlparser.XMLParser;

public class XMLParserTest {
  @Test
  public void testInvalidString1() {
    try {
      XMLParser parser = new XMLParser(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("XML file cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testInvalidString2() {
    try {
      XMLParser parser = new XMLParser("");
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("XML file cannot be null or empty string.", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML1() {
    String str = "abcd";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid XML file.", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML2() {
    String str = "<>";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML3() {
    String str = "<abcd>";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("File is not complete.", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML4() {
    String str = "<abc d></abc d>";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML5() {
    String str = "< abcd></ abcd>";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML6() {
    String str = "<a><b></a></b>";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid end tag: should be b, but got a", e.getMessage());
    }
  }

  @Test
  public void testInvalidXML7() {
    String str = "<a><b></b></a><a></a>";
    try {
      XMLParser parser = new XMLParser(str);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Content after root tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML1() {
    String str = "<a></a>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not start with portfolio tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML2() {
    String str = "<portfolio>a</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML3() {
    String str = "<portfolio><a></a></portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find portfolio-name tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML4() {
    String str = "<portfolio><portfolio-name></portfolio-name></portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find portfolio name.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML5() {
    String str = "<portfolio><portfolio-name> \t</portfolio-name></portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find portfolio name.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML6() {
    String str = "<portfolio><portfolio-name> <a></a></portfolio-name></portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find portfolio name.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML7() {
    String str = "<portfolio><portfolio-name>a<a></a></portfolio-name></portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find portfolio-name end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML8() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>a</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML9() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<a></a></portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML10() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock></stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock-symbol tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML11() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock>abc</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML12() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol></stock-symbol></stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock symbol.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML13() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol> \t </stock-symbol></stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock symbol.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML14() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol><a></a></stock-symbol></stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock symbol.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML15() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG<a></a></stock-symbol></stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock-symbol end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML16() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "abc" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML17() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<abc></abc>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find transaction-history tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML18() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history></transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find date tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML19() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "abc" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML20() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<ate></ate>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find date tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML21() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>abc</date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Illegal date: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML22() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>201811</date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Illegal date: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML23() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>20190101</date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Illegal date: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML24() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>20-12-2019</date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Illegal date: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML25() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>03-31-2019</date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Illegal date: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML26() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-29</date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Illegal date: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML27() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28<abc></abc></date>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find date end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML28() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>abc" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML29() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date><abc></abc>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find sell tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML30() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell></sell>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No sell.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML31() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>abc</sell>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not a boolean.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML32() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell> abd" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML33() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false<abc></abc></sell> " +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find sell end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML34() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<abc></abc>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find volume tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML35() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume></volume>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No volume.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML36() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume><a></a></volume>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No volume.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML37() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>abc</volume>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Not a double: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML38() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234<a></a></volume>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find volume end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML39() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>abc" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML40() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<a></a>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find cost tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML41() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost></cost>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("No cost.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML42() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost>abc</cost>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(0, e.getMessage().indexOf("Not a double: "));
    }
  }

  @Test
  public void testInvalidPortfolioXML43() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost>123<a></a></cost>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find cost end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML44() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost>123</cost>" +
            "<a></a>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find transaction-history end tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML45() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost>123</cost>" +
            "</transaction-history>abc" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid characters after tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML46() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost>123</cost>" +
            "</transaction-history><abc></abc>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find transaction-history tag.", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioXML47() {
    String str = "<portfolio><portfolio-name>a</portfolio-name>" +
            "<stock><stock-symbol>GOOG</stock-symbol>" +
            "<transaction-history>" +
            "<date>2019-02-28</date>" +
            "<sell>false</sell>" +
            "<volume>1234</volume>" +
            "<cost>123</cost>" +
            "</transaction-history>" +
            "</stock><abc></abc>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    try {
      parser.parse();
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Not find stock tag.", e.getMessage());
    }
  }

  @Test
  public void testValid1() {
    String str = "<portfolio><portfolio-name> name\t</portfolio-name>" +
            "<stock><stock-symbol>  GOOG  </stock-symbol>" +
            "<transaction-history>" +
            "<date>\t 2019-02-28 \t</date>" +
            "<sell> false </sell>" +
            "<volume> 1234 </volume>" +
            "<cost> 123 </cost>" +
            "</transaction-history>" +
            "</stock>" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    IPortfolio portfolio = parser.parse();
    assertEquals("name", portfolio.getPortfolioName());
    List<IStock> stockList = portfolio.getPortfolioState();
    assertEquals(1, stockList.size());
    assertEquals("GOOG", stockList.get(0).getStockSymbol());
    assertEquals("2019-02-28",
            stockList.get(0).getTransactionHistory().get(0).getDate().toString());
    assertEquals(false, stockList.get(0).getTransactionHistory().get(0).isSell());
    assertEquals(1234,
            stockList.get(0).getTransactionHistory().get(0).getNumber(), 1e-6);
    assertEquals(123,
            stockList.get(0).getTransactionHistory().get(0).getCost(), 1e-6);
  }

  @Test
  public void testValid2() {
    String str = "<portfolio>\n" +
            "\t<portfolio-name>p2</portfolio-name>\n" +
            "\t<stock>\n" +
            "\t\t<stock-symbol>GOOG</stock-symbol>\n" +
            "\t\t<transaction-history>\n" +
            "\t\t\t<date>2019-03-11</date>\n" +
            "\t\t\t<sell>false</sell>\n" +
            "\t\t\t<volume>123.0</volume>\n" +
            "\t\t\t<cost>100.0</cost>\n" +
            "\t\t</transaction-history>\n" +
            "\t\t<transaction-history>\n" +
            "\t\t\t<date>2019-03-12</date>\n" +
            "\t\t\t<sell>false</sell>\n" +
            "\t\t\t<volume>321.0</volume>\n" +
            "\t\t\t<cost>100.0</cost>\n" +
            "\t\t</transaction-history>\n" +
            "\t</stock>\n" +
            "\t<stock>\n" +
            "\t\t<stock-symbol>APPL</stock-symbol>\n" +
            "\t\t<transaction-history>\n" +
            "\t\t\t<date>2019-03-11</date>\n" +
            "\t\t\t<sell>false</sell>\n" +
            "\t\t\t<volume>64.0</volume>\n" +
            "\t\t\t<cost>100.0</cost>\n" +
            "\t\t</transaction-history>\n" +
            "\t\t<transaction-history>\n" +
            "\t\t\t<date>2019-03-12</date>\n" +
            "\t\t\t<sell>false</sell>\n" +
            "\t\t\t<volume>89.0</volume>\n" +
            "\t\t\t<cost>100.0</cost>\n" +
            "\t\t</transaction-history>\n" +
            "\t</stock>\n" +
            "</portfolio>";
    XMLParser parser = new XMLParser(str);
    IPortfolio portfolio = parser.parse();
    assertEquals("p2", portfolio.getPortfolioName());
    assertEquals(597, portfolio.getStockNumber(), 1e-6);
    assertEquals(59704.0, portfolio.getStockCost(1), 1e-6);
    assertEquals(0, portfolio.getStockCostByDate(
            LocalDate.of(2018, 1, 1), 1), 1e-6);
  }
}