package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardManager;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;
import java.util.*;

import static it.polimi.ingsw.common.utils.Methods.ifNotNull;

/**
 * State representing the retrieval of students from an island
 * @author Mattia Martelli
 */
public final class GameStateChooseCloud implements GameStateActionPhase {
    public GameState nextState() {
        return
            // Check if the game has been won already
            ControllerData.getInstance().checkWinTrigger() ?
                // If the game has ended, return a plain end phase, which will decide who won
                new GameStateEndGame() :
                // Otherwise, go to the next phase
                new GameStateEndOfTurn();
    }

    public void executeState() throws SocketException {
        try {
            ControllerData data                 = ControllerData.getInstance();
            Player         player               = data.getCurrentPlayer();
            VirtualView    playerView           = data.getPlayerView(player);
            InfoMap        chooseCloudInfo      = new InfoMap();
            boolean        expertMode           = data.getExpertMode();
            boolean        canPlayCharacterCard = false;

            // Gets all the cloud still filled by students
            Optional<CloudTile[]> availableCloud = getAvailableClouds();

            // If there are not cloudTile filled by at least one student, end GameStateChooseCloud
            if (availableCloud.isEmpty())
                return;

            chooseCloudInfo.put(GameValues.CLOUDARRAY, availableCloud.get());

            // If the player hasn't player a card yet
            if (expertMode && !data.checkPlayedCard()) {
                // Get all the playableCharacterCard, according to previous CharacterCards utilization and to current player's coin pool
                CharacterCard[] playableCharacterCard = CharacterCardManager.getPlayableCharacterCard(player);

                // If there are CharacterCard playable by the current player, adds them to the moveStudentsInfo
                if (playableCharacterCard != null) {
                    chooseCloudInfo.put(GameValues.CHARACTERCARDARRAY, playableCharacterCard);

                    //Set the flag to true to change the GameCommandRequestAction's type during its initialization
                    canPlayCharacterCard = true;
                }
            }

            // Send a RequestAction to the current player, asking him to choose a CloudTile between the provided ones
            // If the player is allowed to, send also the CharacterCard he could play
            GameCommand request  = canPlayCharacterCard ?
                new GameCommandRequestAction(GameActions.CHOOSECLOUDORPLAYCARD, chooseCloudInfo) :
                new GameCommandRequestAction(GameActions.CHOOSECLOUD, availableCloud.get());
            GameCommand response = playerView.sendRequest(request);

            // If the response is of the right type, try to execute
            if (response instanceof GameCommandChooseCloud c) {
                try {
                    Color[] students = (Color[]) c.executeCommand();
                    for (Color student : students)
                        player.getSchoolBoard().getEntrance().appendStudent(student);

                    InfoMap map = new InfoMap();
                    map.put(GameValues.ENTRANCE, new Tuple<>(player.getPlayerID(), player.getSchoolBoard().getEntrance()));
                    map.put(GameValues.CLOUDARRAY, data.getGameModel().getCloudTile());

                    for (Player playerToUpdate : data.getGameModel().getPlayer())
                        data.getPlayerView(playerToUpdate).sendMessage(new GameCommandSendInfo(map));
                }

                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            // If the player decided to play a CharacterCard
            else if (response instanceof GameCommandPlayCharacterCard c) {
                // If the player already used a CharacterCard during this turn, throws an exception to help debug
                if(data.checkPlayedCard())
                    throw new IllegalStateException("Character card already used");

                // Executes the command received
                Character chosenCharacter = (Character) c.executeCommand();

                // Gets the characterCardStrategy linked to the CharacterCard chosen by the player
                CharacterCardStrategy chosenCardStrategy = CharacterCardManager.getChosenCharacterCardStrategy(chosenCharacter);

                // Calls the selected characterCard's strategy effect
                chosenCardStrategy.playCharacterCard();

                // After the CharacterCard utilization, it recalls
                // execute once again the procedure of GameStateChooseCloud if the game hasn't ended
                if (!data.checkWinTrigger())
                    executeState();
            }

            // If the response is of the wrong kind throw an exception to help debug
            else
                throw new IllegalStateException("Wrong command received: " + response);
        }

        // If there was a connection error, signal the DFA
        catch (SocketException e) {
            throw e;
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    private Optional<CloudTile[]> getAvailableClouds() {
        List<CloudTile> availableCloudTiles = new ArrayList<>();

        Arrays.stream(ControllerData.getInstance().getGameModel().getCloudTile())
            .forEachOrdered(c -> {
                if (c != null && c.getStudents()[0] != null)
                    availableCloudTiles.add(c);
            });

        return availableCloudTiles.isEmpty() ?
            Optional.empty() :
            Optional.of(availableCloudTiles.toArray(CloudTile[]::new));
    }
}
