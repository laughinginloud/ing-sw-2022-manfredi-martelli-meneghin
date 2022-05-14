package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'PRINCESS'
 * @author Giovanni Manfredi
 */
public class PrincessStrategy extends CharacterCardStrategy {
    public PrincessStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'PRINCESS'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] @Gio @Seba implementation
        // The server asks the player which student he wants to move and the schoolBoard he wants to move the player to (requestAction())
        // The player responds with the information requested by the server (responseAction(student))
        // The server moves the student from the CharacterCard to the SchoolBoard (FullBoardException)
        // The server refills the card taking a student from the Bag (EmptyBagException)
        // sendInfo to all players
    }
}
