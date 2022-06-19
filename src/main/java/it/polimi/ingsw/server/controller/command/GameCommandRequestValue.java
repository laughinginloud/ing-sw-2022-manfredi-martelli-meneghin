package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

/**
 * Request a value to the client
 * @author Mattia Martelli
 */
public class GameCommandRequestValue implements GameCommand {
    private final GameValues value;

    public GameCommandRequestValue(GameValues value) {
        this.value = value;
    }

    public Object executeCommand() {
        return value;
    }
}
