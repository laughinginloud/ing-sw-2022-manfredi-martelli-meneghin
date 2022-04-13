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

    public SchoolBoard(TowerColor towerColor, Entrance entrance, DiningRoom diningRoom) {
        this.towerColor = towerColor;
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
    public void setTowerCount(int towerCount) {
        this.towerCount = towerCount;
    }
}
