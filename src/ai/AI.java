package ai;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AI {
  public List<MCSTreeNode> leafNodes = new ArrayList<>();
  public List<MCSTreeNode> decisionNode = new ArrayList<>();
  public MCSTreeNode rootNode;
  public int[] rootCurrentState;
  public int randomPlayLimit = 500;
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
      System.out.println("ttP: " + this.totalNumOfPlay);
    }

    // loop until the limit reach
    int name = maxUCBNode(decisionNode).name;
    totalNumOfPlay = 0;
    return name;

  }

  public void expandNode(MCSTreeNode parentNode, boolean isDecision) {
    MCSTreeNode childNode;
    List<MCSTreeNode> childNodes = new ArrayList<>();
    if (isDecision) {
      System.out.println("a");

      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState
        if (parentNode == null) {
          System.out.println("parent null");

        } else {
          System.out.println("NOT NULL: " + parentNode.name);
        }
        StimulationGame g = new StimulationGame(parentNode.currentState, 1);
        g.execGame(7 + count);
        System.out.println("a1");

        childNode = new MCSTreeNode(count, g.getGameSituation());
        childNodes.add(childNode);
        parentNode.addChild(count, childNode);
        BackPropagate.updateNR(childNode, 1, RandomPlayTest.randomPlayTillEnd(g));
        System.out.println("a2");

        // randomPlayTillEnd
        // update
        System.out.println("a3");

        leafNodes.add(childNode);
        decisionNode.add(childNode);
        System.out.println("count: " + count);

      }
      for (MCSTreeNode node : childNodes) {
        node.updateUCB();
      }
      BackPropagate.updateUCB(parentNode);
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

        } else {
          System.out.println("NOT NULL: " + parentNode.name);
        }
        StimulationGame g = new StimulationGame(parentNode.currentState, 1);
        g.execGame(7 + count);
        childNode = new MCSTreeNode(count, g.getGameSituation());
        childNodes.add(childNode);
        parentNode.addChild(count, childNode);
        BackPropagate.updateNR(childNode, 1, RandomPlayTest.randomPlayTillEnd(g));
        // randomPlayTillEnd
        // update
        leafNodes.add(childNode);
      }
      for (MCSTreeNode node : childNodes) {
        node.updateUCB();
      }
      BackPropagate.updateUCB(parentNode);
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
    return maxUCBNode;
  }
}