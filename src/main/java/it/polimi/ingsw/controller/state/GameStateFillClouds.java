package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class containing the cloud filling procedure of the bigger PlanPhase
 * @author Sebastiano Meneghin
 */
public class GameStateFillClouds implements GameStatePlanPhase {
    public GameState nextState() { return new GameStatePlayCard(); }

    public void executeState() {
        try {
            // Fill all the CloudTiles, one for each player in game
            int numOfClouds = ControllerData.getInstance().getNumOfPlayers();
            for (int i = 0; i < numOfClouds; i++)
                fillCloud(ControllerData.getInstance().getGameModel().getCloudTile(i));

            // Creates the Map to send via GameCommand and adds updated CloudTiles to it
            Map<GameCommandValues, Object> updatedCloud = new HashMap<>();
            updatedCloud.put(GameCommandValues.CLOUDARRAY, ControllerData.getInstance().getGameModel().getCloudTile());

            // Sends to all the players the updated array of Clouds, once they have been filled
            for (Player player : ControllerData.getInstance().getPlayersOrder()) {
                VirtualView playerView = ControllerData.getInstance().getPlayerView(player);
                playerView.sendMessage(new GameCommandSendInfo(updatedCloud));
            }
        }

        catch (Exception e){
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Set a precise number of student on a CloudTile, depending on the number of the player. If it empties the bag it sets "true" the EmptyBagTrigger
     * @param cloudTile The cloudTile to be filled of students
     */
    private void fillCloud (CloudTile cloudTile) {
        int numOfStudentsToDraw;

        // Select the number of students to draw according to the number of players are playing this Game
        switch (ControllerData.getInstance().getNumOfPlayers()) {
            case 2, 4 -> numOfStudentsToDraw = 3;
            case 3    -> numOfStudentsToDraw = 4;
            default   -> throw new IllegalStateException("The number of students to set on a CloudTile could be only 3 or 4");
        }

        try {
            // Try to draw "numOfStudentsToDraw" students
            Color[] drawnStudents = ControllerData.getInstance().getGameModel().getBag().drawStudents(numOfStudentsToDraw).drawnStudents();

            // If the Bag get emptied during the withdrawal set to "true" the EmptyBagTrigger
            if (drawnStudents.length < numOfStudentsToDraw) {
                ControllerData.getInstance().setEmptyBagTrigger();
                drawnStudents = Arrays.copyOf(drawnStudents, numOfStudentsToDraw);
            }

            cloudTile.setStudents(drawnStudents);
        }

        catch (EmptyBagException e){
            ControllerData.getInstance().setEmptyBagTrigger();
        }
    }
}
