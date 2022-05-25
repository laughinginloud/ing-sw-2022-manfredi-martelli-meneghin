package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

//Server -> Client
public class GameCommandRequestValueClient extends GameCommandRequestValue {
    public GameCommandRequestValueClient(GameValues value) {
        super(value);
    }

    public Object executeCommand() {
        return value;
    }
}
