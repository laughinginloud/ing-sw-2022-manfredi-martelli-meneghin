package it.polimi.ingsw.model;

/**
 * Player decorator adding the concept of team
 * @author Mattia Martelli
 */
public class PlayerDecoratorTeam extends PlayerDecorator {
    private final Player teamMember;

    public PlayerDecoratorTeam(Player basePlayer, Player teamMember) {
        super(basePlayer);
        this.teamMember = teamMember;
    }

    /**
     * Returns the team member of the player
     * @return A pointer to the team member
     */
    public Player getTeamMember() {
        return teamMember;
    }
}
