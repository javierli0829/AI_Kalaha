package kalaha;

import kalaha.Player;
import java.awt.*; // Using AWT layouts
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.util.Arrays;

import javax.swing.*; // Using Swing components and containers

// A Swing GUI application inherits from top-level container javax.swing.JFrame
public class GameBoard extends JFrame implements ActionListener {

  // Private instance variables
  public int turn = -1; // -1 = not start yet, 0 = Player1, 1 = Player2
  public JButton[] houseBtns;
  public JTextPane textPane = new JTextPane();
  public final int[] housePosX = { 300, 200, 100, 100, 100, 200, 300, 400, 500, 600, 600, 600, 500, 400 };
  public final int[] housePosY = { 0, 0, 0, 100, 200, 200, 200, 200, 200, 200, 100, 0, 0, 0 };
  public Player player1 = new Player(0);
  public Player player2 = new Player(1);
  public String win = null;
  // Constructor to setup the GUI components and event handlers

  public GameBoard() {
    // Retrieve the top-level content-pane from JFrame
    turn = 0;
    Container cp = getContentPane();

    // Content-pane sets layout
    // cp.setLayout(new FlowLayout());
    cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));

    JPanel menuPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JButton startBtn = new JButton("Restart");
    menuPanel.setPreferredSize(new Dimension(200, 100));
    menuPanel.setMaximumSize(new Dimension(200, 100));
    // menuPanel.setAlignmentY(CENTER_ALIGNMENT);
    gamePanel.setPreferredSize(new Dimension(900, 300));
    gamePanel.setMaximumSize(new Dimension(900, 300));
    gamePanel.setLayout(null);

    startBtn.setVerticalTextPosition(AbstractButton.BOTTOM);
    startBtn.setHorizontalTextPosition(AbstractButton.CENTER); // aka LEFT, for left-to-right locales
    startBtn.setMnemonic(KeyEvent.VK_D);
    startBtn.setActionCommand("restart");
    startBtn.addActionListener(this);

    houseBtns = new JButton[14];
    for (int i = 0; i < 14; ++i) {
      final int houseNo = i;
      JButton houseBtn = new JButton();
      houseBtn.setActionCommand(Integer.toString(i));
      houseBtn.addActionListener(this);
      houseBtn.addMouseListener(new MouseAdapter() {
        Color oldcolor = houseBtn.getForeground();

        public void mouseEntered(MouseEvent me) {
          if (turn == houseNo / 7 && houseNo != 3 && houseNo != 10) {
            oldcolor = houseBtn.getForeground();
            houseBtn.setForeground(Color.red);
          }
        }

        public void mouseExited(MouseEvent me) {
          if (turn == houseNo / 7 && houseNo != 3 && houseNo != 10) {
            houseBtn.setForeground(oldcolor);
          }
        }

        public void mouseClicked(MouseEvent me) {
          houseBtn.setForeground(Color.black);
        }
      });
      houseBtns[i] = houseBtn;
      houseBtn.setBounds(housePosX[i], housePosY[i], 90, 90);
      gamePanel.add(houseBtn);
    }
    updateHouseBtnText();

    textPane.setText("Game starts Player 1 First");
    textPane.setBounds(205, 100, 380, 90);

    menuPanel.add(startBtn);
    gamePanel.add(textPane);

    cp.add(menuPanel);
    cp.add(gamePanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Exit the program when the close-window button clicked
    setTitle("Kalaha"); // "super" JFrame sets title
    setSize(new Dimension(1300, 600)); // "super" JFrame sets initial size
    setVisible(true); // "super" JFrame shows
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    String action = ae.getActionCommand();
    if (action == "restart") {
      turn = 0;
      addDescription("Game starts Player 1 First");
      new GameBoard();
    } else {
      if ((turn == 0 && Integer.parseInt(action) < 7 && Integer.parseInt(action) != 3)
          || (turn == 1 && Integer.parseInt(action) >= 7 && Integer.parseInt(action) != 10)) {
        clearDescription();
        execGame(Integer.parseInt(action));
        updateHouseBtnText();
        System.out.println(Arrays.toString(player1.houses));
        System.out.println(Arrays.toString(player2.houses));
      }
    }

  }

  public void execGame(int houseNo) {
    Player currentPlayer = turn == 0 ? player1 : player2;
    Player opponent = turn == 1 ? player1 : player2;
    int totalSeed = currentPlayer.getHouseSeed((houseNo % 7));
    int a = 0; // add 1 when highlighted house skipped
    for (int i = 1; i < totalSeed + 1 + a; ++i) {
      int correspondingHousePos = (houseNo + i) % 7;
      // boolean isCurrentPlayerHouse = (turn == 0 ? ((houseNo + i) / 7) == 0 :
      // ((houseNo + i) / 7) == 1);
      boolean isCurrentPlayerHouse = (turn == 0 ? (houseNo + i) % 14 < 7 : (houseNo + i) % 14 >= 7);
      System.out.print("CorrespondingHouse: " + correspondingHousePos);
      System.out.println("isCurrentPlayerHouse: " + isCurrentPlayerHouse);
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
            addDescription("Player" + (turn == 0 ? "1" : "2") + " get bonus turn");
            return;
          }

          if (currentPlayer.getHouseSeed(correspondingHousePos) == 1) { // the last seeding house originally has zero
                                                                        // seed
            int opponentHouseNo = Math.abs(correspondingHousePos - 6);
            int noOfSeedSteal = opponent.getHouseSeed(opponentHouseNo); // 0-6,1-5,2-4,3-3
            currentPlayer.addSomeSeedToHouse(3, noOfSeedSteal + 1); // includes the last seed and the stolen seed
            currentPlayer.removeSeedFromHouse(correspondingHousePos, 1); // last seed to highlighted house as well
            opponent.removeAllSeedFromHouse(opponentHouseNo);
            addDescription("Player" + (turn == 0 ? "1" : "2") + " steal opponent house " + noOfSeedSteal + "seed");
          }
        }
        // Check if the game is over
        if (checkDone()) {
          addDescription(player1.houses[3] > player2.houses[3] ? "1 win" : "2 win");
          win = player1.houses[3] > player2.houses[3] ? "0" : "1";
        } else {
          turn = turn == 1 ? 0 : 1;
          addDescription(turn == 0 ? "Player1's turn" : "Player2's turn");
        }
      }
    }
    currentPlayer.removeSeedFromHouse(houseNo % 7, totalSeed);
  }

  public void updateHouseBtnText() {
    for (int i = 0; i < 14; ++i) {
      if (i < 7) {
        // houseBtns[i].setText(new String(new
        // char[player1.getHouseSeed(i)]).replace("\0", "."));
        houseBtns[i].setText(Integer.toString(player1.getHouseSeed(i)));
      } else {
        // houseBtns[i].setText(new String(new char[player2.getHouseSeed(i %
        // 7)]).replace("\0", "."));
        houseBtns[i].setText(Integer.toString(player2.getHouseSeed(i % 7)));
      }
    }
  }

  public void clearDescription() {
    textPane.setText("");
  }

  public void addDescription(String description) {
    textPane.setText(textPane.getText() + "\n" + description);
  }

  public boolean checkDone() {
    if (player1.houses[3] > 36)
      return true;
    if (player2.houses[3] > 36)
      return true;
    int p1 = 0;
    int p2 = 0;
    boolean p1HaveSeed = false;
    boolean p2HaveSeed = false;

    for (int i = 0; i < 7; ++i) {
      if (player1.getHouseSeed(i) > 0) {
        p1HaveSeed = true;
        break;
      }
    }

    if (!p1HaveSeed) {
      for (int i = 0; i < 7; ++i) {
        player1.addSomeSeedToHouse(3, player1.getHouseSeed(i));
        player1.removeAllSeedFromHouse(i);
      }
      return true;
    }

    for (int i = 0; i < 7; ++i) {
      if (player2.getHouseSeed(i) > 0) {
        p2HaveSeed = true;
        break;
      }
    }

    if (!p2HaveSeed) {
      for (int i = 0; i < 7; ++i) {
        player2.addSomeSeedToHouse(3, player2.getHouseSeed(i));
        player2.removeAllSeedFromHouse(i);
      }
      return true;
    }

    return false;
  };

  public static void main(String[] args) {
    // // Run GUI codes in Event-Dispatching thread for thread-safety
    GameBoard g = new GameBoard(); // Let the constructor do the job
    // g.actionPerformed(new ActionEvent(this, ActionEvent.TEXT_EVENT_MASK, '1') {

    // });
  }

}
