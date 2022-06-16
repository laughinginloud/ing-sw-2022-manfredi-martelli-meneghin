package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.common.utils.Ordering;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.command.GameCommandEndGame;
import it.polimi.ingsw.common.model.Color;
import it.polimi.ingsw.common.model.GlobalProfessorTable;
import it.polimi.ingsw.common.model.Player;

import java.util.*;

import static it.polimi.ingsw.common.utils.Ordering.compare;

/**
 * State representing the end of the game
 * @author Mattia Martelli
 */
public final class GameStateEndGame implements GameState {
    private Player       winner     = null;
    private List<Player> winnerList = null;

    public GameStateEndGame() {}

    public GameStateEndGame(Player winner) {
        this.winner = winner;
    }

    public GameStateEndGame(List<Player> winnerList) {
        this.winnerList = winnerList;
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
        else if (winnerList != null)
            sendTeam(data, winnerList);

        // Otherwise, decide the winner
        else {
            Player[] players    = data.getGameModel().getPlayer();
                     winnerList = new ArrayList<>();

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
                winnerList.add(players[0]);
                winnerList.add(players[2]);
                sendTeam(data, winnerList);
            }

            // Team B wins the game by having fewer towers on their board
            case GT -> {
                winnerList.add(players[1]);
                winnerList.add(players[3]);
                sendTeam(data, winnerList);
            }

            // Both teams have the same number of towers left, so I check the professors
            case EQ -> {
                switch (compareProfessorsTeam(data.getGameModel().getGlobalProfessorTable(), players)) {
                    // Team A wins the game by controlling more professors
                    case GT -> {
                        winnerList.add(players[0]);
                        winnerList.add(players[2]);
                        sendTeam(data, winnerList);
                    }

                    // Team B wins the game by controlling more professors
                    case LT -> {
                        winnerList.add(players[1]);
                        winnerList.add(players[3]);
                        sendTeam(data, winnerList);
                    }

                    // Both teams control the same number of professors, so the game ends in a draw
                    case EQ -> sendDraw(data, Arrays.stream(players).toList());
                }
            }
        }
    }

    /**
     * Function to decide the winner in the case of a two or three players game
     * @param data The controller data's instance
     * @param players An array containing the players
     */
    private void twoThreePlayerWinner(ControllerData data, Player[] players) {
        // Start by assuming that the first player is the winner
        // We will check whether it's true by comparing it with the second (and third, if it exists)
        winner = players[0];

        // Also assume that the game hasn't ended in a draw, as it has been assumed that the first player has won it
        boolean draw = false;

        // Iterate on the remaining player(s)
        for (int i = 1; i < players.length; ++i)
            draw = winSwitch(
                players[i],
                // If it is currently a draw, the winner is a generic element of the list, as they all share the same values
                draw ? winnerList.get(0) : winner,
                data.getGameModel().getGlobalProfessorTable(),
                draw);

        if (draw)
            sendDraw(data, winnerList);

        else
            sendWinner(data, winner);
    }

    /**
     * Compare the provided player with the current winner, modifying the state accordingly
     * @return <code>true</code> if the game is now a draw, <code>false</code> otherwise
     */
    private boolean winSwitch(Player player, Player curWinner, GlobalProfessorTable gpt, boolean draw) {
        // Compare the tower counts of the current player and the current winner and return a bool representing the draw's state
        return switch (compare(player.getSchoolBoard().getTowerCount(), curWinner.getSchoolBoard().getTowerCount())) {
            // If the player has fewer towers, he is the new winner
            case LT -> {
                winner = player;
                yield false;
            }

            // If both have the same number of towers, compare the number of professors controlled
            case EQ ->
                switch (compareProfessors(gpt, player, curWinner)) {
                    // If players[i] controls more professors, he is the new winner
                    case GT -> {
                        winner = player;
                        yield false;
                    }

                    // If they both control the same number of professors, the game's a draw, so add the player to the list of "drawers"
                    case EQ -> {
                        // If the game wasn't a draw I need to add the previous winner to the list
                        if (!draw)
                            winnerList.add(winner);

                        winnerList.add(player);

                        yield true;
                    }

                    // If the player controls fewer professors, the results are unchanged
                    case LT -> draw;
                };

            // If the player has more towers, the results are unchanged
            case GT -> draw;
        };
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

            if (professorLocation.equals(players[0]) || professorLocation.equals(players[2]))
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
        int resA = 0,
            resB = 0;

        for (Color color : Color.values())
            if (gpt.getProfessorLocation(color).equals(a))
                resA++;
            else if (gpt.getProfessorLocation(color).equals(b))
                resB++;

        return compare(resA, resB);
    }

    /**
     * Send the winner to every player
     */
    private void sendWinner(ControllerData data, Player winner) {
        data.sendMessageToPlayers(new GameCommandEndGame(winner));
    }

    /**
     * Send the winning team to every player
     */
    private void sendTeam(ControllerData data, List<Player> team) {
        data.sendMessageToPlayers(new GameCommandEndGame(team, false));
    }

    /**
     * Send the result of the draw to every player
     */
    private void sendDraw(ControllerData data, List<Player> players) {
        data.sendMessageToPlayers(new GameCommandEndGame(players, true));
    }
}
