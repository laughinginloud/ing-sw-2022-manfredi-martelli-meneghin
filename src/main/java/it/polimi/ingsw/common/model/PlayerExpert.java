package it.polimi.ingsw.common.model;

/**
 * Class that inherits from Player used for the 'ExpertGame' version - 'PlayerExpert'
 * @author Mattia Martelli
 */
public class PlayerExpert extends Player {
    private int coinCount;

    /**
     * Constructor of the class 'PlayerExpert'
     * @param playerID the player ID (positive integer between 0 and 3)
     * @param username the username of the player
     * @param playerWizard the Wizard associated with the player (not null)
     * @param schoolBoard the schoolBoard of the player (not null)
     */
    public PlayerExpert(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
        super(playerID, username, playerWizard, schoolBoard);
    }

    /**
     * Gets the number of coins currently held by the player
     * @return An integer representing the number of coins
     */
    public int getCoinCount() {
        return coinCount;
    }

    /**
     * Sets the number of coins currently held by the player to specified amount
     * @param coinCount An integer representing the number of coins (positive integer from 0 to 20)
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setCoinCount(int coinCount) throws IllegalArgumentException {
        if (coinCount < 0 || coinCount > 20)
            throw new IllegalArgumentException("Coin count out of range");

        this.coinCount = coinCount;
    }
}
