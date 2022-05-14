package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'HERBALIST'
 * @author Giovanni Manfredi
 */
public class HerbalistStrategy extends CharacterCardStrategy {
    public HerbalistStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'HERBALIST'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // The server gets the Island where a noEntryTile could be placed using the characterCard effect
            Island[] availableIslands = model.getIslands();

            // Create a Map and save the field that will be sent to the player as RequestAction's payload
            Map<GameCommandValues, Object> herbalistMap = new HashMap<>();
            herbalistMap.put(GameCommandValues.ISLANDARRAY, availableIslands);

            // The server asks the player on which Island he would like to put the noEntryTile
            GameCommand request = new GameCommandRequestAction(GameCommandActions.CHARACTERCARDEFFECT, herbalistMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameCommandValues, Object> chosenField = (Map<GameCommandValues, Object>) c.executeCommand();

                // Gets the value of the chosenField from the Map
                Island chosenIsland = chosenField.get(GameCommandValues.ISLANDINDEX);

                // TODO [CharacterCardStrategy] implementation
                // The server places the token on the the selected island (noEntryTile++)
                // sendInfo to all players


                // After the server managed the use of the CharacterCard, gets the updated values of CharacterCardsArray and IslandsArray
                CharacterCard[] updatedCharacterCards = model.getCharacterCards();
                Island[]        updatedIslands        = model.getIslands();

                // Creates a map containing the updated field to send to all the players
                Map<GameCommandValues, Object> afterEffectUpdate = new HashMap<>();
                afterEffectUpdate.put(GameCommandValues.CHARACTERCARDARRAY, updatedCharacterCards);
                afterEffectUpdate.put(GameCommandValues.ISLANDARRAY, updatedIslands);

                // Sends to all the players the updated fields
                Player[] players = data.getPlayersOrder();
                for (Player playersToUpdate : players) {
                    VirtualView playerToUpdateView = data.getPlayerView(playersToUpdate);
                    playerToUpdateView.sendMessage(new GameCommandSendInfo(afterEffectUpdate));
                }
            }

            // If the response is of the wrong kind, send an Illegal Command message and restart the method
            else {
                try {
                    playerView.sendMessage(new GameCommandIllegalCommand());
                }

                catch (Exception ex) {
                    // Fatal error: print the stack trace to help debug
                    ex.printStackTrace();
                }

                activateEffect();
            }
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
