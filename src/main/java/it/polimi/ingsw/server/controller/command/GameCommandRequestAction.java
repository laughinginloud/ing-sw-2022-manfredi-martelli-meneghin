package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameActions;

import java.util.HashMap;
import java.util.Map;

public class GameCommandRequestAction implements GameCommand{
    Map<GameActions, Object> valueData; //TODO: record?

    public GameCommandRequestAction(GameActions value, Object data) {
        this.valueData = new HashMap<>();
        this.valueData.put(value, data);
    }

    public Object executeCommand() { return valueData; }
}
