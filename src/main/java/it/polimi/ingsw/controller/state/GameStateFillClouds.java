package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.*;

/**
 * Class containing the cloud filling procedure of the bigger PlanPhase
 * @author Sebastiano Meneghin
 */
public class GameStateFillClouds implements GameStatePlanPhase {
    public GameState nextState() { return new GameStatePlayCard(); }

    @Override
    public void executeState() {
        // There's one CloudTile for each player, it is required to fill all of them
        int numOfClouds = ControllerData.getInstance().getNumOfPlayers();
        for (int i = 0; i <numOfClouds; i++) {
            fillCloud(ControllerData.getInstance().getGameModel().getCloudTile(i));
        }

        //TODO: [Command] notify all the Players about the filled clouds
    }

    /**
     * Set a precise number of student on a CloudTile, depending on the number of the player. If it empties the bag it sets "true" the EmptyBagTrigger
     * @param cloudTile The cloudTile to be filled of students
     */
    private void fillCloud (CloudTile cloudTile) {
        int studentsToDraw;

        switch (ControllerData.getInstance().getNumOfPlayers()) {
            case 2, 4 -> studentsToDraw = 3;
            case 3    -> studentsToDraw = 4;
            default   -> throw new IllegalStateException("The number of students to set on a CloudTile could be only 3 or 4");
        }

        try {
            cloudTile.setStudents(ControllerData.getInstance().getGameModel().getBag().drawStudents(studentsToDraw).drawnStudents());
        }
        catch (EmptyBagException e){
            ControllerData.getInstance().setEmptyBagTrigger();
        }
    }
}
