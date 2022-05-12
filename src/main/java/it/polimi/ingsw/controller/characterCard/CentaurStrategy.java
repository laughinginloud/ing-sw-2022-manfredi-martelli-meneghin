package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'CENTAUR'
 * @author Giovanni Manfredi
 */
public class CentaurStrategy extends CharacterCardStrategy {
    public CentaurStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'CENTAUR'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server "deactivates" the towers which are not counted for the influence - sets the flag ignoreTowersFlag
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
