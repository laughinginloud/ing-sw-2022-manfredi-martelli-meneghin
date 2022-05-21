package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.server.controller.command.GameCommandValues;
import it.polimi.ingsw.server.virtualView.VirtualView;

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
            ControllerData data  = ControllerData.getInstance();
            GameModel      model = data.getGameModel();

            // Fill all the CloudTiles, one for each player in game
            int numOfClouds = data.getNumOfPlayers();
            for (int i = 0; i < numOfClouds; i++)
                fillCloud(model.getCloudTile(i), data);

            // Creates the Map to send via GameCommand and adds updated CloudTiles to it
            Map<GameCommandValues, Object> updatedCloud = new HashMap<>();
            updatedCloud.put(GameCommandValues.CLOUDARRAY, model.getCloudTile());

            // Sends to all the players the updated array of Clouds, once they have been filled
            for (Player player : model.getPlayer()) {
                VirtualView playerView = data.getPlayerView(player);
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
     * @param data The current Game's controllerData
     */
    private void fillCloud (CloudTile cloudTile, ControllerData data) {
        int numOfStudentsToDraw;

        // Select the number of students to draw according to the number of players are playing this Game
        switch (data.getNumOfPlayers()) {
            case 2, 4 -> numOfStudentsToDraw = 3;
            case 3    -> numOfStudentsToDraw = 4;
            default   -> throw new IllegalStateException("The number of students to set on a CloudTile could be only 3 or 4");
        }

        try {
            // Try to draw "numOfStudentsToDraw" students
            Bag     bagToDrawFrom = data.getGameModel().getBag();
            Color[] drawnStudents = bagToDrawFrom.drawStudents(numOfStudentsToDraw).drawnStudents();

            // If the Bag get emptied during the withdrawal set to "true" the EmptyBagTrigger
            if (drawnStudents.length < numOfStudentsToDraw) {
                data.setEmptyBagTrigger();
                drawnStudents = Arrays.copyOf(drawnStudents, numOfStudentsToDraw);
            }

            cloudTile.setStudents(drawnStudents);
        }

        catch (EmptyBagException e){
            data.setEmptyBagTrigger();
        }
    }
}
