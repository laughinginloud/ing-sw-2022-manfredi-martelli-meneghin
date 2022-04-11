package it.polimi.ingsw.model;

public class CharacterCardNoEntry extends CharacterCard{
    private int noEntryCount;

    /**
     * Construct a new "CharacterCardNoEntry and initialize field "noEntryCount" to 4
     * @param cardID The cardID of the specific CharacterCard to construct
     */
    protected CharacterCardNoEntry (int cardID) {
        super(cardID);
        noEntryCount = 4;
    }

    /**
     * Get the field "noEntrycount"
     * @return The value of the field
     */
    public int getNoEntryCount() { return this.noEntryCount; }

    /**
     * Set the field "noEntryCount" to a specific value
     * @param noEntryCount The value to be set
     */
    public void setNoEntryCount(int noEntryCount) { this.noEntryCount = noEntryCount; }
}
