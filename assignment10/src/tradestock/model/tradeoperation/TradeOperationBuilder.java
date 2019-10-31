package tradestock.model.tradeoperation;

import tradestock.model.getprice.IGetPrice;

/**
 * This interface represent a TradeOperation builder.
 */
public interface TradeOperationBuilder {
  /**
   * Return a TradeOperation object.
   * @return a TradeOperation object
   */
  <K> TradeOperation<K> build();

  /**
   * Set API object. If API is null, this method will throw IllegalArgumentException.
   * @param api given API object
   * @return this object
   */
  TradeOperationBuilder setAPI(IGetPrice api);

  /**
   * Set commission fee. If commission fee is negative, this method will throw
   * IllegalArgumentException.
   * @param fee commission fee
   * @return this object
   */
  TradeOperationBuilder setCommissionFee(double fee);
}
