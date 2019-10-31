package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements methods in FreecellOperations interface. A Freecell game model
 * object could get a list of deck, start game, move a card from one pile to another, check
 * if game is over, and get game state.
 */
public class FreecellModel implements FreecellOperations<Card> {
  private int openNumber;
  private int cascadeNumber;
  private int foundationNumber = 4;
  private List<ArrayList<Card>> openPile;
  private List<ArrayList<Card>> cascadePile;
  private List<ArrayList<Card>> foundationPile;

  private int deckSize = 52;
  private boolean isStart;

  /**
   * Private helper method to initialize a pile list by given number. Result will be a list
   * of ArrayLists.
   * @param number number of pile list
   * @return an pile list with number empty ArrayLists
   */
  private List<ArrayList<Card>> initializePileList(int number) {
    List<ArrayList<Card>> pile = new ArrayList<>();
    for (int i = 0; i < number; ++i) {
      pile.add(new ArrayList<>());
    }
    return pile;
  }

  /**
   * Construct a FreecellModel object with open pile and cascade number. This constructor
   * is private and could be called by builder.
   * @param open open pile number
   * @param cascade cascade pile number
   */
  private FreecellModel(int open, int cascade) {
    this.openNumber = open;
    this.cascadeNumber = cascade;
    this.openPile = initializePileList(this.openNumber);
    this.cascadePile = initializePileList(this.cascadeNumber);
    this.foundationPile = initializePileList(this.foundationNumber);
    this.isStart = false;
  }

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is
   * no restriction imposed on the ordering of these cards in the deck. An
   * invalid deck is defined as a deck that has one or more of these flaws:
   * <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @return the deck of cards as a list
   */
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int i = 1; i <= 13; ++i) {
      for (Suit s : Suit.values()) {
        deck.add(new Card(i, s));
      }
    }
    return deck;
  }

  /**
   * Deal a new game of freecell with the given deck, with or without shuffling
   * it first. This method first verifies that the deck is valid. It deals the
   * deck among the cascade piles in roundrobin fashion. Thus if there are 4
   * cascade piles, the 1st pile will get cards 0, 4, 8, ..., the 2nd pile will
   * get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10, ... and the
   * 4th pile will get cards 3, 7, 11, .... Depending on the number of cascade
   * piles, they may have a different number of cards
   *
   * @param deck    the deck to be dealt
   * @param shuffle if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */
  public void startGame(List<Card> deck, boolean shuffle) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("deck list cannot be null");
    }
    if (deck.size() != 52) {
      throw new IllegalArgumentException("A deck must contain 52 cards");
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < deck.size(); ++i) {
      Card currentCard = deck.get(i);
      if (currentCard == null) {
        throw new IllegalArgumentException("Card in deck cannot be null");
      }
      if (sb.toString().indexOf(currentCard.toString()) == -1) {
        sb.append(currentCard.toString());
        sb.append(" ");
      }
      else {
        throw new IllegalArgumentException("Card is duplicated");
      }
    }
    this.openPile = initializePileList(this.openNumber);
    this.cascadePile = initializePileList(this.cascadeNumber);
    this.foundationPile = initializePileList(this.foundationNumber);
    if (shuffle) {
      Collections.shuffle(deck);
    }
    for (int i = 0; i < this.cascadeNumber; ++i) {
      int index = i;
      while (index < deck.size()) {
        this.cascadePile.get(i).add(deck.get(index));
        index += this.cascadeNumber;
      }
    }
    this.isStart = true;
  }

  /**
   * Private helper method to check if a card could be moved to open pile. A card
   * could be moved to open pile only if that open pile is empty.
   * @param destPileNumber destination open pile number
   * @return true if destination is empty, or false otherwise
   */
  private boolean checkCardToOpen(int destPileNumber) {
    return this.openPile.get(destPileNumber).size() == 0;
  }

  /**
   * Private helper method to check if a card could be moved to a cascade pile. A card
   * could be moved to a cascade pile only when this cascade pile is empty or this card
   * and the last card in that cascade pile have same color and that card's value is
   * equals input card's plus 1.
   * @param card source card
   * @param destPileNumber cascade pile number
   * @return true if given card could be moved to given cascade pile, or false otherwise
   */
  private boolean checkCardToCascade(Card card, int destPileNumber) {
    ArrayList<Card> cards = this.cascadePile.get(destPileNumber);
    if (cards.size() == 0) {
      return true;
    }
    else {
      Card lastCardInList = cards.get(cards.size() - 1);
      return lastCardInList.getColor() != card.getColor()
              && lastCardInList.getValue() == card.getValue() + 1;
    }
  }

  /**
   * Private helper method to check if a card could be moved to a foundation pile. A card
   * could be moved to foundation pile only when that pile is empty and that card's value
   * is 1(A), or when the last card in pile has the same suit with input card, and the last
   * card's value is equals to input card's value minus 1.
   * @param card source card
   * @param destPileNumber foundation pile number
   * @return true if given card could be moved to given foundation pile, or false otherwise
   */
  private boolean checkCardToFoundation(Card card, int destPileNumber) {
    ArrayList<Card> cards = this.foundationPile.get(destPileNumber);
    if (cards.size() == 0) {
      return card.getValue() == 1;
    }
    else {
      Card lastCard = cards.get(cards.size() - 1);
      return card.getSuit() == lastCard.getSuit() && card.getValue() == lastCard.getValue() + 1;
    }
  }

  /**
   * Private helper method to move card when all the parameters are valid.
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   */
  private void moveCardHelper(PileType source, int pileNumber, int cardIndex,
                              PileType destination, int destPileNumber) {
    ArrayList<Card> src;
    ArrayList<Card> dest;
    if (source == PileType.OPEN) {
      src = this.openPile.get(pileNumber);
    }
    else if (source == PileType.CASCADE) {
      src = this.cascadePile.get(pileNumber);
    }
    else {
      src = this.foundationPile.get(pileNumber);
    }
    if (destination == PileType.OPEN) {
      dest = this.openPile.get(destPileNumber);
    }
    else if (destination == PileType.CASCADE) {
      dest = this.cascadePile.get(destPileNumber);
    }
    else {
      dest = this.foundationPile.get(destPileNumber);
    }
    Card card = src.get(cardIndex);
    dest.add(card);
    src.remove(cardIndex);
  }

  /**
   * Move a card from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link
   *                                  PileType})
   * @throws IllegalStateException    if a move is attempted before the game has
   *                                  starts
   */
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.isStart) {
      throw new IllegalStateException("Move before game start");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Move after game over");
    }
    ArrayList<Card> list;
    /**
     * Check source pile and index.
     */
    if (source == PileType.CASCADE) {
      if (pileNumber < 0 || pileNumber >= this.cascadePile.size()) {
        throw new IllegalArgumentException("Illegal source pile number");
      }
      list = this.cascadePile.get(pileNumber);
    }
    else if (source == PileType.OPEN) {
      if (pileNumber < 0 || pileNumber >= this.openPile.size()) {
        throw new IllegalArgumentException("Illegal source pile number");
      }
      list = this.openPile.get(pileNumber);
    }
    else {
      if (pileNumber < 0 || pileNumber >= this.foundationPile.size()) {
        throw new IllegalArgumentException("Illegal source pile number");
      }
      list = this.foundationPile.get(pileNumber);
    }
    if (list.size() == 0) {
      throw new IllegalArgumentException("Source pile is empty");
    }
    if (cardIndex < 0 || cardIndex != list.size() - 1) {
      throw new IllegalArgumentException("Illegal source card index");
    }
    if (source == destination && pileNumber == destPileNumber) {
      return;
    }
    /**
     * Check destination index.
     */
    if (destination == PileType.OPEN) {
      if (destPileNumber < 0 || destPileNumber >= this.openPile.size()) {
        throw new IllegalArgumentException("Illegal destination pile number");
      }
      if (!(checkCardToOpen(destPileNumber))) {
        throw new IllegalArgumentException("Move card fail");
      }
    }
    else if (destination == PileType.CASCADE) {
      if (destPileNumber < 0 || destPileNumber >= this.cascadePile.size()) {
        throw new IllegalArgumentException("Illegal destination pile number");
      }
      if (!(checkCardToCascade(list.get(cardIndex), destPileNumber))) {
        throw new IllegalArgumentException("Move card fail");
      }
    }
    else {
      if (destPileNumber < 0 || destPileNumber >= this.foundationPile.size()) {
        throw new IllegalArgumentException("Illegal destination pile number");
      }
      if (!(checkCardToFoundation(list.get(cardIndex),
              destPileNumber))) {
        throw new IllegalArgumentException("Move card fail");
      }
    }
    moveCardHelper(source, pileNumber, cardIndex, destination, destPileNumber);
  }

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */
  public boolean isGameOver() {
    int totalInFoundation = 0;
    for (int i = 0; i < this.foundationNumber; ++i) {
      totalInFoundation += this.foundationPile.get(i).size();
    }
    return totalInFoundation == deckSize;
  }

  /**
   * Private helper method to convert a card list to string.
   * @param pileList card list
   * @return result string
   */
  private String pileListToString(ArrayList<Card> pileList) {
    StringBuilder sb = new StringBuilder();
    if (pileList.size() > 0) {
      sb.append(" ");
      sb.append(pileList.get(0).toString());
    }
    if (pileList.size() > 1) {
      for (int i = 1; i < pileList.size(); ++i) {
        sb.append(", ");
        sb.append(pileList.get(i).toString());
      }
    }
    return sb.toString();
  }

  /**
   * Return the present state of the game as a string. The string is formatted
   * as follows:
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in
   * order)
   * O1:[b]o11[n] (Cards in open pile 1)
   * O2:[b]o21[n] (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n] (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n] (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n] (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps (Cards in cascade pile s in order)
   *
   * where [b] is a single blankspace, [n] is newline. Note that there is no
   * newline on the last line
   * </pre>
   *
   * @return the formatted string as above
   */
  public String getGameState() {
    if (!this.isStart) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.foundationNumber; ++i) {
      StringBuilder sbSub = new StringBuilder();
      if (sb.toString().length() != 0) {
        sbSub.append('\n');
      }
      sbSub.append("F" + (i + 1) + ":");
      sbSub.append(pileListToString(this.foundationPile.get(i)));
      sb.append(sbSub.toString());
    }
    for (int i = 0; i < this.openNumber; ++i) {
      StringBuilder sbSub = new StringBuilder();
      if (sb.toString().length() != 0) {
        sbSub.append('\n');
      }
      sbSub.append("O" + (i + 1) + ":");
      sbSub.append(pileListToString(this.openPile.get(i)));
      sb.append(sbSub.toString());
    }
    for (int i = 0; i < this.cascadeNumber; ++i) {
      StringBuilder sbSub = new StringBuilder();
      if (sb.toString().length() != 0) {
        sbSub.append('\n');
      }
      sbSub.append("C" + (i + 1) + ":");
      sbSub.append(pileListToString(this.cascadePile.get(i)));
      sb.append(sbSub.toString());
    }
    return sb.toString();
  }

  /**
   * This inner builder class implements FreecellOperationsBuilder interface. An inner freecell
   * model builder object could set variables and return a model object.
   */
  public static class FreecellBuilder implements FreecellOperationsBuilder<Card> {
    private int cascades;
    private int opens;
    private final int minCascades = 4;
    private final int minOpens = 1;

    /**
     * Construct a builder class with default 8 cascades and 4 opens.
     */
    private FreecellBuilder() {
      this.cascades = 8;
      this.opens = 4;
    }

    /**
     * Set the cascade piles number. If cascade number is less than 4, this method will
     * throw IllegalArgumentException. This method will return itself after changing
     * cascade piles number.
     * @param c new cascade piles number
     * @return builder object itself
     */
    public FreecellOperationsBuilder cascades(int c) {
      if (c < this.minCascades) {
        throw new IllegalArgumentException("Minimum of cascade number is 4");
      }
      this.cascades = c;
      return this;
    }

    /**
     * Set the open piles number. If open number is less than 1, this method will
     * throw IllegalArgumentException. This method will return itself after changing
     * open piles number.
     * @param o new open piles number
     * @return builder object itself
     */
    public FreecellOperationsBuilder opens(int o) {
      if (o < this.minOpens) {
        throw new IllegalArgumentException("Minimum of open number is 1");
      }
      this.opens = o;
      return this;
    }

    /**
     * This method will return a FreecellModel object with given cascade and open number.
     * One must use this method to get a model.
     * @return a new FreecellModel object
     */
    public FreecellOperations<Card> build() {
      return new FreecellModel(this.opens, this.cascades);
    }
  }

  /**
   * This static will return a builder object and is the only way to get a model object.
   * @returna builder object
   */
  public static FreecellBuilder getBuilder() {
    return new FreecellBuilder();
  }
}
