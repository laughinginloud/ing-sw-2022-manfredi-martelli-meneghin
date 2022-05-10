package it.polimi.ingsw.model;

/**
 * Class representing a CharacterCard
 * @author Sebastiano Meneghin
 */
public class CharacterCard {
    private       int       cost;
    private final Character character;
    private       boolean   hasCoin;

    // region Constructor and Builder

    protected CharacterCard(Character character) throws IllegalArgumentException {
        //Set the cost of the Card depending on its character
        switch (character) {
            case MONK, MAGICIAN, JESTER, BARD              -> setCost(1);
            case FARMER, HERBALIST, CAVALIER, PRINCESS     -> setCost(2);
            case STANDARD_BEARER, CENTAUR, MERCHANT, THIEF -> setCost(3);
        }

        this.character = character;
        hasCoin        = false;
    }

    /**
     * Build a new CharacterCard according to the character is given (like a factory)
     * @param character The character of a specific CharacterCard (CharacterCard are enum according to their order in the Eriantys Manual)
     * @return A new CharacterCard object of a specific class
     */
    public static CharacterCard build (Character character) throws IllegalArgumentException {
        //CharacterCards that need students on them
        if (character == Character.MONK || character == Character.JESTER || character == Character.PRINCESS)
            return new CharacterCardStudent(character);

        //CharacterCard that needs NoEntryTile on it
        else if (character == Character.HERBALIST)
            return new CharacterCardNoEntry(character);

        else
            return new CharacterCard(character);
    }

    // endregion

    // region Getter

    /**
     * Get the current cost of the CharacterCard
     * @return The cost of the CharacterCard
     */
    public int getCost() { return this.cost; }

    /**
     * Get the character of the CharacterCard
     * @return The character of the CharacterCard
     */
    public Character getCharacter() { return this.character; }

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
