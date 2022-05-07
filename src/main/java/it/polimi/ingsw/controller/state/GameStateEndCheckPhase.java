package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;

/**
 * State representing the check of the triggers before the end of the game
 * @author Mattia Martelli
 */
public class GameStateEndCheckPhase implements GameState {
    private boolean end;

    public GameState nextState() {
        return end ? new GameStateEndGame() : new GameStateFillClouds();
    }

    public void executeState() {
        end = ControllerData.getInstance().getEmptyAssistantDeckTrigger() || ControllerData.getInstance().getEmptyBagTrigger();
    }
}
