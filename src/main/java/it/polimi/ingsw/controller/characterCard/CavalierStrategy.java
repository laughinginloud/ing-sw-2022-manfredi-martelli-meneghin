package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameModel;

/**
 * Strategy representing the activation of the CharacterCard 'CAVALIER'
 * @author Giovanni Manfredi
 */
public class CavalierStrategy extends CharacterCardStrategy {
    public CavalierStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'CAVALIER'
     */
    @Override
    public void activateEffect() {
        ControllerData data = ControllerData.getInstance();
        GameModel model = data.getGameModel();

        // TODO [CharacterCardStrategy] @Seba command implementation
        // The server increases the influence of the current player of two by setting the flag: extraInfluenceFlag
        data.setCharacterCardFlag(ControllerData.Flags.extraInfluenceFlag, true);
        // sendInfo to all players
    }
}
