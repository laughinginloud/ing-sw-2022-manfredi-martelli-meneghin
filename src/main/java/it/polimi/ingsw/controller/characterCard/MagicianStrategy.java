package it.polimi.ingsw.controller.characterCard;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameModel;

/**
 * Strategy representing the activation of the CharacterCard 'MAGICIAN'
 * @author Giovanni Manfredi
 */
public class MagicianStrategy extends CharacterCardStrategy {
    public MagicianStrategy(CharacterCard card) {
        this.card = card;
    }

    /**
     * Activates the effect of the CharacterCard 'MAGICIAN'
     */
    @Override
    public void activateEffect() {
        ControllerData data = ControllerData.getInstance();
        GameModel model = data.getGameModel();
        // TODO [CharacterCardStrategy]: @Seba Command implementation
        // The server allows MotherNature to be moved of two additional islands this turn - sets the flag extraMovementFlag
        data.setCharacterCardFlag(ControllerData.Flags.extraMovementFlag, true);
        // sendInfo to all players
    }
}
