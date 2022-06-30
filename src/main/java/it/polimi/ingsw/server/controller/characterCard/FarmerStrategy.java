package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.state.GameStateMoveStudents;

/**
 * Strategy representing the activation of the CharacterCard 'FARMER'
 *
 * @author Giovanni Manfredi and Sebastiano Meneghin
 */
public class FarmerStrategy extends CharacterCardStrategy {

    /**
     * Builder of the class 'FarmerStrategy'
     *
     * @param card the card to which the class is initialized
     */
    public FarmerStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'FARMER'
     */
    @Override
    public void activateEffect() {
        ControllerData data              = ControllerData.getInstance();
        GameModel      model             = data.getGameModel();
        Player         currentPlayer     = data.getCurrentPlayer();

        // The card allows the player to take control of the professor even if he has the same number of students
        // in the schoolBoard - use of the flag equalStudentsFlag
        data.setCharacterCardFlag(ControllerData.Flags.equalStudentsFlag, true);

        // For each Color, the server re-checks if a Professor needs to be changed
        for (Color student: Color.values())
            // If the currentPlayer is not the controllingPlayer and the professor needs to be moved
            // The professorLocation is changed to the currentPlayer
            model.getGlobalProfessorTable().getProfessorLocation(student).ifPresent(p -> {
                if (!currentPlayer.equals(p) && GameStateMoveStudents.checkProfessorMovement(currentPlayer, p, student))
                    model.getGlobalProfessorTable().setProfessorLocation(student, currentPlayer);
            });

        try {
            Player[] players = model.getPlayers();

            // After the server managed the use of the CharacterCard, gets the updated GlobalProfessorTable and
            // the updated DiningRooms
            GlobalProfessorTable updatedGlobalProfessorTable = model.getGlobalProfessorTable();
            DiningRoom[] updatedDiningRooms                  = new DiningRoom[players.length];
            for (int i = 0; i < players.length; i++)
                updatedDiningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

            afterEffectUpdate.put(GameValues.GLOBALPROFESSORTABLE, updatedGlobalProfessorTable);
            afterEffectUpdate.put(GameValues.DININGROOMARRAY,      updatedDiningRooms);
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
