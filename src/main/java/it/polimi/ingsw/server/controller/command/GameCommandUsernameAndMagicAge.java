package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

public class GameCommandUsernameAndMagicAge implements GameCommand{

    private final UsernameAndMagicAge usernameAndMagicAge;

    public GameCommandUsernameAndMagicAge(UsernameAndMagicAge usernameAndMagicAge) { this.usernameAndMagicAge = usernameAndMagicAge; }

    public Object executeCommand() {
        return usernameAndMagicAge;
    }
}
