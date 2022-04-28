package it.polimi.ingsw.model;

/**
 * Player decorator adding the concept of team
 * @author Mattia Martelli
 */
public class PlayerTeam extends Player {
    private Player teamMember;

    public PlayerTeam(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard) {
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
}
