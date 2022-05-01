package it.polimi.ingsw.model;

/**
 * Class representing a CharacterCard
 * @author Sebastiano Meneghin
 */
public class CharacterCard {
    private       int     cost;
    private final int     cardID;
    private       boolean hasCoin;

    // region Constructor and Builder

    protected CharacterCard(int cardID) throws IllegalArgumentException {
        //Set the cost of the Card depending on its cardID
        switch (cardID) {
            case 0, 3, 6, 9  -> setCost(1);
            case 1, 4, 7, 10 -> setCost(2);
            case 2, 5, 8, 11 -> setCost(3);
            default          -> throw new IllegalArgumentException("CharacterCard.cardID accepted value is between 0 and 11");
        }

        this.cardID = cardID;
        hasCoin     = false;
    }

    /**
     * Build a new CharacterCard according to the cardID is given (like a factory)
     * @param cardID The cardID of a specific CharacterCard (CharacterCard are ordinal numbered according to their order in the Eriantys Manual)
     * @return A new CharacterCard object of a specific class
     */
    //CharacterCard are ordinal numbered according to their order in the Eriantys Manual
    public static CharacterCard build (int cardID) throws IllegalArgumentException {
        if (cardID < 0 || cardID > 11)
            throw new IllegalArgumentException("CharacterCard.cardID accepted value is between 0 and 11");

        //CharacterCards that need students on them
        if (cardID == 0 || cardID == 6 || cardID == 10)
            return new CharacterCardStudent(cardID);

        //CharacterCard that needs NoEntryTile on it
        else if (cardID == 4)
            return new CharacterCardNoEntry(cardID);

        else
            return new CharacterCard(cardID);
    }

    // endregion

    // region Getter

    /**
     * Get the current cost of the CharacterCard
     * @return The cost of the CharacterCard
     */
    public int getCost() { return this.cost; }

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

    // endregion

    // region Setter

    /**
     * Set the cost of the CharacterCard to a specific value
     * @param cost The value to be set to
     */
    public void setCost(int cost) throws IllegalArgumentException {
        if (cost > 4)
            throw new IllegalArgumentException("CharacterCard.cost accepted value is maximum 4");

        this.cost = cost;
    }

    /**
     * Set the flag hasCoin to a specific value
     * @param hasCoin The value to be set (true or false)
     */
    public void setHasCoin(boolean hasCoin) { this.hasCoin = hasCoin; }

    // endregion
}
