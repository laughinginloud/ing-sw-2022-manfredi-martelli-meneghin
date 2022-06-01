package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.sceneHandlers.ClientInfoHandler;
import it.polimi.ingsw.client.view.gui.sceneHandlers.GUIAlert;
import it.polimi.ingsw.client.view.gui.sceneHandlers.GUIHandler;
import it.polimi.ingsw.client.view.gui.sceneHandlers.WizardChoiceHandler;
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
public final class ViewGUI extends Application implements View {
    
    // region Fields

    VirtualController virtualController = null;
    private GameModel model;
    private Scene     currentScene;
    private Stage     stage;
    private Address   connectionAddress;

    // The different scenes and scenesHandlers are saved in two maps, using the page (an enum for the scene) as key
    private final HashMap<Pages,Scene>       nameMapScene   = new HashMap<>();
    private final HashMap<Pages, GUIHandler> nameMapHandler = new HashMap<>();

    // endregion Fields

    // region StartMethods

    /**
     * Starts the Java Application thread and the GUI Window
     * @param stage the stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Adds the scenes and the sceneHandlers to their respective HashMap
        setupScenes();

        // Sets the title, the scene, the icon, the trigger on close request
        stage.setTitle("Eriantys pre alpha 4.0");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image("cranio.png"));

        // Event on close request -> exit
        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            // If the client is still waiting to know if the player wants to start or close the game
            // this signal closes the Client.java
            Client.signalPlayExit(false);

            System.out.println("You successfully exited the game!");

            stage.close();
        }
    }

    // endregion StartMethods

    //********************************************************************************************************

    // region Testing

    /**
     * Changes the scene from a scene to the specified one, also sets the gui contained in that handler
     * @param page the page to switch the scene to
     */
    public void changeScene(Pages page){
        this.currentScene = nameMapScene.get(page);
        nameMapHandler.get(page).setGUI(this);
        stage.setScene(currentScene);
        //stage.show();
    }

    // endregion Testing

    //********************************************************************************************************


    // region ViewImplementation

    // region MiscellanousMethods

    /**
     * Launches the UI (CLI or GUI) of the program
     */
    @Override
    public void launchUI() {
        launch();
    }

    /**
     * Print the initial menu of the application
     */
    @Override
    public void playExitMenu() {
        this.currentScene = nameMapScene.get(Pages.INITIAL_PAGE);
        nameMapHandler.get(Pages.INITIAL_PAGE).setGUI(this);
        stage.setScene(currentScene);
        stage.show();
    }

    /**
     * Signals the player that the connection to the server
     * is unavailable and that the application will be closed
     */
    @Override
    public void signalConnectionError() {
        // TODO [Connection] - Understand how this method should work
    }

    /**
     * Updates the model, in order to show the latest model condition
     * @param model The updated model present on the server's model
     */
    @Override
    public void updateModel(GameModel model) {
        // TODO [Game]
    }

    // endregion MiscellanousMethods

    // region AskPlayer

    /**
     * Asks the player the address (ip, port) of the server he wants to connect to
     */
    @Override
    public void askAddress() {
        this.currentScene = nameMapScene.get(Pages.SERVER_INFO);
        nameMapHandler.get(Pages.SERVER_INFO).setGUI(this);
        stage.setScene(currentScene);
    }

    /**
     * Asks the num of the players the first person connected would like to play with and whether
     * he likes to play the game in expertMode
     */
    @Override
    public void askRules() {
        this.currentScene = nameMapScene.get(Pages.ASK_RULES);
        nameMapHandler.get(Pages.ASK_RULES).setGUI(this);
        stage.setScene(currentScene);
    }

    /**
     * Asks the player whether he wants to resume an old game or he doesn't want to.
     */
    @Override
    public void askReloadGame() {

        this.currentScene = nameMapScene.get(Pages.GAME_CHOICE);
        nameMapHandler.get(Pages.GAME_CHOICE).setGUI(this);
        stage.setScene(currentScene);
    }

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     */
    @Override
    public void askEndOfTurn() {
        // TODO [Game]
    }

    // endregion AskPlayer

    // region Requests

    /**
     * Requests the player to provide his username and from how many years he knows Magic
     *
     * @param forbiddenUsernames A Set(String) containing all the username already used by the other player
     */
    @Override
    public void requestUsernameAndMagicAge(Set<String> forbiddenUsernames) {
        this.currentScene = nameMapScene.get(Pages.CLIENT_INFO);
        nameMapHandler.get(Pages.CLIENT_INFO).setGUI(this);
        ((ClientInfoHandler) nameMapHandler.get(Pages.CLIENT_INFO)).setForbiddenUsernames(forbiddenUsernames);
        stage.setScene(currentScene);
    }

    /**
     * Requests the player which deck (wizard) he wants to play with
     *
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     */
    @Override
    public void requestWizard(Wizard[] availableWizards) {
        this.currentScene = nameMapScene.get(Pages.WIZARD_CHOICE);
        nameMapHandler.get(Pages.WIZARD_CHOICE).setGUI(this);
        ((WizardChoiceHandler) nameMapHandler.get(Pages.WIZARD_CHOICE)).setAvailableWizard(availableWizards);
        stage.setScene(currentScene);
    }

    /**
     * Requests the player which assistantCard he wants to play between the provided assistantCards
     *
     * @param assistantCards An array of AssistantCards that are currently playable
     */
    @Override
    public void requestPlayAssistantCard(AssistantCard[] assistantCards) {
        // TODO [Game]
    }

    /**
     * Requests the player which characterCards he would like to play between the CharacterCard provided
     *
     * @param playableCharacterCards An array of characterCards that are currently playable
     */
    @Override
    public void requestPlayCharacterCard(CharacterCard[] playableCharacterCards) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose one student from the entrance, that he will move to another place
     *
     * @param entranceStudents The students currently on the entrance that are movable
     */
    @Override
    public void requestStudentEntranceSelection(Color[] entranceStudents) {
        // TODO [Game]
    }

    /**
     * Shows to the player the entranceStudents and the playableCharacterCards, waiting for a selection.
     * It sets clickable all and only entranceStudents and playableCharactercards and based on
     * the click, I'll decide the return
     *
     * @param entranceStudents       An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    @Override
    public void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {
        // TODO [Game]
    }

    /**
     * Requests the player to move the selected student from his entrance to an Island or to a table
     * of his diningRoom
     *
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still
     *                             have free seats (where the player can move the student)
     */
    @Override
    public void requestStudentEntranceMovement(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {
        // TODO [Game]
    }

    /**
     * Requests the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player, according to the
     * provided Islands' array
     *
     * @param possibleMovement An array containing the Islands that can be moved by the player
     */
    @Override
    public void requestMotherNatureMovement(Island[] possibleMovement) {
        // TODO [Game]
    }

    /**
     * Requests the player to move motherNature among the possible islands.
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards
     * that can be played. It sets to clickable only the Islands that can be selected by the player,
     * according to the provided Islands' array and only the playable CharacterCards
     *
     * @param possibleMovement       An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    @Override
    public void requestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose a CloudTile from the availableClouds
     *
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     */
    @Override
    public void requestCloudTileSelection(CloudTile[] availableClouds) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose between selecting a CloudTile or playing a CharacterCard.
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     *
     * @param availableClouds        An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     */
    @Override
    public void requestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {
        // TODO [Game]
    }

    /**
     * Requests the player how many students he wants to move
     *
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     */
    @Override
    public void requestHowManyStudentsToMove(int maxNumOfStudentMovable) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose a color between the provided ones
     *
     * @param availableColors The color that can be chosen by the player
     */
    @Override
    public void requestChooseColor(Color[] availableColors) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param characterCardPosition The position in the characterCardArray of the
     *                              characterCard that is being played
     * @param availableColors       The colors correspondent to the students that
     *                              can be chosen between the characterCard's students
     * @param numOfAvailableStudent The number of students available on the characterCard (it could be useful)
     */
    @Override
    public void chooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param availableColors The colors correspondent to the students that can be
     *                        moved/picked from the Entrance
     */
    @Override
    public void chooseStudentFromEntrance(Color[] availableColors) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose an Island. It sets to "clickable"
     * only the island present in the "availableIslands" array
     *
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     */
    @Override
    public void requestChooseIsland(Island[] availableIslands) {
        // TODO [Game]
    }

    /**
     * Requests the player to choose a diningRoomTable from the provided ones.
     * It links the diningRoomTable with theirs color, then make "clickable" only the diningRoomTable
     * that have the same color of the provided "compatibleDiningRoomTable" colors' array
     *
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     */
    @Override
    public void requestChooseDiningRoom(Color[] compatibleDiningRoomTable) {
        // TODO [Game]
    }

    // endregion Requests

    // region Notifications

    /**
     * Notifies the player that there's already a game in progress,
     * then he will be disconnected from the server
     */
    @Override
    public void notifyGameInProgress() {
        // TODO [Connection]
    }

    /**
     * Notifies the player that the Game (new or old) is starting, meaning that all the players required
     * by this game's rules are satisfied
     */
    @Override
    public void notifyGameStart() {
        // TODO [Connection]
    }

    /**
     * Notifies the player that he will be instantly disconnected from the server (then from the Game)
     *
     * @param disconnectionReason An Optional(String) containing the reason for the disconnection
     */
    @Override
    public void notifyPlayerDisconnection(Optional<String> disconnectionReason) {
        // TODO [Connection]
    }

    /**
     * Notifies the view about the chance to play the CharacterCard, if during the States where
     * the CharacterCard are usable there is at least a CharacterCard that is playable according
     * to player's coins and characterCard's tokens. This will "enable" the playCharacterCard button.
     */
    @Override
    public void notifyCharacterCardPlayability() {
        // TODO [Game]
    }

    /**
     * Notifies the player about the beginning of his turn
     */
    @Override
    public void notifyStartGameTurn() {
        // TODO [Game]
    }

    /**
     * Notifies the player he has to wait another player's turn
     */
    @Override
    public void notifyWaitGameTurn() {
        // TODO [Game]
    }

    /**
     * Notifies the player his turn has ended
     */
    @Override
    public void notifyEndOfTurn() {
        // TODO [Game]
    }

    // endregion Notifications

    /**
     * Forwards the infoToSend to the Virtual Controller,
     * contacting it through the method "messageAfterUserInteraction"
     *
     * @param infoToSend An object containing the information that the client has to provide to the server
     *                   in order to make some actions during the game (es. play a CharacterCard,
     *                   move a Students, etc...)
     */
    @Override
    public void forwardViewToVirtualController(Object infoToSend) {
        virtualController.messageAfterUserInteraction(infoToSend);
    }

    /**
     * Sets the virtualController of this class to a specific virtualController received with the method
     *
     * @param virtualController The VirtualController that this.virtualController has to be set to
     */
    @Override
    public void setVirtualController(VirtualController virtualController) {
        this.virtualController = virtualController;
    }

    // region SignalEndGame

    /**
     * Signals the player the winner
     *
     * @param winner the winning player
     */
    @Override
    public void signalWinner(Player winner) {
        // TODO [EndGame]
    }

    /**
     * Signals the player the winning team
     *
     * @param team the winning team
     */
    @Override
    public void signalWinner(List<Player> team) {
        // TODO [EndGame]
    }

    /**
     * Signals the player who ended in a draw
     *
     * @param drawers the player who ended in a draw
     */
    @Override
    public void signalDraw(List<Player> drawers) {
        // TODO [EndGame]
    }

    // endregion SignalEndGame

    // endregion ViewImplementation

}
