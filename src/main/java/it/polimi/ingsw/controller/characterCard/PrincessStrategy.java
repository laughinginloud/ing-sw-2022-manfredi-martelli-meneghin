package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'PRINCESS'
 * @author Giovanni Manfredi
 */
public class PrincessStrategy implements CharacterCardStrategy {

    /**
     * Activates the effect of the CharacterCard 'PRINCESS'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which student he wants to move and the schoolBoard he wants to move the player to (requestAction())
        // The player responds with the information requested by the server (responseAction(student))
        // The server moves the student from the CharacterCard to the SchoolBoard (FullBoardException)
        // The server refills the card taking a student from the Bag (EmptyBagException)
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
