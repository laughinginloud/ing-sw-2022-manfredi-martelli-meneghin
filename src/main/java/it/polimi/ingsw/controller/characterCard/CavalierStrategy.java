package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameModel;

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

        // The server increases the influence of the current player of two by setting the flag: extraInfluenceFlag
        data.setCharacterCardFlag(ControllerData.Flags.extraInfluenceFlag, true);

        // TODO [CharacterCardStrategy]: Command implementation - Confirm Message operation successful
    }
}
