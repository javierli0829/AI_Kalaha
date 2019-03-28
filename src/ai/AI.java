package ai;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AI {
  public List<MCSTreeNode> leafNodes = new ArrayList<>();
  public List<MCSTreeNode> decisionNode = new ArrayList<>();
  public MCSTreeNode rootNode;
  public int[] rootCurrentState;
  public int randomPlayLimit = 2000;
  public int totalNumOfPlay = 0;

  public AI(int[] currentState) { // Constructor
    this.rootNode = new MCSTreeNode(7, currentState); // 7 means root
    this.rootCurrentState = currentState;
  }

  public int runAI() {
    expandNode(rootNode, true);

    ++this.totalNumOfPlay;

    // loop until the limit reach
    while (totalNumOfPlay <= randomPlayLimit) {
      expandNode(maxUCBNode(leafNodes), false);
      ++this.totalNumOfPlay;
    }

    // loop until the limit reach
    int name = maxUCBNode(decisionNode).name;
    totalNumOfPlay = 0;
    System.out.print("UCB: ");

    for (MCSTreeNode node : decisionNode) {
      System.out.print(node.getUCB() + ",");
    }
    System.out.println(" ");
    return name;

  }

  public void expandNode(MCSTreeNode parentNode, boolean isDecision) {
    MCSTreeNode childNode;
    List<MCSTreeNode> childNodes = new ArrayList<>();
    if (isDecision) {

      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState

        StimulationGame g = new StimulationGame(parentNode.currentState, 1);
        g.execGame(7 + count);

        childNode = new MCSTreeNode(count, g.getGameSituation());
        parentNode.addChild(count, childNode);
        childNodes.add(childNode);
        BackPropagate.updateNR(childNode, 1, RandomPlayTest.randomPlayTillEnd(g));

        // randomPlayTillEnd
        // update

        leafNodes.add(childNode);
        decisionNode.add(childNode);
        // System.out.println("count: " + count);

      }
      for (MCSTreeNode node : childNodes) {
        node.updateUCB();
      }
      BackPropagate.updateUCB(parentNode);
    } else {

      leafNodes.remove(parentNode);
      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState

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
    double maxUCB = -9999;
    MCSTreeNode maxUCBNode = null;
    for (MCSTreeNode node : nodes) {
      if (node.getUCB() >= maxUCB) {
        maxUCB = node.getUCB();
        maxUCBNode = node;
      }
    }
    // System.out.println("max: " + maxUCBNode.name);

    return maxUCBNode;
  }
}