package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.Color;

import java.util.Arrays;

/**
 * Command used by the client to reply after ActionPhase's CloudTile choice of the current player
 * @author Mattia Martelli
 */
public class GameCommandChooseCloud implements GameCommand {
    private final Color[] studentsOnCloud;

    public GameCommandChooseCloud(Color[] studentsOnCloud) {
        this.studentsOnCloud = studentsOnCloud;
    }

    public Object executeCommand() { return studentsOnCloud; }
}
