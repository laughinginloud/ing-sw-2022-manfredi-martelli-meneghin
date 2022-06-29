package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'MERCHANT'
 * @author Giovanni Manfredi
 */
public class MerchantStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'MerchantStrategy'
     * @param card the card to which the class is initialized
     */
    public MerchantStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MERCHANT'
     */
    @Override
    public void activateEffect() throws SocketException {
        try {
            ControllerData data = ControllerData.getInstance();
            Player player = data.getCurrentPlayer();
            VirtualView playerView = data.getPlayerView(player);

            // The server select all the student's colors
            Color[] availableColors = Color.values();

            // Create a Map and save the fields that will be sent to the player as RequestAction's payload
            InfoMap merchantMap = new InfoMap();
            merchantMap.put(GameValues.CHARACTERVALUE, PlayCharacterAction.MERCHANTFIRST);
            merchantMap.put(GameValues.COLORARRAY, availableColors);

            // The server asks the player which color he wants to inhibit calculating the influences during this turn
            GameCommand request = new GameCommandRequestAction(GameActions.CHARACTERCARDEFFECT, merchantMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                InfoMap chosenFields = (InfoMap) c.executeCommand();

                // A cast for the information requested by the server (color to inhibit)
                Color selectedColor = (Color) chosenFields.get(GameValues.MERCHANTCOLOR);
                data.setExcludedColor(selectedColor);

                // The server sets the flag: excludeColorFlag and the variable: Color noInfluenceColor
                data.setCharacterCardFlag(ControllerData.Flags.excludeColorFlag, true);

                // Confirm Message operation successful
                String merchantConfirmString = "The card effect has been correctly applied! The color: " +
                                               selectedColor.toString() +
                                               " won't be considered during the influence calculation until the end of turn";
                afterEffectUpdate.put(GameValues.CONFIRMATIONSTRING, merchantConfirmString);
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
