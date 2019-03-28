package ai;

import java.lang.Math;
import ai.StimulationGame;
import java.util.Arrays;

public class RandomPlayTest {

  public static void randomPlay(StimulationGame g) {
    int randomHouseNo = g.turn == 0 ? (int) (Math.random() * 7 + 0) : (int) (Math.random() * 7 + 7);
    while (randomHouseNo == 3 || randomHouseNo == 10) {
      randomHouseNo = g.turn == 0 ? (int) (Math.random() * 7 + 0) : (int) (Math.random() * 7 + 7);
    }
    g.execGame(randomHouseNo);
  }

  public static int randomPlayTillEnd(StimulationGame g) {
    // return the score of player2(AI)
    // System.out.print("random");
    while (!g.checkDone()) {
      randomPlay(g);
    }
    // System.out.println("RPTE: " + Arrays.toString(g.getGameSituation()));

    return g.player2.getScore();

  }
}