package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.utils.SortedList;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.Player;

import java.util.Comparator;

/**
 * Class representing the players' turn order selection made before the end of the PlanPhase
 * @author Sebastiano Meneghin
 */
public final class GameStateSelectTurnOrder implements GameStatePlanPhase {
    @Override
    public GameState nextState() {
        return new GameStateMoveStudents();
    }

    @Override
    public void executeState() {
        try {
            ControllerData data = ControllerData.getInstance();

            // Create a sorted list, that will sort the players based on the value of the card they played
            SortedList<Tuple<Player, Integer>> playList = new SortedList<>(data.getNumOfPlayers(), Comparator.comparingInt(Tuple::right));

            // Add all the players from the current turn order, so that the stable sorting will keep the current ordering for equal values
            for (Player player : data.getPlayersOrder())
                playList.add(new Tuple<>(player, data.getPlayerCard(player).cardValue()));

            // Set the order to the one currently in the list
            data.updatePlayersOrder(playList.stream().map(Tuple::left).toArray(Player[]::new));
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
