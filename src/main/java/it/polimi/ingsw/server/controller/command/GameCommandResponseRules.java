package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.viewRecord.GameRules;

public class GameCommandResponseRules implements GameCommand{

    private final GameRules gameRules;

    public GameCommandResponseRules(GameRules gameRules) { this.gameRules = gameRules; }

    public Object executeCommand() {
        return gameRules;
    }
}
