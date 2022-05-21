package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

public class GameCommandIllegalValue implements GameCommand {
    GameValues value = null;

    public GameCommandIllegalValue() {}

    public GameCommandIllegalValue(GameValues value) {
        this.value = value;
    }

    public Object executeCommand() {
        return value;
    }
}
