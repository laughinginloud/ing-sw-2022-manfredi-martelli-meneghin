package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

/**
 * Request a value to the client
 * @author Mattia Martelli
 */
public final class GameCommandRequestValue implements GameCommand {
    private final GameValues value;

    /**
     * Build the request
     * @param value A <code>GameValues</code> representing the value requested
     */
    public GameCommandRequestValue(GameValues value) {
        this.value = value;
    }

    /**
     * Get the value requested
     * @return A <code>GameValues</code> representing the value requested
     */
    public GameValues executeCommand() {
        return value;
    }
}
