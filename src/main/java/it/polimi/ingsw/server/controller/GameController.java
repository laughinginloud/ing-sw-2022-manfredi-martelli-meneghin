package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.utils.SortedList;
import it.polimi.ingsw.common.utils.Tuple;
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

/**
 * Main server class
 * @author Mattia Martelli
 */
public /*static*/ final class GameController {

    // Hid the constructor to emulate the class being static
    private GameController() {}

    // region Fields

    /**
     * Sorted list used to decide the initial order, which
     * will contain each player and its magic age
     */
    private static SortedList<Tuple<Player, Integer>> playerAgeList;

    /**
     * The current players' usernames, which cannot be chosen by the others
     */
    private static Set<String>                        forbiddenNames;

    private static ControllerData  data;
    private static boolean         activeGame;
    private static boolean         rulesSet;
    private static boolean         loadedGame;

    /**
     * The number of players currently in the waiting room
     */
    private static int             playersNum;

    /**
     * The DFA thread
     */
    private static GameStateThread gameStateThread;

    /**
     * The initial state of the DFA
     */
    private static GameState       startState;

    // endregion

    // region Mains

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
    @SuppressWarnings({"UnnecessaryLabelOnContinueStatement", "ResultOfMethodCallIgnored"})
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
        forbiddenNames = new HashSet<>();

        // The list is set to by sorted by the integer of the tuple, from biggest to smallest
        playerAgeList  = new SortedList<>(Comparator.comparingInt(Tuple<Player, Integer>::right).reversed());

        File savedGame = null;

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        }

        // If the socket cannot be opened there's no point in going forward
        catch (IOException ignored) {
            System.out.println("Error opening the socket. Please check your network configuration.");
            return;
        }

        System.out.println("Server started on port " + port);

        MAIN:
        while (true) {
            VirtualView view;

            // Create the network handler
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
                    // Request the username and the magic age
                    UsernameAndMagicAge usernameAndMagicAge = (UsernameAndMagicAge)
                        view.sendRequest(new GameCommandRequestAction(GameActions.USERNAMEANDMAGICAGE, forbiddenNames)).executeCommand();

                    // If the first player chose to load a saved game
                    if (rulesSet && loadedGame) {
                        // If the loaded game does not contain the current player, send a message saying that a game is in progress
                        if (savedGame != null && !savedGame.getName().contains(usernameAndMagicAge.username())) {
                            view.sendMessage(new GameCommandGameProgress());
                            view.close();

                            continue MAIN;
                        }

                        // If the current player does not want to load a save, send a message saying that a game is in progress
                        if (!((Boolean) view.sendRequest(new GameCommandRequestAction(GameActions.LOADGAME, null)).executeCommand())) {
                            view.sendMessage(new GameCommandGameProgress());
                            view.close();

                            continue MAIN;
                        }
                    }

                    else if (!rulesSet) {
                        // If no rules have been set yet, search for a saved game
                        Optional<File> optSavedGame = GameSave.findSavedGame(usernameAndMagicAge.username());

                        if (optSavedGame.isPresent()) {
                            // If a save was found, extract the file from the optional
                            savedGame = optSavedGame.get();

                            // Ask the player whether he wants to load the saved game
                            if ((Boolean) view.sendRequest(new GameCommandRequestAction(GameActions.LOADGAME, null)).executeCommand()) {
                                loadedGame = true;
                                GameSave.loadGame(savedGame);
                            }

                            // Ask the new rules
                            // Returns false if there was a connection error: in that case do not delete the save and go to the next iteration
                            else if (!askRules(view))
                                continue MAIN;

                            // The game has not been loaded, but new rules have been, so delete the old save
                            else
                                savedGame.delete();
                        }

                        // Ask the new rules
                        // Returns false if there was a connection error: in that case do not delete the save and go to the next iteration
                        else if (!askRules(view))
                            continue MAIN;

                        rulesSet = true;
                    }

                    // Add the player to the game and check with the return value if it was the last needed for the game to start
                    if (addPlayer(view, usernameAndMagicAge)) {
                        // If the game needs to start and a save wasn't loaded, decide the players' order by extracting the one in the sorted list
                        if (!loadedGame)
                            data.setPlayersOrder(playerAgeList.stream().map(Tuple::left).toArray(Player[]::new));

                        // Reset the flags and temporary data, set the active game flag and signal the players that the game is starting
                        savedGame = null;
                        playerAgeList.clear();
                        forbiddenNames.clear();
                        activeGame = true;
                        data.sendMessageToPlayers(new GameCommandGameStart());

                        // If a save was loaded, send the past state of the data
                        if (loadedGame) {
                            InfoMap map = new InfoMap(GameValues.MODEL, data.getGameModel());

                            // If the game loaded is in expert mode, also send the character card array
                            if (data.getExpertMode())
                                map.put(GameValues.CHARACTERCARDARRAY, data.getGameModel().getCharacterCards());

                            data.sendMessageToPlayers(new GameCommandSendInfo(map));
                        }

                        // Start the DFA and reset the starting state
                        (gameStateThread = new GameStateThread(startState, mkFileName(data.getPlayersOrder()))).start();
                        startState = new GameStateModelInitialization();
                    }
                }

                // If there's a SocketException whilst trying to add the player it means that it's no longer available
                // In this case, just go on normally, since the model hasn't been modified yet
                catch (SocketException ignored) {}

                // Should never happen as permissions have been checked at the very beginning
                catch (IOException e) {
                    System.out.println("There was an error accessing the folder. Please move the executable or check the permissions.");
                    return;
                }
        }
    }

    // endregion

    // region Utility methods

    /**
     * Create the file name that will be used
     * @param players The array of players the name will be based on
     * @return The file name
     */
    private static String mkFileName(Player[] players) {
        return Arrays.stream(players)
            .map(Player::getUsername)
            .map(String::toLowerCase)
            .sorted()
            .collect(Collectors.joining("-"));
    }

    /**
     * Signal that a player has been disconnected
     * @param playerView The VirtualView associated with the player
     */
    @SuppressWarnings("DuplicatedCode")
    public static synchronized void signalPlayerDisconnected(VirtualView playerView) {
        if (data.getPlayersOrder() == null)
            return;

        // If a game was active, terminate the DFA and reset the data
        if (activeGame) {
            gameStateThread.close();

            // Reset the data
            data = ControllerData.nukeInstance();
            activeGame = false;
            rulesSet = false;
            loadedGame = false;
            playersNum = 0;
            forbiddenNames.clear();
        }

        // If a game wasn't active but was loaded, disconnet everyone and reset the data
        else if (loadedGame) {
            for (Player player : data.getPlayersOrder())
                if (player != null) {
                    VirtualView view = data.getPlayerView(player);

                    if (view == null)
                        continue;

                    if (!view.equals(playerView))
                        view.sendInterrupt();
                }

            // Reset the data
            data = ControllerData.nukeInstance();
            rulesSet = false;
            loadedGame = false;
            playersNum = 0;
            playerAgeList.clear();
            startState = new GameStateModelInitialization();
            forbiddenNames.clear();
        }

        // If the game hadn't stared and the disconnected player was the one that set the rules,
        // disconnect everyone and reset the data
        else if (data.getPlayersOrder(0).equals(data.getViewPlayer(playerView))) {
            // Signal every player that the game has been ended and close the relative socket
            for (Player player : data.getPlayersOrder())
                ifNotNull(player, () -> {
                    VirtualView view = data.getPlayerView(player);
                    if (!view.equals(playerView))
                        view.sendInterrupt();
                });

            // Reset the data
            data = ControllerData.nukeInstance();
            rulesSet = false;
            loadedGame = false;
            playersNum = 0;
            playerAgeList.clear();
            startState = new GameStateModelInitialization();
            forbiddenNames.clear();
        }

        // Otherwise, a game hadn't started and the disconnected player wasn't the rules' setter,
        // so just remove it from the waiting room
        else {
            Player player = data.removePlayer(playerView);

            ifNotNull(player, () -> {
                data.getPlayersOrder()[player.getPlayerID()] = null;
                playerAgeList.removeIf(t -> t.left().getUsername().equalsIgnoreCase(player.getUsername()));
                forbiddenNames.remove(player.getUsername().toLowerCase());
                playersNum--;
            });
        }
    }

    /**
     * Signal to the game's controller that the game has ended
     */
    public static synchronized void signalEndGame() {
        activeGame = false;
        loadedGame = false;

        data = ControllerData.nukeInstance();
    }

    /**
     * Add a player to the controller's data, linking it with its virtual view
     * @param view                The player's virtual view
     * @param usernameAndMagicAge The player's username and magic age
     * @return <code>true</code> if the player was the last needed for the game to start, <code>false</code> otherwise
     * @throws SocketException If the player gets disconnected whilst adding it
     */
    private static boolean addPlayer(VirtualView view, UsernameAndMagicAge usernameAndMagicAge) throws SocketException {
        Player[] players          = data.getPlayersOrder();
        int      gameNumOfPlayers = data.getNumOfPlayers();

        forbiddenNames.add(usernameAndMagicAge.username().toLowerCase());

        // If a save was loaded, link the player with the data
        if (loadedGame) {
            data.addViewPlayer(Arrays.stream(players)
                .reduce((p1, p2) -> p1.getUsername().equalsIgnoreCase(usernameAndMagicAge.username()) ? p1 : p2).orElseThrow(),
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

        // Find the first free spot in the waiting room and put the player in it
        for (int i = 0; i < players.length; ++i)
            if (players[i] == null) {
                players[i] =
                    buildPlayer(
                        data.getExpertMode(),
                        teamMode,
                        i,
                        usernameAndMagicAge.username(),
                        wizard,
                        // If there are three players, each one gets 6 towers, else if there are two players, each one takes 8 towers,
                        // otherwise, there are four players and the first member of each team gets 8 towers, whilst its teammate gets 0
                        gameNumOfPlayers == 3 ? 6 : gameNumOfPlayers == 2 || i <= 1 ? 8 : 0,
                        // The entrance's size is 7 for two or four players games, 9 otherwise
                        gameNumOfPlayers == 3 ? 9 : 7);

                // Link the current player with its team member if both exist
                if (teamMode && i > 1)
                    linkTeams(players, i);

                // Add the newly created player to the isomorphism (Player, View)
                data.addViewPlayer(players[i], view);

                playerAgeList.add(new Tuple<>(players[i], usernameAndMagicAge.magicAge()));

                break;
            }

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
     * @param index   The index of the second member of the team
     */
    private static void linkTeams(Player[] players, int index) {
        if (players[index] instanceof PlayerTeamExpert p) {
            PlayerTeamExpert teamMember = (PlayerTeamExpert) players[index - 2];
            p.setTeamMemberID(teamMember.getPlayerID());
            teamMember.setTeamMemberID(p.getPlayerID());
        }

        else if (players[index] instanceof PlayerTeam p) {
            PlayerTeam teamMember = (PlayerTeam) players[index - 2];
            p.setTeamMemberID(teamMember.getPlayerID());
            teamMember.setTeamMemberID(p.getPlayerID());
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
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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

    // endregion

}
