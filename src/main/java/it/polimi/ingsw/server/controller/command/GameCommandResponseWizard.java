package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Wizard;

public class GameCommandResponseWizard implements GameCommand{

    private final Wizard chosenWizard;

    public GameCommandResponseWizard(Wizard chosenWizard) { this.chosenWizard = chosenWizard; }

    public Object executeCommand() {
        return chosenWizard;
    }
}
