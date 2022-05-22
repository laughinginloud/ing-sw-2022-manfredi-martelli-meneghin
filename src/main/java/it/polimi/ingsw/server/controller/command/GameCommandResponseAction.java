package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.utils.Tuple;

public class GameCommandResponseAction implements GameCommand {
    Tuple<GameActions, Object> valueResponse;

    public GameCommandResponseAction(GameActions value, Object response) { this.valueResponse = new Tuple<>(value, response); }

    public Object executeCommand(){ return valueResponse; }
}
