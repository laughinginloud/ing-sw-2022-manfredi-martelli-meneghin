package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'JESTER'
 * @author Giovanni Manfredi
 */
public class JesterStrategy implements CharacterCardStrategy {

    /**
     * Activates the effect of the CharacterCard 'JESTER'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which students (3 from the Entrance and 3 from the Card) he wants to exchange  (requestAction())
        // The player responds with the information requested by the server (responseAction(student[], student[]))
        // The server exchanges the students (using a temporary array) from the Entrance and the card
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
