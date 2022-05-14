package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.controller.state.GameStateComputeIsland;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'STANDARD_BEARER'
 * @author Giovanni Manfredi
 */
public class StandardBearerStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'StandardBearerStrategy'
     * @param card the card to which the class is initialized
     */
    public StandardBearerStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'STANDARD_BEARER'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data       = ControllerData.getInstance();
            GameModel      model      = data.getGameModel();
            Player         curPlayer  = data.getCurrentPlayer();
            VirtualView    playerView = data.getPlayerView(curPlayer);

            // The server gets the Island where the influence could be calculated using the characterCard effect
            Island[] availableIslands = model.getIslands();

            // Create a Map and save the fields that will be sent to the player as RequestAction's payload
            Map<GameCommandValues, Object> standardBearerMap = new HashMap<>();
            standardBearerMap.put(GameCommandValues.ISLANDARRAY, availableIslands);

            // The server asks the player on which Island he would like to calculate the influence
            GameCommand request = new GameCommandRequestAction(GameCommandActions.CHARACTERCARDEFFECT, standardBearerMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameCommandValues, Object> chosenFields = (Map<GameCommandValues, Object>) c.executeCommand();

                // The server activates the same routine as in GameStateComputeIsland (also with the possibility of ending the game)
                GameStateComputeIsland computeIsland = new GameStateComputeIsland();
                computeIsland.executeState();
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
