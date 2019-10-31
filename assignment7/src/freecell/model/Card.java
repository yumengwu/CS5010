package freecell.model;

/**
 * This class represent a card object. A card could get value, get suit, and get color.
 */
public class Card {
  private int value;
  private Suit suit;
  private SuitColor color;

  /**
   * Construct a Card object by given value and suit. If value is invalid, this method
   * will throw IllegalArgumentException. A valid card value must be between 1 and 13,
   * represent A, 2, 3,... ,10, J, Q, K
   * @param value card value
   * @param suit card suit
   */
  public Card(int value, Suit suit) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Invalid card number");
    }
    this.value = value;
    this.suit = suit;
    if (this.suit == Suit.hearts || this.suit == Suit.diamonds) {
      this.color = SuitColor.red;
    }
    else {
      this.color = SuitColor.black;
    }
  }

  /**
   * Return card value.
   * @return card value
   */
  public final int getValue() {
    return this.value;
  }

  /**
   * Return card suit.
   * @return card suit
   */
  public final Suit getSuit() {
    return this.suit;
  }

  /**
   * Return card color.
   * @return card color
   */
  public final SuitColor getColor() {
    return this.color;
  }

  /**
   * Return a string to represent this card.
   * @return a string to represent this card
   */
  public String toString() {
    String str = "";
    switch (this.value) {
      case 1:
        str += "A";
        break;
      case 11:
        str += "J";
        break;
      case 12:
        str += "Q";
        break;
      case 13:
        str += "K";
        break;
      default:
        str += this.value;
        break;
    }
    switch (this.suit) {
      case clubs:
        str += "♣";
        break;
      case diamonds:
        str += "♦";
        break;
      case hearts:
        str += "♥";
        break;
      case spades:
        str += "♠";
        break;
      default:
        break;
    }
    return str;
  }
}
