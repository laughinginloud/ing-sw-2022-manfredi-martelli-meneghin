package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.controller.state.GameStateComputeIsland;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.GameModel;
import it.polimi.ingsw.common.model.Island;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;

/**
 * Strategy representing the activation of the CharacterCard 'STANDARD_BEARER'
 *
 * @author Giovanni Manfredi and Sebastiano Meneghin
 */
public class StandardBearerStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'StandardBearerStrategy'
     *
     * @param card the card to which the class is initialized
     */
    public StandardBearerStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'STANDARD_BEARER'
     */
    @Override
    public void activateEffect() throws SocketException {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // The server gets the Island where the influence could be calculated using the characterCard effect
            Island[] availableIslands = model.getIslands();

            // Create a Map and save the fields that will be sent to the player as RequestAction's payload
            InfoMap standardBearerMap = new InfoMap();
            standardBearerMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.STANDARDBEARERFIRST);
            standardBearerMap.put(GameValues.ISLANDARRAY, availableIslands);

            // The server asks the player on which Island he would like to calculate the influence
            GameCommand request = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, standardBearerMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                InfoMap chosenFields = c.executeCommand();

                // Gets the index of Island that the player wants to compute, from the Map received from the client
                int island_index = (int) chosenFields.get(GameValues.ISLANDINDEX);

                // The server activates the same routine as in GameStateComputeIsland (also with the possibility of ending the game)
                GameStateComputeIsland computeIsland = new GameStateComputeIsland(island_index);
                computeIsland.executeState();
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
