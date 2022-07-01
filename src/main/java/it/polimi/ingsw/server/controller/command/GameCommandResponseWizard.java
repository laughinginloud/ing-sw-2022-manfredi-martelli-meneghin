package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Wizard;

/**
 * Represent the response to a wizard request
 * @author Sebastiano Meneghin
 */
public final class GameCommandResponseWizard implements GameCommand {
    private final Wizard chosenWizard;

    /**
     * Build the command with the chosen wizard
     * @param chosenWizard A <code>Wizard</code> representing the choice
     */
    public GameCommandResponseWizard(Wizard chosenWizard) {
        this.chosenWizard = chosenWizard;
    }

    /**
     * Get the chosen wizard
     * @return A <code>Wizard</code> representing the choice
     */
    public Wizard executeCommand() {
        return chosenWizard;
    }
}
