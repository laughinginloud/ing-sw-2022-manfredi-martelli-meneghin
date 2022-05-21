package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.model.CharacterCard;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.HashMap;
import java.util.Map;

/**
 * State representing the request and subsequent movement of the Mother Nature pawn
 * @author Mattia Martelli
 */
public class GameStateMoveMotherNature implements GameStateActionPhase {
    public GameState nextState() {
        return
            // Check if the game has been won already
            ControllerData.getInstance().checkWinTrigger() ?
                // If the game has ended, return a plain end phase, which will decide who won
                new GameStateEndGame() :
                // Otherwise, go to the next phase
                new GameStateComputeIsland();
    }

    public void executeState() {
        try {
            Player      player     = ControllerData.getInstance().getCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerViewMap().getRight(player);
            Map<GameValues, Object> moveMNInfo = new HashMap<>();
            boolean expertMode = ControllerData.getInstance().getExpertMode();
            boolean canPlayCharacterCard = false;

            // Calculates the max motherNature movement and store it on the Map moveMNInfo
            int motherNatureMovement = player.getLastPlayedCard().movementPoints() + (ControllerData.getInstance().getCharacterCardFlag(ControllerData.Flags.extraMovementFlag) ? 2 : 0);
            moveMNInfo.put(GameValues.MOTHERNATUREMOVEMENT, motherNatureMovement);

            // If the player hasn't played a card yet
            if (expertMode && !ControllerData.getInstance().checkPlayedCard()) {
                // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
                CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(player);

                // If there are CharacterCard playable by the current player, adds them to the moveStudentsInfo
                if (playableCharacterCard != null) {
                    moveMNInfo.put(GameValues.CHARACTERCARDARRAY, playableCharacterCard);

                    //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                    canPlayCharacterCard = true;
                }
            }

            // Create a command representing the request of MotherNature's movement and send it to the player
            // If the player is allowed to, send also the CharacterCard he could play
            GameCommand request    = canPlayCharacterCard ?
                new GameCommandRequestAction(GameActions.MOVEMOTHERNATUREORPLAYCARD, moveMNInfo):
                new GameCommandRequestAction(GameActions.MOVEMOTHERNATURE, moveMNInfo);
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
                if (ControllerData.getInstance().checkPlayedCard())
                    throw new IllegalStateException("CharacterCard has been already used by the current player!");

                // Executes the command received
                Character chosenCharacter = (Character) c.executeCommand();

                try {
                    // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
                    CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

                    // Calls the selected characterCard's strategy effect
                    chosenCardStrategy.playCharacterCard();

                    // If the game hasn't ended restart the method from the beginning
                    // in order to allow the MotherNature movement
                    if (!ControllerData.getInstance().checkWinTrigger())
                        executeState();
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
        Map<GameValues, Object> updateInfo = new HashMap<>();
        updateInfo.put(GameValues.MOTHERNATURE, updatedMotherNaturePosition);

        try {
            // Notify to all the player the new MotherNaturePosition
            for (Player playerToUpdate : ControllerData.getInstance().getGameModel().getPlayer())
                ControllerData.getInstance().getPlayerView(playerToUpdate).sendMessage(new GameCommandSendInfo(updateInfo));
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
