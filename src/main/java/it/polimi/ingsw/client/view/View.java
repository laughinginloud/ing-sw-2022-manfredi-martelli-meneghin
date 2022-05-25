package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.viewRecord.*;

import java.util.Optional;

public interface View {
    void initialize(); //TODO: decidere se collassare nel costruttore

    void setUpBoard();

    Address getAddress();

    void signalConnectionError();

    /**
     * Updates the model, in order to show the latest model condition
     * @param model The updated model present on the server's model
     */
    void updateModel(GameModel model);

    /**
     * Asks the player whether he wants to resume an old game or he doesn't want to.
     * @return A boolean representing the player's choice (true -> resume, false -> createNewGame and delete the old game)
     */
    boolean askResumeGame();

    /**
     * Asks the player whether he wants to have another game with the same rules and the same player
     * @return The player's choice about the ReplayMatch request
     */
    boolean askReplayMatch();

    /**
     * Asks the current player which is address he wants to connect to
     * @return A record ConnectionInfo containing serverAddress and serverPort chosen by the player
     */
    ConnectionInfo askConnectionInfo();

    /**
     * Asks the num of the players the first person connected would like to play with and whether he likes to play the game in expertMode
     * @return A record GameRules containing the selected numOfPlayer and a boolean representing the player's choice about expertMode
     */
    GameRules askRules();

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     * @return A boolean representing the decision of the player (true -> ends the Turn, false -> plays the CC)
     */
    boolean askEndOfTurn();

    /**
     * Asks the player which deck (wizard) he wants to play with
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     * @return The wizard chosen by the player
     */
    Wizard requestWizard(Wizard[] availableWizards);

    /**
     * Asks the player to provide his username and from how many years he know Magic
     * @return A record UsernameAndMagicAge representing the username inserted by the client's user and his magic age
     */
    UsernameAndMagicAge requestUsernameAndMagicAge();
        //It must refresh the "insertUsername" page of the GUI and the CLI


    /**
     * Asks the player which assistantCard he wants to play between the provided assistantCards
     * @param assistantCards An array of AssistantCards that are currently playable
     * @return The assistantCard chosen by the player
     */
    AssistantCard requestPlayAssistantCard(AssistantCard[] assistantCards);

    /**
     * Asks the player which characterCards he would like to play between the CharacterCard provided
     * @param playableCharacterCards An array of characterCards that are currently playable
     * @return The characterCard selected by the player
     */
    CharacterCard requestPlayCharacterCard(CharacterCard[] playableCharacterCards);

    /**
     * Asks the player to choose one student from the entrance, that he will move to another place
     * @param entranceStudents The students currently on the entrance that are movable
     * @return An int representing the StudentIndex of the selected student
     */
    int requestStudentEntranceSelection(Color[] entranceStudents);

    /**
     * Show to the player the entranceStudents and the playableCharacterCards, waiting for a selection
     * @param entranceStudents An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     * @return An object representing the selected item: it could be the chosen CharacterCard (CharacterCard)
     * or the chosen entrance's student index (Integer)
     */
    Object requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards);
        // Devo rendere clickabili tutti gli entranceStudents e le playableCharacterCards, a seconda di quello che il player
        // seleziona, decido quale valore ritornare

    /**
     * Asks the player to move the selected student from his entrance to an Island or to a table of his diningRoom
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still have free seats (where the player can move the student)
     * @return A record MoveStudentInfo containing toDiningRoom (which indicates if the player moved the student to the diningRoom),
     *         the optional numIsland of the Island where he moved the student to and the entrance's student index of the student move by the player
     */
    MoveStudentInfo movementStudentEntrance(int selectedStudentIndex, Boolean[] diningRoomFreeTables);

    /**
     * Asks the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array
     * @param possibleMovement An array containing the Islands that can be moved by the player
     * @return In int representing the Index of the Island selected by the player
     */
    int requestMotherNatureMovement(Island[] possibleMovement);

    /**
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards that can be played
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array and
     * only the playable CharacterCards
     * @param possibleMovement An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     * @return An object representing the selected item: it could be the chosen CharacterCard (CharacterCard)
     *         or the Index of the chosenIsland
     */
    Object requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards);

    /**
     * Asks the player to choose a CloudTile from the availableClouds
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     * @return The selected CloudTile
     */
    CloudTile requestCloudTileSelection(CloudTile[] availableClouds);


    /**
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     * @param availableClouds An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     * @return An object representing the selected item: it could be the chosen CharacterCard (CharacterCard)
     *         or the chosen CloudTile (CloudTile)
     */
    Object requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards);

    /**
     * Asks the player how many students he wants to move
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     * @return An int representing the player's choice
     */
    int requestHowManyStudentsToMove(int maxNumOfStudentMovable);

    /**
     * Asks the player to choose a color between the provided ones
     * @param availableColors The color that can be chosen by the player
     * @return The color chosen by the player
     */
    Color requestChooseColor(Color[] availableColors);

    /**
     * Asks the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     * @param characterCardPosition The position in the characterCardArray of the characterCard that is being played
     * @param availableColors The colors correspondent to the students that can be chosen between the characterCard's students
     * @param numOfAvailableStudent The number of students available on the characterCard (it could be useful)
     * @return An int representing the position of the chosen students on the characterCardStudents
     */
    int chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent);

    /**
     * Asks the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     * @param availableColors The colors correspondent to the students that can be moved/picked from the Entrance
     * @return An int representing the position of the chosen students on the player's Entrance
     */
    int chooseStudentFromEntrance(Color[] availableColors);

    /**
     * Asks the player to choose an Island. It sets to "clickable" only the island present in the "availableIslands" array
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     * @return An int representing the index of the Island chosen by the player
     */
    int requestChooseIsland(Island[] availableIslands);

    /**
     * Asks the player to choose a diningRoomTable from the provided ones. It links the diningRoomTable with theirs color, then
     * make "clickable" only the diningRoomTable that have the same color of the provided "compatibleDiningRoomTable" colors' array
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     * @return The color of the chosen DiningRoomTable
     */
    Color requestChooseDiningRoom(Color[] compatibleDiningRoomTable);

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




    MenuItem menu();
}
