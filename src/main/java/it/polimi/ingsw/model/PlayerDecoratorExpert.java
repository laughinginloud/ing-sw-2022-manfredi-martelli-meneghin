package it.polimi.ingsw.model;

/**
 * Player decorator adding the coins count
 * @author Mattia Martelli
 */
public class PlayerDecoratorExpert extends PlayerDecorator {
    private int coinCount;

    public PlayerDecoratorExpert(Player basePlayer) {
        super(basePlayer);
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
    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }
}
