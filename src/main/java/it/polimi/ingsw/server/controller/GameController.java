package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.controller.state.GameState;
import it.polimi.ingsw.server.controller.state.GameStateThread;
import it.polimi.ingsw.server.virtualView.VirtualView;
import it.polimi.ingsw.common.viewRecord.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main server class
 * @author Mattia Martelli
 */
public class GameController {
    // Priority queue used to decide the initial order
    private static PriorityQueue<UsernameAndMagicAge> playerAgeQueue;

    private static ControllerData  data;

    private static boolean         activeGame;
    private static boolean         rulesSet;

    private static GameStateThread gameStateThread;

    /**
     * Start the server with the default port
     */
    public static void main() {
        main(6556);
    }

    /**
     * Start the server with a specified port
     * @param port The port the server will listen to
     */
    public static void main(int port) {
        // Check whether the current file position allows read and write
        // If it's not possible then the program cannot save or read saves
        if (!GameSave.checkFolderPermissions()) {
            System.out.println("Current folder is read only. Please move the executable or check the permissions.");
            return;
        }

        data       = ControllerData.getInstance();
        activeGame = false;
        rulesSet   = false;

        playerAgeQueue = new PriorityQueue<>(Comparator.comparingInt(UsernameAndMagicAge::magicAge).reversed());

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        }

        // If the socket cannot be opened there's no point in going forward
        catch (IOException ignored) {
            System.out.println("Error opening the socket. Please check your network configuration.");
            return;
        }

        while (true) {
            VirtualView view;

            try {
                view = new VirtualView(serverSocket.accept());
            }

            // If new connections cannot be accepted then there must be a network configuration error
            catch (IOException ignored) {
                System.out.println("Error whilst accepting connections. Please check your network configuration.");
                return;
            }

            // If a game is already in progress, notify the player and then close its connection
            if (activeGame) {
                view.sendMessage(new GameCommandGameProgress());
                view.close();
            }

            else
                try {
                    UsernameAndMagicAge usernameAndMagicAge;

                    if (rulesSet) {
                        Set<String> usernameSet =
                            // Morph the array of players to a stream to ease operations
                            Arrays.stream(data.getPlayersOrder())
                                // Morph the stream into a stream containing the usernames
                                .map(p -> p == null ? "" : p.getUsername())
                                // Normalize the usernames to avoid being case-sensitive
                                .map(String::toLowerCase)
                                // Collect the results into a set
                                .collect(Collectors.toSet());

                        // Send the player a request for the username, signaling which ones were already picked
                        usernameAndMagicAge = (UsernameAndMagicAge) view.sendRequest(new GameCommandRequestAction(GameActions.USERNAMEANDMAGICAGE, usernameSet)).executeCommand();
                    }

                    else {

                        // Send the player a request for the username, signaling which ones were already picked
                        usernameAndMagicAge = (UsernameAndMagicAge) view.sendRequest(new GameCommandRequestAction(GameActions.USERNAMEANDMAGICAGE, new HashSet<>())).executeCommand();
                    }

                    if (!rulesSet) {
                        Optional<File> savedGame = GameSave.findSavedGame(usernameAndMagicAge.username());

                        if (savedGame.isPresent()) {
                            File save = savedGame.get();

                            // Ask the player whether he wants to load the saved game
                            if ((Boolean) view.sendRequest(new GameCommandRequestAction(GameActions.LOADGAME, null)).executeCommand()) {
                                try {
                                    GameSave.loadGame(save);
                                    save.delete();
                                }

                                // Should never happen, since the permissions have already been checked
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Ask the new rules
                            // Returns false if there was a connection error: in that case do not delete the save and go to the next iteration
                            else if (!askRules(view))
                                continue;

                            // The game has not been loaded, but new rules have been, so delete the old save
                            else
                                save.delete();
                        }

                        // Ask the new rules
                        // Returns false if there was a connection error: in that case do not delete the save and go to the next loop
                        else if (!askRules(view))
                            continue;

                        rulesSet = true;
                    }

                    if (addPlayer(view, usernameAndMagicAge.username())) {
                        List<Player> players = new ArrayList<>(List.of(data.getPlayersOrder()));

                        // Morph the playerAgeQueue into an array of players, by finding the corresponding player for each username
                        data.setPlayersOrder(playerAgeQueue.stream().map(uM -> {
                            for (Player player : players)
                                if (player.getUsername().equals(uM.username())) {
                                    players.remove(player);
                                    return player;
                                }

                            // Should never be reached, as it would mean that the data has been wrongly created
                            throw new IllegalStateException();
                        }).toArray(Player[]::new));

                        playerAgeQueue.clear();
                        activeGame = true;
                        data.getPlayerViewMap().forEach((p, v) -> v.sendMessage(new GameCommandGameStart()));
                        (gameStateThread = new GameStateThread()).start();
                    }
                }

                // If there's a SocketException whilst trying to add the player it means that it's no longer available
                // In this case, just go on normally, since the model hasn't been modified yet
                catch (SocketException ignored) {}
        }
    }

    /**
     * Signal that a player has been disconnected
     * @param playerView The VirtualView associated with the player
     */
    public static synchronized void signalPlayerDisconnected(VirtualView playerView) {
        // If a game is in progress it needs to be saved
        if (activeGame) {
            Player[]      players  = data.getGameModel().getPlayer();
            StringBuilder fileName = new StringBuilder(players[0].getUsername());
            for (int i = 1; i < players.length; i++)
                fileName.append("-").append(players[i].getUsername());

            try {
                GameSave.saveGame(fileName.toString()); //TODO: check correctness of the DFA's interruption
            }

            //Should never happen as permissions have already been checked
            catch (IOException e) {
                e.printStackTrace();
            }

            gameStateThread.interrupt();

            // Signal every player that the game has been ended and close the relative socket
            for (Player player : players) {
                VirtualView view = data.getPlayerView(player);
                if (view != playerView)
                    view.sendMessage(new GameCommandInterruptGame());

                view.close();
            }

            // Reset the data
            ControllerData.nukeInstance();
            data       = ControllerData.getInstance();
            activeGame = false;
            rulesSet   = false;
        }

        else {
            if (data.getPlayersOrder(0).equals(data.getViewPlayer(playerView))) {
                // Signal every player that the game has been ended and close the relative socket
                for (Player player : data.getGameModel().getPlayer()) {
                    VirtualView view = data.getPlayerView(player);
                    if (view != playerView)
                        view.sendMessage(new GameCommandInterruptGame());

                    view.close();
                }

                // Reset the data
                ControllerData.nukeInstance();
                data     = ControllerData.getInstance();
                rulesSet = false;
            }

            else {
                Player player = data.getPlayerViewMap().removeRight(playerView);

                // Get the removed player's index
                int playerIndex = 0;
                for (int i = 0; i < data.getNumOfPlayers(); ++i)
                    if (data.getPlayersOrder(i).equals(player)) {
                        playerIndex = i;
                        break;
                    }

                // Move all the other players upwards
                for (int i = playerIndex; i < data.getNumOfPlayers() - 1; ++i)
                    data.getPlayersOrder()[i] = data.getPlayersOrder(i + 1);

                data.getPlayersOrder()[data.getNumOfPlayers() - 1] = null;

                playerAgeQueue.removeIf(uM -> uM.username().equals(player.getUsername()));
            }
        }
    }

    /**
     * Signal to the game's controller that the game has ended
     */
    public static synchronized void signalEndGame() {
        activeGame = false;
        ControllerData.nukeInstance();
        data = ControllerData.getInstance();
    }

    // Returns true if the correct number of players has been reached

    /**
     * Add a player to the controller's data, linking it with its virtual view
     * @param view The player's virtual view
     * @param username The player's username
     * @return <code>true</code> if the player was the last needed for the game to start, <code>false</code> otherwise
     * @throws SocketException If the player gets disconnected whilst adding it
     */
    private static boolean addPlayer(VirtualView view, String username) throws SocketException {
        Player[] players = data.getPlayersOrder();
        int numOfPlayers = data.getNumOfPlayers();

        // Get a set of all the wizards and remove the ones that were already picked
        Set<Wizard> wizardSet = Wizard.set();
        Arrays.stream(players).forEach(player -> wizardSet.remove(player.getPlayerWizard()));

        // If there is one wizard left, the player gets that by default, otherwise they get to choose between the ones remaining
        Wizard wizard = (Wizard)
            (wizardSet.size() == 1 ?
                wizardSet.toArray()[0] :
                view.sendRequest(new GameCommandRequestAction(GameActions.WIZARD, wizardSet.toArray())).executeCommand());

        boolean teamMode = numOfPlayers == 4;

        for (int i = 0; i < numOfPlayers; ++i)
            if (players[i] == null) {
                players[i] =
                    buildPlayer(
                        data.getExpertMode(),
                        teamMode,
                        i,
                        username,
                        wizard,
                        // If there are three players, each one gets 6 towers, else if there are two players, each one takes 8 towers,
                        // otherwise, there are four players and the first member of each team gets 8 towers, whilst its teammate gets 0
                        numOfPlayers == 3 ? 6 : numOfPlayers == 2 || i <= 1 ? 8 : 0,
                        // The entrance's size is 7 for two or four players games, 9 otherwise
                        numOfPlayers == 3 ? 9 : 7);

                // Link the current player with its team member if both exist
                if (teamMode && i > 1)
                    linkTeams(players, i);

                // Add the newly created player to the isomorphism (Player, View)
                data.getPlayerViewMap().put(players[i], view);

                return i + 1 == numOfPlayers;
            }

        // Should never be reached
        throw new IllegalStateException();
    }

    /**
     * Build a player according to the flags and values passed
     */
    private static Player buildPlayer(boolean expertMode, boolean teamMode, int id, String username, Wizard wizard, int towerCount, int entranceSize) {
        if (!expertMode && !teamMode) return new Player          (id, username, wizard, new SchoolBoard(TowerColor.fromInt(id),          towerCount, new Entrance(entranceSize), new DiningRoom()));
        if (!expertMode /*teamMode*/) return new PlayerTeam      (id, username, wizard, new SchoolBoard(TowerColor.fromInt(id % 2), towerCount, new Entrance(entranceSize), new DiningRoom()));
        if (/*expertMode*/ !teamMode) return new PlayerExpert    (id, username, wizard, new SchoolBoard(TowerColor.fromInt(id),          towerCount, new Entrance(entranceSize), new DiningRoom()));
        /*otherwise*/                 return new PlayerTeamExpert(id, username, wizard, new SchoolBoard(TowerColor.fromInt(id % 2), towerCount, new Entrance(entranceSize), new DiningRoom()));
    }

    /**
     * Method that links the players in a team
     * @param players The array of all players
     * @param index The index of the second member of the team
     */
    private static void linkTeams(Player[] players, int index) {
        if (players[index] instanceof PlayerTeamExpert p) {
            PlayerTeamExpert teamMember = (PlayerTeamExpert) players[index - 2];
            p.setTeamMember(teamMember.getPlayerID());
            teamMember.setTeamMember(p.getPlayerID());
        }

        else if (players[index] instanceof PlayerTeam p) {
            PlayerTeam teamMember = (PlayerTeam) players[index - 2];
            p.setTeamMember(teamMember.getPlayerID());
            teamMember.setTeamMember(p.getPlayerID());
        }

        else
            // Should never be reached, as it would mean the players were not correctly created
            throw new IllegalStateException();
    }

    /**
     * Ask and set the rules of the game
     * @param view The virtual view of the player deciding the rules
     * @return <code>true</code> if the rules were set correctly, <code>false</code> otherwise
     */
    private static boolean askRules(VirtualView view) {
        try {
            GameRules rules = (GameRules) view.sendRequest(new GameCommandRequestAction(GameActions.RULES, null)).executeCommand();

            data.setNumOfPlayers(rules.numOfPlayers());
            data.setPlayersOrder(new Player[rules.numOfPlayers()]);
            if (rules.expertMode())
                data.setExpertMode();

            return true;
        }

        catch (SocketException ignored) {
            return false;
        }
    }

    //TODO
    public static Object requestValue(GameValues value) {
        return switch (value) {
            case MOTHERNATURE -> ControllerData.getInstance().getGameModel().getMotherNaturePosition();
            default           -> throw new IllegalArgumentException();
        };
    }

    public static GameState saveGameState() {
        return gameStateThread.saveGameState();
    }
}
