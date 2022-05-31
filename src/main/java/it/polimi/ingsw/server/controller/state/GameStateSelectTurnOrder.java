package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.AssistantCard;
import it.polimi.ingsw.common.model.Player;

import java.util.Map;

/**
 * Class representing the players' turn order selection made before the end of the PlanPhase
 * @author Sebastiano Meneghin
 */
public final class GameStateSelectTurnOrder implements GameStatePlanPhase {
    public GameState nextState() {
        return new GameStateMoveStudents();
    }

    @Override
    public void executeState() {
        try {
            ControllerData data     = ControllerData.getInstance();
            int            numOfPlayers   = data.getNumOfPlayers();
            Player[]       newPlayerOrder = new Player[numOfPlayers];
            Player         playerToOrder;

            // For each player in the game, which will be his turn priority during the next ActionPhase
            for (int i = 0; i < numOfPlayers; i++) {
                playerToOrder  = data.getPlayersOrder()[i];
                newPlayerOrder = addPlayerInOrder(newPlayerOrder, playerToOrder, i);
            }

            data.setPlayersOrder(newPlayerOrder);

            // Set current player to null at the end of the GameStatePlanPhase. It will be initialized to firstPlayer in GameStateActionPhase.GameStateMoveStudents
            data.setCurrentPlayer(null);
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }

    /**
     * Adds one player to the already existing array of ordered students, modifying the order if it's necessary
     * @param playersOrdered An array of Player that has been already ordered
     * @param playerToOrder Player to be added to playersOrdered
     * @param numOfAlreadyOrderedPlayers Int representing the number of Player already added to playersOrdered
     * @return An array of ordered Player containing the playerToOrder too
     */
    private Player[] addPlayerInOrder(Player[] playersOrdered, Player playerToOrder, int numOfAlreadyOrderedPlayers) {
        Map<Player, AssistantCard> playerAssistantCardMap = ControllerData.getInstance().getPlayerAssistantCardMap();
        int playerToOrderDest = 0;

        // Searches along the "playersOrdered" array (if there is) the first orderedPlayer which has a cardValue bigger than the playerToOrder.cardValue()
        for (int j = 0; j < numOfAlreadyOrderedPlayers; j++) {
            if (playerAssistantCardMap.get(playerToOrder).cardValue() < playerAssistantCardMap.get(playersOrdered[j]).cardValue()) {

                // If found, it saves its position into the array "playersOrdered" and it ends the loop
                playerToOrderDest = j;
                break;
            }
        }

        // If the playerToOrder.cardValue isn't the smallest one neither the biggest one, it frees space for it in the middle of the playersOrdered array
        if (!(playerToOrderDest == numOfAlreadyOrderedPlayers))
            System.arraycopy(playersOrdered, playerToOrderDest, playersOrdered, playerToOrderDest + 1, numOfAlreadyOrderedPlayers - playerToOrderDest);

        // Sets the playerToOrder in the playersOrdered array at the playerToOrderDest
        playersOrdered[playerToOrderDest] = playerToOrder;
        return playersOrdered;
    }
}
