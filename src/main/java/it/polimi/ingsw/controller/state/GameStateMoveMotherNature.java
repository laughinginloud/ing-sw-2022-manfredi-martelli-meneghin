package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualView.VirtualView;
import it.polimi.ingsw.controller.command.*;

import java.io.IOException;
/**
 * State representing the request and subsequent movement of the Mother Nature pawn
 * @author Mattia Martelli
 */
public class GameStateMoveMotherNature implements GameStateActionPhase {
    public GameState nextState() {
        return new GameStateComputeIsland();
    }

    public void executeState() {
        try {
            Player      player     = ControllerData.getInstance().getCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerViewMap().getRight(player);

            // Create a command representing the request of MotherNature's movement and send it to the player
            GameCommand request    = new GameCommandRequestValueClient(GameCommandValues.MOTHERNATURE);
            GameCommand response   = playerView.sendRequest(request);

            // If the response is of the right kind, try to execute the movement
            if (response instanceof GameCommandMoveMotherNature c) {
                try {
                    c.checkLegalValue(player.getLastPlayedCard().movementPoints());
                    c.executeCommand();
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

                catch (Exception ex) {
                    // Fatal error: print the stack trace to help debug
                    ex.printStackTrace();
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
