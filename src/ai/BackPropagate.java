package ai;

import java.util.ArrayList;
import java.util.List;

//import AI.selectionection

public class BackPropagate {
	public static void updateScores(MCSTreeNode node, int n, int r) {
		// System.out.print("update");
		MCSTreeNode currentNode = node;
		while (currentNode.getParent().name != 7) {
			currentNode.updateRNUCB(n, r);
			currentNode = currentNode.getParent();
			System.out.println("Root: " + currentNode.name);
		}

	}
}