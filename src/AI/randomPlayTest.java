package AI;

import java.lang.Math;
import AI.StimulationGame;

public class randomPlayTest {

  public static void randomPlay(StimulationGame g) {
    int randomHouseNo = g.turn == 0 ? (int) (Math.random() * 7 + 0) : (int) (Math.random() * 7 + 7);
    while (randomHouseNo == 3 || randomHouseNo == 10) {
      randomHouseNo = g.turn == 0 ? (int) (Math.random() * 7 + 0) : (int) (Math.random() * 7 + 7);
    }
    System.out.print(randomHouseNo);
    g.execGame(randomHouseNo);
  }

  public static void main(String[] args) {
    StimulationGame g = new StimulationGame(new int[] { 7, 7, 7, 1, 7, 7, 7, 6, 6, 6, 1, 6, 6, 6 }, 0);
    randomPlay(g);
    g.printHouseSeedArr();
  }
}