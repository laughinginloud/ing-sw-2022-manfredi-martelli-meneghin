package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.ControllerData;

/**
 * Command used by the client to reply after ActionPhase's CloudTile choice of the current player
 * @author Mattia Martelli
 */
public class GameCommandChooseCloud implements GameCommand {
    private final int cloudIndex;

    public GameCommandChooseCloud(int cloudIndex) {
        this.cloudIndex = cloudIndex;
    }

    public Object executeCommand() {
        return ControllerData.getInstance().getGameModel().getCloudTile(cloudIndex).retrieveStudents();
    }
}
