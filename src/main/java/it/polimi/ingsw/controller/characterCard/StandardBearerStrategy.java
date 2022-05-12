package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.model.CharacterCard;

/**
 * Strategy representing the activation of the CharacterCard 'STANDARD_BEARER'
 * @author Giovanni Manfredi
 */
public class StandardBearerStrategy extends CharacterCardStrategy {
    public StandardBearerStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'STANDARD_BEARER'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which island he wants to activate the effect (requestAction())
        // The player responds with the information requested by the server (responseAction(island))
        // The server activates the same routine as in GameStateComputeIsland (also with the possibility of ending the game)
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
