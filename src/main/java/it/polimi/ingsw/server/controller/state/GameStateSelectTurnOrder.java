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
    public GameState nextState() {
        return new GameStateMoveStudents();
    }

    @Override
    public void executeState() {
        try {
            ControllerData data = ControllerData.getInstance();

            SortedList<Tuple<Player, Integer>> playQueue = new SortedList<>(data.getNumOfPlayers(), Comparator.comparingInt(Tuple::right));

            // Add all the players from the current turn order, so that the stable sorting will keep the current ordering for equal values
            for (Player player : data.getPlayersOrder())
                playQueue.add(new Tuple<>(player, data.getPlayerCard(player).cardValue()));

            data.updatePlayersOrder(playQueue.stream().map(Tuple::left).toArray(Player[]::new));
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
