package it.polimi.ingsw.controller.characterCard;

/**
 * Strategy representing the activation of the CharacterCard 'CAVALIER'
 * @author Giovanni Manfredi
 */
public class CavalierStrategy implements CharacterCardStrategy {

    /**
     * Activates the effect of the CharacterCard 'CAVALIER'
     */
    @Override
    public void activateEffect() {
        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The server increases the influence of the current player of two by setting the flag: extraInfluenceFlag
        // The server sets the Player to hasPlayedCard = true
        // sendInfo to all players
    }
}
