package it.polimi.ingsw.controller.state;

/**
 * Class containing the cloud filling procedure of the bigger PlanPhase
 * @author Sebastiano Meneghin
 */
public class GameStateFillClouds implements GameStatePlanPhase {
    public GameState nextState() {
        //return new GameStatePlayCard();
        return null;
    }

    public void executeState() {
        //TODO: Fill Clouds
        //TODO: Activate emptyBagTrigger if number of returned students isn't enough to fill all the clouds
    }
}
