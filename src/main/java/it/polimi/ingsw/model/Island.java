package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * Class representing the islands
 * @author Sebastiano Meneghin
 */
public class Island {
    private int[] studentCounters;
    private int multiplicity;
    private TowerColor towerColor;
    private Optional<Integer> noEntryTileCount;
    private int[] backgroundID;

    public Island() {
        studentCounters = new int[Color.values().length];
        setStudentCounter(0);
        setMultiplicity(0);
        noEntryTileCount = Optional.empty();
        //A merged island will consist of a maximum of 11 sub-islands, according to winning rules
        backgroundID = new int[11];
    }

    /**
     * Get the current TowerColor on the Island. This color is associated to the player who is controlling the Island
     * @return The color of towerColor
     */
    public TowerColor getTowerColor() { return towerColor; }

    /**
     * Set towerColor to a specific TowerColor
     * @param towerColor The color to set towerColor to
     */
    public void setTowerColor(TowerColor towerColor) { this.towerColor = towerColor; }

    /**
     * Get the multiplicity, the number of sub-island this island consist in
     * @return An int representing the multiplicity
     */
    public int getMultiplicity() { return multiplicity; }

    /**
     * Set the multiplicity to a value
     * @param multiplicity Value to be set
     */
    public void setMultiplicity(int multiplicity) { this.multiplicity = multiplicity; }

    /**
     * Get the counter specific color
     * @param color The color of the counter
     * @return The value of the counter
     */
    public int getStudentCounter(Color color) { return studentCounters[color.ordinal()]; }

    /**
     * Set a specific counter to a value
     * @param color Color of the counter
     * @param n Value to be set
     */
    public void setStudentCounter(Color color, int n) { this.studentCounters[color.ordinal()] = n; }

    /**
     * Set all the counters to a value
     * @param n Value to be set for all counters
     */
    public void setStudentCounter(int n) {
        for (Color color : Color.values()) {
            setStudentCounter(color, n);
        }
    }

    /**
     * Get the number of noEntryTiles currently on the island
     * @return An Integer representing noEntryTilesCount, null if there are no noEntryTiles on the island.
     */
    public Optional<Integer> getNoEntryTileCount() { return noEntryTileCount; }

    /**
     *Set noEntryTileCount to a value
     * @param noEntryTileCount Value to be set
     */
    public void setNoEntryTileCount(Optional<Integer> noEntryTileCount) { this.noEntryTileCount = noEntryTileCount; }

    /**
     * Get all the backgroundIDs assigned to the island, without deleting them
     * @return An array of island's backgroundIDs
     */
    public int[] getBackgroundID() { return backgroundID; }

    /**
     * Add a backgroundID to backgroundID's array on first free position, without modifying its length
     * @param id The backgroundID to add
     */
    public void addBackgroundID(int id) { backgroundID[multiplicity - 1] = id; }

    /**
     * Add every backgroundID of the array ids to backgroundID's array on first free positions, without modifying its length
     * @param ids Array of backgroundID to add
     */
    public void addBackgroundID(int[] ids) {
        System.arraycopy(ids, 0, backgroundID, multiplicity - ids.length, backgroundID.length);
    }









}
