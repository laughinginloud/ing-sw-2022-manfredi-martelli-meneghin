package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

import java.util.Map;

public class GameCommandSendInfo implements GameCommand {
    Map<GameValues, Object> data;

    public GameCommandSendInfo(Map<GameValues, Object> data) {
        this.data = data;
    }

    public Object executeCommand() {
        return data;
    }
}
