package it.polimi.ingsw.common.model;

/**
 * Class that inherits from Player used for 4 players game mode and Expert version - 'PlayerTeamExpert'
 * @author Mattia Martelli
 */
public class PlayerTeamExpert extends Player {
    private Player teamMember;
    private int    coinCount;

    /**
     * Constructor of the class 'PlayerTeamExpert'
     * @param playerID the player ID (positive integer between 0 and 3)
     * @param username the username of the player
     * @param playerWizard the Wizard associated with the player (not null)
     * @param schoolBoard the schoolBoard of the player (not null)
     */
    public PlayerTeamExpert(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
        super(playerID, username, playerWizard, schoolBoard);
    }

    /**
     * Gets the team member of the player
     * @return A reference to the team member
     */
    public Player getTeamMember() {
        return teamMember;
    }

    /**
     * Sets the associated team member
     * @param teamMember A pointer to the Player representing the team member
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setTeamMember(Player teamMember) throws IllegalArgumentException {
        if (teamMember == null)
            throw new IllegalArgumentException("teamMember is null");

        this.teamMember = teamMember;
    }

    /**
    * Gets the number of coins currently hold by the player
    * @return An integer representing the number of coins (positive integer between 0 and 20)
    */
    public int getCoinCount() {
        return coinCount;
    }

    /**
     * Sets the number of coins currently hold by the player to specified amount
     * @param coinCount An integer representing the number of coins (positive integer between 0 and 20)
     */
    public void setCoinCount(int coinCount) throws IllegalArgumentException {
        if (coinCount < 0 || coinCount > 15)
            throw new IllegalArgumentException("Coin count out of range");

        this.coinCount = coinCount;
    }
}
