import freecell.model.Card;
import freecell.model.Suit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

/**
 * A JUnit test class for Card.
 */
public class CardTest {
  private Card card1;
  private Card card2;
  private Card card3;
  private Card card4;
  private Card card5;
  private Card card6;
  private Card card7;
  private Card card8;
  private Card card9;
  private Card card10;
  private Card card11;
  private Card card12;
  private Card card13;
  ArrayList<Card> deck;

  @Before
  public void setUp() {
    card1 = new Card(1, Suit.spades);
    card2 = new Card(2, Suit.hearts);
    card3 = new Card(3, Suit.diamonds);
    card4 = new Card(4, Suit.clubs);
    card5 = new Card(5, Suit.spades);
    card6 = new Card(6, Suit.hearts);
    card7 = new Card(7, Suit.diamonds);
    card8 = new Card(8, Suit.clubs);
    card9 = new Card(9, Suit.spades);
    card10 = new Card(10, Suit.hearts);
    card11 = new Card(11, Suit.diamonds);
    card12 = new Card(12, Suit.clubs);
    card13 = new Card(13, Suit.spades);

    deck = new ArrayList<Card>();
    for (int i = 1; i < 14; i++) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
  }

  @Test
  public void testCard() {
    assertEquals("A♠", card1.toString());
    assertEquals("2♥", card2.toString());
    assertEquals("3♦", card3.toString());
    assertEquals("4♣", card4.toString());
    assertEquals("5♠", card5.toString());
    assertEquals("6♥", card6.toString());
    assertEquals("7♦", card7.toString());
    assertEquals("8♣", card8.toString());
    assertEquals("9♠", card9.toString());
    assertEquals("10♥", card10.toString());
    assertEquals("J♦", card11.toString());
    assertEquals("Q♣", card12.toString());
    assertEquals("K♠", card13.toString());
  }

  @Test
  public void invalidCardSetUp() {
    try {
      Card card14 = new Card(-4, Suit.clubs);
      fail("cannot construct a card with minus number");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid card number", e.getMessage());
    }

    try {
      Card card15 = new Card(14, Suit.spades);
      fail("cannot construct a card with number greater than 13");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Invalid card number", e.getMessage());
    }
  }
}
