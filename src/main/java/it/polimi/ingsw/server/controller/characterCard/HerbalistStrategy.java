package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.GameModel;
import it.polimi.ingsw.common.model.Island;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;
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
    public void activateEffect() throws SocketException {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // The server gets the Island where a noEntryTile could be placed using the characterCard effect
            Island[] availableIslands = model.getIslands();

            // Create a Map and save the field that will be sent to the player as RequestAction's payload
            InfoMap herbalistMap = new InfoMap();
            herbalistMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.HERBALISTFIRST);
            herbalistMap.put(GameValues.ISLANDARRAY, availableIslands);

            // The server asks the player on which Island he would like to put the noEntryTile
            GameCommand request = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, herbalistMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                InfoMap chosenField = (InfoMap) c.executeCommand();

                // Gets the value of the chosenField from the Map and retrieve the selected island from its index
                int    chosenIslandIndex = (int) chosenField.get(GameValues.ISLANDINDEX);
                Island chosenIsland      = model.getIsland(chosenIslandIndex);

                // The server places the token on the selected island (noEntryTile++)
                int currentNoEntryTileCount = chosenIsland.getNoEntryTileCount();
                chosenIsland.setNoEntryTileCount(currentNoEntryTileCount + 1);

                // After the server managed the use of the CharacterCard, gets the updated values of
                // CharacterCardsArray and IslandsArray and put them in the map that will be broadcast
                afterEffectUpdate.put(GameValues.CHARACTERCARDARRAY, model.getCharacterCards());
                afterEffectUpdate.put(GameValues.ISLANDARRAY,        model.getIslands());
            }

            // If the response is of the wrong kind throw an exception to help debug
            else
                throw new IllegalStateException("Wrong command received: " + response);
        }

        catch (SocketException e) {
            throw e;
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
