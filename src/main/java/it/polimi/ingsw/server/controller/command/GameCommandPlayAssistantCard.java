package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.AssistantCard;

/**
 * Get the assistant card played
 * @author Sebastiano Meneghin
 */
public final class GameCommandPlayAssistantCard implements GameCommand {
    private final AssistantCard chosenAssistantCard;

    /**
     * Build the command with card played
     * @param chosenAssistantCard The <code>AssistantCard</code> played
     */
    public GameCommandPlayAssistantCard(AssistantCard chosenAssistantCard) {
        this.chosenAssistantCard = chosenAssistantCard;
    }

    /**
     * Get the card
     * @return An <code>AssistantCard</code> representing the one played
     */
    public AssistantCard executeCommand() {
        return chosenAssistantCard;
    }
}
