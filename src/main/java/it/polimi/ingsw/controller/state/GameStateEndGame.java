package it.polimi.ingsw.controller.state;

import it.polimi.ingsw.controller.ControllerData;
import it.polimi.ingsw.controller.command.GameCommandEndGame;
import it.polimi.ingsw.controller.command.GameCommandSendInfo;
import it.polimi.ingsw.controller.command.GameCommandValues;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GlobalProfessorTable;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.util.*;

/**
 * State representing the end of the game
 * @author Mattia Martelli
 */
public class GameStateEndGame implements GameState {
    // Quick definition of an enum used as the result of a compare
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
            sendWinner(data, winner);

        // If the winning team was decided when the state was called, send it to everyone
        else if (team != null)
            sendTeam(data, team);

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

    /**
     * Function to decide the winner in the case of a two or three players game
     * @param data The controller data's instance
     * @param players An array containing the players, ordered by their id
     */
    private void fourPlayerWinner(ControllerData data, Player[] players) {
        // If there are four players, I check for the teams (A for ids 0 and 2, B for 1 and 3)
        switch (compare(players[0].getSchoolBoard().getTowerCount(), players[1].getSchoolBoard().getTowerCount())) {
            // Team A wins the game by having fewer towers on their board
            case LT -> {
                team.add(players[0]);
                team.add(players[2]);
                sendTeam(data, team);
            }

            // Team B wins the game by having fewer towers on their board
            case GT -> {
                team.add(players[1]);
                team.add(players[3]);
                sendTeam(data, team);
            }

            // Both teams have the same number of towers left, so I check the professors
            case EQ -> {
                switch (compareProfessorsTeam(data.getGameModel().getGlobalProfessorTable(), players)) {
                    // Team A wins the game by controlling more professors
                    case GT -> {
                        team.add(players[0]);
                        team.add(players[2]);
                        sendTeam(data, team);
                    }

                    // Team B wins the game by controlling more professors
                    case LT -> {
                        team.add(players[1]);
                        team.add(players[3]);
                        sendTeam(data, team);
                    }

                    // Both teams control the same number of professors, so the game ends in a draw
                    case EQ -> sendDraw(data, Arrays.stream(players).toList());
                }
            }
        }
    }

    /**
     * Function to decide the winner in the case of a two or three players game
     * @param data The contoller data's instance
     * @param players An array containing the players
     */
    private void twoThreePlayerWinner(ControllerData data, Player[] players) {
        winner = players[0];

        boolean draw = false;

        for (int i = 1; i < players.length; ++i) {
            if (draw) {
                // If it currently is a draw, compare players[i] with the head of the "team" list, as all players in the list are equal
                switch (compare(players[i].getSchoolBoard().getTowerCount(), team.get(0).getSchoolBoard().getTowerCount())) {
                    // If players[i] has fewer towers, he is the new winner
                    case LT -> {
                        draw = false;
                        winner = players[i];
                    }

                    // If both have the same number of towers, compare the number professors controlled
                    case EQ -> {
                        switch (compareProfessors(data.getGameModel().getGlobalProfessorTable(), players[i], team.get(0))) {
                            // If players[i] controls more professors, he is the new winner
                            case GT -> {
                                draw = false;
                                winner = players[i];
                            }

                            // If they both control the same number of professors, the game's a draw, so add players[i] to the list of "drawers"
                            case EQ -> team.add(players[i]);

                            // If players[i] controls fewer professors, the results are unchanged
                            case LT -> {}
                        }
                    }

                    // If players[i] has more towers, the results are unchanged
                    case GT -> {}
                }
            }

            else {
                // If it currently is not a draw, compare players[i] with the current winner
                switch (compare(players[i].getSchoolBoard().getTowerCount(), winner.getSchoolBoard().getTowerCount())) {
                    // If players[i] has fewer towers, he is the new winner
                    case LT -> winner = players[i];

                    // If both have the same number of towers, compare the number professors controlled
                    case EQ -> {
                        switch (compareProfessors(data.getGameModel().getGlobalProfessorTable(), players[i], winner)) {
                            // If players[i] controls more professors, he is the new winner
                            case GT -> winner = players[i];

                            // If they both control the same number of professors, the game's a draw, so add players[i] to the list of "drawers"
                            case EQ -> {
                                team.add(winner);
                                team.add(players[i]);
                                draw = true;
                            }

                            // If players[i] controls fewer professors, the results are unchanged
                            case LT -> {}
                        }
                    }

                    // If players[i] has more towers, the results are unchanged
                    case GT -> {}
                }
            }
        }

        if (draw)
            sendDraw(data, team);

        else
            sendWinner(data, winner);
    }

    /**
     * Function that compares two integers returning a result relative to the first (e.g. LT <=> a < b)
     * @param a The first integer
     * @param b The second integer
     * @return The result of the compare
     */
    private Ordering compare(int a, int b) {
        int result = b - a;

        return
            result < 0 ? Ordering.LT :
                result > 0 ? Ordering.GT :
                    Ordering.EQ;
    }

    /**
     * Function that compares the professors for the two teams, (0, 2) and (1, 3)
     * @param gpt The global professor table
     * @param players An array containing the players, ordered by id
     * @return The result represented an Ordering
     */
    private Ordering compareProfessorsTeam(GlobalProfessorTable gpt, Player[] players) {
        int teamA = 0,
            teamB = 0;

        for (Color color : Color.values()) {
            Player professorLocation = gpt.getProfessorLocation(color);

            if (professorLocation == players[0] || professorLocation == players[2])
                teamA++;

            else
                teamB++;
        }

        return compare(teamA, teamB);
    }

    /**
     * Compare the professors controlled by two provided players
     * @param gpt The global professor table
     * @param a The first player
     * @param b The second player
     * @return The player that controls more professors, using an Ordering
     */
    private Ordering compareProfessors(GlobalProfessorTable gpt, Player a, Player b) {
        int aR = 0,
            bR = 0;

        for (Color color : Color.values())
            if (gpt.getProfessorLocation(color) == a)
                aR++;
            else if (gpt.getProfessorLocation(color) == b)
                bR++;

        return compare(aR, bR);
    }

    /**
     * Send the winner to every player
     */
    private void sendWinner(ControllerData data, Player winner) {
        data.getPlayerViewMap()
            .forEach((p, v) -> {
                Map<GameCommandValues, Object> winnerMap = new HashMap<>();
                winnerMap.put(GameCommandValues.WINNER, winner.getUsername());
                v.sendMessage(new GameCommandSendInfo(winnerMap));
            });
    }

    /**
     * Send the winning team to every player
     */
    private void sendTeam(ControllerData data, List<Player> team) {
        List<String> teamMembers = team.stream().map(Player::getUsername).toList();

        data.getPlayerViewMap()
            .forEach((p, v) -> {
                Map<GameCommandValues, Object> winnerMap = new HashMap<>();
                winnerMap.put(GameCommandValues.WINNINGTEAM, teamMembers);
                v.sendMessage(new GameCommandSendInfo(winnerMap));
            });
    }

    /**
     * Send the result of the draw to every player
     */
    private void sendDraw(ControllerData data, List<Player> players) {
        List<String> drawers = players.stream().map(Player::getUsername).toList();

        data.getPlayerViewMap()
            .forEach((p, v) -> {
                Map<GameCommandValues, Object> drawerMap = new HashMap<>();
                drawerMap.put(GameCommandValues.DRAWERS, drawers);
                v.sendMessage(new GameCommandSendInfo(drawerMap));
            });
    }
}
