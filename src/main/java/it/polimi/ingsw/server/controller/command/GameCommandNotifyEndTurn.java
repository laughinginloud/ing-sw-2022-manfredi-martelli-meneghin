package it.polimi.ingsw.server.controller.command;

/**
 * Command used by the server to notify the client about the end of it's turn
 * @author Sebastiano Meneghin
 */
public final class GameCommandNotifyEndTurn implements GameCommand {

    public GameCommandNotifyEndTurn() {}

    public Object executeCommand() {
        return null;
    }
}
