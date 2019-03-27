package ai;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AI {
  public List<MCSTreeNode> leafNodes = new ArrayList<>();
  public List<MCSTreeNode> decisionNode = new ArrayList<>();
  public MCSTreeNode rootNode;
  public int[] rootCurrentState;
  public int randomPlayLimit = 100;
  public int totalNumOfPlay = 0;

  public AI(int[] currentState) { // Constructor
    this.rootNode = new MCSTreeNode(7, currentState); // 7 means root
    this.rootCurrentState = currentState;
  }

  public int runAI() {
    System.out.println("4");

    expandNode(rootNode, true);
    System.out.println("5");

    ++this.totalNumOfPlay;

    // loop until the limit reach
    while (totalNumOfPlay <= randomPlayLimit) {
      expandNode(maxUCBNode(leafNodes), false);
      ++this.totalNumOfPlay;
      System.out.println(this.totalNumOfPlay);
    }

    // loop until the limit reach
    return maxUCBNode(decisionNode).name;

  }

  public void expandNode(MCSTreeNode parentNode, boolean isDecision) {
    MCSTreeNode childNode;
    if (isDecision) {
      System.out.println("a");

      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState
        StimulationGame g = new StimulationGame(parentNode.currentState, 1);
        g.execGame(7 + count);
        System.out.println("a1");

        childNode = new MCSTreeNode(count, g.getGameSituation());
        parentNode.addChild(count, childNode);
        System.out.println("a2");

        // randomPlayTillEnd
        // update
        BackPropagate.updateScores(childNode, 1, RandomPlayTest.randomPlayTillEnd(g));
        System.out.println("a3");

        leafNodes.add(childNode);
        decisionNode.add(childNode);
        System.out.println("count: " + count);

      }
    } else {
      System.out.println("b");

      leafNodes.remove(parentNode);
      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState
        System.out.println("hey");
        if (parentNode == null) {
          System.out.println("parent null");

        }
        StimulationGame g = new StimulationGame(parentNode.currentState, 1);
        g.execGame(7 + count);
        childNode = new MCSTreeNode(count, g.getGameSituation());
        parentNode.addChild(count, childNode);
        // randomPlayTillEnd
        // update
        BackPropagate.updateScores(childNode, 1, RandomPlayTest.randomPlayTillEnd(g));
        leafNodes.add(childNode);
      }
    }
    // update decision tree
  }

  public MCSTreeNode maxUCBNode(List<MCSTreeNode> nodes) {
    int maxUCB = -9999;
    MCSTreeNode maxUCBNode = null;
    for (MCSTreeNode node : nodes) {
      if (node.getUCB() >= maxUCB) {
        maxUCBNode = node;
      }
    }
    if (maxUCBNode == null) {
      System.out.println("node null");
    }
    return maxUCBNode;
  }
}