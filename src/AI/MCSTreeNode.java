package ai;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class MCSTreeNode{
  public MCSTreeNode parent = null;
  public int name;
  public int n;
  public int r;
  public int[] currentState; // the current situation of the board
  public double ucb; // the ucb node itself
  public List<MCSTreeNode> children = new ArrayList<>(); // children

  public MCSTreeNode(int name, int[] currentState){ // constructor
    this.name = name;
    this.n = 0;
    this.r = 0;
    this.currentState = currentState;
    this.ucb = 0;
  }

  public MCSTreeNode addChild(int name, MCSTreeNode child){
    // expand the node and add children
    child.name = name;
    child.parent = this;
    this.children.add(child);
    return child;
  }

  public MCSTreeNode getParent(){
    return this.parent;
  }

  public double getUCB(){
    return this.ucb;
  }

  public int getN(){
    return this.n;
  }

  public int getR(){
    return this.r;
  }

  public double calculateUCB(int n, int r){
    return (r/n) + Math.sqrt((Math.log(this.parent.n) * 2) / n);
  }
}