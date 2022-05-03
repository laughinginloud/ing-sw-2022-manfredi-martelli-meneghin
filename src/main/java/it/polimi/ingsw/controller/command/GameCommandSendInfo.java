package it.polimi.ingsw.controller.command;

import java.util.HashMap;
import java.util.Map;

public class GameCommandSendInfo implements GameCommand {
    Map<GameCommandValues, Object> data;

    public GameCommandSendInfo(Map<GameCommandValues, Object> data) {
        this.data = data;
    }

    public Object executeCommand() {
        return data;
    }
}
