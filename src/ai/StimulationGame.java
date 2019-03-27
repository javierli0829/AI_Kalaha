package ai;

import kalaha.Player;
import java.awt.*; // Using AWT layouts
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.util.Arrays;
import javax.swing.*; // Using Swing components and containers

// A Swing GUI application inherits from top-level container javax.swing.JFrame
public class StimulationGame {

  // Private instance variables
  public int turn = -1; // -1 = not start yet, 0 = Player1, 1 = Player2
  public Player player1 = new Player(0);
  public Player player2 = new Player(1);
  public String win = null;
  // Constructor to setup the GUI components and event handlers

  public StimulationGame(int[] arrOfSeed, int turn) {
    // Retrieve the top-level content-pane from JFrame
    this.turn = turn;
    player1.houses = Arrays.copyOfRange(arrOfSeed, 0, 7);
    player2.houses = Arrays.copyOfRange(arrOfSeed, 7, 14);
  }

  public void execGame(int houseNo) {
    Player currentPlayer = turn == 0 ? player1 : player2;
    Player opponent = turn == 1 ? player1 : player2;
    int totalSeed = currentPlayer.getHouseSeed((houseNo % 7));
    int a = 0; // add 1 when highlighted house skipped
    for (int i = 1; i < totalSeed + 1 + a; ++i) {
      int correspondingHousePos = (houseNo + i) % 7;
      boolean isCurrentPlayerHouse = (turn == 0 ? (houseNo + i) % 14 < 7 : (houseNo + i) % 14 >= 7);

      if (isCurrentPlayerHouse) {
        // put seed to currentPlayerHouse
        currentPlayer.addSomeSeedToHouse(correspondingHousePos, 1);
      } else {
        if ((houseNo + i) % 7 != 3) {
          opponent.addSomeSeedToHouse(correspondingHousePos, 1);
        } else {
          ++a; // skip the opponent highlighted house
        }
      }
      if (i == totalSeed + a) {
        // last seed
        if (isCurrentPlayerHouse) {
          if (correspondingHousePos == 3) {
            // last seed in currentPlayer highlighted house, reward a turn
            currentPlayer.removeSeedFromHouse(houseNo % 7, totalSeed);
            return;
          }

          if (currentPlayer.getHouseSeed(correspondingHousePos) == 1) { // the last seeding house originally has zero
                                                                        // seed
            int opponentHouseNo = Math.abs(correspondingHousePos - 6);
            int noOfSeedSteal = opponent.getHouseSeed(opponentHouseNo); // 0-6,1-5,2-4,3-3
            currentPlayer.addSomeSeedToHouse(3, noOfSeedSteal + 1); // includes the last seed and the stolen seed
            currentPlayer.removeSeedFromHouse(correspondingHousePos, 1); // last seed to highlighted house as well
            opponent.removeAllSeedFromHouse(opponentHouseNo);
          }
        }
        // Check if the game is over
        if (checkDone()) {
          win = player1.houses[3] > player2.houses[3] ? "0" : "1";
        } else {
          turn = turn == 1 ? 0 : 1;
        }
      }
    }
    currentPlayer.removeSeedFromHouse(houseNo % 7, totalSeed);
  }

  public boolean checkDone() {
    if (player1.houses[3] > 36)
      return true;
    if (player2.houses[3] > 36)
      return true;

    boolean p1HaveSeed = false;
    boolean p2HaveSeed = false;

    for (int i = 0; i < 7; ++i) {
      if (i == 3)
        continue;
      if (player1.getHouseSeed(i) > 0) {
        p1HaveSeed = true;
        break;
      }
    }

    if (!p1HaveSeed) {
      for (int i = 0; i < 7; ++i) {
        if (i == 3)
          continue;
        player2.addSomeSeedToHouse(3, player2.getHouseSeed(i));
        player2.removeAllSeedFromHouse(i);
      }
      return true;
    }

    for (int i = 0; i < 7; ++i) {
      if (i == 3)
        continue;
      if (player2.getHouseSeed(i) > 0) {
        p2HaveSeed = true;
        break;
      }
    }

    if (!p2HaveSeed) {
      for (int i = 0; i < 7; ++i) {
        if (i == 3)
          continue;
        player1.addSomeSeedToHouse(3, player1.getHouseSeed(i));
        player1.removeAllSeedFromHouse(i);
      }
      return true;
    }
    return false;
  };

  public int returnPlayer1FinalScore() {
    // return r
    return player1.getHouseSeed(3);
  }

  public int[] getGameSituation() {
    int[] array = new int[14];
    for (int i = 0; i < 7; ++i) {
      array[i] = player1.getHouseSeed(i);
    }
    for (int i = 7; i < 14; ++i) {
      array[i] = player2.getHouseSeed(i % 7);
    }
    return array;
  }

  public void printHouseSeedArr() {
    // for validation check
    int[] result = new int[14];
    System.arraycopy(player1.houses, 0, result, 0, 7);
    System.arraycopy(player2.houses, 0, result, 7, 7);
    System.out.print("[");
    for (int a : result) {
      System.out.print(a + ",");
    }
    System.out.print("]");
  }
}
