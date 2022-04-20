package it.polimi.ingsw.model;

/**
 * Class representing a CharacterCard
 * @author Sebastiano Meneghin
 */
public class CharacterCard {
    private       int cost;
    private final int cardID;
    private       boolean hasCoin;

    protected CharacterCard(int cardID) {
        this.cardID = cardID;

        //Set the cost of the Card depending on its cardID
        if (cardID == 0 || cardID == 3 || cardID == 6 || cardID == 9)
            setCost(1);
        else if (cardID == 1 || cardID == 4 || cardID == 7 || cardID == 10)
            setCost(2);
        else if (cardID == 2 || cardID == 5 || cardID == 8 || cardID == 11)
            setCost(3);

        hasCoin = false;
    }

    /**
     * Build a new CharacterCard according to the cardID is given (like a factory)
     * @param cardID The cardID of a specific CharacterCard (CharacterCard are ordinal numbered according to their order in the Eriantys Manual)
     * @return A new CharacterCard object of a specific class
     */
    //CharacterCard are ordinal numbered according to their order in the Eriantys Manual
    public static CharacterCard build(int cardID) {
        //CharacterCards that need students on them
        if(cardID == 0 || cardID == 6 || cardID == 10)
            return new CharacterCardStudent(cardID);

        //CharacterCard that needs NoEntryTile on it
        else if (cardID == 4)
            return new CharacterCardNoEntry(cardID);

        else
            return new CharacterCard(cardID);
    }

    /**
     * Get the current cost of the CharacterCard
     * @return The cost of the CharacterCard
     */
    public int getCost() { return this.cost; }

    /**
     * Set the cost of the CharacterCard to a specific value
     * @param cost The value to be set to
     */
    public void setCost(int cost) { this.cost = cost; }

    /**
     * Get the cardID of the CharacterCard
     * @return The cardID of the CharacterCard
     */
    public int getCardID() { return this.cardID; }

    /**
     * Get the flag hasCoin of CharacterCard
     * @return A Boolean representing the absence/presence of the coin on the CharacterCard
     */
    public boolean getHasCoin() { return hasCoin; }

    /**
     * Set the flag hasCoin to a specific value
     * @param hasCoin The value to be set (true or false)
     */
    public void setHasCoin(boolean hasCoin) { this.hasCoin = hasCoin; }
}
