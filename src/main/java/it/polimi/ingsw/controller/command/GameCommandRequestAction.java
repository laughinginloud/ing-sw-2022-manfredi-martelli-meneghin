package it.polimi.ingsw.controller.command;

import java.util.HashMap;
import java.util.Map;

public class GameCommandRequestAction implements GameCommand{
    Map<GameCommandActions, Object> valueData;

    public GameCommandRequestAction(GameCommandActions value, Object data) {
        this.valueData = new HashMap<>();
        this.valueData.put(value, data);
    }

    public Object executeCommand() { return valueData; }
}
