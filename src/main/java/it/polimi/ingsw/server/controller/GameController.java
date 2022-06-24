package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.controller.state.GameState;
import it.polimi.ingsw.server.controller.state.GameStateModelInitialization;
import it.polimi.ingsw.server.controller.state.GameStateThread;
import it.polimi.ingsw.server.virtualView.VirtualView;
import it.polimi.ingsw.common.viewRecord.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.common.utils.Methods.ifNotNull;

// TODO: controllare l'id nel caso di un player che si disconnette ed uno che si connette dopo

/**
 * Main server class
 * @author Mattia Martelli
 */
public class GameController {
    // Priority queue used to decide the initial order
    private static PriorityQueue<UsernameAndMagicAge> playerAgeQueue;
    private static Set<String>                        forbiddenNames;

    private static ControllerData  data;

    private static boolean         activeGame;
    private static boolean         rulesSet;
    private static boolean         loadedGame;

    private static int             playersNum;

    private static GameStateThread gameStateThread;

    private static GameState       startState;

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

        data           = ControllerData.getInstance();
        activeGame     = false;
        rulesSet       = false;
        loadedGame     = false;
        playersNum     = 0;
        startState     = new GameStateModelInitialization();
        playerAgeQueue = new PriorityQueue<>(Comparator.comparingInt(UsernameAndMagicAge::magicAge).reversed());
        forbiddenNames = new HashSet<>();

        File         savedGame      = null;

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        }

        // If the socket cannot be opened there's no point in going forward
        catch (IOException ignored) {
            System.out.println("Error opening the socket. Please check your network configuration.");
            return;
        }

        System.out.println("Server started");

        MAIN:
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
                    UsernameAndMagicAge usernameAndMagicAge = (UsernameAndMagicAge)
                        view.sendRequest(new GameCommandRequestAction(GameActions.USERNAMEANDMAGICAGE, forbiddenNames)).executeCommand();

                    if (rulesSet && loadedGame) {
                        if (!savedGame.getName().contains(usernameAndMagicAge.username())) {
                            view.sendMessage(new GameCommandGameProgress());
                            view.close();

                            continue MAIN;
                        }

                        // Ask the player whether he wants to load the saved game
                        if ((Boolean) view.sendRequest(new GameCommandRequestAction(GameActions.LOADGAME, null)).executeCommand())
                            loadedGame = true;

                        else {
                            view.sendMessage(new GameCommandGameProgress());
                            view.close();

                            continue MAIN;
                        }
                    }

                    else if (!rulesSet) {
                        Optional<File> optSavedGame = GameSave.findSavedGame(usernameAndMagicAge.username());

                        if (optSavedGame.isPresent()) {
                            savedGame = optSavedGame.get();

                            // Ask the player whether he wants to load the saved game
                            if ((Boolean) view.sendRequest(new GameCommandRequestAction(GameActions.LOADGAME, null)).executeCommand()) {
                                loadedGame = true;
                                GameSave.loadGame(savedGame);
                            }

                            else if (loadedGame) {
                                view.sendMessage(new GameCommandGameProgress());
                                view.close();
                            }

                            // Ask the new rules
                            // Returns false if there was a connection error: in that case do not delete the save and go to the next iteration
                            else if (!askRules(view))
                                continue;

                            // The game has not been loaded, but new rules have been, so delete the old save
                            else
                                savedGame.delete();
                        }

                        // Ask the new rules
                        // Returns false if there was a connection error: in that case do not delete the save and go to the next iteration
                        else if (!askRules(view))
                            continue;

                        rulesSet = true;
                    }

                    if (addPlayer(view, usernameAndMagicAge)) {
                        Player[] players = data.getPlayersOrder();

                        // Morph the playerAgeQueue into an array of players, by finding the corresponding player for each username
                        data.setPlayersOrder(playerAgeQueue.stream().map(uM -> {
                            for (Player player : players)
                                if (player.getUsername().equals(uM.username()))
                                    return player;

                            // Should never be reached, as it would mean that the data has been wrongly created
                            throw new IllegalStateException();
                        }).toArray(Player[]::new));

                        savedGame = null;
                        playerAgeQueue.clear();
                        activeGame = true;
                        data.sendMessageToPlayers(new GameCommandGameStart());

                        if (loadedGame)
                            data.sendMessageToPlayers(new GameCommandSendInfo(new InfoMap(GameValues.MODEL, data.getGameModel())));

                        (gameStateThread = new GameStateThread(startState, mkFileName(data.getPlayersOrder()))).start();
                        startState = new GameStateModelInitialization();
                    }
                }

                // If there's a SocketException whilst trying to add the player it means that it's no longer available
                // In this case, just go on normally, since the model hasn't been modified yet
                catch (SocketException ignored) {}

                // Should never happen as permissions have been checked at the very beginning
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    /**
     * Create the file name that will be used
     * @param players The array of players the name will be based on
     * @return The file name
     */
    private static String mkFileName(Player[] players) {
        StringBuilder sb = new StringBuilder(players[0].getUsername());

        for (int i = 1; i < players.length; i++)
            sb.append("-").append(players[i].getUsername());

        return sb.toString();
    }

    /**
     * Signal that a player has been disconnected
     * @param playerView The VirtualView associated with the player
     */
    public static synchronized void signalPlayerDisconnected(VirtualView playerView) {
        if (ControllerData.getInstance().getPlayersOrder() == null)
            return;

        if (activeGame) {
            try {
                gameStateThread.close();
                gameStateThread.join();
            }

            // Should never happen, since no one calls the interrupt method on the DFA
            // It's here to make the type system happy
            catch (InterruptedException ignored) {}

            finally {
                // Reset the data
                ControllerData.nukeInstance();
                data       = ControllerData.getInstance();
                activeGame = false;
                rulesSet   = false;
                loadedGame = false;
                playersNum = 0;
                forbiddenNames.clear();
            }
        }

        else {
            if (data.getPlayersOrder() == null)
                return;

            if (data.getPlayersOrder(0).equals(data.getViewPlayer(playerView))) {
                // Signal every player that the game has been ended and close the relative socket
                for (Player player : data.getPlayersOrder())
                    ifNotNull(player, () -> {
                        VirtualView view = data.getPlayerView(player);
                        if (!view.equals(playerView))
                            view.sendInterrupt();
                    });

                // Reset the data
                ControllerData.nukeInstance();
                data       = ControllerData.getInstance();
                rulesSet   = false;
                loadedGame = false;
                playersNum = 0;
                playerAgeQueue.clear();
                startState = new GameStateModelInitialization();
                forbiddenNames.clear();
            }

            else {
                Player player = data.removePlayer(playerView);

                ifNotNull(player, () -> {
                    // Get the removed player's index
                    int playerIndex = 0;
                    for (int i = 0; i < playersNum; ++i)
                        if (data.getPlayersOrder(i).equals(player)) {
                            playerIndex = i;
                            break;
                        }

                    // Move all the other players upwards
                    for (int i = playerIndex; i < data.getNumOfPlayers() - 1; ++i)
                        data.getPlayersOrder()[i] = data.getPlayersOrder(i + 1);

                    data.getPlayersOrder()[data.getNumOfPlayers() - 1] = null;

                    playerAgeQueue.removeIf(uM -> uM.username().equalsIgnoreCase(player.getUsername()));
                    forbiddenNames.remove(player.getUsername().toLowerCase());
                    playersNum--;
                });
            }
        }
    }

    /**
     * Signal to the game's controller that the game has ended
     */
    public static synchronized void signalEndGame() {
        activeGame = false;
        loadedGame = false;

        ControllerData.nukeInstance();
        data = ControllerData.getInstance();
    }

    // Returns true if the correct number of players has been reached

    /**
     * Add a player to the controller's data, linking it with its virtual view
     * @param view The player's virtual view
     * @param usernameAndMagicAge The player's username and magic age
     * @return <code>true</code> if the player was the last needed for the game to start, <code>false</code> otherwise
     * @throws SocketException If the player gets disconnected whilst adding it
     */
    private static boolean addPlayer(VirtualView view, UsernameAndMagicAge usernameAndMagicAge) throws SocketException {
        Player[] players          = data.getPlayersOrder();
        int      gameNumOfPlayers = data.getNumOfPlayers();

        playerAgeQueue.add(usernameAndMagicAge);
        forbiddenNames.add(usernameAndMagicAge.username().toLowerCase());

        if (loadedGame) {
            data.addViewPlayer(Arrays.stream(players)
                .reduce((p1, p2) -> p1.getUsername().equals(usernameAndMagicAge.username().toLowerCase()) ? p1 : p2).orElseThrow(),
                view);

            playersNum++;

            return playersNum == gameNumOfPlayers;
        }

        // Get a set of all the wizards and remove the ones that were already picked
        Set<Wizard> wizardSet = Wizard.set();
        Arrays.stream(players).forEach(player -> {
            if (player != null)
                wizardSet.remove(player.getPlayerWizard());
        });

        // If there is one wizard left, the player gets that by default, otherwise they get to choose between the ones remaining
        Wizard wizard = (Wizard)
            (wizardSet.size() == 1 ?
                wizardSet.toArray(Wizard[]::new)[0] :
                view.sendRequest(new GameCommandRequestAction(GameActions.WIZARD, wizardSet.toArray(Wizard[]::new))).executeCommand());

        boolean teamMode = data.getNumOfPlayers() == 4;

        players[playersNum] =
            buildPlayer(
                data.getExpertMode(),
                teamMode,
                playersNum,
                usernameAndMagicAge.username(),
                wizard,
                // If there are three players, each one gets 6 towers, else if there are two players, each one takes 8 towers,
                // otherwise, there are four players and the first member of each team gets 8 towers, whilst its teammate gets 0
                gameNumOfPlayers == 3 ? 6 : gameNumOfPlayers == 2 || playersNum <= 1 ? 8 : 0,
                // The entrance's size is 7 for two or four players games, 9 otherwise
                gameNumOfPlayers == 3 ? 9 : 7);

        // Link the current player with its team member if both exist
        if (teamMode && playersNum > 1)
            linkTeams(players, playersNum);

        // Add the newly created player to the isomorphism (Player, View)
        data.addViewPlayer(players[playersNum], view);

        playersNum++;

        return playersNum == gameNumOfPlayers;
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

    /**
     * Load a specific state as the entry point for the DFA
     * @param state The initial state
     */
    public static void loadGameState(GameState state) {
        startState = state;
    }
}
