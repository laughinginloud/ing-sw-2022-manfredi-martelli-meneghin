package it.polimi.ingsw.controller.command;

public abstract class GameCommandRequestValue implements GameCommand {
    protected GameCommandValues value;

    public GameCommandRequestValue(GameCommandValues value) {
        this.value = value;
    }
}
