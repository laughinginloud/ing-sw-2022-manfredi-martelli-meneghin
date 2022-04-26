package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing the islands
 * @author Sebastiano Meneghin
 */
public class Island {
    private final int[]             studentCounters;
    private       int               multiplicity;
    private       TowerColor        towerColor;
    private       Optional<Integer> noEntryTileCount;
    private final List<Integer>     backgroundID;

    public Island() {
        studentCounters = new int[Color.values().length];
        setStudentCounters(0);
        setMultiplicity(0);
        noEntryTileCount = Optional.empty();
        //A merged island will consist of a maximum of 11 sub-islands, according to winning rules
        backgroundID = new ArrayList<>();
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
    public void setMultiplicity(int multiplicity) throws IllegalArgumentException {
        if(multiplicity < 0 || multiplicity > 12)
            throw new IllegalArgumentException("Island.multiplicity accepted value is between 0 and 12");

        this.multiplicity = multiplicity;
    }

    /**
     * Get the counter specific color
     * @param color The color of the counter
     * @return The value of the counter
     */
    public int getStudentCounters(Color color) { return studentCounters[color.ordinal()]; }

    /**
     * Set a specific counter to a value
     * @param color Color of the counter
     * @param n Value to be set
     */
    public void setStudentCounters(Color color, int n) throws IllegalArgumentException {
        if(n < 0 || n > 26)
            throw new IllegalArgumentException("Island.studentCounters accepted value is between 0 and 26");

        this.studentCounters[color.ordinal()] = n;
    }

    /**
     * Set all the counters to a value
     * @param n Value to be set for all counters
     */
    public void setStudentCounters(int n) {
        for (Color color : Color.values()) {
            setStudentCounters(color, n);
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
    public void setNoEntryTileCount(int noEntryTileCount) throws IllegalArgumentException {
        if(noEntryTileCount < 0 || noEntryTileCount > 4)
            throw new IllegalArgumentException("Island.noEntryTileCount accepted value is between 0 and 4");

        this.noEntryTileCount = Optional.of(noEntryTileCount);
    }

    /**
     * Get all the backgroundIDs assigned to the island, without deleting them
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

    /**
     * Add a backgroundID to the end of the backgroundID's list, modifying its length
     * @param id The backgroundID to add
     */
    public void addBackgroundID(int id) throws IllegalArgumentException {
        if (id < 0 || id > 3)
            throw new IllegalArgumentException("Island.backgroundID accepted value is between 0 and 3");

        backgroundID.add(id);
    }

    /**
     * Add every backgroundID of the array ids to the end of backgroundID's list, modifying its length
     * @param ids Array of backgroundID to add
     */
    public void addBackgroundID(int[] ids) throws IllegalArgumentException {
        if (ids == null)
            throw new IllegalArgumentException("addBackgroundID does not accept null arrays! Insert a not null int[]");

        for (int id : ids)
            addBackgroundID(id);
    }
}
