package kahala;

public class Player {
	// Properties
	public int num; // 1, 2
	public int[] holeArr = new int[7]; // holeArr[3] is score
	// Identifier
	public Player (int numberOfPlayer) {
		this.num = numberOfPlayer;
		this.holeArr = new int[] {6, 6, 6, 0, 6, 6, 6};
	};
	// Methods
	public boolean checkDone() {
		if(holeArr[3] >= 36) return true;
		for(int count = 0; count <= 6; count++) {
			if(count != 3 && holeArr[count] != 0) {
				return false;
			}
		}
		return true;
	}
}
