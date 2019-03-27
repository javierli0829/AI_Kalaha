package ai;
import java.lang.Math;

public class AI {
  public static void main(String[] args) {
    StimulationGame g = new StimulationGame(new int[] { 7, 7, 7, 1, 7, 7, 7, 6, 6, 6, 1, 6, 6, 6 }, 1);
    randomPlayTest.randomPlay(g);
    g.printHouseSeedArr();
  }
}