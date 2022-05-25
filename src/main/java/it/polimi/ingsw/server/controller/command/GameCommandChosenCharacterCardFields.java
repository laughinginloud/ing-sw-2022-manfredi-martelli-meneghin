package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.GameValues;

import java.util.Map;

/**
 * GameCommand representing the response to a GameCommandRequestAction.CharacterCardEffect containing the fields
 * value chosen by the player in order the use selected characterCard's effect
 * @author Sebastiano Meneghin
 */
public class GameCommandChosenCharacterCardFields implements GameCommand {
    Map<GameValues, Object> chosenData;

    public GameCommandChosenCharacterCardFields(Map<GameValues, Object> chosenData) { this.chosenData = chosenData; }

    /**
     * Executes the GameCommand procedure
     * @return An object representing a Map(GameCommandValues, Object) containing all the field's values chosen by the player
     */
    public Object executeCommand() {
        return chosenData;
    }
}
