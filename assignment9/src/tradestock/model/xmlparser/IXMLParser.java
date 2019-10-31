package tradestock.model.xmlparser;

import tradestock.model.portfolio.IPortfolio;

/**
 * This interface represent an IXMLParser. An IXMLParser could return IPortfolio
 * object after parsing input string.
 */
public interface IXMLParser {
  /**
   * Return IPortfolio object after parsing input string.
   * @return IPortfolio object
   */
  IPortfolio parse();
}
