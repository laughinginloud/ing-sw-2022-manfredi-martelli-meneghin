package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.utils.Tuple;

public class GameCommandResponseAction implements GameCommand {
    private final Object response;

    public GameCommandResponseAction(Object response) { this.response = response; }

    public Object executeCommand(){ return response; }
}
