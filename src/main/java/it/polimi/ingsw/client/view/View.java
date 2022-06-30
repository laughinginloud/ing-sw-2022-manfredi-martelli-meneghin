package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;

import java.util.List;
import java.util.Set;

/**
 * Interface shared between ViewCLI and ViewGUI
 * @author Giovanni Manfredi, Mattia Martelli and Sebastiano Meneghin
 */
public sealed interface View permits ViewCLI, ViewGUI {

    /**
     * Launches the UI (CLI or GUI) of the program
     */
    void launchUI();

    /**
     * Print the initial menu of the application
     */
    void playExitMenu();

    /**
     * Signals the player that the connection to the server
     * is unavailable and that the application will be closed
     */
    void signalConnectionError();

    /**
     * Updates the model, in order to show the latest model condition
     *
     * @param model The updated model present on the server's model
     * @param updatedValues
     */
    void updateModel(GameModel model, Set<GameValues> updatedValues);

    // region AskPlayer

    /**
     * Asks the player the address (ip, port) of the server he wants to connect to
     */
    void askAddress();

    /**
     * Asks the num of the players the first person connected would like to play with and whether
     * he likes to play the game in expertMode
     */
    void askRules();

    /**
     * Asks the player whether he wants to resume an old game or he doesn't want to.
     */
    void askReloadGame();

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     */
    void askEndOfTurn();

    // endregion AskPlayer

    // region Requests

    /**
     * Requests the player to provide his username and from how many years he knows Magic
     *
     * @param forbiddenUsernames A Set(String) containing all the username already used by the other player
     */
    void requestUsernameAndMagicAge(Set<String> forbiddenUsernames);

    /**
     * Requests the player which deck (wizard) he wants to play with
     *
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     */
    void requestWizard(Wizard[] availableWizards);

    /**
     * Requests the player which assistantCard he wants to play between the provided assistantCards
     *
     * @param assistantCards An array of AssistantCards that are currently playable
     */
    void requestPlayAssistantCard(AssistantCard[] assistantCards);

    /**
     * Requests the player which characterCards he would like to play between the CharacterCard provided
     *
     * @param playableCharacterCards An array of characterCards that are currently playable
     */
    void requestPlayCharacterCard(CharacterCard[] playableCharacterCards);

    /**
     * Shows to the player the entranceStudents and the playableCharacterCards, waiting for a selection.
     * It sets clickable all and only entranceStudents and playableCharacterCards and based on
     * the click, I'll decide the return
     *
     * @param entranceStudents       An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards);

    /**
     * Requests the player to move the selected student from his entrance to an Island or to a table
     * of his diningRoom
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still
     *                             have free seats (where the player can move the student)
     */
    void requestStudentEntranceMovement(int selectedStudentIndex, Boolean[] diningRoomFreeTables);

    /**
     * Requests the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player,
     * according to the provided Islands' array
     *
     * @param possibleMovement An array containing the Islands that can be moved by the player
     */
    void requestMotherNatureMovement(Island[] possibleMovement);

    /**
     * Requests the player to move motherNature among the possible islands.
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards
     * that can be played. It sets to clickable only the Islands that can be selected by the player,
     * according to the provided Islands' array and only the playable CharacterCards
     *
     * @param possibleMovement       An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    void requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards);

    /**
     * Requests the player to choose a CloudTile from the availableClouds
     *
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     */
    void requestCloudTileSelection(CloudTile[] availableClouds);


    /**
     * Requests the player to choose between selecting a CloudTile or playing a CharacterCard.
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     *
     * @param availableClouds        An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     */
    void requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards);

    /**
     * Requests the player how many students he wants to move
     *
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     */
    void requestHowManyStudentsToMove(int maxNumOfStudentMovable);

    /**
     * Requests the player to choose a color between the provided ones
     *
     * @param availableColors The color that can be chosen by the player
     */
    void requestChooseColor(Color[] availableColors);

    /**
     * Requests the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param characterCardPosition The position in the characterCardArray
     *                              of the characterCard that is being played
     * @param availableColors       The colors correspondent to the students that
     *                              can be chosen between the characterCard's students
     * @param numOfAvailableStudent The number of students available
     *                              on the characterCard (it could be useful)
     */
    void chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent);

    /**
     * Requests the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param availableColors The colors correspondent to the students that can be
     *                        moved/picked from the Entrance
     */
    void chooseStudentFromEntrance(Color[] availableColors);

    /**
     * Requests the player to choose an Island. It sets to "clickable"
     * only the island present in the "availableIslands" array
     *
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     */
    void requestChooseIsland(Island[] availableIslands);

    /**
     * Requests the player to choose a diningRoomTable from the provided ones.
     * It links the diningRoomTable with theirs color, then make "clickable" only the
     * diningRoomTable that have the same color of the provided "compatibleDiningRoomTable" colors' array
     *
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     */
    void requestChooseDiningRoom(Color[] compatibleDiningRoomTable);

    // endregion Requests

    // region Notifications

    /**
     * Notifies the player that there's already a game in progress, then he will be disconnected
     * from the server
     */
    void notifyGameInProgress();

    /**
     * Notifies the player that the Game (new or old) is starting, meaning that all the players required
     * by this game's rules are satisfied
     */
    void notifyGameStart();

    /**
     * Notifies the player that he will be instantly disconnected from the server (then from the Game)
     */
    void notifyPlayerDisconnection();

    /**
     * Notifies the player about the beginning of another player's turn
     *
     * @param playingPlayerUsername The username of the currentPlayer, that is the
     *                              player which is beginning his actionPhase turn
     */
    void notifyStartGameTurn(String playingPlayerUsername);

    /**
     * Notifies the player that his turn is ended
     */
    void notifyEndOfTurn();

    // endregion Notifications

    // region VirtualControllerInteraction

    /**
     * Forwards the infoToSend to the Virtual Controller,
     * contacting it through the method "messageAfterUserInteraction"
     *
     * @param infoToSend An object containing the information that the client has to provide to the
     *                   server in order to make some actions during the game (es. play a CharacterCard,
     *                   move a Students, etc...)
     */
    void forwardViewToVirtualController(Object infoToSend);

    /**
     * Sets the username in the VirtualController after getting it from the player.
     * This will come in handy when the View needs to identify self-player infos in the model
     *
     * @param readUsername the username of the player
     */
    void setUsernameVirtualController(String readUsername);

    // endregion VirtualControllerInteraction

    // region SignalEndGame

    /**
     * Signals the player the winner
     *
     * @param winner the winning player
     */
    void signalWinner(Player winner);

    /**
     * Signals the player the winning team
     *
     * @param team the winning team
     */
    void signalWinner(List<Player> team);

    /**
     * Signals the player who ended in a draw
     *
     * @param drawers the player who ended in a draw
     */
    void signalDraw(List<Player> drawers);

    // endregion SignalEndGame

    // region Getter

    /**
     * Gets the model saved in the View
     *
     * @return the model saved
     */
    GameModel getModel();

    /**
     * Gets the localPlayer by comparing the Username present in the VirtualController
     * with the players present in the GameModel
     *
     * @return the localPlayer (null if not present)
     */
    Player getLocalPlayer();

    // endregion Getter

    // region Setter

    /**
     * Sets the current Game Model to the one specified
     *
     * @param model A reference to the model
     */
    void setModel(GameModel model);

    /**
     * Sets the virtualController of this class to a specific virtualController received with the method
     *
     * @param virtualController The VirtualController that this.virtualController has to be set to
     */
    void setVirtualController(VirtualController virtualController);

    // endregion Setter
}
