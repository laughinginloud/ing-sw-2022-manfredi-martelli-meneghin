package it.polimi.ingsw.common.model;

/**
 * Class that inherits from Player used for 4 players game mode and Expert version - 'PlayerTeamExpert'
 * @author Mattia Martelli
 */
public final class PlayerTeamExpert extends Player {
    private Integer teamMemberID;
    private int     coinCount;

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

    // region Getter

    /**
     * Gets the team member of the player
     * @return A reference to the team member
     */
    public Integer getTeamMemberID() {
        return teamMemberID;
    }

    /**
    * Gets the number of coins currently hold by the player
    * @return An integer representing the number of coins (positive integer between 0 and 20)
    */
    public int getCoinCount() {
        return coinCount;
    }

    // endregion Getter

    // region Setter

    /**
     * Sets the associated team member
     * @param teamMemberID The ID of the team member
     */
    public void setTeamMemberID(int teamMemberID) {
        this.teamMemberID = teamMemberID;
    }

    /**
     * Sets the number of coins currently hold by the player to specified amount
     * @param coinCount An integer representing the number of coins (positive integer between 0 and 20)
     */
    public void setCoinCount(int coinCount) {
        assert coinCount >= 0 && coinCount <= 20: "Coin count out of range";

        this.coinCount = coinCount;
    }

    // endregion Setter
}
