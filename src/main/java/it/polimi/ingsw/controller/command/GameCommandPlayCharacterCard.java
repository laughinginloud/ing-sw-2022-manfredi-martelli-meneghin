package it.polimi.ingsw.controller.command;

import it.polimi.ingsw.model.Character;

public class GameCommandPlayCharacterCard {
    private final Character character;

    public GameCommandPlayCharacterCard(Character character) { this.character = character; }

    public Object executeCommand() { return this.character; }
}
