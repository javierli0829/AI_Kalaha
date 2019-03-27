package ai;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AI {
  public List<MCSTreeNode> leafNodes = new ArrayList<>();
  public int[] decisionNode;
  public MCSTreeNode rootNode;
  public int[] currentState;

  public AI(int[] currentState){ // Constructor

  }
  
  public void runAI(){

  }

  public void expandNode(MCSTreeNode node){
    for(int count = 0; count < 7; count++){
      if(count == 3) continue;
      node.addChild(count, new MCSTreeNode(count, node.currentState));
    }
  }
}