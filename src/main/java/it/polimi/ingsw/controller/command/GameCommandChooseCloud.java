package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.Color;

import java.lang.reflect.Array;
import java.util.Arrays;

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
