package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'MERCHANT'
 * @author Giovanni Manfredi
 */
public class MerchantStrategy extends CharacterCardStrategy {
    public MerchantStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MERCHANT'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which color he wants to "deactivate" from the turn (requestAction())
        // The player responds with the information requested by the server (responseAction(color))
        // The server sets the flag: excludeColorFlag and the variable: Color noInfluenceColor
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
