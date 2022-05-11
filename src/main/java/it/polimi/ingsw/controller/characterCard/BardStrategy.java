package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'BARD'
 * @author Giovanni Manfredi
 */
public class BardStrategy implements CharacterCardStrategy {

    /**
     * Activates the effect of the CharacterCard 'BARD'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which student he wants to exchange between the entrance and the schoolBoard (requestAction())
        // The player responds with the information requested by the server (responseAction(student[], student[]))
        // The server exchanges the students across entrance and schoolBoard (using a tmp array)
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
