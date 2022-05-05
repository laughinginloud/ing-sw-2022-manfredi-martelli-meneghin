package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.controller.ControllerData;

public class GameCommandMoveMotherNature implements GameCommand {
    private final int movementPoints;

    public GameCommandMoveMotherNature(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public Object executeCommand() {
        ControllerData.getInstance().getGameModel().movemotherNature(movementPoints);
        return null;
    }

    public void checkLegalValue(int cardPoints) throws IllegalArgumentException {
        if (cardPoints < movementPoints || movementPoints < 0)
            throw new IllegalArgumentException();
    }
}
