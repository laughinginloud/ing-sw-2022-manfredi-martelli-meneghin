package it.polimi.ingsw.server.controller.command;

/**
 * Command used by the server to notify the players about the beginning of
 * another player's ActionPhase Turn
 * @author Sebastiano Meneghin
 */
public final class GameCommandNotifyBeginningTurn implements GameCommand {
    private final String username;

    /**
     * Build the command with the player beginning its turn
     * @param username The username of the player
     */
    public GameCommandNotifyBeginningTurn(String username) {
        this.username = username;
    }

    /**
     * Get the username
     * @return A <code>String</code> containing the username
     */
    public String executeCommand() {
        return this.username;
    }
}
