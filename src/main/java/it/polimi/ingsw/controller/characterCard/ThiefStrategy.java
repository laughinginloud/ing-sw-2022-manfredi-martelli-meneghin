package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;

/**
 * Strategy representing the activation of the CharacterCard 'THIEF'
 * @author Giovanni Manfredi
 */
public class ThiefStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'ThiefStrategy'
     * @param card the card to which the class is initialized
     */
    public ThiefStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'THIEF'
     */
    @Override
    public void activateEffect() {
        ControllerData data = ControllerData.getInstance();
        GameModel model = data.getGameModel();

        // TODO [CharacterCardStrategy] Command implementation
        // The server asks the player which color he wants to reduce from each player (requestAction())
        // The player responds with the information requested by the server (responseAction(removeStudents))

        // The server removes 3 (or all) students of the specified color from each SchoolBoard
        Player[] players = model.getPlayer();
        int currentStudents;

        for (Player player: players) {
            currentStudents = player.getSchoolBoard().getDiningRoom().getStudentCounters(removeStudents);
            if (currentStudents >= 3) {
                player.getSchoolBoard().getDiningRoom().setStudentCounters(removeStudents, currentStudents - 3);
            }
            else {
                player.getSchoolBoard().getDiningRoom().setStudentCounters(removeStudents, 0);
            }
        }

        // TODO [CharacterCardStrategy] Command implementation
        // sendInfo to all players
    }
}
