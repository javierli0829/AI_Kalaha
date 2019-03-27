package ai;

import java.util.ArrayList;
import java.util.List;

//import AI.selectionection

public class BackPropagate {
	public static void updateScore(MCSTreeNode node ,int n ,int r) {
		MCSTreeNode currentNode = node;
		while(currentNode.getParent() != null) {
			currentNode.updateRNUCB(n, r);
			currentNode = currentNode.getParent();
	}
}