package it.polimi.ingsw.common.model;

/**
 * Class representing the school board of a player
 * @author Mattia Martelli
 */
public class SchoolBoard {
    private final TowerColor towerColor;
    private       int        towerCount;
    private final Entrance   entrance;
    private final DiningRoom diningRoom;

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
        if (towerCount < 0 || towerCount > 8)
            return;

        this.towerCount = towerCount;
    }

    /**
     * Increases the tower's count by one
     */
    public void increaseTowerCount() {
        increaseTowerCount(1);
    }

    public void increaseTowerCount(int n) {
        if (towerCount + n > 8)
            throw new IllegalStateException();

        towerCount += n;
    }

    /**
     * Decreases the tower's count by one
     */
    public void decreaseTowerCount() {
        decreaseTowerCount(1);
    }

    public void decreaseTowerCount(int n) {
        if (towerCount < n)
            return;

        towerCount -= n;
    }

    /**
     * Gets the entrance associated with the school board
     * @return A reference to the school board's entrance
     */
    public Entrance getEntrance() { return entrance; }

    /**
     * Gets the dining room associated with the school board
     * @return A reference to the school board's dining room
     */
    public DiningRoom getDiningRoom() { return diningRoom; }
}
