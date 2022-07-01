package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

/**
 * Get the player's username and magic age
 * @author Sebastiano Meneghin
 */
public final class GameCommandUsernameAndMagicAge implements GameCommand {
    private final UsernameAndMagicAge usernameAndMagicAge;

    /**
     * Build the command with the player's info
     * @param usernameAndMagicAge A <code>UsernameAndMagicAge</code> containing the info
     */
    public GameCommandUsernameAndMagicAge(UsernameAndMagicAge usernameAndMagicAge) {
        this.usernameAndMagicAge = usernameAndMagicAge;
    }

    /**
     * Get the player's info
     * @return A <code>UsernameAndMagicAge</code> containing the info
     */
    public UsernameAndMagicAge executeCommand() {
        return usernameAndMagicAge;
    }
}
