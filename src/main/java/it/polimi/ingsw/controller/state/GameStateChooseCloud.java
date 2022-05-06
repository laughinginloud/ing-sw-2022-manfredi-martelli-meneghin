package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * State representing the retrieval of students from an island
 * @author Mattia Martelli
 */
public class GameStateChooseCloud implements GameStateActionPhase {
    public GameState nextState() {
        //return new GameStateEndCheckPhase();
        return null;
    }

    public void executeState() {
        try {
            Player player          = ControllerData.getInstance().getCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerView(player);

            GameCommand request    = new GameCommandRequestValueClient(GameCommandValues.CLOUD);
            GameCommand response   = playerView.sendRequest(request);

            // If the response is of the right type, try to execute
            if (response instanceof GameCommandChooseCloud c) {
                try {
                    c.checkLegalValue();
                    Color[] students = (Color[]) c.executeCommand();
                    for (Color student : students)
                        player.getSchoolBoard().getEntrance().appendStudent(student);

                    Map<GameCommandValues, Object> map = new HashMap<>();
                    map.put(GameCommandValues.ENTRANCE, player.getSchoolBoard().getEntrance().getStudents());
                    playerView.sendMessage(new GameCommandSendInfo(map));
                }

                catch (IllegalArgumentException e) {
                    try {
                        playerView.sendMessage(new GameCommandIllegalValue());
                    }

                    catch (Exception ex) {
                        // Fatal error: print the stack trace to help debug
                        ex.printStackTrace();
                    }

                    executeState();
                }
            }

            // If the response is of the wrong kind, send an Illegal Command message and try this state again
            else {
                try {
                    playerView.sendMessage(new GameCommandIllegalCommand());
                }

                catch (Exception e) {
                    // Fatal error: print the stack trace to help debug
                    e.printStackTrace();
                }

                executeState();
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
