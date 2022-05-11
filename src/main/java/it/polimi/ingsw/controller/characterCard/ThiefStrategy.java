package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'THIEF'
 * @author Giovanni Manfredi
 */
public class ThiefStrategy implements CharacterCardStrategy {

    /**
     * Activates the effect of the CharacterCard 'THIEF'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which color he wants to reduce from each player (requestAction())
        // The player responds with the information requested by the server (responseAction(color))
        // The server removes 3 (or all) students of the specified color from each SchoolBoard
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
