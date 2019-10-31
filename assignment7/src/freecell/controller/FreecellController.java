package freecell.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import freecell.model.Card;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

/**
 * This class implements method in IFreecellController
 * interface and provides a game of freecell.
 */
public class FreecellController implements IFreecellController<Card> {
  private final Readable rd;
  private final Appendable ap;
  private States states;

  private String sourcePile;
  private String index;
  private String destPile;

  /**
   * Inner enum to indicate game states. A game has four states: input source pile number,
   * input card index, input destination pile number, and move cards.
   */
  private enum States {
    INPUT_SOURCE, INPUT_INDEX, INPUT_DESTINATION, MOVE_CARD
  }

  /**
   * Construct a FreecellController object with Readable to input and Appendable to output.
   * If Readable or Appendable is null, this method will throw IllegalArgumentException.
   * @param rd Readable object
   * @param ap Appendable object
   * @throws IllegalArgumentException if rd or ap is null
   */
  public FreecellController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable or Appendable cannot be null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Private helper method to append message in output Appendable object.
   * @param msg output message
   */
  private void outputMsg(String msg) {
    try {
      this.ap.append(msg);
    }
    catch (IOException e) {
      throw new IllegalStateException("Cannot output");
    }
  }

  /**
   * Private helper method parses source pile. If there is not next in Reader, this method
   * will throw IllegalStateException. If input string is not a legal source pile, this
   * method will add message in Appendable to ask the user to input it again. If input is
   * 'q' or 'Q', this method will return true to indicate a game over state. If input is
   * source pile, this method will return false to indicate this is not a game over.
   * @param scanner Scanner object to get next string
   * @return true if it is game over state, false otherwise
   */
  private boolean inputSource(Scanner scanner) {
    if (!scanner.hasNext()) {
      throw new IllegalStateException("No input");
    }
    String str = scanner.next();
    if (str.equals("q") || str.equals("Q")) {
      outputMsg("Game quit prematurely.\n");
      return true;
    }
    if (str.length() >= 2) {
      if (!(str.charAt(0) == 'C' || str.charAt(0) == 'F' || str.charAt(0) == 'O')) {
        outputMsg("Illegal source pile number! Input again:\n");
        return false;
      }
      for (int i = 1; i < str.length(); ++i) {
        if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
          outputMsg("Illegal source pile number! Input again:\n");
          return false;
        }
      }
      this.sourcePile = str;
      this.states = States.INPUT_INDEX;
      outputMsg("Input card index:\n");
      return false;
    }
    else {
      outputMsg("Illegal source pile number! Input again:\n");
      return false;
    }
  }

  /**
   * Private helper method parses card index. If there is not next in Reader, this method
   * will throw IllegalStateException. If input string is not a legal number, this method
   * will add message in Appendable to ask the user to input it again. If input is 'q' or
   * 'Q', this method will return true to indicate a game over state. If input is number,
   * this method will return false to indicate this is not a game over.
   * @param scanner Scanner object to get next string
   * @return true if it is game over state, false otherwise
   */
  private boolean inputIndex(Scanner scanner) {
    if (!scanner.hasNext()) {
      throw new IllegalStateException("No input");
    }
    String str = scanner.next();
    if (str.equals("q") || str.equals("Q")) {
      outputMsg("Game quit prematurely.\n");
      return true;
    }
    for (int i = 0; i < str.length(); ++i) {
      if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
        outputMsg("Illegal card index! Input again:\n");
        return false;
      }
    }
    this.index = str;
    this.states = States.INPUT_DESTINATION;
    outputMsg("Input destination number:\n");
    return false;
  }

  /**
   * Private helper method parses destination pile. If there is not next in Reader, this
   * method will throw IllegalStateException. If input string is not a legal destination
   * pile, this method will add message in Appendable to ask the user to input it again.
   * If input is 'q' or 'Q', this method will return true to indicate a game over state.
   * If input is destination pile, this method will return false to indicate this is not
   * a game over.
   * @param scanner Scanner object to get next string
   * @return true if it is game over state, false otherwise
   */
  private boolean inputDest(Scanner scanner) {
    if (!scanner.hasNext()) {
      throw new IllegalStateException("No input");
    }
    String str = scanner.next();
    if (str.equals("q") || str.equals("Q")) {
      outputMsg("Game quit prematurely.\n");
      return true;
    }
    if (str.length() >= 2) {
      if (!(str.charAt(0) == 'C' || str.charAt(0) == 'F' || str.charAt(0) == 'O')) {
        outputMsg("Illegal destination pile number! Input again:\n");
        return false;
      }
      for (int i = 1; i < str.length(); ++i) {
        if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
          outputMsg("Illegal destination pile number! Input again:\n");
          return false;
        }
      }
      this.destPile = str;
      this.states = States.MOVE_CARD;
      return false;
    }
    else {
      outputMsg("Illegal destination pile number! Input again:\n");
      return false;
    }
  }

  /**
   * Private helper method to move cards with input source pile, card index, and destination
   * pile. If model.move throw exception, this method will add message in Appendable to
   * indicate this is a invalid move. After move cards, if it is in game over state, this
   * method will return true, or false otherwise.
   * @param model FreecellOperations object
   * @return true if game over, or false otherwise
   */
  private boolean moveCard(FreecellOperations<Card> model) {
    PileType src;
    if (this.sourcePile.charAt(0) == 'O') {
      src = PileType.OPEN;
    }
    else if (this.sourcePile.charAt(0) == 'C') {
      src = PileType.CASCADE;
    }
    else {
      src = PileType.FOUNDATION;
    }
    int srcNum = Integer.valueOf(this.sourcePile.substring(1)).intValue() - 1;
    int cardIndex = Integer.valueOf(this.index).intValue() - 1;
    PileType dest;
    if (this.destPile.charAt(0) == 'O') {
      dest = PileType.OPEN;
    }
    else if (this.destPile.charAt(0) == 'C') {
      dest = PileType.CASCADE;
    }
    else {
      dest = PileType.FOUNDATION;
    }
    int destNum = Integer.valueOf(this.destPile.substring(1)).intValue() - 1;
    try {
      model.move(src, srcNum, cardIndex, dest, destNum);
      outputMsg(model.getGameState() + '\n');
      this.states = States.INPUT_SOURCE;
      if (model.isGameOver()) {
        outputMsg("Game over.");
        return true;
      }
      outputMsg("Input source pile number:\n");
      return false;
    }
    catch (IllegalArgumentException | IllegalStateException e) {
      outputMsg("Invalid move. Try again." + e.getMessage() + '\n');
      this.states = States.INPUT_SOURCE;
    }
    return false;
  }

  /**
   * Start and play a new game of freecell with the provided deck. This deck
   * should be used as-is. This method returns only when the game is over
   * (either by winning or by quitting)
   *
   * @param deck    the deck to be used to play this game
   * @param model   the model for the game
   * @param shuffle shuffle the deck if true, false otherwise
   * @throws IllegalArgumentException if the deck is null or invalid, or if the
   *                                  model is null
   * @throws IllegalStateException    if the controller is unable to read input
   *                                  or transmit output
   */
  public void playGame(List<Card> deck, FreecellOperations<Card> model, boolean shuffle) throws
          IllegalArgumentException, IllegalStateException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    model.startGame(deck, shuffle);
    Scanner scanner = new Scanner(this.rd);
    outputMsg(model.getGameState() + '\n');
    if (model.isGameOver()) {
      outputMsg("Game over.\n");
      return;
    }
    outputMsg("Input source pile number:\n");
    this.states = States.INPUT_SOURCE;
    while (true) {
      switch (this.states) {
        case INPUT_SOURCE:
          if (inputSource(scanner)) {
            return;
          }
          break;
        case INPUT_INDEX:
          if (inputIndex(scanner)) {
            return;
          }
          break;
        case INPUT_DESTINATION:
          if (inputDest(scanner)) {
            return;
          }
          break;
        case MOVE_CARD:
          if (moveCard(model)) {
            return;
          }
          break;
      }
    }
  }

  public static void main(String [] args) {
    FreecellOperations<Card> model = FreecellMultiMoveModel.getBuilder().cascades(52).build();
    List<Card> deck = model.getDeck();
    IFreecellController<Card> controller =
            new FreecellController(new InputStreamReader(System.in), System.out);
    controller.playGame(deck, model, false);
  }
}
