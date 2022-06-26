package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.common.model.AssistantCard;
import it.polimi.ingsw.common.model.GameModel;
import it.polimi.ingsw.common.model.Player;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.net.SocketException;
import java.util.*;

/**
 * Class controlling the PlayCardPhase of the PlanPhase of every round
 * @author Sebastiano Meneghin
 */
public final class GameStatePlayCard implements GameStatePlanPhase {
    public GameState nextState() {
        return new GameStateSelectTurnOrder();
    }

    @Override
    public void executeState() throws SocketException {
        ControllerData data  = ControllerData.getInstance();
        // Orders the player according to the Eriantys Rules (the first to pick is the first player of the previous round, the other follow clockwise)
        selectPlanPhasePlayersOrder();

        // Cleans the PlayerAssistantCardMap, removing previous rounds' played cards
        data.nukePlayerAssistantCardMap();

        // Makes every player play an AssistantCard
        for (Player player : data.getPlayersOrder()) {
            AssistantCard[] playableAssistantCards = getPlayableAssistantCards(player);

            try {
                // Requests to the current player to play a card between the playable AssistantCard he has in his deck
                VirtualView playerView = data.getPlayerView(player);
                GameCommand request    = new GameCommandRequestAction(GameActions.PLAYASSISTANTCARD, playableAssistantCards);
                GameCommand response   = playerView.sendRequest(request);

                if (response instanceof GameCommandPlayAssistantCard c) {
                    AssistantCard chosenCard = (AssistantCard) c.executeCommand();

                    // Saves the chosenAssistantCard into the playerAssistantCardMap, updates the GameBoard and notifies all the players
                    saveAssistantCardChoice(player, chosenCard);

                    // If playing the AssistantCard the player emptied his deck, sets the EmptyAssistantDeckTrigger
                    if (player.getAssistantDeck().length == 0)
                        data.setEmptyAssistantDeckTrigger();
                }
            }

            catch (SocketException e) {
                throw e;
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
        AssistantCard[]            availableCards     = player.getAssistantDeck();
        List<AssistantCard>        playableCards      = new LinkedList<>();
        Map<Player, AssistantCard> alreadyPlayedCards = ControllerData.getInstance().getPlayerAssistantCardMap();

        if (availableCards.length == 0)
            throw new IllegalStateException("The FSM cannot return to the PlanPhase if the deck is empty");

        // If there are already played AssistantCards, check which AssistantCards can be played by the current player
        if (!alreadyPlayedCards.isEmpty()) {

            // Find the AssistantCards in the current player deck which hasn't already been used by other players during this turn
            for (AssistantCard assistantCard : availableCards)
                if (!alreadyPlayedCards.containsValue(assistantCard))
                    playableCards.add(assistantCard);

            // If playableCards is empty, the current player has only already played cards in his deck, so he can play them
            if (playableCards.isEmpty())
                return availableCards;

            // Saves the playable AssistantCards in an array of AssistantCard that will be returned by the current function
            AssistantCard[] remainingAssistantCards = new AssistantCard[playableCards.size()];
            for (int i = 0; i < playableCards.size(); i++)
                remainingAssistantCards[i] = playableCards.get(i);

            return remainingAssistantCards;
        }

        return availableCards;
    }

    /**
     * It does everything is needed when a player draws an AssistantCard from his deck
     * @param player The Player who just chose an AssistantCard
     * @param chosenCard The AssistantCard that has been chosen
     */
    private void saveAssistantCardChoice(Player player, AssistantCard chosenCard) {
        try {
            ControllerData data = ControllerData.getInstance();

            // Gets the AssistantCard from the current player's deck
            AssistantCard drawnFromDeck = player.getAssistantCard(chosenCard.cardValue() - 1);
            if (!drawnFromDeck.equals(chosenCard))
                throw new IllegalStateException("The two AssistantCards cannot be different");

            // Save the usage of the chosenCard into the ControllerData's PlayerAssistantCardMap
            data.addPlayerCard(player, chosenCard);

            // Set the chosenCard as lastPlayerCard on the current player board
            player.setLastPlayedCard(chosenCard);

            // Creates updateInfo and adds to it the updated playerArray, containing the new value of lastPlayerCard
            InfoMap updateInfo = new InfoMap();
            updateInfo.put(GameValues.PLAYERARRAY, data.getGameModel().getPlayer());

            // Notifies all the players about the lastPlayedCard now on the board of the current player
            for (Player playerToUpdate : data.getGameModel().getPlayer()) {
                VirtualView playerToUpdateView = data.getPlayerView(playerToUpdate);

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
        ControllerData data  = ControllerData.getInstance();
        GameModel      model = data.getGameModel();

        // Retrieve the number of the current players and create a vector of this length
        int      numOfPlayers          = data.getNumOfPlayers();
        Player[] planPhasePlayersOrder = new Player[numOfPlayers];

        // Retrieve the first player of the previous round
        Player firstPickers            = data.getPlayersOrder()[0];
        int    firstPickerSeatPosition = 0;

        // Find the seat of the first picker around the table
        for (int i = 0; i < numOfPlayers; i++)
            if (firstPickers.equals(model.getPlayer(i))) {
                firstPickerSeatPosition = i;
                break;
            }

        // Order the player clockwise after the first picker, depending on where they are sat around the "table"
        for (int i = 0; i < numOfPlayers; i++)
            planPhasePlayersOrder[i] = model.getPlayer((i + firstPickerSeatPosition) % numOfPlayers);

        // Set then the new playersOrder in ControllerData
        data.setPlayersOrder(planPhasePlayersOrder);
    }
}
