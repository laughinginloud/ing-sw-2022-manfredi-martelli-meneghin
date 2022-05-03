package it.polimi.ingsw.controller.command;

public class GameCommandIllegalValue implements GameCommand {
    GameCommandValues value = null;

    public GameCommandIllegalValue() {}

    public GameCommandIllegalValue(GameCommandValues value) {
        this.value = value;
    }

    public Object executeCommand() {
        return value;
    }
}
