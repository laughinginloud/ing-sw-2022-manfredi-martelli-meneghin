package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.message.InfoMap;

/**
 * GameCommand representing the response to a GameCommandRequestAction.CharacterCardEffect containing the fields
 * value chosen by the player in order the use selected characterCard's effect
 * @author Sebastiano Meneghin
 */
public class GameCommandChosenCharacterCardFields implements GameCommand {
    private final InfoMap chosenData;

    public GameCommandChosenCharacterCardFields(InfoMap chosenData) { this.chosenData = chosenData; }

    /**
     * Executes the GameCommand procedure
     * @return An object representing a Map(GameCommandValues, Object) containing all the field's values chosen by the player
     */
    public Object executeCommand() {
        return chosenData;
    }
}
