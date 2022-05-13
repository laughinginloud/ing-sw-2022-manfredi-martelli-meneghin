package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'FARMER'
 * @author Giovanni Manfredi
 */
public class FarmerStrategy extends CharacterCardStrategy {
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

        // TODO [CharacterCardStrategy] implementation
        // The player sends the index of the chosen Card to play
        // The cards allows the player to take control of the professor even if he has the same number of students in the schoolBoard - use of the flag equalStudentsFlag
        // For each Color, the server re-checks if a Professor needs to be changed
        // The server sets the Player to hasPlayedCard = true

        try {
            Player[] players = data.getPlayersOrder();

            // After the server managed the use of the CharacterCard, gets the updated GlobalProfessorTable and the updated DiningRooms
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
