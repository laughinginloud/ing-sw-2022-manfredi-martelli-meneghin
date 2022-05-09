package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.model.AssistantCard;

public class GameCommandPlayAssistantCard implements GameCommand {
    private final AssistantCard chosenAssistantCard;

    public GameCommandPlayAssistantCard(AssistantCard chosenAssistantCard) {
        this.chosenAssistantCard = chosenAssistantCard;
    }

    public Object executeCommand() {
        return chosenAssistantCard;
    }
}
