package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'BARD'
 * @author Giovanni Manfredi
 */
public class BardStrategy extends CharacterCardStrategy {

    public BardStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'BARD'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] @Gio @Seba implementation
        // The server asks the player which student he wants to exchange between the entrance and the schoolBoard (requestAction())
        // The player responds with the information requested by the server (responseAction(student[], student[]))
        // The server exchanges the students across entrance and schoolBoard (using a tmp array)
        // sendInfo to all players
    }
}
