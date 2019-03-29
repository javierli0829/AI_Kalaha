package ai;

import java.lang.Math;
import ai.StimulationGame;
import java.util.Arrays;

public class RandomPlayTest {
  // 0- final score
  // 1- weigh
  public static int heuristicMethod = 0;

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

    // heuristic function 1:
    switch (heuristicMethod) {
    case 0:
      return g.player2.getScore();
    case 1:
      if (g.player2.getScore() > 36) {
        return 100;
      } else if (g.player2.getScore() == 36) {
        return 1;
      } else {
        return 0;
      }
    default:
      return 0;
    }

    // heuristic function 2: weigh

  }
}