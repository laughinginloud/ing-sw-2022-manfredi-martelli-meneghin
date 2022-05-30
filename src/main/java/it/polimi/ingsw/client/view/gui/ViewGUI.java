package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.MenuItem;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Color;
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
            Scene serverInfoScene = new Scene(fxmlLoader.load(), 1920, 1080, javafx.scene.paint.Color.BLACK);

            Image icon = new Image("cranio.png");
            stage.getIcons().add(icon);
            stage.setTitle("Eriantys pre alpha 3.0");

            stage.setResizable(false);
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
