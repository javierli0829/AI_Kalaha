package ai;

import java.util.ArrayList;
import java.util.List;

//import AI.selectionection

public class BackPropagate {
	public static void updateUCB(MCSTreeNode node) {
		// System.out.print("update");
		MCSTreeNode currentNode = node;
		while (currentNode.getParent() != null) {
			currentNode.updateUCB();
			currentNode = currentNode.getParent();
		}

	}

	public static void updateNR(MCSTreeNode node, int addN, int addR) {
		// System.out.print("update");
		MCSTreeNode currentNode = node;
		while (currentNode != null) {
			currentNode.updateN(addN);
			currentNode.updateR(addR);
			currentNode = currentNode.getParent();
		}

	}
}