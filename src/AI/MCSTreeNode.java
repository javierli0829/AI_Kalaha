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

  public MCSTreeNode(MCSTreeNode<T> parent, int[] currentState){ // constructor
    this.parent = parent;
    this.n = 0;
    this.r = 0;
    this.currentState = currentState;
    this.ucb = 0;
  }

  public void addChild(){
    // expand the chosen node and add children
  }
}