package it.polimi.ingsw.model;

/**
 * Class representing the school board of a player
 * @author Mattia Martelli
 */
public class SchoolBoard {
    private final TowerColor towerColor;
    private       int        towerCount;
    private final Entrance   entrance;
    private final DiningRoom diningRoom;

    public SchoolBoard(TowerColor towerColor, int towerCount, Entrance entrance, DiningRoom diningRoom) {
        this.towerColor = towerColor;
        this.towerCount = towerCount;
        this.entrance   = entrance;
        this.diningRoom = diningRoom;
    }

    /**
     * Returns the tower color associated with the school board
     * @return An enum value representing the towers' color
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * Returns the number of towers currently on the board
     * @return An integer containing the number of towers
     */
    public int getTowerCount() {
        return towerCount;
    }

    /**
     * Sets the number of towers on the board to a specified value
     * @param towerCount An integer containing the number to be set
     */
    public void setTowerCount(int towerCount) throws IllegalArgumentException {
        if (towerCount < 0 || towerCount > 8)
            throw new IllegalArgumentException("TowerCount must be an element of [0, 8]");

        this.towerCount = towerCount;
    }


    /**
     * Increase the tower's count by one
     */
    public void increaseTowerCount() throws IllegalStateException {
        if (towerCount >= 8)
            throw new IllegalStateException("Cannot have more than 8 towers");

        towerCount++;
    }


    /**
     * Decrease the tower's count by one
     */
    public void decreaseTowerCount() throws IllegalStateException {
        if (towerCount <= 0)
            throw new IllegalStateException("Cannot have negative towers");

        towerCount--;
    }

    /**
     * Returns the entrance associated with the school board
     * @return A pointer to the school board's entrance
     */
    public Entrance getEntrance() { return entrance; }

    /**
     * Returns the dining room associated with the school board
     * @return A pointer to the school board's dining room
     */
    public DiningRoom getDiningRoom() { return diningRoom; }
}
