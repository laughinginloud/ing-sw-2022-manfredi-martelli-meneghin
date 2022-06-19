package it.polimi.ingsw.common.model;

/**
 * Class representing the school board of a player
 * @author Mattia Martelli
 */
public final class SchoolBoard {
    private final TowerColor towerColor;
    private       int        towerCount;
    private       Entrance   entrance;
    private       DiningRoom diningRoom;

    /**
     * Constructor of the class 'SchoolBoard'
     * @param towerColor color of the tower
     * @param towerCount number of the towers
     * @param entrance entrance of the player
     * @param diningRoom dining room of the player
     */
    public SchoolBoard(TowerColor towerColor, int towerCount, Entrance entrance, DiningRoom diningRoom) {
        this.towerColor = towerColor;
        this.towerCount = towerCount;
        this.entrance   = entrance;
        this.diningRoom = diningRoom;
    }

    /**
     * Gets the tower color associated with the school board
     * @return An enum value representing the towers' color
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * Gets the number of towers currently on the board
     * @return An integer containing the number of towers
     */
    public int getTowerCount() {
        return towerCount;
    }

    /**
     * Sets the number of towers on the board to a specified value
     * @param towerCount An integer containing the number to be set (positive integer between 0 and 8)
     */
    public void setTowerCount(int towerCount) {
        assert towerCount >= 0 && towerCount <= 8: "TowerCount must be in the range [0, 8]";

        this.towerCount = towerCount;
    }

    //TODO: test

    /**
     * Increases the tower's count by one
     */
    public void increaseTowerCount() {
        increaseTowerCount(1);
    }

    /**
     * Increases the tower's count
     * @param n The number to add to the tower count
     */
    public void increaseTowerCount(int n) {
        assert towerCount + n <= 8: "TowerCount cannot be greater than 8";

        towerCount += n;
    }

    /**
     * Decreases the tower's count by one
     */
    public void decreaseTowerCount() {
        decreaseTowerCount(1);
    }

    /**
     * Decreases the tower's count
     * @param n The number to subtract to the tower count
     */
    public void decreaseTowerCount(int n) {
        assert towerCount >= n: "TowerCount cannot be negative";

        towerCount -= n;
    }

    /**
     * Gets the entrance associated with the school board
     * @return A reference to the school board's entrance
     */
    public Entrance getEntrance() { return entrance; }

    /**
     * Sets the entrance associated with the school board
     * @param entrance A reference to the school board's entrance
     */
    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    /**
     * Gets the dining room associated with the school board
     * @return A reference to the school board's dining room
     */
    public DiningRoom getDiningRoom() {
        return diningRoom;
    }
    /**
     * Sets the dining room associated with the school board
     * @param diningRoom A reference to the school board's dining room
     */
    public void setDiningRoom(DiningRoom diningRoom) {
        this.diningRoom = diningRoom;
    }
}
