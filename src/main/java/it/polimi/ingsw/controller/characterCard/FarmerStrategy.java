package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;
import it.polimi.ingsw.controller.state.GameStateMoveStudents;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'FARMER'
 * @author Giovanni Manfredi
 */
public class FarmerStrategy extends CharacterCardStrategy {

    /**
     * Builder of the class 'FarmerStrategy'
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
        ControllerData data = ControllerData.getInstance();
        GameModel model = data.getGameModel();
        GameStateMoveStudents stateWithMethod = new GameStateMoveStudents();
        Player currentPlayer = data.getCurrentPlayer();
        Player controllingPlayer;

        // The card allows the player to take control of the professor even if he has the same number of students
        // in the schoolBoard - use of the flag equalStudentsFlag
        data.setCharacterCardFlag(ControllerData.Flags.equalStudentsFlag, true);

        // For each Color, the server re-checks if a Professor needs to be changed
        for (Color student: Color.values()) {
            controllingPlayer = model.getGlobalProfessorTable().getProfessorLocation(student);
            // If the currentPlayer is not the controllingPlayer and the professor needs to be moved
            // The professorLocation is changed to the currentPlayer
            if (!currentPlayer.equals(controllingPlayer) &&
                stateWithMethod.checkProfessorMovement(currentPlayer, controllingPlayer, student)) {

                model.getGlobalProfessorTable().setProfessorLocation(student, currentPlayer);
            }
        }

        try {
            Player[] players = data.getPlayersOrder();

            // After the server managed the use of the CharacterCard, gets the updated GlobalProfessorTable and
            // the updated DiningRooms
            GlobalProfessorTable updatedGlobalProfessorTable = model.getGlobalProfessorTable();
            DiningRoom[] updatedDiningRooms                  = new DiningRoom[players.length];
            for (int i = 0; i < players.length; i++)
                updatedDiningRooms[i] = players[i].getSchoolBoard().getDiningRoom();

            // Creates a map containing the updated field to send to all the players
            Map<GameCommandValues, Object> afterEffectUpdate = new HashMap<>();
            afterEffectUpdate.put(GameCommandValues.GLOBALPROFESSORTABLE, updatedGlobalProfessorTable);
            afterEffectUpdate.put(GameCommandValues.DININGROOMARRAY, updatedDiningRooms);

            // Sends to all the players the updated fields
            for (Player playersToUpdate : players) {
                VirtualView playerToUpdateView = data.getPlayerView(playersToUpdate);
                playerToUpdateView.sendMessage(new GameCommandSendInfo(afterEffectUpdate));
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
