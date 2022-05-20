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
     * Asks the player his username and from how many years he knows "magic"
     * @return A record PlayerInfo containing the player's username and player's magicAge
     */
    PlayerInfo askPlayerInfo();

    /**
     * Asks the current player which is address he wants to connect to
     * @return A record ConnectionInfo containing serverAddress and serverPort chosen by the player
     */
    ConnectionInfo askConnectionInfo();

    /**
     * Asks the num of the players the first person connected would like to play with and whether he likes to play the game in expertMode
     * @param possibleNum The possible number of player that a Game could contain
     * @return A record GameRules containing the selected numOfPlayer and a boolean representing the player's choice about expertMode
     */
    GameRules askRules(int[] possibleNum);

    /**
     * Asks the player whether he wants to end his turn or he wants to play a CharacterCard
     * It's possible only when the player hasn't already played a characterCard
     * @return A boolean representing the decision of the player
     */
    boolean askEndOfTurn();

    /**
     * Asks the player which deck (wizard) he wants to play with
     * @param availableWizards An array of Wizard that haven't been already chosen by other players
     * @return The wizard chosen by the player
     */
    Wizard requestWizard(Wizard[] availableWizards);

    /**
     * Asks the player the which characterCards he would like to play between the CharacterCard provided
     * @param characterCards An array of characterCards that are currently playable
     * @return The characterCard selected by the player
     */
    CharacterCard requestPlayCharacterCard(CharacterCard[] characterCards);

    /**
     * Notify the player that there's already a game in progress, then he will be disconnected from the server
     */
    void notifyGameInProgress();

    /**
     * Notify to player that the username inserted is currently used by another player. Disconnects him, telling him to try the connection again
     */
    void notifyUsernameAlreadyChosen ();

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





}
