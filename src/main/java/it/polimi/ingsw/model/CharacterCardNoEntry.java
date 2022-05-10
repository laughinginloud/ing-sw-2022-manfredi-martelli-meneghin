package it.polimi.ingsw.model;

public class CharacterCardNoEntry extends CharacterCard {
    private int noEntryCount;

    /**
     * Construct a new "CharacterCardNoEntry and initialize field "noEntryCount" to 4
     * @param character The character of the specific CharacterCard to construct
     */
    protected CharacterCardNoEntry (Character character) {
        super(character);
        noEntryCount = 4;
    }

    /**
     * Get the field "noEntryCount"
     * @return The value of the field
     */
    public int getNoEntryCount() { return this.noEntryCount; }

    /**
     * Set the field "noEntryCount" to a specific value
     * @param noEntryCount The value to be set
     */
    public void setNoEntryCount (int noEntryCount) throws IllegalArgumentException {
        if (noEntryCount < 0 || noEntryCount > 4)
            throw new IllegalArgumentException("CharacterCardNoEntry.noEntryCount accepted value is between 0 and 4");

        this.noEntryCount = noEntryCount;
    }
}
