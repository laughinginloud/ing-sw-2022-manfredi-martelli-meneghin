package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.GameActions;

/**
 * Represent the request of an action from the controller to a player
 * @author Sebastiano Meneghin
 */
public final class GameCommandRequestAction implements GameCommand {
    private final Tuple<GameActions, Object> valueData;

    /**
     * Build the request
     * @param value A <code>GameActions</code> representing the action requested
     * @param data  Additional data that may be required by the specified action
     */
    public GameCommandRequestAction(GameActions value, Object data) {
        this.valueData = new Tuple<>(value, data);
    }

    /**
     * Get the action requested
     * @return A <code>Tuple&lt;GameActions, Object&gt;</code> representing the request
     */
    public Tuple<GameActions, Object> executeCommand() {
        return valueData;
    }
}
