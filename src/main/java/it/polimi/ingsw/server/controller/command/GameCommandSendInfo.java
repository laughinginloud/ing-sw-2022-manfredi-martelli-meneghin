package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.message.InfoMap;

/**
 * Send various model info to the players
 * @author Mattia Martelli
 */
public final class GameCommandSendInfo implements GameCommand {
    private final InfoMap data;

    /**
     * Build the command with the info to send
     * @param data An <code>InfoMap</code> containing the info
     */
    public GameCommandSendInfo(InfoMap data) {
        this.data = data;
    }

    /**
     * Get the info sent
     * @return An <code>InfoMap</code> containing the info
     */
    public InfoMap executeCommand() {
        return data;
    }
}
