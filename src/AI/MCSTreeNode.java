package AI;

import java.util.ArrayList;
import java.util.List;

public class MCSTreeNode<T>{
  public MCSTreeNode<T> parent = null;
  public int n;
  public int r;
  public int[] currentState; // the current situation of the board
  public int ucb; // the ucb node itself
  public List<MCSTreeNode<T>> children = new ArrayList<>(); // children

  public MCSTreeNode(int[] currentState){ // constructor
    this.n = 0;
    this.r = 0;
    this.currentState = currentState;
    this.ucb = 0;
  }

  public MCSTreeNode<T> addChild(MCSTreeNode<T> child){
    // expand the node and add children
    child.parent = this;
    this.children.add(child);
    return child;
  }

  public int getUCB(){
    return this.ucb;
  }

  public int getN(){
    return this.n;
  }

  public int getR(){
    return this.r;
  }
}