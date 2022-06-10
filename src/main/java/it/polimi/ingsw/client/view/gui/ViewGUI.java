package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.gui.sceneHandlers.*;
import it.polimi.ingsw.client.virtualController.VirtualController;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Color;
import javafx.application.Application;
import javafx.application.Platform;
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

    // region Constants

    public static final int MAX_NUM_PLAYERS = 4;

    public static final int MAX_ISLANDS = 12;

    public static final int MAX_CLOUD_TILES = 4;

    public static final int MAX_ASSISTANT_CARDS = 10;

    public static final int MAX_CC_ELEMENTS = 6;

    public static final int MAX_CC = 3;

    public static final int MAX_ENTRANCE_STUDENTS = 9;

    public static final int MAX_DINING_ROOM_STUDENTS = 10;

    // endregion Constants

    // region Fields

    VirtualController virtualController = null;
    private GameModel model;
    private Scene     currentScene;
    private Stage     stage;
    private Address   connectionAddress;

    // The different scenes and scenesHandlers are saved in two maps,
    // using the page (an enum for the scene) as key
    private final HashMap<Pages,Scene>       nameMapScene   = new HashMap<>();
    private final HashMap<Pages, GUIHandler> nameMapHandler = new HashMap<>();

    // endregion Fields

    // region StartMethods

    /**
     * Starts the Java Application thread and the GUI Window
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {
        // Adds the scenes and the sceneHandlers to their respective HashMap
        setupScenes();
        this.stage = stage;

        // Sets the title, the scene, the icon
        stage.setTitle("Eriantys pre alpha 5.0");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/it/polimi/ingsw/images/cranio.png")));

        // Sets the trigger on close request. Event on close request -> exit
        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });

        playExitMenu();
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
                FXMLLoader loader;
                if (page.equals(Pages.GAME_SCENE) || page.equals(Pages.SCHOOL_BOARDS)){
                    loader = new FXMLLoader(getClass().getResource(Pages.getPathOf(page)));
                    nameMapScene.put(page, new Scene(loader.load(), 1920, 1080, javafx.scene.paint.Color.BLACK));
                }
                else {
                    loader = new FXMLLoader(getClass().getResource(Pages.getPathOf(page)));
                    nameMapScene.put(page, new Scene(loader.load(), javafx.scene.paint.Color.BLACK));
                }

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

        Optional<ButtonType> choice = alert.showAndWait();

        // alert.showAndWait is the function that allows the alert to trigger
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            stage.close();

            if (virtualController != null)
                virtualController.close();
        }
    }

    // endregion StartMethods

    // region SwitchScene

    /**
     * Changes the scene from a scene to the specified one, also sets the gui contained in that handler
     * @param nextPage the page to switch the scene to
     */
    public void switchScene(Pages nextPage) {
        this.currentScene = nameMapScene.get(nextPage);
        nameMapHandler.get(nextPage).setGUI(this);

        Platform.runLater(() -> stage.setScene(currentScene));
    }

    // endregion SwitchScene

    // region ViewImplementation

    // region MiscellaneousMethods

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
        stage.setResizable(false);

        Platform.runLater(() -> stage.setScene(currentScene));
        stage.show();
    }

    /**
     * Signals the player that the connection to the server
     * is unavailable and that the application will be closed
     */
    @Override
    public void signalConnectionError() {
        virtualController = null;
        model = null;

        this.currentScene = nameMapScene.get(Pages.CONNECTION_ERROR);
        nameMapHandler.get(Pages.CONNECTION_ERROR).setGUI(this);

        Platform.runLater(() -> stage.setScene(currentScene));
    }

    /**
     * Updates the model, in order to show the latest model condition
     * @param model The updated model present on the server's model
     * @param updatedValues The updated fields of the model
     */
    @Override
    public void updateModel(GameModel model, Set<GameValues> updatedValues) {
        ((GameSceneHandler) nameMapHandler.get(Pages.GAME_SCENE)).gsUpdateModel(model, getLocalPlayer(), updatedValues);
        ((PlayersSchoolBoardHandler) nameMapHandler.get(Pages.SCHOOL_BOARDS)).psbUpdateModel(model,updatedValues);

        if (updatedValues.contains(GameValues.MODEL)){
            this.currentScene = nameMapScene.get(Pages.GAME_SCENE);
            nameMapHandler.get(Pages.GAME_SCENE).setGUI(this);

            Platform.runLater(() -> {
                stage.setScene(currentScene);
                //stage.setFullScreen(true);
            });
        }
    }

    // endregion MiscellaneousMethods

    // region AskPlayer

    /**
     * Asks the player the address (ip, port) of the server he wants to connect to
     */
    @Override
    public void askAddress() {
        currentScene = nameMapScene.get(Pages.SERVER_INFO);
        nameMapHandler.get(Pages.SERVER_INFO).setGUI(this);

        Platform.runLater(() -> stage.setScene(currentScene));
        stage.show();
    }

    /**
     * Asks the num of the players the first person connected would like to play with and whether
     * he likes to play the game in expertMode
     */
    @Override
    public void askRules() {
        this.currentScene = nameMapScene.get(Pages.ASK_RULES);
        nameMapHandler.get(Pages.ASK_RULES).setGUI(this);

        Platform.runLater(() -> stage.setScene(currentScene));
    }

    /**
     * Asks the player whether he wants to resume an old game or he doesn't want to.
     */
    @Override
    public void askReloadGame() {
        this.currentScene = nameMapScene.get(Pages.GAME_CHOICE);
        nameMapHandler.get(Pages.GAME_CHOICE).setGUI(this);

        Platform.runLater(() -> stage.setScene(currentScene));
    }

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     */
    @Override
    public void askEndOfTurn() {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsAskEndOfTurn();

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

        Platform.runLater(() -> stage.setScene(currentScene));
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
        ((WizardChoiceHandler) nameMapHandler.get(Pages.WIZARD_CHOICE)).setClicksWizard();

        Platform.runLater(() -> stage.setScene(currentScene));
    }

    /**
     * Requests the player which assistantCard he wants to play between the provided assistantCards
     *
     * @param assistantCards An array of AssistantCards that are currently playable
     */
    @Override
    public void requestPlayAssistantCard(AssistantCard[] assistantCards) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        Platform.runLater(() -> gs.gsRequestPlayAssistantCard(assistantCards));
    }

    /**
     * Requests the player which characterCards he would like to play between the CharacterCard provided
     *
     * @param playableCharacterCards An array of characterCards that are currently playable
     */
    @Override
    public void requestPlayCharacterCard(CharacterCard[] playableCharacterCards) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestPlayCharacterCard(playableCharacterCards);
    }

    /**
     * Shows to the player the entranceStudents and the playableCharacterCards, waiting for a selection.
     * It sets clickable all and only entranceStudents and playableCharacterCards and based on
     * the click, I'll decide the return
     *
     * @param entranceStudents       An array of students containing the entrance's students
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    @Override
    public void requestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestMoveStudentOrPlayCC(entranceStudents, playableCharacterCards);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestStudentEntranceMovement(selectedStudentIndex, diningRoomFreeTables);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestMotherNatureMovement(possibleMovement);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestMoveMotherNatureOrPlayCC(possibleMovement, playableCharacterCards);
    }

    /**
     * Requests the player to S a CloudTile from the availableClouds
     *
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     */
    @Override
    public void requestCloudTileSelection(CloudTile[] availableClouds) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestCloudTileSelection(availableClouds);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestChooseCloudOrPlayCC(availableClouds, playableCharacterCards);
    }

    /**
     * Requests the player how many students he wants to move
     *
     * @param maxNumOfStudentMovable The maximum number of student the player can decide to move
     */
    @Override
    public void requestHowManyStudentsToMove(int maxNumOfStudentMovable) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestHowManyStudentsToMove(maxNumOfStudentMovable);
    }

    /**
     * Requests the player to choose a color between the provided ones
     *
     * @param availableColors The color that can be chosen by the player
     */
    @Override
    public void requestChooseColor(Color[] availableColors) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestChooseColor(availableColors);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsChooseStudentFromCharacterCard(characterCardPosition, availableColors, numOfAvailableStudent);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsChooseStudentFromEntrance(availableColors);
    }

    /**
     * Requests the player to choose an Island. It sets to "clickable"
     * only the island present in the "availableIslands" array
     *
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     */
    @Override
    public void requestChooseIsland(Island[] availableIslands) {
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestChooseIsland(availableIslands);
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
        GameSceneHandler gs = (GameSceneHandler) this.nameMapHandler.get(Pages.GAME_SCENE);
        gs.gsRequestChooseDiningRoom(compatibleDiningRoomTable);
    }

    // endregion Requests

    // region Notifications

    /**
     * Notifies the player that there's already a game in progress,
     * then he will be disconnected from the server
     */
    @Override
    public void notifyGameInProgress() {
        this.currentScene = nameMapScene.get(Pages.UNABLE_JOIN);
        nameMapHandler.get(Pages.UNABLE_JOIN).setGUI(this);

        Platform.runLater(() -> stage.setScene(currentScene));
    }

    /**
     * Notifies the player that the Game (new or old) is starting, meaning that all the players required
     * by this game's rules are satisfied
     */
    @Override
    public void notifyGameStart() {
        boolean uselessFunction = true;
    }

    /**
     * Notifies the player that he will be instantly disconnected from the server (then from the Game)
     */
    @Override
    // TODO [Implement -> Connection]
    public void notifyPlayerDisconnection() {
        Alert playerDisconnection = GUIAlert.getAlert(GUIAlert.NOTIFY_PLAYER_DISCONNECTION, null);
        playerDisconnection.showAndWait();

        //TODO: Implementare la pressione del bottone per chiudere la GUI
    }

    /**
     * Notifies the player about the beginning of another player's turn
     * @param playingPlayerUsername The username of the currentPlayer, that is the
     *                              player which is beginning his actionPhase turn
     */
    @Override
    public void notifyStartGameTurn(String playingPlayerUsername) {
        Alert playerDisconnection = GUIAlert.getAlert(GUIAlert.START_ANOTHER_PLAYER_TURN, playingPlayerUsername);
        playerDisconnection.showAndWait();
    }

    /**
     * Notifies the player his turn has ended
     */
    @Override
    public void notifyEndOfTurn() {
        Alert playerDisconnection = GUIAlert.getAlert(GUIAlert.NOTIFY_END_THIS_PLAYER_TURN, null);
        playerDisconnection.showAndWait();
    }

    // endregion Notifications

    // region VirtualControllerInteraction

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
     * Sets the username in the VirtualController after getting it from the player.
     * This will come in handy when the View needs to identify self-player infos in the model
     * @param readUsername the username of the player
     */
    @Override
    public void setUsernameVirtualController(String readUsername) {
        this.virtualController.setUsername(readUsername);
    }

    // endregion VirtualControllerInteraction

    // region SignalEndGame

    /**
     * Signals the player the winner
     *
     * @param winner the winning player
     */
    @Override
    public void signalWinner(Player winner) {
        ((EndGameHandler) nameMapHandler.get(Pages.END_GAME)).displayWinner(winner);
        switchScene(Pages.END_GAME);
    }

    /**
     * Signals the player the winning team
     *
     * @param team the winning team
     */
    @Override
    public void signalWinner(List<Player> team) {
        ((EndGameHandler) nameMapHandler.get(Pages.END_GAME)).displayWinningTeam(team);
        switchScene(Pages.END_GAME);
    }

    /**
     * Signals the player who ended in a draw
     *
     * @param drawers the player who ended in a draw
     */
    @Override
    public void signalDraw(List<Player> drawers) {
        ((EndGameHandler) nameMapHandler.get(Pages.END_GAME)).displayDraw(drawers);
        switchScene(Pages.END_GAME);
    }

    // endregion SignalEndGame

    // region Getter

    /**
     * Gets the model saved in the View
     * @return the model saved
     */
    @Override
    public GameModel getModel() {
        return model;
    }

    /**
     * Gets the localPlayer by comparing the Username present in the VirtualController
     * with the players present in the GameModel
     * @return the localPlayer (null if not present)
     */
    @Override
    public Player getLocalPlayer() {
        String localUsername = virtualController.getUsername();
        Player localPlayer = null;

        // Forall players in model, checks if the localUsername is equal to the one present in the model
        for (Player player : model.getPlayer()) {
            if (localUsername.equals(player.getUsername())){
                localPlayer = player;
                break;
            }
        }
        return localPlayer;
    }

    /**
     * Gets the positional index of the localPlayer in the playersArray of the current Model
     * @return An int representing the localPlayerIndex
     */
    public int getLocalPlayerIndex() {
        String localUsername    = virtualController.getUsername();
        int    localPlayerIndex = 0;

        // Forall players in the model, checks if the localUsername is equal to the one present
        // in the model. In this case, saves the position of the player in the model
        for (int i = 0; i < model.getPlayersCount(); i++) {
            if (Objects.equals(localUsername, model.getPlayer(i).getUsername())) {
                localPlayerIndex = i;
                break;
            }
        }

        return localPlayerIndex;
    }


    // endregion Getter

    // region Setter

    /**
     * Sets the virtualController of this class to a specific virtualController received with the method
     *
     * @param virtualController The VirtualController that this.virtualController has to be set to
     */
    @Override
    public void setVirtualController(VirtualController virtualController) {
        this.virtualController = virtualController;
    }

    /**
     * Sets the current Game Model to the one specified
     * @param model A reference to the model
     */
    @Override
    public void setModel(GameModel model) {
        this.model = model;
    }

    // endregion Setter

    // endregion ViewImplementation
}
