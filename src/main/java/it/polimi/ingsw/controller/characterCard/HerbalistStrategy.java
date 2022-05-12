package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'HERBALIST'
 * @author Giovanni Manfredi
 */
public class HerbalistStrategy extends CharacterCardStrategy {
    public HerbalistStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'HERBALIST'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // If there are still noEntryTiles then [...] Else the effect does nothing.
        // The server asks the player on which island he wants to place the noEntryTile (requestAction())
        // The player responds with the information requested by the server (responseAction(island))
        // The server places the token on the the selected island (noEntryTile++)
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
