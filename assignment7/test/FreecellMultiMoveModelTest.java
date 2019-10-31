import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.List;

import freecell.model.Card;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

public class FreecellMultiMoveModelTest {
  @Test
  public void test1() {
    FreecellOperations<Card> fmm = FreecellMultiMoveModel.getBuilder().cascades(52).build();
    List<Card> deck = fmm.getDeck();
    fmm.startGame(deck,false);
    System.out.println(fmm.getGameState());
    for (int i = 0; i < 3; ++i) {
      fmm.move(PileType.CASCADE, i * 4, 0, PileType.CASCADE,
              (i + 1) * 4 + 1);
      fmm.move(PileType.CASCADE, i * 4 + 1, 0, PileType.CASCADE,
              (i + 1) * 4);
      fmm.move(PileType.CASCADE, i * 4 + 2, 0, PileType.CASCADE,
              (i + 1) * 4 + 3);
      fmm.move(PileType.CASCADE, i * 4 + 3, 0, PileType.CASCADE,
              (i + 1) * 4 + 2);
    }
    System.out.println(fmm.getGameState());
  }
}