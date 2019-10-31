package freecell.model;

import java.util.List;
import java.util.ArrayList;

/**
 * This class implements methods in FreecellOperations interface extending FreecellModel
 * class. A Freecell multi move game model object could get a list of deck, start game,
 * move a list of cards from one pile to another, check if game is over, and get game state.
 */
public class FreecellMultiMoveModel extends FreecellModel implements FreecellOperations<Card> {
  /**
   * Construct a FreecellMultiMoveModel object with given open and cascade pile number.
   * If open or cascade number is less than minimum number, this method will throw
   * IllegalArgumentException.
   * @param open open pile number
   * @param cascade cascade pile number
   */
  public FreecellMultiMoveModel(int open, int cascade) {
    super(open, cascade);
    if (open < 1) {
      throw new IllegalArgumentException("Minimum of open number is 1");
    }
    if (cascade < 4) {
      throw new IllegalArgumentException("Minimum of cascade number is 4");
    }
  }

  /**
   * Private helper method to check if a list of cards could be moved to given cascade pile.
   * This method will return true when list of cards could be moved, return false otherwise.
   * @param cards list of cards
   * @param destPileNumber destination cascade pile number
   * @return true if cards could be moved, false otherwise
   */
  private boolean checkCardsToCascade(List<Card> cards, int destPileNumber) {
    for (int i = 0; i < cards.size() - 1; ++i) {
      if (!(cards.get(i).getColor() != cards.get(i + 1).getColor()
              && cards.get(i).getValue() == cards.get(i + 1).getValue() + 1)) {
        return false;
      }
    }
    List<Card> destPile = this.cascadePile.get(destPileNumber);
    if (destPile.size() != 0) {
      Card lastCard = destPile.get(destPile.size() - 1);
      if (!(lastCard.getColor() != cards.get(0).getColor()
              && lastCard.getValue() == cards.get(0).getValue() + 1)) {
        return false;
      }
    }
    int freeOpenPiles = 0;
    for (int i = 0; i < this.openNumber; ++i) {
      if (this.openPile.get(i).size() == 0) {
        ++freeOpenPiles;
      }
    }
    int emptyCascadePiles = 0;
    for (int i = 0; i < this.cascadeNumber; ++i) {
      if (this.cascadePile.get(i).size() == 0) {
        ++emptyCascadePiles;
      }
    }
    double maximumCards = (freeOpenPiles + 1) * Math.pow(2, emptyCascadePiles);
    return cards.size() <= maximumCards;
  }

  /**
   * Private helper method to move a list of cards from one pile to another when all
   * the parameters are valid.
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
    for (int i = cardIndex; i < src.size(); ++i) {
      dest.add(src.get(i));
    }
    while (src.size() > cardIndex) {
      src.remove(src.size() - 1);
    }
  }

  /**
   * Move a list of cards from the given source pile to the given destination pile, if
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
  @Override
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
      if (pileNumber < 0 || pileNumber >= this.cascadeNumber) {
        throw new IllegalArgumentException("Illegal source pile number");
      }
      list = this.cascadePile.get(pileNumber);
    }
    else if (source == PileType.OPEN) {
      if (pileNumber < 0 || pileNumber >= this.openNumber) {
        throw new IllegalArgumentException("Illegal source pile number");
      }
      list = this.openPile.get(pileNumber);
    }
    else {
      if (pileNumber < 0 || pileNumber >= this.foundationNumber) {
        throw new IllegalArgumentException("Illegal source pile number");
      }
      list = this.foundationPile.get(pileNumber);
    }
    if (list.size() == 0) {
      throw new IllegalArgumentException("Source pile is empty");
    }
    if (cardIndex < 0 || cardIndex >= list.size()) {
      throw new IllegalArgumentException("Illegal source card index");
    }
    if (source == destination && pileNumber == destPileNumber) {
      return;
    }
    List<Card> subList = list.subList(cardIndex, list.size());
    if (destination == PileType.OPEN) {
      if (destPileNumber < 0 || destPileNumber >= this.openNumber) {
        throw new IllegalArgumentException("Illegal destination pile number");
      }
      if (subList.size() == 0 || subList.size() > 1) {
        throw new IllegalArgumentException("Only one card can be moved to open pile");
      }
      if (!(this.checkCardToOpen(destPileNumber))) {
        throw new IllegalArgumentException("Move card fail");
      }
    }
    else if (destination == PileType.CASCADE) {
      if (destPileNumber < 0 || destPileNumber >= this.cascadeNumber) {
        throw new IllegalArgumentException("Illegal destination pile number");
      }
      if (!checkCardsToCascade(subList, destPileNumber)) {
        throw new IllegalArgumentException("Move card fail");
      }
    }
    else {
      if (destPileNumber < 0 || destPileNumber >= this.foundationNumber) {
        throw new IllegalArgumentException("Illegal destination pile number");
      }
      if (subList.size() == 0 || subList.size() > 1) {
        throw new IllegalArgumentException("Only one card can be moved to foundation pile");
      }
      if (!this.checkCardToFoundation(subList.get(0), destPileNumber)) {
        throw new IllegalArgumentException("Move card fail");
      }
    }
    moveCardHelper(source, pileNumber, cardIndex, destination, destPileNumber);
  }

  /**
   * This inner builder class extends FreecellModel.FreecellBuilder class. An inner freecell
   * model builder object could set variables and return a model object.
   */
  public static class FreecellMultiMoveBuilder extends FreecellModel.FreecellBuilder {
    @Override
    public FreecellOperations<Card> build() {
      return (FreecellOperations<Card>)
              new FreecellMultiMoveModel(this.opens, this.cascades);
    }
  }

  /**
   * This static will return a builder object and is the only way to get a model object.
   * @return a builder object
   */
  public static FreecellMultiMoveBuilder getBuilder() {
    return new FreecellMultiMoveBuilder();
  }
}
