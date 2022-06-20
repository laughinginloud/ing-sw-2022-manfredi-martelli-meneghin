package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.utils.Isomorphism;
import it.polimi.ingsw.server.controller.characterCard.CharacterCardStrategy;
import it.polimi.ingsw.common.model.AssistantCard;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.GameModel;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.controller.command.GameCommand;
import it.polimi.ingsw.server.controller.command.GameCommandEndGame;
import it.polimi.ingsw.server.controller.state.GameState;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.common.utils.Methods.copyOf;

/**
 * Singleton containing data used by the controller during the game
 * @author Mattia Martelli
 */
public final class ControllerData {

    //FIXME: mettere in regioni
    public enum Flags { equalStudentsFlag, extraMovementFlag, ignoreTowersFlag, extraInfluenceFlag, excludeColorFlag }

    // region Fields

    private static ControllerData                   instance;
    private        GameModel                        gameModel;
    private        Map<Player, AssistantCard>       playerAssistantCardMap;
    private        Player[]                         playersOrder;
    private        boolean                          emptyBagTrigger;
    private        boolean                          emptyAssistantDeckTrigger;
    private        boolean                          hasPlayedCard;
    private        int                              numOfPlayers;
    private        boolean                          expertMode;
    private        Isomorphism<Player, VirtualView> playerViewMap;
    private        Player                           currentPlayer;
    private        CharacterCardStrategy[]          cardStrategies;
    private        boolean[]                        characterCardFlags;
    private        Color                            excludedColor;
    private        boolean                          winTrigger;

    // endregion

    // region Getters

    /**
     * Returns the currently instanced GameModel
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
     * Get the PlayerAssistantCardMap
     * @return A Map (Player, AssistantCard) copy of the PlayerAssistantCardMap
     */
    public Map<Player, AssistantCard> getPlayerAssistantCardMap() {
        if (this.playerAssistantCardMap == null)
            return new HashMap<>();

        return new HashMap<>(this.playerAssistantCardMap);
    }

    /**
     * Returns the players' play order
     * @return An array containing the play order
     */
    public Player[] getPlayersOrder() {
        return playersOrder;
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
     * Returns the trigger representing the bag's emptiness
     * @return A boolean representing the trigger
     */
    public boolean getEmptyBagTrigger() {
        return emptyBagTrigger;
    }

    /**
     * Returns the trigger representing the deck's emptiness
     * @return A boolean representing the trigger
     */
    public boolean getEmptyAssistantDeckTrigger() {
        return emptyAssistantDeckTrigger;
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

    /**
     * Returns the entire player view map
     * @return A pointer to the map
     */
    //public Isomorphism<Player, VirtualView> getPlayerViewMap() {
    //    return playerViewMap;
    //}

    /**
     * Get the player associated with the provided view
     * @param virtualView The view to search for
     * @return The associated player, or null if none exists
     */
    public Player getViewPlayer(VirtualView virtualView) {
        return playerViewMap.getLeft(virtualView);
    }

    /**
     * Get the view associated with the provided player
     * @param player The player to search for
     * @return The associated view, or null if none exists
     */
    public VirtualView getPlayerView(Player player) {
        return playerViewMap.getRight(player);
    }

    /**
     * Return the player currently playing
     * @return A pointer to the player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
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
        if (playerAssistantCardMap == null)
            playerAssistantCardMap = new HashMap<>();

        playerAssistantCardMap.put(player, assistantCard);
    }

    /**
     * Set the order the players will follow
     * @param playersOrder An array containing the play order
     */
    public void setPlayersOrder(Player[] playersOrder) {
        this.playersOrder = playersOrder;
    }

    /**
     * Set the empty bag trigger to true
     */
    public void setEmptyBagTrigger() {
        emptyBagTrigger = true;
    }

    /**
     * Set the empty bag trigger to false
     */
    public void resetEmptyBagTrigger() {
        emptyBagTrigger = false;
    }

    /**
     * Set the assistant deck trigger to true
     */
    public void setEmptyAssistantDeckTrigger() {
        emptyAssistantDeckTrigger = true;
    }

    /**
     * Set the assistant deck trigger to false
     */
    public void resetEmptyAssistantDeckTrigger() {
        emptyAssistantDeckTrigger = false;
    }

    /**
     * Set the trigger that represents whether the current player has played a card to true
     */
    public void setPlayedCard() {
        hasPlayedCard = true;
    }

    /**
     * Set the trigger that represents whether the current player has played a card to false
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

    /**
     * Set the player view map to a specified value
     * @param playerViewMap The pointer to the map to set
     */
    public void setPlayerViewMap(Isomorphism<Player, VirtualView> playerViewMap) {
        this.playerViewMap = playerViewMap;
    }

    /**
     * Set the player currently playing
     * @param currentPlayer The player to set
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    // endregion

    // region Instance related methods

    private ControllerData() {
        expertMode = false;
        winTrigger = false;
        //TODO: trigger rimanenti
    }

    /**
     * Return the only instance of ControllerData
     * @return The ControllerData instance
     */
    public static ControllerData getInstance() {
        if (instance == null)
            instance = new ControllerData();

        return instance;
    }

    /**
     * Load all the data from a previous ControllerData instance into the current one
     * @param data The instance to load all data from
     */
    public static void loadData(ControllerData data) {
        if (instance == null)
            instance = new ControllerData();

        // Load everything that doesn't need linking
        instance.gameModel                 = data.gameModel;
        instance.emptyBagTrigger           = data.emptyBagTrigger;
        instance.emptyAssistantDeckTrigger = data.emptyBagTrigger;
        instance.hasPlayedCard             = data.hasPlayedCard;
        instance.numOfPlayers              = data.numOfPlayers;
        instance.expertMode                = data.expertMode;
        instance.cardStrategies            = copyOf(data.cardStrategies);
        instance.characterCardFlags        = copyOf(data.characterCardFlags);
        instance.winTrigger                = data.winTrigger;

        // Create a copy the players, to aid the linking process
        Player[] players = instance.gameModel.getPlayer();

        instance.playerAssistantCardMap = new HashMap<>();
        // For each entry (Player, AssistantCard) of the map, load it in the new map whilst linking the player
        for (Map.Entry<Player, AssistantCard> entry : data.playerAssistantCardMap.entrySet())
            instance.playerAssistantCardMap.put(
                // Find the original copy of the player
                findPlayer(players, entry.getKey()),
                // Along with the player, put the corresponding assistant card
                entry.getValue());

        instance.playersOrder = new Player[data.numOfPlayers];
        // For each element of the array "playersOrder", find the original copy of the player and put it in the same spot in the array
        for (int i = 0; i < data.numOfPlayers; ++i)
            instance.playersOrder[i] = findPlayer(players, data.playersOrder[i]);

        // Find the original copy of the current player and put it into the corresponding spot in the instance
        instance.currentPlayer = findPlayer(players, data.getCurrentPlayer());
    }

    /**
     * Load the data into the current instance
     * @return A pointer to the instance
     */
    public static ControllerData loadData(
        GameModel model,
        Map<Player, AssistantCard> assistantCardMap,
        Player[] playersOrder,
        boolean emptyBagTrigger,
        boolean emptyAssistantDeckTrigger,
        boolean hasPlayedCard,
        int numOfPlayers,
        boolean expertMode,
        Player currentPlayer,
        CharacterCardStrategy[] characterCardStrategies,
        boolean[] characterCardFlags,
        Color excludedColor,
        boolean winTrigger) {

        if (instance == null)
            instance = new ControllerData();

        instance.gameModel                 = model;
        instance.playerAssistantCardMap    = assistantCardMap;
        instance.playersOrder              = playersOrder;
        instance.emptyBagTrigger           = emptyBagTrigger;
        instance.emptyAssistantDeckTrigger = emptyAssistantDeckTrigger;
        instance.hasPlayedCard             = hasPlayedCard;
        instance.numOfPlayers              = numOfPlayers;
        instance.expertMode                = expertMode;
        instance.currentPlayer             = currentPlayer;
        instance.cardStrategies            = characterCardStrategies;
        instance.characterCardFlags        = characterCardFlags;
        instance.excludedColor             = excludedColor;
        instance.winTrigger                = winTrigger;

        return instance;
    }

    /**
     * Method to search for the original copy of a player in the model
     * @param players The array containing the original copies
     * @param player The player to search in the model
     * @return A pointer to the original copy of the player
     */
    private static Player findPlayer(Player[] players, Player player) {
        // Transform the array into a stream to ease operations
        return Arrays.stream(players)
            // Reduce the stream to a single element, using a lambda that will compare all players and keep only the one with the correct id
            .reduce((a, b) -> a.getPlayerID() == player.getPlayerID() ? a : b)
            // Extract the value, throwing an exception in case of an error, just to be safe
            .orElseThrow();
    }

    /**
     * Delete all data currently saved
     */
    public static void nukeInstance() {
        instance = null;
    }

    // endregion

    public CharacterCardStrategy[] getCardStrategies() { return copyOf(cardStrategies); }

    public void setCardStrategies(CharacterCardStrategy[] cardStrategies) {
        this.cardStrategies = cardStrategies;
    }

    public void setCardStrategies(CharacterCardStrategy cardStrategy, int index) {
        cardStrategies[index] = cardStrategy;
    }

    public void setCharacterCardFlag(Flags flag, boolean value) {
        if (characterCardFlags == null)
            characterCardFlags = new boolean[Flags.values().length];

        characterCardFlags[flag.ordinal()] = value;
    }

    public boolean getCharacterCardFlag(Flags flag) {
        if (characterCardFlags == null)
            characterCardFlags = new boolean[Flags.values().length];

        return characterCardFlags[flag.ordinal()];
    }

    public void setWinTrigger(boolean winTrigger) {
        this.winTrigger = winTrigger;
    }

    public boolean checkWinTrigger() {
        return winTrigger;
    }

    public void setExcludedColor(Color excludedColor) {
        this.excludedColor = excludedColor;
    }

    public Color getExcludedColor() {
        return excludedColor;
    }

    public void addViewPlayer(Player player, VirtualView view) {
        if (playerViewMap == null)
            playerViewMap = new Isomorphism<>(numOfPlayers);

        playerViewMap.put(player, view);
    }

    public void sendMessageToPlayers(GameCommand gameCommand) {
        playerViewMap.forEach((p, v) -> v.sendMessage(gameCommand));
    }

    public Player removePlayer(VirtualView virtualView) {
        return playerViewMap.removeRight(virtualView);
    }

}
