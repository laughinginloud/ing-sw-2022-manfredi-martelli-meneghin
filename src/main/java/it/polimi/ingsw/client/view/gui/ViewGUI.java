package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.viewRecord.ConnectionInfo;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class ViewGUI extends Application implements View {

    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewGUI.class.getResource("/it/polimi/ingsw/fxml/gamePage.fxml"));
            Scene serverInfoScene = new Scene(fxmlLoader.load(), 1920, 1080);

            Image icon = new Image("cranio.png");
            stage.getIcons().add(icon);
            stage.setTitle("Eriantys pre alpha 3.0");

            stage.setScene(serverInfoScene);
            stage.show();

            // Event on close request -> exit
            // TODO Manage the difference between "exiting and closing the game"
            stage.setOnCloseRequest(event -> {
                event.consume();
                exit(stage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

    }

    public void setUpBoard() {

    }

    public Address getAddress() {
        return null;
    }

    public void signalConnectionError() {

    }

    /**
     * Updates the model, in order to show the latest model condition
     *
     * @param model The updated model present on the server's model
     */
    @Override
    public void updateModel(GameModel model) {

    }

    /**
     * Asks the player whether he wants to resume an old game or he doesn't want to.
     *
     * @return A boolean representing the player's choice (true -> resume, false -> createNewGame and delete the old game)
     */
    @Override
    public boolean askResumeGame() {
        return false;
    }

    /**
     * Asks the player whether he wants to have another game with the same rules and the same player
     *
     * @return The player's choice about the ReplayMatch request
     */
    @Override
    public boolean askReplayMatch() {
        return false;
    }

    /**
     * Asks the current player which is address he wants to connect to
     *
     * @return A record ConnectionInfo containing serverAddress and serverPort chosen by the player
     */
    @Override
    public ConnectionInfo askConnectionInfo() {
        return null;
    }

    /**
     * Asks the num of the players the first person connected would like to play with and whether he likes to play the game in expertMode
     *
     * @param possibleNum The possible number of player that a Game could contain
     * @return A record GameRules containing the selected numOfPlayer and a boolean representing the player's choice about expertMode
     */
    @Override
    public GameRules askRules(int[] possibleNum) {
        return null;
    }

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     *
     * @return A boolean representing the decision of the player (true -> ends the Turn, false -> plays the CC)
     */
    @Override
    public boolean askEndOfTurn() {
        return false;
    }

    /**
     * Asks the player which deck (wizard) he wants to play with
     *
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     * @return The wizard chosen by the player
     */
    @Override
    public Wizard requestWizard(Wizard[] availableWizards) {
        return null;
    }

    /**
     * Asks the player to provide his username and from how many years he know Magic
     *
     * @return A record UsernameAndMagicAge representing the username inserted by the client's user and his magic age
     */
    @Override
    public UsernameAndMagicAge requestUsernameAndMagicAge() {
        return null;
    }

    /**
     * Asks the player which assistantCard he wants to play between the provided assistantCards
     *
     * @param assistantCards An array of AssistantCards that are currently playable
     * @return The assistantCard chosen by the player
     */
    @Override
    public AssistantCard requestPlayAssistantCard(AssistantCard[] assistantCards) {
        return null;
    }

    /**
     * Asks the player which characterCards he would like to play between the CharacterCard provided
     *
     * @param playableCharacterCards An array of characterCards that are currently playable
     * @return The characterCard selected by the player
     */
    @Override
    public CharacterCard requestPlayCharacterCard(CharacterCard[] playableCharacterCards) {
        return null;
    }

    /**
     * Asks the player to choose one student from the entrance, that he will move to another place
     *
     * @param entranceStudents The students currently on the entrance that are movable
     * @return An int representing the StudentIndex of the selected student
     */
    @Override
    public int requestStudentEntranceSelection(Color[] entranceStudents) {
        return 0;
    }

    /**
     * Show to the player the entranceStudents and the playableCharacterCards, waiting for a selection
     *
     * @param entranceStudents       An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     * @return An object representing the selected item: it could be the chosen CharacterCard (CharacterCard)
     * or the chosen entrance's student index (Integer)
     */
    @Override
    public Object requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {
        return null;
    }

    /**
     * Asks the player to move the selected student from his entrance to an Island or to a table of his diningRoom
     *
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still have free seats (where the player can move the student)
     * @return A record MoveStudentInfo containing toDiningRoom (which indicates if the player moved the student to the diningRoom),
     * the optional numIsland of the Island where he moved the student to and the entrance's student index of the student move by the player
     */
    @Override
    public MoveStudentInfo movementStudentEntrance(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {
        return null;
    }

    /**
     * Asks the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array
     *
     * @param possibleMovement An array containing the Islands that can be moved by the player
     * @return In int representing the Index of the Island selected by the player
     */
    @Override
    public int requestMotherNatureMovement(Island[] possibleMovement) {
        return 0;
    }

    /**
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards that can be played
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array and
     * only the playable CharacterCards
     *
     * @param possibleMovement       An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     * @return An object representing the selected item: it could be the chosen CharacterCard (CharacterCard)
     * or the Index of the chosenIsland
     */
    @Override
    public Object requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {
        return null;
    }

    /**
     * Asks the player to choose a CloudTile from the availableClouds
     *
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     * @return The selected CloudTile
     */
    @Override
    public CloudTile requestCloudTileSelection(CloudTile[] availableClouds) {
        return null;
    }

    /**
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     *
     * @param availableClouds        An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     * @return An object representing the selected item: it could be the chosen CharacterCard (CharacterCard)
     * or the chosen CloudTile (CloudTile)
     */
    @Override
    public Object requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {
        return null;
    }

    /**
     * Asks the player how many students he wants to move
     *
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     * @return An int representing the player's choice
     */
    @Override
    public int requestHowManyStudentsToMove(int maxNumOfStudentMovable) {
        return 0;
    }

    /**
     * Asks the player to choose a color between the provided ones
     *
     * @param availableColors The color that can be chosen by the player
     * @return The color chosen by the player
     */
    @Override
    public Color requestChooseColor(Color[] availableColors) {
        return null;
    }

    /**
     * Asks the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param characterCardPosition The position in the characterCardArray of the characterCard that is being played
     * @param availableColors       The colors correspondent to the students that can be chosen between the characterCard's students
     * @return An int representing the position of the chosen students on the characterCardStudents
     */
    @Override
    public int chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors) {
        return 0;
    }

    /**
     * Asks the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param availableColors The colors correspondent to the students that can be moved/picked from the Entrance
     * @return An int representing the position of the chosen students on the player's Entrance
     */
    @Override
    public int chooseStudentFromEntrance(Color[] availableColors) {
        return 0;
    }

    /**
     * Asks the player to choose an Island. It sets to "clickable" only the island present in the "availableIslands" array
     *
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     * @return An int representing the index of the Island chosen by the player
     */
    @Override
    public int requestChooseIsland(Island[] availableIslands) {
        return 0;
    }

    /**
     * Asks the player to choose a diningRoomTable from the provided ones. It links the diningRoomTable with theirs color, then
     * make "clickable" only the diningRoomTable that have the same color of the provided "compatibleDiningRoomTable" colors' array
     *
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     * @return The color of the chosen DiningRoomTable
     */
    @Override
    public Color requestChooseDiningRoom(Color[] compatibleDiningRoomTable) {
        return null;
    }

    /**
     * Notify the player that there's already a game in progress, then he will be disconnected from the server
     */
    @Override
    public void notifyGameInProgress() {

    }

    /**
     * Notify to player he has to wait for a "waitingReason"
     *
     * @param waitingReason A string representing the reason why the player has to wait (for a parametric use of the function)
     */
    @Override
    public void notifyWait(String waitingReason) {

    }

    /**
     * Notify the player that the Game (new or old) is starting, meaning that all the players required by this game's rules are satisfied
     */
    @Override
    public void notifyGameStart() {

    }

    /**
     * Notify the player he will be instantly disconnected from the server (then from the Game)
     *
     * @param disconnectionReason An Optional(String) containing the reason for the disconnection
     */
    @Override
    public void notifyPlayerDisconnection(Optional<String> disconnectionReason) {

    }

    /**
     * Notify the view about the chance to play the CharacterCard, if during the States where the CharacterCard are usable
     * there is at least a CharacterCard that is playable according to player's coins and characterCard's tokens. This will
     * "enable" the playCharacterCard button.
     */
    @Override
    public void notifyCharacterCardPlayability() {

    }

    /**
     * Notify the player about the beginning of his turn
     */
    @Override
    public void notifyStartGameTurn() {

    }

    /**
     * Notify the player he has to wait another player's turn
     */
    @Override
    public void notifyWaitGameTurn() {

    }

    /**
     * Notify the player his turn has ended
     */
    @Override
    public void notifyEndOfTurn() {

    }

    // Exit = Close the application without saving the progress
    public void exit(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting the game");
        alert.setHeaderText("You're about to exit the game!");
        alert.setContentText("Your progress won't be saved. Do you want to exit anyways?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("You successfully exited the game!");
            stage.close();
        }
    }
}
