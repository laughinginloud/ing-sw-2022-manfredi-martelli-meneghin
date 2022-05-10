package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.controller.ControllerData;

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
