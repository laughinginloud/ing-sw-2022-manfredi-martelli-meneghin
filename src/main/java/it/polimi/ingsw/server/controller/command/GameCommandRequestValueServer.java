package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.server.controller.GameController;

//Client -> Server
public class GameCommandRequestValueServer extends GameCommandRequestValue {
    public GameCommandRequestValueServer(GameCommandValues value) {
        super(value);
    }

    public Object executeCommand() {
        return GameController.requestValue(value);
    }
}
