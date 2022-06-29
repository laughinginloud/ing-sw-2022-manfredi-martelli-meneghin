package it.polimi.ingsw.common.model;

/**
 * Class representing CharacterCards that need noEntryTile on them - HERBALIST
 * @author Sebastiano Meneghin
 */
public final class CharacterCardNoEntry extends CharacterCard {
    private int noEntryCount;

    /**
     * Constructor of 'CharacterCardNoEntry': initializes field "noEntryCount" to 4
     * @param character The character of the specific CharacterCard to construct (equal to HERBALIST)
     */
    CharacterCardNoEntry (Character character) {
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
     */
    public void setNoEntryCount (int noEntryCount) {
        assert noEntryCount >= 0 && noEntryCount <= 4: "CharacterCardNoEntry.noEntryCount accepted value is between 0 and 4, you've inserted" + noEntryCount;

        this.noEntryCount = noEntryCount;
    }
}
