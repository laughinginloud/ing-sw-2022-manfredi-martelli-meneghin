package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'MONK'
 * @author Giovanni Manfredi
 */
public class MonkStrategy implements CharacterCardStrategy{

    /**
     * Activates the effect of the CharacterCard 'MONK'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server asks the player which student he wants to move and the island he wants to move the player to (requestAction())
        // The player responds with the information requested by the server (responseAction(student, island))
        // The server moves the student from the CharacterCard to the selected island
        // The server refills the card taking a student from the Bag (EmptyBagException)
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
