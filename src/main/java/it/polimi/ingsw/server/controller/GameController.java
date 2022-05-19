package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.server.controller.command.*;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.controller.state.GameStateThread;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.virtualView.VirtualView;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Optional;

/**
 * Main server class
 * @author Mattia Martelli
 */
public class GameController {
    private static ControllerData  data;

    private static boolean         activeGame;
    private static boolean         rulesSet;

    private static GameStateThread gameStateThread;

    /**
     * The server executable's entry point
     */
    public static void main(String[] args) {
        // Check whether the current file position allows read and write
        // If it's not possibile then the program cannot save or read saves
        if (!GameSave.checkFolderPermissions()) {
            System.out.println("Current folder is read only. Please move the executable or check the permissions.");
            return;
        }

        data       = ControllerData.getInstance();
        activeGame = false;
        rulesSet   = false;

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(6554);
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

            // If a game is already in progess, notify the player and then close its connection
            if (activeGame) {
                view.sendMessage(new GameCommandGameProgress());
                view.close();
            }

            else
                try {
                    String username  = (String) view.sendRequest(new GameCommandRequestValueClient(GameCommandValues.USERNAME)).executeCommand();

                    if (!rulesSet) {
                        Optional<File> savedGame = GameSave.findSavedGame(username);

                        if (savedGame.isPresent()) {
                            File save = savedGame.get();

                            // Ask the player whether he wants to load the saved game
                            if ((boolean) view.sendRequest(new GameCommandRequestAction(GameCommandActions.LOADGAME, null)).executeCommand())
                                GameSave.loadGame(save);

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

                    if (addPlayer(view, username)) {
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

    public static void signalPlayerDisconnected(VirtualView player) {
        //TODO: salvare il gioco interrompendo il DFA
        GameSave.saveGame(/*TODO*/ null);
        gameStateThread.interrupt();
        //TODO: mandare a tutti il messaggio di fine partita
    }

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

        Wizard  wizard   = (Wizard) view.sendRequest(new GameCommandRequestValueClient(GameCommandValues.WIZARD)).executeCommand();
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
            p.setTeamMember(teamMember);
            teamMember.setTeamMember(p);
        }

        else if (players[index] instanceof PlayerTeam p) {
            PlayerTeam teamMember = (PlayerTeam) players[index - 2];
            p.setTeamMember(teamMember);
            teamMember.setTeamMember(p);
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
            GameRules rules = (GameRules) view.sendRequest(new GameCommandRequestValueClient(GameCommandValues.RULES)).executeCommand();

            data.setNumOfPlayers(rules.numOfPlayers());
            if (rules.expertMode())
                data.setExpertMode();

            return true;
        }

        catch (SocketException ignored) {
            return false;
        }
    }

    //TODO
    public static Object requestValue(GameCommandValues value) {
        return switch (value) {
            case MOTHERNATURE -> ControllerData.getInstance().getGameModel().getMotherNaturePosition();
            default           -> throw new IllegalArgumentException();
        };
    }
}
