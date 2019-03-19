package AI;
import javax.swing.tree.TreeNode;

//import AI.selectionection

public class backPropagate{
	public static void updateScore(TreeNode N) {
//		int s = UCB();
		int s = 10;
		N.score = s;
		if(N.getParent()!=null) {
			TreeNode P = N.getParent();
			updateScore(P);
		}
		return;
		
	}
}