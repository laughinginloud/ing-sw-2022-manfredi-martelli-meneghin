package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.ControllerData;

public class GameCommandMoveMotherNature implements GameCommand {
    private final int movementPoints;

    public GameCommandMoveMotherNature(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public Object executeCommand() {
        ControllerData.getInstance().getGameModel().moveMotherNature(movementPoints);
        return null;
    }
}
