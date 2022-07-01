package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Player;

import java.util.List;

/**
 * Command representing the end of a game, containing the result
 * @author Mattia Martelli
 */
public final class GameCommandEndGame implements GameCommand {
    private final Player       winner;
    private final List<Player> team;
    private final boolean      draw;

    /**
     * Build the command with a single winner
     * @param winner The winning player
     */
    public GameCommandEndGame(Player winner) {
        this.winner = winner;
             team   = null;
             draw   = false;
    }

    /**
     * Build the command with a set of "drawers" or the winning team
     * @param team The players that have won the game (team or "drawers")
     * @param draw <code>true</code> if the players passed represent the "drawers",
     *             <code>false</code> if they represent the winning team
     */
    public GameCommandEndGame(List<Player> team, boolean draw) {
             winner = null;
        this.team   = team;
        this.draw   = draw;
    }

    /**
     * Return the winner or the winning team
     * @return A single <code>Player</code> or an array of them
     */
    @SuppressWarnings("ConstantConditions")
    public Object executeCommand() {
        if (winner != null)
            return winner;

        return team.toArray(Player[]::new);
    }

    /**
     * Check whether the game ended in a draw
     * @return <code>true</code> if the game ended in a draw, <code>false</code> otherwise
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * Check whether this command contains a team of players
     * @return <code>true</code> if the return of the command will be an array of players,
     *         <code>false</code> if it will be a single player
     */
    public boolean isTeam() {
        return winner == null;
    }
}
