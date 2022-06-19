package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.ControllerData;

public class GameCommandMoveMotherNature implements GameCommand {
    private final int islandIndex;

    public GameCommandMoveMotherNature(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    public Object executeCommand() {
        ControllerData.getInstance().getGameModel().setMotherNaturePosition(islandIndex);
        return null;
    }
}
