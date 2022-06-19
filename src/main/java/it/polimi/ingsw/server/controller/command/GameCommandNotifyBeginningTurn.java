package it.polimi.ingsw.server.controller.command;

/**
 * Command used by the server to notify the players about the beginning of
 * another player's ActionPhase Turn
 * @author Sebastiano Meneghin
 */
public class GameCommandNotifyBeginningTurn implements GameCommand {
    private final String username;

    public GameCommandNotifyBeginningTurn(String username) { this.username = username; }

    public Object executeCommand() { return this.username; }
}
