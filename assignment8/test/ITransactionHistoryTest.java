import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.time.LocalDate;

import tradestock.model.transactionhistory.ITransactionHistory;
import tradestock.model.transactionhistory.TransactionHistory;

/**
 * A JUnit test class for ITransactionHistory interface.
 */
public class ITransactionHistoryTest {
  @Test
  public void testValidConstruct() {
    ITransactionHistory transaction = new TransactionHistory(
            LocalDate.of(2018, 4, 1), false, 10.5, 10);
    assertEquals("2018-04-01", transaction.getDate().toString());
    assertEquals(false, transaction.isSell());
    assertEquals(10.5, transaction.getNumber(), 1e-6);
    assertEquals(10.0, transaction.getCost(), 1e-6);
  }

  @Test
  public void testInvalidConstruct1() {
    ITransactionHistory transaction;
    try {
      transaction = new TransactionHistory(null, false, 10, 10);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Date cannot be null.", e.getMessage());
    }
  }

  @Test
  public void testInvalidConstruct2() {
    ITransactionHistory transaction;
    try {
      transaction = new TransactionHistory(LocalDate.of(2018, 1, 1),
              false, -10, 10);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Number must be positive.", e.getMessage());
    }
  }

  @Test
  public void testInvalidConstruct3() {
    ITransactionHistory transaction;
    try {
      transaction = new TransactionHistory(LocalDate.of(2018, 1, 1),
              false, 10, -10);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("Cost must be positive.", e.getMessage());
    }
  }
}