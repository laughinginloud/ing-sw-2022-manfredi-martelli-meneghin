package it.polimi.ingsw.controller.command;

public class GameCommandIllegalCommand implements GameCommand {
    String message = "";

    public GameCommandIllegalCommand() {}

    public GameCommandIllegalCommand(String message) {
        this.message = message;
    }

    public Object executeCommand() {
        return message;
    }
}
