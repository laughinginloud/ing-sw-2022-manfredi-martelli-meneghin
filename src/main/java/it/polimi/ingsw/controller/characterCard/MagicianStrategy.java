package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'MAGICIAN'
 * @author Giovanni Manfredi
 */
public class MagicianStrategy extends CharacterCardStrategy {
    public MagicianStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MAGICIAN'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server allows MotherNature to be moved of two additional islands this turn - sets the flag extraMovementFlag
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
