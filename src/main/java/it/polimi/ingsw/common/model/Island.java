package it.polimi.ingsw.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the islands
 * @author Sebastiano Meneghin
 */
public class Island {

    // region Fields

    private final int[]         studentCounters;
    private       int           multiplicity;
    private       TowerColor    towerColor;
    private       Integer       noEntryTileCount;
    private final List<Integer> backgroundID;

    // endregion

    //region Constructor

    /**
     * Constructor of the class 'Island'
     */
    public Island() {
        studentCounters = new int[Color.values().length];
        setStudentCounters(0);
        setMultiplicity(0);
        noEntryTileCount = null;
        //A merged island will consist of a maximum of 11 sub-islands, according to winning rules
        backgroundID = new ArrayList<>();
        towerColor = null;
    }

    // endregion

    //region Getter

    /**
     * Gets the current TowerColor on the Island. This color is associated to the player who is controlling the Island
     * @return The color of towerColor
     */
    public TowerColor getTowerColor() { return towerColor; }

    /**
     * Gets the multiplicity, the number of sub-island this island consist in
     * @return An int representing the multiplicity
     */
    public int getMultiplicity() { return multiplicity; }

    /**
     * Gets the counter of a specific color
     * @param color The color of the counter (not null)
     * @return The value of the counter
     */
    public int getStudentCounters(Color color) { return studentCounters[color.ordinal()]; }

    /**
     * Gets the number of noEntryTiles currently on the island
     * @return An Integer representing noEntryTilesCount, null if there are no noEntryTiles on the island.
     */
    public Integer getNoEntryTileCount() { return noEntryTileCount; }

    /**
     * Gets all the backgroundIDs assigned to the island, without deleting them
     * @return An int array of island's backgroundIDs
     */
    public int[] getBackgroundID() {
        int[] temp = null;
        if (backgroundID.size() > 0){
            temp = new int[backgroundID.size()];
            for (int i = 0; i < backgroundID.size(); i++)
                temp[i] = backgroundID.get(i);
        }

        return temp;
    }

    // endregion

    // region Setter

    /**
     * Sets towerColor to a specific TowerColor
     * @param towerColor The color to set towerColor to (not null)
     */
    public void setTowerColor(TowerColor towerColor) { this.towerColor = towerColor; }

    /**
     * Sets the multiplicity to a value
     * @param multiplicity Value to be set (positive integer between 0 and 12)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setMultiplicity(int multiplicity) throws IllegalArgumentException {
        if(multiplicity < 0 || multiplicity > 12)
            throw new IllegalArgumentException("Island.multiplicity accepted value is between 0 and 12");

        this.multiplicity = multiplicity;
    }

    /**
     * Sets a specific counter to a value
     * @param color Color of the counter (not null)
     * @param n Value to be set (positive integer between 0 and 26)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setStudentCounters(Color color, int n) throws IllegalArgumentException {
        if(n < 0 || n > 26)
            throw new IllegalArgumentException("Island.studentCounters accepted value is between 0 and 26");

        this.studentCounters[color.ordinal()] = n;
    }

    /**
     * Set all the counters to a value
     * @param n Value to be set for all counters (positive integer between 0 and 26)
     */
    public void setStudentCounters(int n) {
        for (Color color : Color.values()) {
            setStudentCounters(color, n);
        }
    }

    /**
     * Sets noEntryTileCount to a value
     * @param noEntryTileCount Value to be set (positive integer between 0 and 4)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setNoEntryTileCount(int noEntryTileCount) throws IllegalArgumentException {
        if(noEntryTileCount < 0 || noEntryTileCount > 4)
            throw new IllegalArgumentException("Island.noEntryTileCount accepted value is between 0 and 4");

        this.noEntryTileCount = noEntryTileCount;
    }

    // endregion

    // region Others

    /**
     * Adds a backgroundID to the end of the backgroundID's list, modifying its length
     * @param id The backgroundID to add (positive integer between 0 and 3)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void addBackgroundID(int id) throws IllegalArgumentException {
        if (id < 0 || id > 3)
            throw new IllegalArgumentException("Island.backgroundID accepted value is between 0 and 3");

        backgroundID.add(id);
    }

    /**
     * Adds every backgroundID of the array ids to the end of backgroundID's list, modifying its length
     * @param ids Array of backgroundID to add (not null)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void addBackgroundID(int[] ids) throws IllegalArgumentException {
        if (ids == null)
            throw new IllegalArgumentException("addBackgroundID does not accept null arrays! Insert a not null int[]");

        for (int id : ids)
            addBackgroundID(id);
    }

    // endregion
}
