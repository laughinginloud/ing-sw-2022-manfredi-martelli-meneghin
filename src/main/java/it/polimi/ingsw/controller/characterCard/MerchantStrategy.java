package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * Strategy representing the activation of the CharacterCard 'MERCHANT'
 * @author Giovanni Manfredi
 */
public class MerchantStrategy extends CharacterCardStrategy {
    public MerchantStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MERCHANT'
     */
    @Override
    public void activateEffect() {
        try {
            ControllerData data = ControllerData.getInstance();
            Player player = data.getCurrentPlayer();
            VirtualView playerView = data.getPlayerView(player);

            // The server select all the student's colors
            Color[] availableColors = Color.values();

            // Create a Map and save the fields that will be sent to the player as RequestAction's payload
            Map<GameCommandValues, Object> merchantMap = new HashMap<>();
            merchantMap.put(GameCommandValues.COLORARRAY, availableColors);

            // The server asks the player which color he wants to inhibit calculating the influences during this turn
            GameCommand request = new GameCommandRequestAction(GameCommandActions.CHARACTERCARDEFFECT, merchantMap);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind
            if (response instanceof GameCommandChosenCharacterCardFields c) {
                // The player responds with the information requested by the server
                @SuppressWarnings("unchecked")
                Map<GameCommandValues, Object> chosenFields = (Map<GameCommandValues, Object>) c.executeCommand();

                // A cast for the information requested by the server (color to inhibit)
                Color selectedColor = (Color) chosenFields.get(GameCommandValues.MERCHANTCOLOR);



                // TODO [CharacterCardStrategy] @Gio @Seba? implementation
                // The server sets the flag: excludeColorFlag and the variable: Color noInfluenceColor
                // Update player? Not sure it's needed
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
