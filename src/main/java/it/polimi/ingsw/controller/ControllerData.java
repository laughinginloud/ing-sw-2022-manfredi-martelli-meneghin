package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.Arrays;
import java.util.Map;

/**
 * Singleton containing data used by the controller during the game
 * @author Mattia Martelli
 */
public class ControllerData {
    private static ControllerData             instance;
    private        GameModel                  gameModel;
    private        Map<Player, AssistantCard> playerAssistantCardMap;
    private        Player[]                   playersOrder;
    private        boolean                    trigger;
    private        boolean                    hasPlayedCard;
    private        Integer                    numOfPlayers;
    private        boolean                    expertMode;

    public static ControllerData getInstance() {
        return instance != null ? instance : new ControllerData();
    }

    public static void loadData(ControllerData data) {
        if (instance == null)
            instance = new ControllerData();

        instance.gameModel              = data.gameModel;
        instance.playerAssistantCardMap = data.playerAssistantCardMap;
        instance.playersOrder           = data.playersOrder;
        instance.trigger                = data.trigger;
        instance.numOfPlayers           = data.numOfPlayers;
    }

    public static void nukeInstance() {
        instance = null;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void setPlayerAssistantCardMap(Map<Player, AssistantCard> playerAssistantCardMap) {
        this.playerAssistantCardMap = playerAssistantCardMap;
    }

    public void addPlayerCard(Player player, AssistantCard assistantCard) {
        playerAssistantCardMap.put(player, assistantCard);
    }

    public AssistantCard getPlayerCard(Player player) {
        return playerAssistantCardMap.get(player);
    }

    public void setPlayersOrder(Player[] playersOrder) {
        this.playersOrder = Arrays.copyOf(playersOrder, playersOrder.length);
    }

    public Player[] getPlayersOrder() {
        return Arrays.copyOf(playersOrder, playersOrder.length);
    }

    public Player getPlayersOrder(int index) {
        return playersOrder[index];
    }

    public void nukeList() {
        playerAssistantCardMap = null;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public boolean getTrigger() {
        return trigger;
    }

    public void nukePlayedCard() {
        hasPlayedCard = false;
    }

    public void setPlayedCard() {
        hasPlayedCard = true;
    }

    public boolean checkPlayedCard() {
        return hasPlayedCard;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public boolean getExpertMode() {
        return expertMode;
    }
}
