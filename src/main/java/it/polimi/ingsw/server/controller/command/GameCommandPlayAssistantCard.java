package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.AssistantCard;

public class GameCommandPlayAssistantCard implements GameCommand {
    private final AssistantCard chosenAssistantCard;

    public GameCommandPlayAssistantCard(AssistantCard chosenAssistantCard) {
        this.chosenAssistantCard = chosenAssistantCard;
    }

    public Object executeCommand() {
        return chosenAssistantCard;
    }
}
