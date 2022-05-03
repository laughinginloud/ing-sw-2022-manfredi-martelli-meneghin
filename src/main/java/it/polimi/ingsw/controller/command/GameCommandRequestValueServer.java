package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.controller.GameController;

//Client -> Server
public class GameCommandRequestValueServer extends GameCommandRequestValue {
    public GameCommandRequestValueServer(GameCommandValues value) {
        super(value);
    }

    public Object executeCommand() {
        return GameController.requestValue(value);
    }
}
