package it.polimi.ingsw.server.controller.command;

public abstract class GameCommandRequestValue implements GameCommand {
    protected GameCommandValues value;

    public GameCommandRequestValue(GameCommandValues value) {
        this.value = value;
    }
}
