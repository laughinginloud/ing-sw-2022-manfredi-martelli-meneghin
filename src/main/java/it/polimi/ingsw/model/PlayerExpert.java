package it.polimi.ingsw.model;

/**
 * Player decorator adding the coins count
 * @author Mattia Martelli
 */
public class PlayerExpert extends Player {
    private int coinCount;

    public PlayerExpert(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
        super(playerID, username, playerWizard, schoolBoard);
    }

    /**
     * Returns the number of coins currently hold by the player
     * @return An integer representing the number of coins
     */
    public int getCoinCount() {
        return coinCount;
    }

    /**
     * Set the number of coins currently hold by the player to specified amount
     * @param coinCount An integer representing the number of coins
     */
    public void setCoinCount(int coinCount) throws IllegalArgumentException {
        if (coinCount < 0 || coinCount > 15)
            throw new IllegalArgumentException("Coin count out of range");

        this.coinCount = coinCount;
    }
}
