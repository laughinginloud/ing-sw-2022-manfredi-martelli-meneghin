package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.common.model.Player;

import java.util.Comparator;
import java.util.PriorityQueue;

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
            ControllerData data         = ControllerData.getInstance();
            int            numOfPlayers = data.getNumOfPlayers();

            PriorityQueue<Tuple<Player, Integer>> playQueue = new PriorityQueue<>(numOfPlayers, Comparator.comparingInt(Tuple::right));

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
