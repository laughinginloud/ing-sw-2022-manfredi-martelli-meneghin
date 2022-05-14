package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Character;
import it.polimi.ingsw.virtualView.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * State representing the retrieval of students from an island
 * @author Mattia Martelli
 */
public class GameStateChooseCloud implements GameStateActionPhase {
    public GameState nextState() { return new GameStateEndOfTurn(); }

    public void executeState() {
        try {
            Player player          = ControllerData.getInstance().getCurrentPlayer();
            VirtualView playerView = ControllerData.getInstance().getPlayerView(player);
            Map<GameCommandValues, Object> chooseCloudInfo = new HashMap<>();
            boolean expertMode = ControllerData.getInstance().getExpertMode();
            boolean canPlayCharacterCard = false;

            // Gets all the cloud still filled by students
            CloudTile[] availableCloud = getAvailableClouds();
            chooseCloudInfo.put(GameCommandValues.CLOUDARRAY, availableCloud);

            // If there are not cloudTile filled by at least one student, end GameStateChooseCloud
            if (availableCloud == null)
                return;

            // If the player hasn't player a card yet
            if (expertMode && !ControllerData.getInstance().checkPlayedCard()) {
                // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
                CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(player);

                // If there are CharacterCard playable by the current player, adds them to the moveStudentsInfo
                if (playableCharacterCard != null) {
                    chooseCloudInfo.put(GameCommandValues.CHARACTERCARDARRAY, playableCharacterCard);

                    //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                    canPlayCharacterCard = true;
                }
            }

            // Send a RequestAction to the current player, asking him to choose a CloudTile between the provided ones
            // If the player is allowed to, send also the CharacterCard he could play
            GameCommand request    = canPlayCharacterCard ?
                new GameCommandRequestAction(GameCommandActions.CHOOSECLOUDORPLAYCARD, chooseCloudInfo):
                new GameCommandRequestAction(GameCommandActions.CHOOSECLOUD, chooseCloudInfo);
            GameCommand response   = playerView.sendRequest(request);

            // If the response is of the right type, try to execute
            if (response instanceof GameCommandChooseCloud c) {
                try {
                    c.checkLegalValue();
                    Color[] students = (Color[]) c.executeCommand();
                    for (Color student : students)
                        player.getSchoolBoard().getEntrance().appendStudent(student);

                    Map<GameCommandValues, Object> map = new HashMap<>();
                    map.put(GameCommandValues.ENTRANCE, player.getSchoolBoard().getEntrance());
                    map.put(GameCommandValues.CLOUDARRAY, ControllerData.getInstance().getGameModel().getCloudTile());

                    for (Player playerToUpdate : ControllerData.getInstance().getPlayersOrder())
                        ControllerData.getInstance().getPlayerView(playerToUpdate).sendMessage(new GameCommandSendInfo(map));
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

            // If the player decided to play a CharacterCard
            else if (response instanceof GameCommandPlayCharacterCard c) {
                // If the player already used a CharacterCard during this turn, throws an exception
                if(ControllerData.getInstance().checkPlayedCard())
                    throw new IllegalStateException("CharacterCard has been already used by the current player!");

                // Executes the command received
                Character chosenCharacter = (Character) c.executeCommand();

                // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
                CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

                // Calls the selected characterCard's strategy effect
                chosenCardStrategy.playCharacterCard();
                // TODO [CharacterCardStrategy]: Check Possible EndGame Condition

                // After the CharacterCard utilization, it recalls execute once again the procedure of GameStateChooseCloud
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

    private CloudTile[] getAvailableClouds() {
        CloudTile[] presentCloudTiles = ControllerData.getInstance().getGameModel().getCloudTile();
        CloudTile[] cloudTilesToSend;
        List<CloudTile> availableCloudTiles = new ArrayList<>();

        for (CloudTile cloudTile : presentCloudTiles)
            for (Color color : cloudTile.getStudents())
                if (color != null)
                    availableCloudTiles.add(cloudTile);

        // If there are no available CloudTiles
        if (availableCloudTiles.isEmpty())
            return null;

        // If there is at least one available CloudTile
        else {
            cloudTilesToSend = new CloudTile[availableCloudTiles.size()];
            for (int i = 0; i < availableCloudTiles.size(); i++)
                cloudTilesToSend[i] = availableCloudTiles.get(i);

            return cloudTilesToSend;
        }
    }
}
