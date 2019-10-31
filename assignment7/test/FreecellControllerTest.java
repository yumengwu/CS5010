import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import freecell.controller.FreecellController;
import freecell.controller.IFreecellController;
import freecell.model.Card;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class FreecellControllerTest {
  @Test
  public void test1() {
    Reader reader = new StringReader("C1 7\nO1\nq");
    StringBuilder stringBuilder = new StringBuilder();
    IFreecellController<Card> controller = new FreecellController(reader, stringBuilder);
    FreecellOperations<Card> model = FreecellMultiMoveModel.getBuilder().build();
    List<Card> deck = model.getDeck();
    controller.playGame(deck, model, false);
    System.out.println(stringBuilder.toString());
  }
}