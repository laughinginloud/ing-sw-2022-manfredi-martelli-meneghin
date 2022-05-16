package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Player;

/**
 * Strategy representing the activation of the CharacterCard 'CAVALIER'
 * @author Giovanni Manfredi
 */
public class CavalierStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'CavalierStrategy'
     * @param card the card to which the class is initialized
     */
    public CavalierStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'CAVALIER'
     */
    @Override
    public void activateEffect() {
        ControllerData data = ControllerData.getInstance();
        Player currentPlayer = data.getCurrentPlayer();

        // The server increases the influence of the current player of two by setting the flag: extraInfluenceFlag
        data.setCharacterCardFlag(ControllerData.Flags.extraInfluenceFlag, true);

        // Confirm Message operation successful
        String cavalierConfirmString = "The card effect has been correctly applied! " +
                                        currentPlayer.getUsername() +
                                       " will have two more influence on each island until the end of turn";
        afterEffectUpdate.put(GameCommandValues.CONFIRMATIONSTRING, cavalierConfirmString);
    }
}
