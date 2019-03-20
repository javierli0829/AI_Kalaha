package AI;

public class MCSTreeNode{
  public int[] currentState; // the current situation of the board
  public int[] ucbs; // the ucb of each available choice
  public int n;
  public int r;
  public MCSTreeNode[] children; // children

  public MCSTreeNode(){ // constructor

  }

  public void addChild(){
    // expand the chosen node and add children
  }
}