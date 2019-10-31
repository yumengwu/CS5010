package tradestock.model.tradeoperation;

/**
 * This interface represent a TradeOperation builder.
 */
public interface TradeOperationBuilder {
  /**
   * Return a TradeOperation object.
   * @return a TradeOperation object
   */
  <K> TradeOperation<K> build();
}
