package it.polimi.ingsw.model;

/**
 * Class representing a CharacterCard
 * @author Sebastiano Meneghin
 */
public class CharacterCard {
    private       int cost;
    private final int cardID;

    protected CharacterCard(int cardID) { this.cardID = cardID; }

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
}
