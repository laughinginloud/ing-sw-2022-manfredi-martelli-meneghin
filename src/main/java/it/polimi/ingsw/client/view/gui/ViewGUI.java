package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.MenuItem;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.sceneHandlers.GUIAlert;
import it.polimi.ingsw.client.view.gui.sceneHandlers.GUIHandler;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Color;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Set;
import java.io.IOException;
import java.util.*;

/**
 * View implementation for the GUI (Graphic User Interface)
 * ViewGUI implements Application to execute the program with the Java Application Thread
 * @author Giovanni Manfredi & Sebastiano Meneghin
 */
public class ViewGUI extends Application implements View {

    VirtualController virtualController = null;
    private GameModel model;
    private Scene     currentScene;
    private Stage     stage;

    // The different scenes are saved in a map, using the page (an enum for the scene) as key
    private final HashMap<Pages,Scene>       nameMapScene   = new HashMap<>();

    // The different scenesHandlers are saved in a map, using the page (an enum for the scene) as key
    private final HashMap<Pages, GUIHandler> nameMapHandler = new HashMap<>();

    private Address connectionAddress;

    /**
     * Main of the ViewGUI application
     * @param args arguments to be passed
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method which overrides the one inherited with Application
     * @param stage the stage opened
     */
    @Override
    public void start(Stage stage) {
        // Adds the scenes and the sceneHandlers to their respective HashMap
        setupScenes();
        // Saves the stage in the ViewGUI
        this.stage = stage;
        // Sets the title, the icon, the trigger on closing the scene and shows the scene
        initialize();
    }

    /**
     * Creates all the Scenes, fills the scenesMap and the handlersMap, then sets the currentScene
     * to the firstScene
     */
    public void setupScenes() {
        try {
            // An array of all pages
            Pages[] pages = Pages.values();
            // For each page, it loads the scene and its respective handler (or controller)
            for (Pages page : pages) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Pages.getPathOf(page)));
                nameMapScene.put(page, new Scene(loader.load()));
                GUIHandler handler = loader.getController();
                handler.setGUI(this);
                nameMapHandler.put(page, handler);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        // Sets the current scene to the first scene, named "Server Info"
        currentScene = nameMapScene.get(Pages.SERVER_INFO);
    }

    /**
     *  Sets the title, the scene, the icon, the trigger on close request
     */
    public void initialize() {
        stage.setTitle("Eriantys pre alpha 4.0");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image("cranio.png"));

        // Event on close request -> exit
        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });

        stage.show();
    }

    /**
     * Sets an alert if the close button is pressed, and if confirmed closes the stage (and the game)
     * @param stage the stage to be closed
     */
    public void exit(Stage stage){
        // Sets a particular alert passing the parameters to the function getAlert present in GUIAlert class
        Alert alert = GUIAlert.getAlert(GUIAlert.EXIT, null);

        // alert.showAndWait is the function that allows the alert to trigger
        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("You successfully exited the game!");
            stage.close();
        }
    }

    /**
     * Chages the scene from a scene to the specified one, also sets the gui contained in that handler
     * @param page the page to switch the scene to
     */
    public void changeScene(Pages page){
        this.currentScene = nameMapScene.get(page);
        nameMapHandler.get(page).setGUI(this);
        stage.setScene(currentScene);
        //stage.show();
    }

    public void setUpBoard() {

    }

    public Address getAddress() {
        return connectionAddress;
    }

    public void setAddress(Address connectionAddress) {
        this.connectionAddress = connectionAddress;
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
     */
    @Override
    public void askReloadGame() {

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
     * @return A record Address containing serverAddress and serverPort chosen by the player
     */
    @Override
    public Address askConnectionInfo() {
        return null;
    }

    /**
     * Asks the num of the players the first person connected would like to play with and whether he likes to play the game in expertMode
     */
    @Override
    public void askRules() {

    }

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     */
    @Override
    public void askEndOfTurn() {

    }

    /**
     * Asks the player which deck (wizard) he wants to play with
     *
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     */
    @Override
    public void requestWizard(Wizard[] availableWizards) {

    }

    /**
     * Asks the player to provide his username and from how many years he knows Magic
     *
     * @param forbiddenUsernames A Set(String) containing all the username already used by the other player
     */
    @Override
    public void requestUsernameAndMagicAge(Set<String> forbiddenUsernames) {

    }

    /**
     * Asks the player which assistantCard he wants to play between the provided assistantCards
     *
     * @param assistantCards An array of AssistantCards that are currently playable
     */
    @Override
    public void requestPlayAssistantCard(AssistantCard[] assistantCards) {

    }

    /**
     * Asks the player which characterCards he would like to play between the CharacterCard provided
     *
     * @param playableCharacterCards An array of characterCards that are currently playable
     */
    @Override
    public void requestPlayCharacterCard(CharacterCard[] playableCharacterCards) {

    }

    /**
     * Asks the player to choose one student from the entrance, that he will move to another place
     *
     * @param entranceStudents The students currently on the entrance that are movable
     */
    @Override
    public void requestStudentEntranceSelection(Color[] entranceStudents) {

    }

    /**
     * Show to the player the entranceStudents and the playableCharacterCards, waiting for a selection
     *
     * @param entranceStudents       An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    @Override
    public void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {

    }

    /**
     * Asks the player to move the selected student from his entrance to an Island or to a table of his diningRoom
     *
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still have free seats (where the player can move the student)
     */
    @Override
    public void movementStudentEntrance(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {

    }

    /**
     * Asks the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array
     *
     * @param possibleMovement An array containing the Islands that can be moved by the player
     */
    @Override
    public void requestMotherNatureMovement(Island[] possibleMovement) {

    }

    /**
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards that can be played
     * It sets to clickable only the Islands that can be selected by the player, according to the provided Islands' array and
     * only the playable CharacterCards
     *
     * @param possibleMovement       An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    @Override
    public void requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {

    }

    /**
     * Asks the player to choose a CloudTile from the availableClouds
     *
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     */
    @Override
    public void requestCloudTileSelection(CloudTile[] availableClouds) {

    }

    /**
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     *
     * @param availableClouds        An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     */
    @Override
    public void requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {

    }

    /**
     * Asks the player how many students he wants to move
     *
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     */
    @Override
    public void requestHowManyStudentsToMove(int maxNumOfStudentMovable) {

    }

    /**
     * Asks the player to choose a color between the provided ones
     *
     * @param availableColors The color that can be chosen by the player
     */
    @Override
    public void requestChooseColor(Color[] availableColors) {

    }

    /**
     * Asks the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param characterCardPosition The position in the characterCardArray of the characterCard that is being played
     * @param availableColors       The colors correspondent to the students that can be chosen between the characterCard's students
     * @param numOfAvailableStudent The number of students available on the characterCard (it could be useful)
     */
    @Override
    public void chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent) {

    }

    /**
     * Asks the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param availableColors The colors correspondent to the students that can be moved/picked from the Entrance
     */
    @Override
    public void chooseStudentFromEntrance(Color[] availableColors) {

    }

    /**
     * Asks the player to choose an Island. It sets to "clickable" only the island present in the "availableIslands" array
     *
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     */
    @Override
    public void requestChooseIsland(Island[] availableIslands) {

    }

    /**
     * Asks the player to choose a diningRoomTable from the provided ones. It links the diningRoomTable with theirs color, then
     * make "clickable" only the diningRoomTable that have the same color of the provided "compatibleDiningRoomTable" colors' array
     *
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     */
    @Override
    public void requestChooseDiningRoom(Color[] compatibleDiningRoomTable) {

    }

    /**
     * Asks the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param characterCardPosition The position in the characterCardArray of the characterCard that is being played
     * @param availableColors       The colors correspondent to the students that can be chosen between the characterCard's students
     * @return An int representing the position of the chosen students on the characterCardStudents
     */
    public int chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors) {
        return 0;
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

    @Override
    public MenuItem menu() {
        return null;
    }

    /**
     * Implementation of infoToSend of the abstract interface "View": uses the method "messageAfterUserInteraction" of VirtualController
     * @param infoToSend An object containing the information that the client has to provide to the server in order
     *                   to make some actions during the game (es. play a CharacterCard, move a Students, etc...)
     */
    @Override
    public void viewToVirtualControllerBroker(Object infoToSend) { this.virtualController.messageAfterUserInteraction(infoToSend); }

    /**
     * Sets the virtualController of this class to a specific virtualController received with the method
     * @param virtualController The VirtualController that this.virtualController has to be set to
     */
    public void setVirtualController(VirtualController virtualController) { this.virtualController = virtualController; }
}
