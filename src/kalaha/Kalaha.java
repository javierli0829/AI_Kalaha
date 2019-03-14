package kalaha;

public class Kalaha {
	// Properties
	public static int turns = 1; // 1 -> 1, -1 -> 2
	
	// Methods
	public void houseIsChosen(Player player, int houseNo){
		player.getHouseSeed(houseNo);
	}

	// Main
	public static void main(String[] args) {
		Player player1 = new Player(1);
		Player player2 = new Player(-1);
		// while(player1.checkDone() == false && player2.checkDone() == false) {
		// 	turns *= -1;
		// }
	}
}
