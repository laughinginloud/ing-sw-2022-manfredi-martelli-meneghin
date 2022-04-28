package it.polimi.ingsw.model;

public class PlayerTeamExpert extends Player {
    private Player teamMember;
    private int    coinCount;

    public PlayerTeamExpert(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
        super(playerID, username, playerWizard, schoolBoard);
    }

    /**
     * Returns the team member of the player
     * @return A pointer to the team member
     */
    public Player getTeamMember() {
        return teamMember;
    }

    /**
     * Sets the associated team member
     * @param teamMember A pointer to the Player representing the team member
     */
    public void setTeamMember(Player teamMember) throws IllegalArgumentException {
        if (teamMember == null)
            throw new IllegalArgumentException("teamMember is null");

        this.teamMember = teamMember;
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
