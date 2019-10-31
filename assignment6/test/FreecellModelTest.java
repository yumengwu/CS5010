import freecell.model.FreecellOperations;
import freecell.model.FreecellModel;
import freecell.model.PileType;
import freecell.model.Card;
import freecell.model.Suit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A JUnit test class for FreecellOperations interface.
 */
public class FreecellModelTest {
  private FreecellOperations<Card> test1;
  private FreecellOperations<Card> test2;
  private FreecellOperations<Card> test3;
  private FreecellOperations<Card> test4;

  @Before
  public void setUp() {
    test1 = FreecellModel.getBuilder().build();
    test3 = FreecellModel.getBuilder().build();
    List<Card> deck = new ArrayList<>();
    for (int i = 13; i >= 1; i--) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    test4 = FreecellModel.getBuilder().cascades(4).opens(4).build();
    assertEquals(false, test4.isGameOver());
    test4.startGame(deck, false);
  }

  @Test
  public void testValidBuilder() {
    test2 = FreecellModel.getBuilder().opens(9).cascades(12).build();
    assertEquals("", test2.getGameState());
    List<Card> deck = test2.getDeck();
    test2.startGame(deck, false);
    String expect = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n" + "O5:\n"
            + "O6:\n" + "O7:\n" + "O8:\n" + "O9:\n"
            + "C1: A♣, 4♣, 7♣, 10♣, K♣\n"
            + "C2: A♦, 4♦, 7♦, 10♦, K♦\n"
            + "C3: A♥, 4♥, 7♥, 10♥, K♥\n"
            + "C4: A♠, 4♠, 7♠, 10♠, K♠\n"
            + "C5: 2♣, 5♣, 8♣, J♣\n"
            + "C6: 2♦, 5♦, 8♦, J♦\n"
            + "C7: 2♥, 5♥, 8♥, J♥\n"
            + "C8: 2♠, 5♠, 8♠, J♠\n"
            + "C9: 3♣, 6♣, 9♣, Q♣\n"
            + "C10: 3♦, 6♦, 9♦, Q♦\n"
            + "C11: 3♥, 6♥, 9♥, Q♥\n"
            + "C12: 3♠, 6♠, 9♠, Q♠";
    assertEquals(expect, test2.getGameState());
  }

  @Test
  public void testInvalidBuilder() {
    try {
      test2 = FreecellModel.getBuilder().opens(0).cascades(8).build();
      fail("test2 open pile size is less than minimum");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Minimum of open number is 1", e.getMessage());
    }

    try {
      test2 = FreecellModel.getBuilder().opens(2).cascades(3).build();
      fail("test cascade pile size is less than minimum");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Minimum of cascade number is 4", e.getMessage());
    }
  }

  @Test
  public void testGetDeck() {
    List<Card> expectDeck = new ArrayList<>();
    for (int i = 1; i <= 13; i++) {
      for (Suit suit : Suit.values()) {
        expectDeck.add(new Card(i, suit));
      }
    }
    if (expectDeck.size() != test1.getDeck().size()) {
      fail("deck size is different from the expect deck size");
    }
    else {
      for (int i = 0; i < expectDeck.size(); i++) {
        assertEquals(expectDeck.get(i).toString(), test1.getDeck().get(i).toString());
      }
    }
  }

  @Test
  public void testStartGameNoShuffle() {
    List<Card> deck = test3.getDeck();
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    test3.startGame(deck, false);
    assertEquals(expected, test3.getGameState());
  }

  @Test
  public void testStartGameAfterMoveNoShuffle() {
    List<Card> deck = test3.getDeck();
    test3.startGame(deck, false);
    test3.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    test3.move(PileType.CASCADE, 1,6, PileType.OPEN, 1);
    test3.startGame(deck, false);
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    assertEquals(expected, test3.getGameState());
  }

  @Test
  public void testInvalidStartGame() {
    List<Card> deck = new ArrayList<>();
    List<Card> deck1 = new ArrayList<>();
    try {
      test3.startGame(deck, false);
      fail("cannot start game with an empty deck");
    }
    catch (IllegalArgumentException e) {
      /**
       * start game with an empty deck without shuffle.
       */
      assertEquals("A deck must contain 52 cards", e.getMessage());
    }
    try {
      test3.startGame(deck, true);
      fail("cannot start game with an empty deck");
    }
    catch (IllegalArgumentException e) {
      /**
       * start game with an empty deck with shuffle.
       */
      assertEquals("A deck must contain 52 cards", e.getMessage());
    }
    for (int i = 1; i < 13; i ++) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    try {
      test3.startGame(deck, false);
      fail("Invalid deck");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid deck, have invalid cards.
       */
      assertEquals("A deck must contain 52 cards", e.getMessage());
    }
    try {
      test3.startGame(deck, true);
      fail("Invalid deck");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid deck, have invalid cards.
       */
      assertEquals("A deck must contain 52 cards", e.getMessage());
    }
    deck1.add(new Card(1, Suit.diamonds));
    deck1.add(new Card(2, Suit.spades));
    try {
      test3.startGame(deck, false);
      fail("Invalid deck");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid deck, don't have enough cards
       */
      assertEquals("A deck must contain 52 cards", e.getMessage());
    }
  }

  @Test
  public void testMoveFromCascadeToOpen() {
    List<Card> deck = test3.getDeck();
    test3.startGame(deck, false);
    test3.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    test3.move(PileType.CASCADE, 1,6, PileType.OPEN, 0);
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1: K♦\n" + "O2: K♣\n" + "O3:\n" + "O4:\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    assertEquals(expected, test3.getGameState());
    test3.move(PileType.CASCADE, 2, 6, PileType.OPEN, 2);
    test3.move(PileType.CASCADE, 3,6, PileType.OPEN, 3);
    try {
      test3.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
      fail("Open pile is occupied, cannot move to there");
    }
    catch (IllegalArgumentException e ) {
      /**
       *
       */
      assertEquals("Move card fail", e.getMessage());
    }
  }

  @Test
  public void testMoveFromCascadeToCascade() {
    List<Card> deck = test3.getDeck();
    test3.startGame(deck, false);
    test3.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣, Q♦\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    assertEquals(expected, test3.getGameState());
    try {
      test3.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 3);
      fail("Cannot move the card next to a card that has the same color");
    }
    catch (IllegalArgumentException e) {
      /**
       * Have same color, right value.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 5);
      fail("Cannot move the card next to a card that value is not great than it");
    }
    catch (IllegalArgumentException e) {
      /**
       * different color, wrong value.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 6);
      fail("cannot move the card");
    }
    catch (IllegalArgumentException e) {
      /**
       *  same color wrong value.
       */
      assertEquals("Move card fail", e.getMessage());
    }
  }

  @Test
  public void testMoveFromCascadeToCascadeDestinationEmpty() {

    for (int i = 12; i >= 0; i--) {
      test4.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
    }
    test4.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 2);
    test4.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 0);
    String expect = "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F2:\n" + "F3: A♦\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: 2♦\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test4.getGameState());
  }

  @Test
  public void testMoveFromCascadeToFoundation() {
    List<Card> deck = test1.getDeck();
    test3.startGame(deck, false);
    test3.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    test3.move(PileType.CASCADE, 0, 5, PileType.OPEN, 2);
    test3.move(PileType.OPEN, 2, 0, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 6, 5, PileType.CASCADE, 3);
    test3.move(PileType.CASCADE, 6, 4, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 6, 3, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 6, 2, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 0, 2, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 6, 1, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 0, 1, PileType.CASCADE, 5);
    test3.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    test3.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 0);
    test3.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 1);
    String expected = "F1:\n" + "F2: A♣\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2: K♣\n" + "O3:\n" + "O4:\n"
            + "C1:\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠, Q♥\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦, J♣, 10♥, 9♣, 8♥, 7♣, 6♥, 5♣, 4♥, 3♣\n"
            + "C7: 2♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    assertEquals(expected, test3.getGameState());
    test3.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    test3.move(PileType.CASCADE, 1, 5, PileType.OPEN, 2);
    test3.move(PileType.OPEN, 2, 0, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 4, 5, PileType.OPEN, 2);
    test3.move(PileType.CASCADE, 4, 4, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 1, 4, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 4, 3, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 1, 3, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 4, 2, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 4, 1, PileType.CASCADE, 7);
    test3.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 1);
    String expected1 = "F1:\n" + "F2: A♣, 2♣\n" + "F3:\n" + "F4:\n"
            + "O1: K♦\n" + "O2: K♣\n" + "O3: Q♣\n" + "O4:\n"
            + "C1:\n"
            + "C2: A♦, 3♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠, Q♥\n"
            + "C5:\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦, J♣, 10♥, 9♣, 8♥, 7♣, 6♥, 5♣, 4♥, 3♣\n"
            + "C7: 2♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠, J♦, 10♣, 9♦, 8♣, 7♦, 6♣, 5♦, 4♣";
    assertEquals(expected1, test3.getGameState());

    try {
      test3.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
      fail("Only accept same suit and same number");
    } catch (IllegalArgumentException e) {
      /**
       * correct number, invalid suit.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
      fail("moving different suit card to the foundation");
    }
    catch (IllegalArgumentException e) {
      /**
       * Foundation has 2 club, move 3 diamond to it
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 0);
      fail("first foundation element must be A");
    } catch (IllegalArgumentException e) {
      /**
       * first element add to foundation is not A.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
      fail("The card is not the last element of the list");
    } catch (IllegalArgumentException e) {
      /**
       * Move the second card.
       */
      assertEquals("Illegal source card index", e.getMessage());
    }

  }

  @Test
  public void testMoveFromOpenToCascade() {
    List<Card> deck = test3.getDeck();
    test3.startGame(deck, false);
    test3.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    test3.move(PileType.CASCADE, 0, 5, PileType.OPEN, 2);
    test3.move(PileType.OPEN, 2, 0, PileType.CASCADE, 5);
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2: K♣\n" + "O3:\n" + "O4:\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦, J♣\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    assertEquals(expected, test3.getGameState());
    try {
      test3.move(PileType.OPEN, 1, 0, PileType.CASCADE, 4);
      fail("This card is no less 1 than the current last card on the pile");
    } catch (IllegalArgumentException e) {
      /**
       * same color, wrong value
       */
      assertEquals("Move card fail", e.getMessage());
    }
    test3.move(PileType.CASCADE, 5, 6, PileType.OPEN, 0);
    try {
      test3.move(PileType.OPEN, 0, 0, PileType.CASCADE, 4);
      fail("Two card value matches, but color is the same");
    } catch (IllegalArgumentException e) {
      /**
       * same color, right value.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 0, 0, PileType.CASCADE, 1);
      fail("two cards have different card, but value don't match");
    } catch (IllegalArgumentException e) {
      /**
       * different color, wrong value.
       */
      assertEquals("Move card fail", e.getMessage());
    }
  }

  @Test
  public void testMoveFromOpenToCascadeDestinationEmpty() {
    List<Card> deck = new ArrayList<>();
    for (int i = 13; i >= 1; i--) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    test2 = FreecellModel.getBuilder().cascades(4).opens(4).build();
    assertEquals(false, test4.isGameOver());
    test2.startGame(deck, false);
    for (int i = 12; i >= 0; i--) {
      test2.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
    }
    test2.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
    test2.move(PileType.CASCADE, 1, 11, PileType.OPEN, 1);
    test2.move(PileType.OPEN, 1, 0, PileType.CASCADE, 0);
    String expect = "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1: A♦\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: 2♦\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test2.getGameState());
  }

  @Test
  public void testMoveFromOpenToOpen() {
    List<Card> deck = test3.getDeck();
    test3.startGame(deck, false);
    test3.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    test3.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
    test3.move(PileType.OPEN, 0, 0, PileType.OPEN, 2);
    test3.move(PileType.OPEN, 2, 0, PileType.OPEN, 3);
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2: J♣\n" + "O3:\n" + "O4: K♣\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    assertEquals(expected, test3.getGameState());
    try {
      test3.move(PileType.OPEN, 1, 0, PileType.OPEN, 3);
      fail("This open pile is occupied");
    }
    catch (IllegalArgumentException e) {
      /**
       * move open to open destination is occupied.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 0, 0, PileType.OPEN, 3);
      fail("source pile is empty, have no card to move");
    }
    catch (IllegalArgumentException e) {
      /**
       * source pile is empty, have no card to move
       */
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, -1, 0, PileType.OPEN, 3);
      fail("source pile number is invalid");
    }
    catch (IllegalArgumentException e) {
      /**
       * source pile number is less than 0
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 8, 0, PileType.OPEN, 3);
      fail("source pile number is invalid");
    }
    catch (IllegalArgumentException e) {
      /**
       * source pile number is greater than its size
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 1, 3, PileType.OPEN, 3);
      fail("open source pile index is invalid");
    }
    catch (IllegalArgumentException e) {
      /**
       * open source pile index is not 0
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 1, -3, PileType.OPEN, 3);
      fail("open source pile index is invalid");
    }
    catch (IllegalArgumentException e) {
      /**
       * open source pile index is greater than 0
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 1, 0, PileType.OPEN, -3);
      fail("open to open destination pile number is invalid");
    }
    catch (IllegalArgumentException e) {
      /**
       * open to open destination pile number is invalid.
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test3.move(PileType.OPEN, 1, 0, PileType.OPEN, 30);
      fail("open to open destination pile number is invalid, greater than its size");
    }
    catch (IllegalArgumentException e) {
      /**
       * open to open destination pile number is invalid, greater than its size.
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }

  }

  @Test
  public void testMoveFromOpenToFoundation() {
    test2 = FreecellModel.getBuilder().opens(8).cascades(12).build();
    List<Card> deck = test2.getDeck();
    test2.startGame(deck, false);
    test2.move(PileType.CASCADE, 0,4, PileType.OPEN, 0);
    test2.move(PileType.CASCADE, 0,3, PileType.OPEN, 1);
    test2.move(PileType.CASCADE, 0,2, PileType.OPEN, 2);
    test2.move(PileType.CASCADE, 0,1, PileType.OPEN, 3);
    test2.move(PileType.CASCADE, 0,0, PileType.OPEN, 4);
    test2.move(PileType.OPEN, 4,0, PileType.FOUNDATION, 0);
    test2.move(PileType.CASCADE, 4,3, PileType.OPEN, 4);
    test2.move(PileType.CASCADE, 4,2, PileType.OPEN, 5);
    test2.move(PileType.CASCADE, 4,1, PileType.OPEN, 6);
    test2.move(PileType.CASCADE, 4,0, PileType.OPEN, 7);
    test2.move(PileType.OPEN, 7,0, PileType.FOUNDATION, 0);
    String expect = "F1: A♣, 2♣\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1: K♣\n" + "O2: 10♣\n" + "O3: 7♣\n" + "O4: 4♣\n"
            + "O5: J♣\n" + "O6: 8♣\n" + "O7: 5♣\n"
            + "O8:\n"
            + "C1:\n"
            + "C2: A♦, 4♦, 7♦, 10♦, K♦\n"
            + "C3: A♥, 4♥, 7♥, 10♥, K♥\n"
            + "C4: A♠, 4♠, 7♠, 10♠, K♠\n"
            + "C5:\n"
            + "C6: 2♦, 5♦, 8♦, J♦\n"
            + "C7: 2♥, 5♥, 8♥, J♥\n"
            + "C8: 2♠, 5♠, 8♠, J♠\n"
            + "C9: 3♣, 6♣, 9♣, Q♣\n"
            + "C10: 3♦, 6♦, 9♦, Q♦\n"
            + "C11: 3♥, 6♥, 9♥, Q♥\n"
            + "C12: 3♠, 6♠, 9♠, Q♠";
    assertEquals(expect, test2.getGameState());
    try {
      test2.move(PileType.OPEN, 7,0, PileType.FOUNDATION, 0);
      fail("open to foundation, no card in open pile");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, no card in open pile.
       */
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 0,0, PileType.FOUNDATION, 0);
      fail("open to foundation, card does not match");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, card does not match.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 0,0, PileType.FOUNDATION, 1);
      fail("open to foundation, first foundation must be A");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, first foundation must be A.
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, -1,0, PileType.FOUNDATION, 1);
      fail("open to foundation, source pile invalid less than 0");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, source pile invalid less than 0.
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 20,0, PileType.FOUNDATION, 1);
      fail("open to foundation, source pile invalid greater than its size");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, source pile greater than its size.
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 1,3, PileType.FOUNDATION, 1);
      fail("open to foundation, card Index invalid");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, card Index invalid.
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 1,0, PileType.FOUNDATION, -1);
      fail("open to foundation, destination pile num invalid less than 0");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, destination pile num invalid less than 0.
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 1,0, PileType.FOUNDATION, 20);
      fail("open to foundation, destination pile num invalid greater than its size");
    }
    catch (IllegalArgumentException e) {
      /**
       * Open to foundation, destination pile num invalid greater than its size;
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
  }

  @Test
  public void testMoveWithOutBoundArguments() {
    List<Card> deck = test3.getDeck();
    test3.startGame(deck, false);
    try {
      test3.move(PileType.CASCADE, -1, 1, PileType.OPEN, 1);
      fail("move from cascade to open, contains invalid origin pile number");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid pile number move from cascade to open.
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 1, 6, PileType.OPEN, -1);
      fail("move from cascade to open,contains invalid destination pile number");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid pile number move from cascade to open.
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 1, -1, PileType.OPEN, 1);
      fail("move from cascade to open,contains invalid index");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid index.move from cascade to open.
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, -1, 1, PileType.FOUNDATION, 1);
      fail("contains invalid origin pile number, move from cascade to foundation");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid pile number, move from cascade to foundation
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, -1);
      fail("contains invalid destination pile number, move from cascade to foundation");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid pile number, move from cascade to foundation
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 1, -1, PileType.FOUNDATION, 1);
      fail("contains invalid card index, move from cascade to foundation");
    }
    catch (IllegalArgumentException e) {
      /**
       * Invalid card index.move from cascade to foundation
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
  }

  @Test
  public void testMoveBeforeGameStart() {
    try {
      test3.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
      fail("the game have not started yet, cannot move");
    }
    catch (IllegalStateException e) {
      /**
       * Move from cascade to open before game start.
       */
      assertEquals("Move before game start", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 6, 5, PileType.CASCADE, 0);
      fail("the game have not started yet, cannot move");
    }
    catch (IllegalStateException e) {
      /**
       * Move from cascade to open before game start.
       */
      assertEquals("Move before game start", e.getMessage());
    }
    try {
      test3.move(PileType.CASCADE, 0, 1, PileType.OPEN, 1);
      fail("the game have not started yet, cannot move");
    }
    catch (IllegalStateException e) {
      /**
       * Move from cascade to open before game start.(Invalid move anyway)
       */
      assertEquals("Move before game start", e.getMessage());
    }
  }

  @Test
  public void testGetGameStatus() {
    assertEquals("", test3.getGameState());
    List<Card> deck = test3.getDeck();
    String expected = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
            + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
            + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
            + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
            + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
            + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
            + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
            + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠";
    test3.startGame(deck, false);
    assertEquals(expected, test3.getGameState());
  }

  @Test
  public void testMoveFoundationToOpen() {

    test4.move(PileType.CASCADE, 0,12, PileType.FOUNDATION, 0);
    test4.move(PileType.CASCADE, 1,12, PileType.FOUNDATION, 1);
    test4.move(PileType.CASCADE, 1,11, PileType.FOUNDATION, 1);
    test4.move(PileType.FOUNDATION, 0,0, PileType.OPEN, 1);
    String expect = "F1:\n" + "F2: A♦, 2♦\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2: A♣\n" + "O3:\n" + "O4:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test4.getGameState());
    try {
      test4.move(PileType.FOUNDATION, 1,1, PileType.OPEN, 1);
      fail("foundation to open, Open file is occupied");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, open file is occupied
       */
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1,5, PileType.OPEN, 1);
      fail("foundation to open, card Index is invalid greater than its size");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, card Index
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0,0, PileType.OPEN, 1);
      fail("foundation to open, source pile is empty");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, source pile is empty
       */
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1,0, PileType.OPEN, 1);
      fail("foundation to open, card Index is not valid, not the last element of this pile");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, card Index is not valid, not the last element of this pile
       */
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1,1, PileType.OPEN, 20);
      fail("foundation to open, destination pile number is not invalid greater than its size");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, destination pile number is not invalid greater than its size
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1,1, PileType.OPEN, -3);
      fail("foundation to open, destination pile number is not invalid less than 0");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, destination pile number is not invalid less than 0
       */
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, -1,1, PileType.OPEN, -3);
      fail("foundation to open, source pile number is not invalid less than 0");
    }
    catch (IllegalArgumentException e) {
      /**
       * foundation to open, source pile number is not invalid less than 0
       */
      assertEquals("Illegal source pile number", e.getMessage());
    }
  }

  @Test
  public void testMoveFoundationToCascade() {
    test4.move(PileType.CASCADE, 0,12, PileType.FOUNDATION, 0);
    test4.move(PileType.CASCADE, 1,12, PileType.FOUNDATION, 1);
    test4.move(PileType.FOUNDATION, 0,0, PileType.CASCADE, 1);
    String expect = "F1:\n" + "F2: A♦\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♣\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test4.getGameState());
    try {
      test4.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 0);
      fail("foundation to cascade, the card that be moved is not the last element of source pile");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1, 0, PileType.CASCADE, 2);
      fail("foundation to cascade, the card doesn't match the last one at destination pile");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 1);
      fail("foundation to cascade, source pile is empty");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, -1, 0, PileType.CASCADE, 1);
      fail("foundation to cascade, invalid source pile number, less than 0");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 50, 0, PileType.CASCADE, 1);
      fail("foundation to cascade, invalid source pile number, greater than its size");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0, -1, PileType.CASCADE, 1);
      fail("foundation to cascade, invalid card index number, less than 0");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0, 20, PileType.CASCADE, 1);
      fail("foundation to cascade, invalid card index number, greater than its size");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1, 0, PileType.CASCADE, -1);
      fail("foundation to cascade, invalid destination pile number, less than 0");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1, 0, PileType.CASCADE, 100);
      fail("foundation to cascade, invalid destination pile number, greater than size");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal destination pile number", e.getMessage());
    }
  }

  @Test
  public void testMoveFoundationToCascadeDestinationEmpty() {
    for (int i = 12; i >= 0; i--) {
      test4.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
    }
    test4.move(PileType.CASCADE, 1, 12, PileType.FOUNDATION, 1);
    test4.move(PileType.FOUNDATION, 1, 0, PileType.CASCADE, 0);
    String expect = "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: A♦\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test4.getGameState());
  }

  @Test
  public void testMoveFoundationToFoundation() {

    test4.move(PileType.CASCADE, 0,12, PileType.FOUNDATION, 0);
    test4.move(PileType.CASCADE, 0,11, PileType.FOUNDATION, 0);
    test4.move(PileType.CASCADE, 1,12, PileType.FOUNDATION, 1);
    test4.move(PileType.FOUNDATION, 1,0, PileType.FOUNDATION, 2);
    String expect = "F1: A♣, 2♣\n" + "F2:\n" + "F3: A♦\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test4.getGameState());
    try {
      test4.move(PileType.FOUNDATION, 0,1, PileType.FOUNDATION, 2);
      fail("foundation to foundation, two cards don't have same suit");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0,1, PileType.FOUNDATION, 1);
      fail("foundation to foundation, first card at foundation card must be A");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Move card fail", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0,0, PileType.FOUNDATION, 1);
      fail("foundation to foundation, card be moved not the last element of the source pile");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 1,0, PileType.FOUNDATION, 1);
      fail("foundation to foundation, source pile doesn't have any card");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Source pile is empty", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, -1,0, PileType.FOUNDATION, 1);
      fail("foundation to foundation, invalid source pile number, less than 0");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 20,0, PileType.FOUNDATION, 1);
      fail("foundation to foundation, invalid source pile number, greater than its size");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal source pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0,-1, PileType.FOUNDATION, 1);
      fail("foundation to foundation, invalid card index number, less than 0");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal source card index", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0,1, PileType.FOUNDATION,-1);
      fail("foundation to foundation, invalid destination pile number, less than 0");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal destination pile number", e.getMessage());
    }
    try {
      test4.move(PileType.FOUNDATION, 0,1, PileType.FOUNDATION,50);
      fail("foundation to foundation, invalid destination pile number, greater than its size");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Illegal destination pile number", e.getMessage());
    }
  }

  @Test
  public void testIsGameOver() {
    List<Card> deck = new ArrayList<>();
    for (int i = 13; i >= 1; i--) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    test2 = FreecellModel.getBuilder().cascades(4).opens(4).build();
    assertEquals(false, test2.isGameOver());
    test2.startGame(deck, false);
    for (int i = 12; i >= 0; i--) {
      test2.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
    }
    assertEquals(false, test2.isGameOver());
    for (int i = 12; i >= 0; i--) {
      test2.move(PileType.CASCADE, 1, i, PileType.FOUNDATION, 1);
      test2.move(PileType.CASCADE, 2, i, PileType.FOUNDATION, 2);
      test2.move(PileType.CASCADE, 3, i, PileType.FOUNDATION, 3);
    }
    assertEquals(true, test2.isGameOver());
  }

  @Test
  public void testMoveAfterGameOver() {
    List<Card> deck = new ArrayList<>();
    for (int i = 13; i >= 1; i--) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    test2 = FreecellModel.getBuilder().cascades(4).opens(4).build();
    test2.startGame(deck, false);
    for (int i = 12; i >= 0; i--) {
      test2.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
      test2.move(PileType.CASCADE, 1, i, PileType.FOUNDATION, 1);
      test2.move(PileType.CASCADE, 2, i, PileType.FOUNDATION, 2);
      test2.move(PileType.CASCADE, 3, i, PileType.FOUNDATION, 3);
    }
    assertEquals(true, test2.isGameOver());
    try {
      test2.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 0, 6, PileType.OPEN, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 0, 6, PileType.CASCADE, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.OPEN, 0, 6, PileType.FOUNDATION, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.FOUNDATION, 0, 6, PileType.OPEN, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.FOUNDATION, 0, 6, PileType.CASCADE, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
    try {
      test2.move(PileType.FOUNDATION, 0, 6, PileType.FOUNDATION, 1);
      fail("Game is over, cannot move");
    }
    catch (IllegalStateException e) {
      assertEquals("Move after game over", e.getMessage());
    }
  }

  @Test
  public void testStartGameAfterGameOver() {
    for (int i = 12; i >= 0; i--) {
      test4.move(PileType.CASCADE, 0, i, PileType.FOUNDATION, 0);
      test4.move(PileType.CASCADE, 1, i, PileType.FOUNDATION, 1);
      test4.move(PileType.CASCADE, 2, i, PileType.FOUNDATION, 2);
      test4.move(PileType.CASCADE, 3, i, PileType.FOUNDATION, 3);
    }
    assertEquals(true, test4.isGameOver());
    List<Card> deck = new ArrayList<>();
    for (int i = 13; i >= 1; i--) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    test4.startGame(deck, false);
    String expect = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n"
            + "O1:\n" + "O2:\n" + "O3:\n" + "O4:\n"
            + "C1: K♣, Q♣, J♣, 10♣, 9♣, 8♣, 7♣, 6♣, 5♣, 4♣, 3♣, 2♣, A♣\n"
            + "C2: K♦, Q♦, J♦, 10♦, 9♦, 8♦, 7♦, 6♦, 5♦, 4♦, 3♦, 2♦, A♦\n"
            + "C3: K♥, Q♥, J♥, 10♥, 9♥, 8♥, 7♥, 6♥, 5♥, 4♥, 3♥, 2♥, A♥\n"
            + "C4: K♠, Q♠, J♠, 10♠, 9♠, 8♠, 7♠, 6♠, 5♠, 4♠, 3♠, 2♠, A♠";
    assertEquals(expect, test4.getGameState());
  }
}
