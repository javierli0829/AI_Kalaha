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
  
  public void runAI(){

  }

  public void expandNode(MCSTreeNode node){
    for(int count = 0; count < 7; count++){
      if(count == 3) continue;
      // run one step and get currentState
      node.addChild(count, new MCSTreeNode(count, node.currentState));
      // random
      // update
    }
  }
}