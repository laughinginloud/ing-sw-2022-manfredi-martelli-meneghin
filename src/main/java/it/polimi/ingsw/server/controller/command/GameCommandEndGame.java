package it.polimi.ingsw.server.controller.command;

import it.polimi.ingsw.common.model.Player;

import java.util.List;

/**
 * Command representing the end of a game, containing the result
 * @author Mattia Martelli
 */
public class GameCommandEndGame implements GameCommand {
    private Player       winner = null;
    private List<Player> team   = null;

    private boolean      draw   = false;

    public GameCommandEndGame(Player winner) {
        this.winner = winner;
    }

    public GameCommandEndGame(List<Player> team, boolean draw) {
        this.team = team;
        this.draw = draw;
    }

    public Object executeCommand() {
        if (winner != null)
            return winner;

        return team;
    }

    public boolean isDraw() {
        return draw;
    }

    public boolean isTeam() {
        return winner == null;
    }
}
