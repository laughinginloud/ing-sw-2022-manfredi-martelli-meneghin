package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.viewRecord.GameRules;

/**
 * Represent the response to the request of the rules
 * @author Sebastiano Meneghin
 */
public final class GameCommandResponseRules implements GameCommand{
    private final GameRules gameRules;

    /**
     * Build the command with the rules
     * @param gameRules A <code>GameRules</code> containing the rules
     */
    public GameCommandResponseRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    /**
     * Get the rules
     * @return A <code>GameRules</code> containing the rules
     */
    public GameRules executeCommand() {
        return gameRules;
    }
}
