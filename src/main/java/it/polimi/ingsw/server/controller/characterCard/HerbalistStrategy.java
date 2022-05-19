package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.GameModel;
import it.polimi.ingsw.common.model.Island;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'HERBALIST'
 * @author Giovanni Manfredi
 */
public class HerbalistStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'HerbalistStrategy'
     * @param card the card to which the class is initialized
     */
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
                Island chosenIsland = (Island) chosenField.get(GameCommandValues.ISLANDINDEX);

                // The server places the token on the selected island (noEntryTile++)
                int currentNoEntryTileCount = chosenIsland.getNoEntryTileCount();
                chosenIsland.setNoEntryTileCount(currentNoEntryTileCount + 1);

                // After the server managed the use of the CharacterCard, gets the updated values of
                // CharacterCardsArray and IslandsArray and put them in the map that will be broadcast
                afterEffectUpdate.put(GameCommandValues.CHARACTERCARDARRAY, model.getCharacterCards());
                afterEffectUpdate.put(GameCommandValues.ISLANDARRAY,        model.getIslands());
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
