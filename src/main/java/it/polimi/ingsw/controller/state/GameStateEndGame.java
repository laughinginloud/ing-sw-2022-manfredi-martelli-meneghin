package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandEndGame;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GlobalProfessorTable;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStateEndGame implements GameState {
    // Quick definition of an enum used as a result of a compare
    private enum Ordering { LT, GT, EQ }

    private Player       winner = null;
    private List<Player> team   = null;

    public GameStateEndGame() {}

    public GameStateEndGame(Player winner) {
        this.winner = winner;
    }

    public GameStateEndGame(List<Player> team) {
        this.team = team;
    }

    public GameState nextState() {
        return null;
    }

    public void executeState() {
        ControllerData data = ControllerData.getInstance();

        // If the winner was decided when the state was called, send it to everyone
        if (winner != null)
            sendWinner(data);

        // If the winning team was decided when the state was called, send it to everyone
        else if (team != null)
            sendTeam(data);

        // Otherwise, decide the winner
        else {
            Player[] players = data.getGameModel().getPlayer();
                     team    = new ArrayList<>();

            if (players.length == 4)
                fourPlayerWinner(data, players);

            else
                twoThreePlayerWinner(data, players);
        }
    }

    private void fourPlayerWinner(ControllerData data, Player[] players) {
        // If there are four players, I check for the teams (A for ids 0 and 2, B for 1 and 3)
        switch (compare(players[0].getSchoolBoard().getTowerCount(), players[1].getSchoolBoard().getTowerCount())) {
            // Team A wins the game by having fewer towers on their board
            case LT -> {
                team.add(players[0]);
                team.add(players[2]);
                sendTeam(data);
            }

            // Team B wins the game by having fewer towers on their board
            case GT -> {
                team.add(players[1]);
                team.add(players[3]);
                sendTeam(data);
            }

            // Both teams have the same number of towers left, so I check the professors
            case EQ -> {
                switch (compareProfessorsTeam(data.getGameModel().getGlobalProfessorTable(), players)) {
                    // Team A wins the game by controlling more professors
                    case GT -> {
                        team.add(players[0]);
                        team.add(players[2]);
                        sendTeam(data);
                    }

                    // Team B wins the game by controlling more professors
                    case LT -> {
                        team.add(players[1]);
                        team.add(players[3]);
                        sendTeam(data);
                    }

                    // Both teams control the same number of professors, so the game ends in a draw
                    case EQ -> sendDraw(data, players);
                }
            }
        }
    }

    private void twoThreePlayerWinner(ControllerData data, Player[] players) {
        winner = players[0];

        boolean draw = false;

        for (int i = 1; i < players.length; ++i) {
            if (draw) {
                if (players[i].getSchoolBoard().getTowerCount() < team.get(0).getSchoolBoard().getTowerCount()) {
                    draw = false;
                    winner = players[i];
                }

                else if (players[i].getSchoolBoard().getTowerCount() == team.get(0).getSchoolBoard().getTowerCount())
                    switch (compareProfessors(data.getGameModel().getGlobalProfessorTable(), team.get(0), players[i])) {
                        case LT -> {
                            draw = false;
                            winner = players[i];
                        }

                        case EQ -> team.add(players[i]);

                        case GT -> {}
                    }
            }

            else {
                if (players[i].getSchoolBoard().getTowerCount() < winner.getSchoolBoard().getTowerCount())
                    winner = players[i];

                else if (players[i].getSchoolBoard().getTowerCount() == winner.getSchoolBoard().getTowerCount())
                    switch (compareProfessors(data.getGameModel().getGlobalProfessorTable(), team.get(0), players[i])) {
                        case LT -> winner = players[i];

                        case EQ -> {
                            team.add(winner);
                            team.add(players[i]);
                            draw = true;
                        }

                        case GT -> {}
                    }
            }
        }

        if (draw)
            sendDraw(data, team.toArray(Player[]::new));

        else
            sendWinner(data);
    }

    private Ordering compare(int a, int b) {
        int result = Integer.compare(a, b);

        return
            result < 0 ? Ordering.LT :
                result > 0 ? Ordering.GT :
                    Ordering.EQ;
    }

    private Ordering compareProfessorsTeam(GlobalProfessorTable globalProfessorTable, Player[] players) {
        int teamA = 0,
            teamB = 0;

        for (Color color : Color.values()) {
            Player professorLocation = globalProfessorTable.getProfessorLocation(color);

            if (professorLocation == players[0] || professorLocation == players[2])
                teamA++;

            else
                teamB++;
        }

        return compare(teamA, teamB);
    }

    private Ordering compareProfessors(GlobalProfessorTable gpt, Player a, Player b) {
        int aR = 0,
            bR = 0;

        for (Color color : Color.values())
            if (gpt.getProfessorLocation(color) == a)
                ++aR;
            else if (gpt.getProfessorLocation(color) == b)
                ++bR;

        return compare(aR, bR);
    }

    private void sendWinner(ControllerData data) {
        data.getPlayerViewMap()
            .forEach((p, v) -> {
                try {
                    v.sendMessage(new GameCommandEndGame(winner.getUsername()));
                }

                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    private void sendTeam(ControllerData data) {
        List<String> teamMembers = team.stream().map(Player::getUsername).toList();

        data.getPlayerViewMap()
            .forEach((p, v) -> {
                try {
                    v.sendMessage(new GameCommandEndGame(teamMembers, false));
                }

                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    private void sendDraw(ControllerData data, Player[] players) {
        List<String> winners = Arrays.stream(players).map(Player::getUsername).toList();

        data.getPlayerViewMap()
            .forEach((p, v) -> {
                try {
                    v.sendMessage(new GameCommandEndGame(winners, true));
                }

                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
