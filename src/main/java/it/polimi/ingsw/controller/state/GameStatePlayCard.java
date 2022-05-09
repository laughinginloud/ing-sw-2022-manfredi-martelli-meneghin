package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualView.VirtualView;

import javax.naming.ldap.Control;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class controlling the PlayCardPhase of the PlanPhase of every round
 * @author Sebastiano Meneghin
 */
public class GameStatePlayCard implements GameStatePlanPhase {
    public GameState nextState() { return new GameStateSelectTurnOrder(); }

    @Override
    public void executeState() {
        // Order the player according to the Eriantys Rules (the first to pick is the first player of the previous round, the other follow clockwise)
        selectPlanPhasePlayersOrder();

        // Clean the PlayerAssistantCardMap, removing previous rounds' played cards
        ControllerData.getInstance().nukePlayerAssistantCardMap();
        Player[] players = ControllerData.getInstance().getPlayersOrder();

        // Makes every player play an AssistantCard
        for (Player player : players) {
            ControllerData.getInstance().setCurrentPlayer(player);
            AssistantCard[] playableAssistantCards = getPlayableAssistantCards(player);

            try {
                // Request to the current player to play a card between the playable AssistantCard he has in his deck
                VirtualView playerView = ControllerData.getInstance().getPlayerViewMap().getRight(player);
                GameCommand request = new GameCommandRequestAction(GameCommandActions.PLAYASSISTANTCARD, playableAssistantCards);
                GameCommand response = playerView.sendRequest(request);

                if (response instanceof GameCommandPlayAssistantCard c) {
                    AssistantCard chosenCard = (AssistantCard) c.executeCommand();

                    // Save the chosenAssistantCard into the playerAssistantCardMap, updates the GameBoard and notifies all the players
                    saveAssistantCardChoice(player, chosenCard);

                    // If playing the AssistantCard the player emptied his deck, sets the EmptyAssistantDeckTrigger
                    if (player.getAssistantDeck().length == 0)
                        ControllerData.getInstance().setEmptyAssistantDeckTrigger();
                }
            }

            catch (Exception e) {
                // Fatal error: print the stack trace to help debug
                e.printStackTrace();
            }
        }
    }


    /**
     * Get all the playable AssistantCards from a specific player's deck, according to the Eriantys Rules
     * @param player The current player from whose deck we need to draw the playable AssistantCards
     * @return An array of AssistantCards containing the playable AssistantCards
     */
    private AssistantCard[] getPlayableAssistantCards (Player player) {
        AssistantCard[] availableCards = player.getAssistantDeck();
        List<AssistantCard> playableCards = new LinkedList<>();
        Map<Player, AssistantCard> alreadyPlayedCards = ControllerData.getInstance().getPlayerAssistantCardMap();

        if (availableCards.length == 0)
            throw new IllegalStateException("The FSM cannot return to the PlanPhase if the deck is empty");

        // If there are already played AssistantCards, check which AssistantCards can be played by the current player
        if (!alreadyPlayedCards.isEmpty()) {

            // Find the AssistantCards in the current player deck which hasn't already been used by other players during this turn
            for (AssistantCard assistantCard : availableCards) {
                if (!alreadyPlayedCards.containsValue(assistantCard))
                    playableCards.add(assistantCard);
            }

            // If playableCards is empty, the current player has only already played cards in his deck, so he can play them
            if (playableCards.isEmpty())
                return availableCards;


            AssistantCard[] remainingAssistantCards = new AssistantCard[playableCards.size()];
            for (int i = 0; i < playableCards.size(); i++)
                remainingAssistantCards[i] = playableCards.get(i);

            return remainingAssistantCards;
        }

        return availableCards;
    }

    /**
     * Does everything is needed when a player draws an AssistantCard from his deck
     * @param player The Player who just chose an AssistantCard
     * @param chosenCard The AssistantCard that has been chosen
     */
    private void saveAssistantCardChoice(Player player, AssistantCard chosenCard) {
        // Gets the AssistantCard from the current player's deck
        AssistantCard drawnFromDeck = player.getAssistantCard(chosenCard.cardValue());
        if (!drawnFromDeck.equals(chosenCard))
            throw new IllegalStateException("The two AssistantCards cannot be different");

        // Save the usage of the chosenCard into the ControllerData's PlayerAssistantCardMap
        ControllerData.getInstance().addPlayerCard(player, chosenCard);

        // Set the chosenCard as lastPlayerCard on the current player board
        player.setLastPlayedCard(chosenCard);

        try {
            // Notifies all the players about the lastPlayedCard now on the board of the current player
            for (Player playerToUpdate : ControllerData.getInstance().getPlayersOrder()) {
                VirtualView playerToUpdateView = ControllerData.getInstance().getPlayerViewMap().getRight(playerToUpdate);

                // Creates updateInfo and adds to it the updated playerArray, containing the new value of lastPlayerCard
                Map<GameCommandValues, Object> updateInfo = new HashMap<>();
                updateInfo.put(GameCommandValues.PLAYERARRAY, ControllerData.getInstance().getGameModel().getPlayer());

                GameCommand update = new GameCommandSendInfo(updateInfo);
                playerToUpdateView.sendMessage(update);
            }
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Modify the playersOrder in ControllerData in order to start the DrawAssistantCardPhase with the rule-based new playersOrder
     */
    private void selectPlanPhasePlayersOrder() {
        // Retrieve the number of the current players and create a vector of this length
        int numOfPlayers = ControllerData.getInstance().getNumOfPlayers();
        Player[] planPhasePlayersOrder = new Player[numOfPlayers];

        // Retrieve the first player of the previous round
        Player firstPickers = ControllerData.getInstance().getPlayersOrder()[0];
        int firstPickerSeatPosition = 0;

        // Find the seat of the first picker around the table
        for (int i = 0; i < numOfPlayers; i++)
            if (firstPickers.equals(ControllerData.getInstance().getGameModel().getPlayer(i)))
                firstPickerSeatPosition = i;

        // Order the player clockwise after the first picker, depending on where they are sat around the "table"
        for (int i = 0; i < numOfPlayers; i++)
            planPhasePlayersOrder[i] = ControllerData.getInstance().getGameModel().getPlayer((i + firstPickerSeatPosition) % 4);

        // Set then the new playersOrder in ControllerData
        ControllerData.getInstance().setPlayersOrder(planPhasePlayersOrder);
    }
}








