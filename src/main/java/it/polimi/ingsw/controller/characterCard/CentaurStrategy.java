package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;

/**
 * Strategy representing the activation of the CharacterCard 'CENTAUR'
 * @author Giovanni Manfredi
 */
public class CentaurStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'CentaurStrategy'
     * @param card the card to which the class is initialized
     */
    public CentaurStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'CENTAUR'
     */
    @Override
    public void activateEffect() {
        ControllerData data = ControllerData.getInstance();

        // The server "deactivates" the towers which are not counted for the influence - sets the flag ignoreTowersFlag
        data.setCharacterCardFlag(ControllerData.Flags.ignoreTowersFlag, true);

        // Confirm Message operation successful
        String centaurConfirmString = "The card effect has been correctly applied!" +
                                      " Towers won't be counted for the influence calculation until the end of turn";
        afterEffectUpdate.put(GameCommandValues.CONFIRMATIONSTRING, centaurConfirmString);
    }
}
