package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Character;

/**
 * Get the character card played
 * @author Sebastiano Meneghin
 */
public final class GameCommandPlayCharacterCard implements GameCommand {
    private final Character character;

    /**
     * Build the command with card played
     * @param character The <code>Character</code> of the card
     */
    public GameCommandPlayCharacterCard(Character character) {
        this.character = character;
    }

    /**
     * Get the card
     * @return A <code>Character</code> representing the one played
     */
    public Character executeCommand() {
        return this.character;
    }
}
