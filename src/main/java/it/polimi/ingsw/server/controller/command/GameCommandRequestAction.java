package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.GameActions;

public class GameCommandRequestAction implements GameCommand {
    Tuple<GameActions, Object> valueData;

    public GameCommandRequestAction(GameActions value, Object data) {
        this.valueData = new Tuple<>(value, data);
    }

    public Object executeCommand() { return valueData; }
}
