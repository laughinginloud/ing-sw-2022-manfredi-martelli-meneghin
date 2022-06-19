package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Character;

public class GameCommandPlayCharacterCard implements GameCommand{
    private final Character character;

    public GameCommandPlayCharacterCard(Character character) { this.character = character; }

    public Object executeCommand() { return this.character; }
}
