import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.IGetPrice;

/**
 * A JUnit test class for IGetPrice interface.
 */
public class IGetPriceTest {
  @Test
  public void test1() {
    IGetPrice getPrice = new GetPrice();
    assertEquals(1184.4600, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,15)), 1e-6);
    assertEquals(1185.5500, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,14)), 1e-6);
    assertEquals(1175.7600, getPrice.getPrice("GOOG",
            LocalDate.of(2019,3,11)), 1e-6);
  }
}