package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.CharacterCard;

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

        // The server allows MotherNature to be moved of two additional islands this turn - sets the flag extraMovementFlag
        data.setCharacterCardFlag(ControllerData.Flags.extraMovementFlag, true);

        // TODO [CharacterCardStrategy]: Command implementation - Confirm Message operation successful
    }
}
