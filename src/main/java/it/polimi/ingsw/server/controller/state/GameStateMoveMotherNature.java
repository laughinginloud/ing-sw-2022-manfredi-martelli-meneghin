package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * State representing the request and subsequent movement of the Mother Nature pawn
 * @author Mattia Martelli & Sebastiano Meneghin
 */
@SuppressWarnings("unused")
public final class GameStateMoveMotherNature implements GameStateActionPhase {
    @Override
    public GameState nextState() {
        return
            // Check if the game has been won already
            ControllerData.getInstance().checkWinTrigger() ?
                // If the game has ended, return a plain end phase, which will decide who won
                new GameStateEndGame() :
                // Otherwise, go to the next phase
                new GameStateComputeIsland();
    }

    @Override
    public void executeState() throws SocketException {
        try {
            ControllerData data                 = ControllerData.getInstance();
            Player         player               = data.getCurrentPlayer();
            VirtualView    playerView           = data.getPlayerView(player);
            boolean        expertMode           = data.getExpertMode();
            boolean        canPlayCharacterCard = false;

            // Calculates the max motherNature movement and store it on the Map moveMNInfo
            int motherNatureMovement = player.getLastPlayedCard().movementPoints();

            if (data.getExpertMode() && data.getCharacterCardFlag(ControllerData.Flags.extraMovementFlag))
                motherNatureMovement += 2;

            int      currentMotherNaturePosition = data.getGameModel().getMotherNaturePosition();
            Island[] reachableIsland             = getReachableIslands(motherNatureMovement, currentMotherNaturePosition);

            // Creates a Map where to store the info in order to let the player play his turn, then saves the reachableIsland in it
            InfoMap moveMNInfo = new InfoMap();
            moveMNInfo.put(GameValues.MNPOSSIBLEMOVEMENTS, reachableIsland);

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

            // If the player is forced to move MN by exactly one island, the server does it automatically
            if (motherNatureMovement == 1 && !canPlayCharacterCard) {
                GameModel model = data.getGameModel();
                model.setMotherNaturePosition((model.getMotherNaturePosition() + 1) % model.getIslandsCount());
                return;
            }

            // Create a command representing the request of MotherNature's movement and send it to the player
            // If the player is allowed to, send also the CharacterCard he could play
            GameCommand request  = canPlayCharacterCard ?
                new GameCommandRequestAction(GameActions.MOVEMOTHERNATUREORPLAYCARD, moveMNInfo):
                new GameCommandRequestAction(GameActions.MOVEMOTHERNATURE, moveMNInfo);
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right kind, try to execute the movement
            if (response instanceof GameCommandMoveMotherNature c) {
                try {
                    c.executeCommand();
                }

                catch (Exception e) {
                    // Fatal error: print the stack trace to help debug
                    e.printStackTrace();
                }
            }

            // If the player decided to play a CharacterCard
            else if (response instanceof GameCommandPlayCharacterCard c) {
                // If the player already used a CharacterCard during this turn, throws an exception
                if (data.checkPlayedCard())
                    throw new IllegalStateException("CharacterCard has been already used by the current player!");

                // Executes the command received
                Character chosenCharacter = c.executeCommand();

                try {
                    // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
                    CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

                    // Calls the selected characterCard's strategy effect
                    chosenCardStrategy.playCharacterCard();

                    // If the game hasn't ended restart the method from the beginning
                    // in order to allow the MotherNature movement
                    if (!data.checkWinTrigger()) {
                        executeState();
                        return;
                    }
                }

                catch (Exception e) {
                    // Fatal error: print the stack trace to help debug
                    e.printStackTrace();
                }

                // After the CharacterCard usage, execute once again the procedure of GameStateMoveMotherNature
                executeState();
            }

            // If the response is of the wrong kind throw an exception to help debug
            else
                throw new IllegalStateException("Wrong command received: " + response);
        }

        catch (SocketException e) {
            throw e;
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    private void updateMotherNaturePosition() {
        int updatedMotherNaturePosition = ControllerData.getInstance().getGameModel().getMotherNaturePosition();

        // Creates a map and saves in it the current MotherNaturePosition on islandArray
        InfoMap updateInfo = new InfoMap();
        updateInfo.put(GameValues.MOTHERNATURE, updatedMotherNaturePosition);

        try {
            // Notify to all the player the new MotherNaturePosition
            for (Player playerToUpdate : ControllerData.getInstance().getGameModel().getPlayers())
                ControllerData.getInstance().getPlayerView(playerToUpdate).sendMessage(new GameCommandSendInfo(updateInfo));
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    private Island[] getReachableIslands(int motherNatureMovement, int motherNaturePosition) {
        Island[]     islands             = ControllerData.getInstance().getGameModel().getIslands();
        int          numOfIsland         = islands.length;
        List<Island> reachableIslandList = new ArrayList<>();

        for (int i = 0, p = (motherNaturePosition + 1) % numOfIsland; i < motherNatureMovement; ++i, p = (p + 1) % numOfIsland)
            reachableIslandList.add(islands[p]);

        return reachableIslandList.toArray(Island[]::new);
    }
}
