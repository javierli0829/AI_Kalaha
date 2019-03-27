package ai;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AI {
  public List<MCSTreeNode> leafNodes = new ArrayList<>();
  public List<MCSTreeNode> decisionNode = new ArrayList<>();
  public MCSTreeNode rootNode;
  public int[] rootCurrentState;

  public AI(int[] currentState){ // Constructor
    this.rootNode = new MCSTreeNode(7, currentState); // 7 means root
    this.rootCurrentState = currentState;
  }
  
  public int runAI(){
    
    return 1;
  }

  public void expandNode(MCSTreeNode parentNode){
    MCSTreeNode childNode;
    leafNodes.remove(parentNode);
    for(int count = 0; count < 7; count++){
      if(count == 3) continue;
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
    // update decision tree
  }
}