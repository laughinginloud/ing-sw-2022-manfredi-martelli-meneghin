package it.polimi.ingsw.model;

/**
 * Class that inherits from Player used for 4 players game mode version - 'PlayerTeam'
 * @author Mattia Martelli
 */
public class PlayerTeam extends Player {
    private Player teamMember;

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
    public Player getTeamMember() {
        return teamMember;
    }

    /**
     * Sets the associated team member
     * @param teamMember A reference to the Player representing the team member
     * @throws IllegalArgumentException exception thrown when an illegalArgument is passed
     */
    public void setTeamMember(Player teamMember) throws IllegalArgumentException {
        if (teamMember == null)
            throw new IllegalArgumentException("teamMember is null");

        this.teamMember = teamMember;
    }
}
