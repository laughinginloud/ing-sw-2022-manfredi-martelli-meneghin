package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.server.controller.ControllerData;

/**
 * Command used by the client to reply after ActionPhase's CloudTile choice of the current player
 * @author Mattia Martelli
 */
public final class GameCommandChooseCloud implements GameCommand {
    private final int cloudIndex;

    /**
     * Build the command, setting the cloud that will be emptied
     * @param cloudIndex The index of the cloud to empty
     */
    public GameCommandChooseCloud(int cloudIndex) {
        this.cloudIndex = cloudIndex;
    }

    /**
     * Empty the cloud pointed by this command
     * @return The students currently on the tile
     */
    public Color[] executeCommand() {
        return ControllerData.getInstance().getGameModel().getCloudTile(cloudIndex).retrieveStudents();
    }
}
