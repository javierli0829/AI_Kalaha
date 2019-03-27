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
    expandNode(rootNode, true);
    ++this.totalNumOfPlay;

    // loop until the limit reach
    while (totalNumOfPlay <= randomPlayLimit) {
      expandNode(maxUCBNode(leafNodes), false);
    }

    // loop until the limit reach
    return maxUCBNode(decisionNode).name;

  }

  public void expandNode(MCSTreeNode parentNode, boolean isDecision) {
    MCSTreeNode childNode;
    if (isDecision) {
      leafNodes.remove(parentNode);
      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState
        StimulationGame g = new StimulationGame(parentNode.currentState, 1);
        g.execGame(7 + count);
        childNode = new MCSTreeNode(count, g.getGameSituation());
        parentNode.addChild(count, childNode);
        // randomPlayTillEnd
        // update
        BackPropagate.updateScores(childNode, 1, RandomPlayTest.randomPlayTillEnd(g));
        leafNodes.add(childNode);
        decisionNode.add(childNode);
      }
    } else {
      leafNodes.remove(parentNode);
      for (int count = 0; count < 7; count++) {
        if (count == 3)
          continue;
        // run one step and get currentState
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
    return maxUCBNode;
  }
}