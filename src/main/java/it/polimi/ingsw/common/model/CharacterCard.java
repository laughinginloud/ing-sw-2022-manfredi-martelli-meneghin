package it.polimi.ingsw.common.model;

/**
 * Class representing a CharacterCard
 * @author Sebastiano Meneghin
 */
public class CharacterCard {
    private       int       cost;
    private final Character character;
    private       boolean   hasCoin;

    // region Constructor and Builder

    /**
     * Constructor of the class 'CharacterCard'
     * @author Giovanni Manfredi & Sebastiano Meneghin
     * @param character the character the card needs to be initialized with (not null)
     */
    protected CharacterCard(Character character){
        //Sets the cost of the Card depending on its character
        switch (character) {
            case MONK, MAGICIAN, JESTER, BARD              -> setCost(1);
            case FARMER, HERBALIST, CAVALIER, PRINCESS     -> setCost(2);
            case STANDARD_BEARER, CENTAUR, MERCHANT, THIEF -> setCost(3);
        }

        this.character = character;
        hasCoin        = false;
    }

    /**
     * Builds a new CharacterCard according to the character is given (factory pattern)
     * @author Giovanni Manfredi & Sebastiano Meneghin
     * @param character The character of a specific CharacterCard
     *                  (CharacterCard are enum according to their order in the Eriantys Manual) - not null
     * @return A new CharacterCard object of a specific class
     */
    public static CharacterCard build (Character character){
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
     * Gets the current cost of the CharacterCard
     * @return The cost of the CharacterCard (positive integer between 1 and 4)
     */
    public int getCost() { return this.cost; }

    /**
     * Gets the character of the CharacterCard
     * @author Giovanni Manfredi
     * @return The character of the CharacterCard
     */
    public Character getCharacter() { return this.character; }

    /**
     * Gets the flag hasCoin of CharacterCard
     * @return A Boolean representing the absence/presence of the coin on the CharacterCard
     */
    public boolean getHasCoin() { return hasCoin; }

    // endregion

    // region Setter

    /**
     * Sets the cost of the CharacterCard to a specific value
     * @param cost The value to be set to (positive integer between 1 and 4)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setCost(int cost) throws IllegalArgumentException {
        if (cost > 4)
            throw new IllegalArgumentException("CharacterCard.cost accepted value is maximum 4");

        this.cost = cost;
    }

    /**
     * Sets the flag hasCoin to a specific value
     * @param hasCoin The value to be set (true or false)
     */
    public void setHasCoin(boolean hasCoin) { this.hasCoin = hasCoin; }

    // endregion
}