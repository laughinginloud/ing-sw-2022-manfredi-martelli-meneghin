package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.Color;

import java.util.Arrays;

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

    public void checkLegalValue() throws IllegalArgumentException {
        if (Arrays.equals(ControllerData.getInstance().getGameModel().getCloudTile(cloudIndex).getStudents(), new Color[4]))
            throw new IllegalArgumentException();
    }
}
