package it.polimi.ingsw.model;

/**
 * Player decorator adding the concept of team
 * @author Mattia Martelli
 */
public class PlayerTeam extends Player {
    private final Player teamMember;

    public PlayerTeam(int playerID, String username, Wizard playerWizard, SchoolBoard schoolBoard, Player teamMember) {
        super(playerID, username, playerWizard, schoolBoard);
        this.teamMember = teamMember;
    }


    /*public PlayerTeam(Player basePlayer, Player teamMember) {
        super(basePlayer);
        this.teamMember = teamMember;
    }*/

    /**
     * Returns the team member of the player
     * @return A pointer to the team member
     */
    public Player getTeamMember() {
        return teamMember;
    }
}
