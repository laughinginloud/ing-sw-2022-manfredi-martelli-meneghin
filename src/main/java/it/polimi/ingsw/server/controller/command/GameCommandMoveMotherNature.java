package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.ControllerData;

/**
 * Move mother nature by the number of spaces decided by the player
 * @author Mattia Martelli & Sebastiano Meneghin
 */
public final class GameCommandMoveMotherNature implements GameCommand {
    private final int islandIndex;

    /**
     * Build the command specifying the island mother nature will move to
     * @param islandIndex The index of the island
     */
    public GameCommandMoveMotherNature(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    /**
     * Move mother nature to the island specified
     * @return <code>null</code>
     */
    public Object executeCommand() {
        ControllerData.getInstance().getGameModel().setMotherNaturePosition(islandIndex);
        return null;
    }
}
