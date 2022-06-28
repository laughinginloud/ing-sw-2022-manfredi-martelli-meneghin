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

            for (Player player : data.getGameModel().getPlayers())
                playQueue.add(new Tuple<>(player, data.getPlayerCard(player).cardValue()));

            data.updatePlayersOrder(playQueue.stream().map(Tuple::left).toArray(Player[]::new));
        }

        catch (Exception e) {
            // Fatal error: print the stack trace to help debug
            e.printStackTrace();
        }
    }
}
