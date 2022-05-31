package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.ControllerData;

/**
 * State representing the check of the triggers before the end of the game
 * @author Mattia Martelli
 */
public final class GameStateEndCheckPhase implements GameState {
    private boolean end;

    public GameState nextState() {
        return end ? new GameStateEndGame() : new GameStateFillClouds();
    }

    public void executeState() {
        end = ControllerData.getInstance().getEmptyAssistantDeckTrigger() || ControllerData.getInstance().getEmptyBagTrigger();
    }
}
