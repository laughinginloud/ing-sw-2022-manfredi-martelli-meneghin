package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.cli.ViewCLI;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.viewRecord.*;

import java.util.Optional;
import java.util.Set;

public sealed interface View permits ViewCLI, ViewGUI {
    void launchUI();

    void setUpBoard();

    void requestAddress();

    void signalConnectionError();

    /**
     * Updates the model, in order to show the latest model condition
     * @param model The updated model present on the server's model
     */
    void updateModel(GameModel model);

    /**
     * Asks the player whether he wants to resume an old game or he doesn't want to.
     */
    void askReloadGame();

    /**
     * Asks the player whether he wants to have another game with the same rules and the same player
     * @return The player's choice about the ReplayMatch request
     */
    boolean askReplayMatch();

    /**
     * Asks the current player which is address he wants to connect to
     * @return A record Address containing serverAddress and serverPort chosen by the player
     */
    Address askConnectionInfo();

    /**
     * Asks the num of the players the first person connected would like to play with and whether he likes to play the game in expertMode
     */
    void askRules();

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     */
    void askEndOfTurn();

    /**
     * Asks the player which deck (wizard) he wants to play with
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     */
    void requestWizard(Wizard[] availableWizards);

    /**
     * Asks the player to provide his username and from how many years he knows Magic
     * @param forbiddenUsernames A Set(String) containing all the username already used by the other player
     */
    void requestUsernameAndMagicAge(Set<String> forbiddenUsernames);

    /**
     * Asks the player which assistantCard he wants to play between the provided assistantCards
     * @param assistantCards An array of AssistantCards that are currently playable
     */
    void requestPlayAssistantCard(AssistantCard[] assistantCards);

    /**
     * Asks the player which characterCards he would like to play between the CharacterCard provided
     * @param playableCharacterCards An array of characterCards that are currently playable
     */
    void requestPlayCharacterCard(CharacterCard[] playableCharacterCards);

    /**
     * Asks the player to choose one student from the entrance, that he will move to another place
     * @param entranceStudents The students currently on the entrance that are movable
     */
    void requestStudentEntranceSelection(Color[] entranceStudents);

    /**
     * Show to the player the entranceStudents and the playableCharacterCards, waiting for a selection
     * @param entranceStudents An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards);
        // Devo rendere clickabili tutti gli entranceStudents e le playableCharacterCards, a seconda di quello che il player
        // seleziona, decido quale valore ritornare

    /**
     * Asks the player to move the selected student from his entrance to an Island or to a table of his diningRoom
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still have free seats (where the player can move the student)
     */
    void movementStudentEntrance(int selectedStudentIndex, Boolean[] diningRoomFreeTables);

    /**
     * Asks the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array
     * @param possibleMovement An array containing the Islands that can be moved by the player
     */
    void requestMotherNatureMovement(Island[] possibleMovement);

    /**
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards that can be played
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array and
     * only the playable CharacterCards
     * @param possibleMovement An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    void requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards);

    /**
     * Asks the player to choose a CloudTile from the availableClouds
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     */
    void requestCloudTileSelection(CloudTile[] availableClouds);


    /**
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     * @param availableClouds An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     */
    void requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards);

    /**
     * Asks the player how many students he wants to move
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     */
    void requestHowManyStudentsToMove(int maxNumOfStudentMovable);

    /**
     * Asks the player to choose a color between the provided ones
     * @param availableColors The color that can be chosen by the player
     */
    void requestChooseColor(Color[] availableColors);

    /**
     * Asks the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     * @param characterCardPosition The position in the characterCardArray of the characterCard that is being played
     * @param availableColors The colors correspondent to the students that can be chosen between the characterCard's students
     * @param numOfAvailableStudent The number of students available on the characterCard (it could be useful)
     */
    void chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent);

    /**
     * Asks the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     * @param availableColors The colors correspondent to the students that can be moved/picked from the Entrance
     */
    void chooseStudentFromEntrance(Color[] availableColors);

    /**
     * Asks the player to choose an Island. It sets to "clickable" only the island present in the "availableIslands" array
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     */
    void requestChooseIsland(Island[] availableIslands);

    /**
     * Asks the player to choose a diningRoomTable from the provided ones. It links the diningRoomTable with theirs color, then
     * make "clickable" only the diningRoomTable that have the same color of the provided "compatibleDiningRoomTable" colors' array
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     */
    void requestChooseDiningRoom(Color[] compatibleDiningRoomTable);

    /**
     * Notify the player that there's already a game in progress, then he will be disconnected from the server
     */
    void notifyGameInProgress();

    /**
     * Notify to player he has to wait for a "waitingReason"
     * @param waitingReason A string representing the reason why the player has to wait (for a parametric use of the function)
     */
    void notifyWait(String waitingReason);

    /**
     * Notify the player that the Game (new or old) is starting, meaning that all the players required by this game's rules are satisfied
     */
    void notifyGameStart();

    /**
     * Notify the player he will be instantly disconnected from the server (then from the Game)
     * @param disconnectionReason An Optional(String) containing the reason for the disconnection
     */
    void notifyPlayerDisconnection(Optional<String> disconnectionReason);

    /**
     * Notify the view about the chance to play the CharacterCard, if during the States where the CharacterCard are usable
     * there is at least a CharacterCard that is playable according to player's coins and characterCard's tokens. This will
     * "enable" the playCharacterCard button.
     */
    void notifyCharacterCardPlayability();

    /**
     * Notify the player about the beginning of his turn
     */
    void notifyStartGameTurn();

    /**
     * Notify the player he has to wait another player's turn
     */
    void notifyWaitGameTurn();

    /**
     * Notify the player his turn has ended
     */
    void notifyEndOfTurn();

    /**
     * Forward the infoToSend to the Virtual Controller, contacting it through the method "messageAfterUserInteraction"
     * @param infoToSend An object containing the information that the client has to provide to the server in order
     *                   to make some actions during the game (es. play a CharacterCard, move a Students, etc...)
     */
    void viewToVirtualControllerBroker(Object infoToSend);

    /**
     * Sets the virtualController of this class to a specific virtualController received with the method
     * @param virtualController The VirtualController that this.virtualController has to be set to
     */
    void setVirtualController(VirtualController virtualController);
}
