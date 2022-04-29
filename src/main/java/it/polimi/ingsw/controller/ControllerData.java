package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.Arrays;
import java.util.Map;

/**
 * Singleton containing data used by the controller during the game
 * @author Mattia Martelli
 */
public class ControllerData {

    // region Fields

    private static ControllerData             instance;
    private        GameModel                  gameModel;
    private        Map<Player, AssistantCard> playerAssistantCardMap;
    private        Player[]                   playersOrder;
    private        boolean                    trigger;
    private        boolean                    hasPlayedCard;
    private        Integer                    numOfPlayers;
    private        boolean                    expertMode;

    // endregion

    // region Getters

    /**
     * Returns the currently instanciated GameModel
     * @return The pointer to the GameModel
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Returns the assistant card played by the requested player
     * @param player A pointer to the player that used the card
     * @return The AssistantCard played
     */
    public AssistantCard getPlayerCard(Player player) {
        return playerAssistantCardMap.get(player);
    }

    /**
     * Returns the players' play order
     * @return An array containing the play order
     */
    public Player[] getPlayersOrder() {
        return Arrays.copyOf(playersOrder, playersOrder.length);
    }

    /**
     * Return the player corresponding to the requested index
     * @param index The index of the player to return
     * @return A pointer to the requested player
     */
    public Player getPlayersOrder(int index) {
        return playersOrder[index];
    }

    /**
     * Returns the trigger value
     * @return A boolean representing the trigger
     */
    public boolean getTrigger() {
        return trigger;
    }

    /**
     * Check whether the current player has played a card
     * @return A boolean representing the value
     */
    public boolean checkPlayedCard() {
        return hasPlayedCard;
    }

    /**
     * Return the number of players of the current game
     * @return The number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Returns the value of the expert mode flag
     * @return The flag value
     */
    public boolean getExpertMode() {
        return expertMode;
    }

    // endregion

    // region Setters

    /**
     * Set the GameModel instance
     * @param gameModel A pointer to the GameModel's instance
     */
    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * Set the relation Player -> AssistantCard
     * @param playerAssistantCardMap A Map representing the relation
     */
    public void setPlayerAssistantCardMap(Map<Player, AssistantCard> playerAssistantCardMap) {
        this.playerAssistantCardMap = playerAssistantCardMap;
    }

    /**
     * Cleans the map Player -> AssistantCard
     */
    public void nukePlayerAssistantCardMap() {
        playerAssistantCardMap = null;
    }

    /**
     * Add a tuple (Player, AssistantCard) to the map
     * @param player The Player that chose the card
     * @param assistantCard The chosen card
     */
    public void addPlayerCard(Player player, AssistantCard assistantCard) {
        playerAssistantCardMap.put(player, assistantCard);
    }

    /**
     * Set the order the players will follw
     * @param playersOrder An array containing the play order
     */
    public void setPlayersOrder(Player[] playersOrder) {
        this.playersOrder = Arrays.copyOf(playersOrder, playersOrder.length);
    }

    /**
     * Set the generic trigger to true
     */
    public void setTrigger() {
        trigger = true;
    }

    /**
     * Set the generic trigger to false
     */
    public void resetTrigger() {
        trigger = false;
    }

    /**
     * Set the trigger that represents whether the current player has played a card to true
     */
    public void setPlayedCard() {
        hasPlayedCard = true;
    }

    /**
     * Set the trigger that represents whether the current player has played a card to true
     */
    public void resetPlayedCard() {
        hasPlayedCard = false;
    }

    /**
     * Set the number of players of the current game
     * @param numOfPlayers The number of players
     */
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * Set the expert mode flag to true
     */
    public void setExpertMode() {
        expertMode = true;
    }

    // endregion

    // region Instance related methods

    private ControllerData() {
        expertMode = false;
    }

    /**
     * Return the only instance of ControllerData
     * @return The ControllerData instance
     */
    public static ControllerData getInstance() {
        return instance != null ? instance : new ControllerData();
    }

    /**
     * Load all the data from a previous ControllerData instance into the current one
     * @param data The instance to load all data from
     */
    public static void loadData(ControllerData data) {
        if (instance == null)
            instance = new ControllerData();

        instance.gameModel              = data.gameModel;
        instance.playerAssistantCardMap = data.playerAssistantCardMap;
        instance.playersOrder           = data.playersOrder;
        instance.trigger                = data.trigger;
        instance.numOfPlayers           = data.numOfPlayers;
    }

    /**
     * Delete all data currently saved
     */
    public static void nukeInstance() {
        instance = null;
    }

    // endregion

}
