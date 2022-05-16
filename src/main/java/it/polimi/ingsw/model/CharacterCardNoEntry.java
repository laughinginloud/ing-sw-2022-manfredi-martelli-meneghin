package it.polimi.ingsw.model;

/**
 * Class representing CharacterCards that need noEntryTile on them - HERBALIST
 * @author Sebastiano Meneghin
 */
public class CharacterCardNoEntry extends CharacterCard {
    private int noEntryCount;

    /**
     * Constructor of 'CharacterCardNoEntry': initializes field "noEntryCount" to 4
     * @param character The character of the specific CharacterCard to construct (equal to HERBALIST)
     */
    protected CharacterCardNoEntry (Character character) {
        super(character);
        noEntryCount = 4;
    }

    /**
     * Gets the field "noEntryCount"
     * @return The value of the field (positive integer between 0 and 4)
     */
    public int getNoEntryCount() { return this.noEntryCount; }

    /**
     * Sets the field "noEntryCount" to a specific value
     * @param noEntryCount The value to be set at (positive integer between 0 and 4)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setNoEntryCount (int noEntryCount) throws IllegalArgumentException {
        if (noEntryCount < 0 || noEntryCount > 4)
            throw new IllegalArgumentException("CharacterCardNoEntry.noEntryCount accepted value is between 0 and 4");

        this.noEntryCount = noEntryCount;
    }
}
