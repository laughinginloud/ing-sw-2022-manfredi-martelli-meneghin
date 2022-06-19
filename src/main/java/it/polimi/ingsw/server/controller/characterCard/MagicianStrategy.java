package it.polimi.ingsw.server.controller.characterCard;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.Player;

/**
 * Strategy representing the activation of the CharacterCard 'MAGICIAN'
 * @author Giovanni Manfredi
 */
public class MagicianStrategy extends CharacterCardStrategy {

    /**
     * Constructor of the class 'MagicianStrategy'
     * @param card the card to which the class is initialized
     */
    public MagicianStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MAGICIAN'
     */
    @Override
    public void activateEffect() {
        ControllerData data = ControllerData.getInstance();
        Player currentPlayer = data.getCurrentPlayer();

        // The server allows MotherNature to be moved of two additional islands this turn - sets the flag extraMovementFlag
        data.setCharacterCardFlag(ControllerData.Flags.extraMovementFlag, true);

        // Confirm Message operation successful
        String magicianConfirmString = "The card effect has been correctly applied! " +
                                       currentPlayer.getUsername() +
                                       " can move Mother Nature of two additional tiles this turn";
        afterEffectUpdate.put(GameValues.CONFIRMATIONSTRING, magicianConfirmString);
    }
}
