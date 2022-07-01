package it.polimi.ingsw.server.controller.command;

/**
 * Get the response to an action
 * @author Sebastiano Meneghin
 */
public final class GameCommandResponseAction implements GameCommand {
    private final Object response;

    /**
     * Build the command with the response
     * @param response An <code>Object</code> containing the response
     */
    public GameCommandResponseAction(Object response) {
        this.response = response;
    }

    /**
     * Get the response
     * @return An <code>Object</code> containing the response
     */
    public Object executeCommand() {
        return response;
    }
}
