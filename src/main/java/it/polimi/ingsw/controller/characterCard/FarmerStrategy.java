package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'FARMER'
 * @author Giovanni Manfredi
 */
public class FarmerStrategy implements CharacterCardStrategy{

    /**
     * Activates the effect of the CharacterCard 'FARMER'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The cards allows the player to take control of the professor even if he has the same number of students in the schoolBoard - use of the flag equalStudentsFlag
        // For each Color, the server re-checks if a Professor needs to be changed
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
