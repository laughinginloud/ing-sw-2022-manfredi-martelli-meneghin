package it.polimi.ingsw.common.model;

/**
 * Class that inherits from Player used for 4 players game mode version - 'PlayerTeam'
 * @author Mattia Martelli
 */
public final class PlayerTeam extends Player {
    private Integer teamMemberID;

    /**
     * Constructor of the class 'PlayerTeam'
     * @param playerID the player ID (positive integer between 0 and 3)
     * @param username the username of the player
     * @param playerWizard the Wizard associated with the player (not null)
     * @param schoolBoard the schoolBoard of the player (not null)
     */
    public PlayerTeam(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
        super(playerID, username, playerWizard, schoolBoard);
    }

    /**
     * Gets the team member of the player
     * @return A reference to the team member
     */
    public int getTeamMemberID() {
        return teamMemberID;
    }

    /**
     * Sets the associated team member
     * @param teamMemberID The ID of the team member
     */
    public void setTeamMemberID(int teamMemberID) {
        this.teamMemberID = teamMemberID;
    }
}
