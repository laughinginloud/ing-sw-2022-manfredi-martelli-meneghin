package it.polimi.ingsw.server.controller.command;

import java.util.List;

/**
 * Class that could be useful in the Client
 */
public class GameCommandEndGame implements GameCommand {
    private String       winner = null;
    private List<String> team   = null;

    private boolean      draw   = false;

    public GameCommandEndGame() {}

    public GameCommandEndGame(String winner) {
        this.winner = winner;
    }

    public GameCommandEndGame(List<String> team, boolean draw) {
        this.team = team;
        this.draw = draw;
    }

    public Object executeCommand() {
        if (winner != null)
            return winner;

        if (team != null)
            return team;

        return null;
    }

    public boolean isDraw() {
        return winner == null && team == null;
    }
}