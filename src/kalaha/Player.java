package kalaha;

public class Player {
  // House 3 (index 3) is the highlighted house
  public int no;
  public int[] houses = {6, 6, 6, 0, 6, 6, 6};

  public Player(int playerNo){
    this.no = playerNo;
  }

  public int getHouseSeed(int houseNo) {
    return this.houses[houseNo];
  }

  public int getScore() {
    return this.houses[3];
  }

  public boolean isHouseEmpty(int houseNo) {
    return this.houses[houseNo] == 0;
  }

  public boolean isHouseHasOneSeed (int houseNo) {
    return this.houses[houseNo] == 1;
  }

  public void addSomeSeedToHouse(int houseNo, int noOfSeed) {
    this.houses[houseNo] += noOfSeed;
  }

  public int removeAllSeedFromHouse(int houseNo) {
    int noOfSeed;
    if (houseNo == 3) {
      throw new Error("Seeds in Highlighted House is not allowed to be remove");
    }
    noOfSeed = this.getHouseSeed(houseNo);
    this.houses[houseNo] = 0;
    return noOfSeed;
  }
  
  public boolean checkDone(){
    if(getScore() > 36) return true;
    for(int count = 0; count <= 6; count++){
      if(isHouseEmpty(count) != true && count != 3){
        return false;
      }
    }
    return true;
  }

  // public int getNoOfSeedFromHouse(int houseNo) {
  //   return this.houses[houseNo];
  // }

  // public void addSeedToSeveralHouse(int fromHouseNo, int toHouseNo) {
  //   for (int i = fromHouseNo; i <= toHouseNo; ++i) {
  //     this.houses[i] += 1;
  //   }
  // }

  // public void addScore(int score) {
  //   this.houses[3] += score;
  // }

  // public void removeSeedFromHouse(int houseNo, int noOfSeed) {
  //   this.houses[houseNo] -= noOfSeed;
  // }
}
