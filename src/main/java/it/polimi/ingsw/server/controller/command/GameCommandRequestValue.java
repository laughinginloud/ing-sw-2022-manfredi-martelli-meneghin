package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

public abstract class GameCommandRequestValue implements GameCommand {
    protected GameValues value;

    public GameCommandRequestValue(GameValues value) {
        this.value = value;
    }
}
