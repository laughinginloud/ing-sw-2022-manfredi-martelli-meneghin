package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;

import java.util.Map;

public class GameCommandSendInfo implements GameCommand {
    private final InfoMap data;

    public GameCommandSendInfo(InfoMap data) {
        this.data = data;
    }

    public Object executeCommand() {
        return data;
    }
}
