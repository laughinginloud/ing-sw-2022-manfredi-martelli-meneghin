package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualView.VirtualView;
import it.polimi.ingsw.controller.command.*;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            GameCommand request    = new GameCommandRequestAction(GameCommandActions.MOVEMOTHERNATURE, player.getLastPlayedCard().movementPoints() + (ControllerData.getInstance().getCharacterCardFlag(ControllerData.Flags.extraMovementFlag) ? 2 : 0));
            GameCommand response   = playerView.sendRequest(request);

            // If the response is of the right kind, try to execute the movement
            if (response instanceof GameCommandMoveMotherNature c) {
                try {
                    c.executeCommand();
                    updateMotherNaturePosition();
                }

                catch (Exception e) {
                    // Fatal error: print the stack trace to help debug
                    e.printStackTrace();
                }
            }

            // If the player decided to play a CharacterCard
            else if (response instanceof GameCommandPlayCharacterCard c) {
                // If the player already used a CharacterCard during this turn, throws an exception
                if(ControllerData.getInstance().checkPlayedCard())
                    throw new IllegalStateException("CharacterCard has been already used by the current player!");

                // Executes the command received and set to "true" the flag hasPlayedCard stored in ControllerData
                Character calledCharacter = (Character) c.executeCommand();
                ControllerData.getInstance().setPlayedCard();

                try {
                    /* TODO: [CharacterCard] Insert here the real name of the function which manage the CharacterCardUse */
                    // CharacterCardUse.useCharacterCard(calledCharacter);
                }

                catch (Exception e) {
                    // Fatal error: print the stack trace to help debug
                    e.printStackTrace();
                }

                // After the CharacterCard usage, execute once again the procedure of GameStateMoveMotherNature
                executeState();
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

    private void updateMotherNaturePosition() {
        int updatedMotherNaturePosition = ControllerData.getInstance().getGameModel().getMotherNaturePosition();

        // Creates a map and saves in it the current MotherNaturePosition on islandArray
        Map<GameCommandValues, Object> updateInfo = new HashMap<>();
        updateInfo.put(GameCommandValues.MOTHERNATURE, updatedMotherNaturePosition);

        try {
            // Notify to all the player the new MotherNaturePosition
            for (Player playerToUpdate : ControllerData.getInstance().getPlayersOrder())
                ControllerData.getInstance().getPlayerView(playerToUpdate).sendMessage(new GameCommandSendInfo(updateInfo));
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
