package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.GameModel;

/**
 * State representing the initial model initialization
 * @author Mattia Martelli
 */
public class GameStateModelInitialization implements GameStateSetup {
    public GameState nextState() {
        return new GameStatePlaceTokens();
    }

    public void sendNetworkMessage() {

    }

    public void executeState() {
        ControllerData.getInstance().setGameModel(new GameModel(ControllerData.getInstance().getNumOfPlayers(), ControllerData.getInstance().getExpertMode()));
    }
}
