public class Player {
  // House 3 (index 3) is the highlighted house
  private int[] houses = { 6, 6, 6, 0, 6, 6, 6 };

  public getHouseSeed(int houseNo) {
    return this.houses[houseNo];
  }

  public getScore() {
    return this.houses[3];
  }

  public void addScore(int score) {
    this.houses[3] += score;
  }

  public isHouseEmpty(int houseNo) {
    return this.houses[houseNo] == 0;
  }

  public isHouseHasOneSeed (int houseNo) {
    return this.houses[houseNo] == 1;
  }

  public int getNoOfSeedFromHouse(int houseNo) {
    return this.houses[houseNo];
  }

  public void addOneSeedToHouse(int houseNo) {
    this.houses[houseNo] += 1;
  }

  public void removeSeedFromHouse(int houseNo, int noOfSeed) {
    this.houses[houseNo] -= noOfSeed;
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

  public addSeedToSeveralHouse(int fromHouseNo, int toHouseNo) {
    for (int i = fromHouseNo; i <= toHouseNo; ++i) {
      this.houses[i] += 1;
    }
  }
}